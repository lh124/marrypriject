package io.renren.jstx.core;

import io.renren.jstx.client.intf.ClientChannelContext;
import io.renren.jstx.client.intf.ReconnConf;
import io.renren.jstx.core.intf.AioListener;
import io.renren.jstx.core.intf.Packet;
import io.renren.jstx.core.maintain.MaintainUtils;
import io.renren.jstx.core.utils.SystemTimer;

import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CloseRunnable<SessionContext, P extends Packet, R>  implements Runnable
{
	
	private static Logger log = LoggerFactory.getLogger(CloseRunnable.class);

	private ChannelContext<SessionContext, P, R> channelContext;
	private Throwable throwable;
	private String remark;
	private boolean isNeedRemove;
 
	/**
	 * 
	 *
	 * @author: tanyaowu
	 * 2017年3月1日 下午1:52:12
	 * 
	 */
	public CloseRunnable(ChannelContext<SessionContext, P, R> channelContext, Throwable throwable, String remark, boolean isNeedRemove)
	{
		this.channelContext = channelContext;
		this.throwable = throwable;
		this.remark = remark;
		this.isNeedRemove = isNeedRemove;
	}
	/** 
	 * @see java.lang.Runnable#run()
	 * 
	 * @author: tanyaowu
	 * 2017年3月1日 下午1:54:34
	 * 
	 */
	@Override
	public void run()
	{
		try
		{
			
			try
			{
				AsynchronousSocketChannel asynchronousSocketChannel = channelContext.getAsynchronousSocketChannel();
				if (asynchronousSocketChannel != null && asynchronousSocketChannel.isOpen())
				{
					try
					{
						asynchronousSocketChannel.close();
					} catch (Exception e)
					{
						log.error(e.toString(), e);
					}
				}
			} catch (Throwable e)
			{
				log.error(e.toString(), e);
			}
			
			boolean isClientChannelContext = channelContext instanceof ClientChannelContext;
//			ReconnConf<SessionContext, P, R> reconnConf = channelContext.getGroupContext().getReconnConf();
			boolean isRemove = this.isNeedRemove;
			if (!isRemove)
			{
//				if (reconnConf != null && reconnConf.getInterval() > 0)
//				{
//					if (reconnConf.getRetryCount() <= 0 || reconnConf.getRetryCount() >= channelContext.getReconnCount())
//					{
//						//需要重连，所以并不删除
//					} else
//					{
//						isRemove = true;
//					}
//				} else
//				{
//					isRemove = true;
//				}
				
				if (isClientChannelContext)
				{
					ClientChannelContext<SessionContext, P, R> clientChannelContext = (ClientChannelContext<SessionContext, P, R>) channelContext;

					if (!ReconnConf.isNeedReconn(clientChannelContext, false))
					{
						isRemove = true;
					}
				} else
				{
					isRemove = true;
				}
			}

			ReentrantReadWriteLock reentrantReadWriteLock = channelContext.getCloseLock();//.getLock();
			WriteLock writeLock = reentrantReadWriteLock.writeLock();
			boolean isLock = writeLock.tryLock();

			try
			{
				if (!isLock)
				{
					if (isRemove)
					{
						if (channelContext.isRemoved())
						{
							return;
						} else
						{
							writeLock.lock();
						}
					} else
					{
						return;
					}
				}

				if (channelContext.isClosed() && !isRemove)
				{
					return;
				}

				if (channelContext.isRemoved())
				{
					return;
				}

				//必须先取消任务再清空队列
//				channelContext.getDecodeRunnable().setCanceled(true);
				channelContext.getHandlerRunnableNormPrior().setCanceled(true);
				//		channelContext.getHandlerRunnableHighPrior().setCanceled(true);
				channelContext.getSendRunnableNormPrior().setCanceled(true);
				//		channelContext.getSendRunnableHighPrior().setCanceled(true);

				channelContext.getDecodeRunnable().clearMsgQueue();
				channelContext.getHandlerRunnableNormPrior().clearMsgQueue();
				//		channelContext.getHandlerRunnableHighPrior().clearMsgQueue();
				channelContext.getSendRunnableNormPrior().clearMsgQueue();
				//		channelContext.getSendRunnableHighPrior().clearMsgQueue();

				GroupContext<SessionContext, P, R> groupContext = channelContext.getGroupContext();
				AioListener<SessionContext, P, R> aioListener = groupContext.getAioListener();

				try
				{
					if (isRemove)
					{
						MaintainUtils.removeFromMaintain(channelContext);
					} else
					{
						groupContext.getCloseds().add(channelContext);
						groupContext.getConnecteds().remove(channelContext);
						
						if (StringUtils.isNotBlank(channelContext.getUserid()))
						{
							try
							{
								Aio.unbindUser(channelContext);
							} catch (Throwable e)
							{
								log.error(e.toString(), e);
							}
						}

						try
						{
							Aio.unbindGroup(channelContext);
						} catch (Throwable e)
						{
							log.error(e.toString(), e);
						}
					}

					try
					{
						channelContext.setClosed(true);
						channelContext.setRemoved(isRemove);
						channelContext.getGroupContext().getGroupStat().getClosed().incrementAndGet();
						channelContext.getStat().setTimeClosed(SystemTimer.currentTimeMillis());
					} catch (Exception e)
					{
						log.error(e.toString(), e);
					}

					try
					{
						aioListener.onAfterClose(channelContext, throwable, remark, isRemove);
					} catch (Throwable e)
					{
						log.error(e.toString(), e);
					}
				} catch (Throwable e)
				{
					log.error(e.toString(), e);
				} finally
				{
					if (!isRemove && channelContext.isClosed() && (isClientChannelContext)) //不删除且没有连接上，则加到重连队列中
					{
						ClientChannelContext<SessionContext, P, R> clientChannelContext = (ClientChannelContext<SessionContext, P, R>) channelContext;
						ReconnConf.put(clientChannelContext);
					}
				}

			} catch (Exception e)
			{
				log.error(throwable.toString(), e);
			} finally
			{
				writeLock.unlock();
			}
		} finally
		{
			channelContext.setWaitingClose(false);
		}
	}

}
