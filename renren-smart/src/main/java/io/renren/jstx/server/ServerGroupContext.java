package io.renren.jstx.server;

import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.renren.jstx.core.Aio;
import io.renren.jstx.core.ChannelContext;
import io.renren.jstx.core.ChannelStat;
import io.renren.jstx.core.GroupContext;
import io.renren.jstx.core.ObjWithLock;
import io.renren.jstx.core.intf.AioHandler;
import io.renren.jstx.core.intf.AioListener;
import io.renren.jstx.core.intf.Packet;
import io.renren.jstx.core.stat.GroupStat;
import io.renren.jstx.core.threadpool.DefaultThreadFactory;
import io.renren.jstx.core.utils.SystemTimer;
import io.renren.jstx.server.intf.ServerAioHandler;
import io.renren.jstx.server.intf.ServerAioListener;

/**
 * The Class ServerGroupContext.
 *
 * @author tanyaowu
 */
public class ServerGroupContext<SessionContext, P extends Packet, R> extends GroupContext<SessionContext, P, R>
{
	static Logger log = LoggerFactory.getLogger(ServerGroupContext.class);

	/** The group executor. */
	private ThreadPoolExecutor groupExecutor = null;

	private AcceptCompletionHandler<SessionContext, P, R> acceptCompletionHandler = null;

	private ServerAioHandler<SessionContext, P, R> serverAioHandler = null;

	private ServerAioListener<SessionContext, P, R> serverAioListener = null;

	protected ServerGroupStat serverGroupStat = new ServerGroupStat();

	/** The accept executor. */
	//private ThreadPoolExecutor acceptExecutor = null;

	private Thread checkHeartbeatThread = null;
	
	

	/**
	 * 
	 * @param aioHandler
	 * @param aioListener
	 *
	 * @author: tanyaowu
	 * 2017年2月2日 下午1:40:29
	 *
	 */
	public ServerGroupContext(ServerAioHandler<SessionContext, P, R> aioHandler, ServerAioListener<SessionContext, P, R> aioListener)
	{
		this(aioHandler, aioListener, new ThreadPoolExecutor(CORE_POOL_SIZE, CORE_POOL_SIZE, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(),
				DefaultThreadFactory.getInstance("t-aio-server-group")));
	}

	/**
	 * 
	 * @param serverAioHandler
	 * @param serverAioListener
	 * @param groupExecutor
	 *
	 * @author: tanyaowu
	 * 2017年2月2日 下午1:40:11
	 *
	 */
	public ServerGroupContext(ServerAioHandler<SessionContext, P, R> serverAioHandler, ServerAioListener<SessionContext, P, R> serverAioListener, ThreadPoolExecutor groupExecutor)
	{
		super();
		this.groupExecutor = groupExecutor;
		this.acceptCompletionHandler = new AcceptCompletionHandler<>();
		this.setServerAioHandler(serverAioHandler);
		this.setServerAioListener(serverAioListener);

		//this.acceptExecutor = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), DefaultThreadFactory.getInstance("t-aio-server-accept"));

		checkHeartbeatThread = new Thread(new Runnable()
		{
			@SuppressWarnings("unused")
			@Override
			public void run()
			{
				long sleeptime = heartbeatTimeout;
				while (!isStopped())
				{
					long start = SystemTimer.currentTimeMillis();
					ObjWithLock<Set<ChannelContext<SessionContext, P, R>>> objWithLock = ServerGroupContext.this.getConnections().getSetWithLock();
					Set<ChannelContext<SessionContext, P, R>> set = null;
					ReadLock readLock = objWithLock.getLock().readLock();
					long start1 = 0;
					int count = 0;
					try
					{
						readLock.lock();
						start1 = SystemTimer.currentTimeMillis();
						set = objWithLock.getObj();
						
						for (ChannelContext<SessionContext, P, R> entry : set)
						{
							count++;
							ChannelContext<SessionContext, P, R> channelContext = entry;
							ChannelStat stat = channelContext.getStat();
							long timeLatestReceivedMsg = stat.getLatestTimeOfReceivedPacket();
							long timeLatestSentMsg = stat.getLatestTimeOfSentPacket();
							long compareTime = Math.max(timeLatestReceivedMsg, timeLatestSentMsg);
							long currtime = SystemTimer.currentTimeMillis();
							long interval = (currtime - compareTime);
							if (interval > heartbeatTimeout)
							{
								log.warn("{}, {} ms没有收发消息", channelContext, interval);
								Aio.remove(channelContext, interval + " ms没有收发消息");
							}
						}

						
					} catch (Throwable e)
					{
						log.error("", e);
					} finally{
						try{
							readLock.unlock();
							
							if (log.isErrorEnabled()){
								int groups = 0;
								ObjWithLock<Set<ChannelContext<SessionContext, P, R>>> objwithlock = ServerGroupContext.this.getGroups().clients("g");
								if (objwithlock != null){
									groups = objwithlock.getObj().size();
								}
								
							}
							
							//打印各集合信息
							if (log.isErrorEnabled()){							
							}
							
							if (log.isErrorEnabled()){
								long end = SystemTimer.currentTimeMillis();
								long iv1 = start1 - start;
								long iv = end - start1;
								System.out.println("检查心跳,共"+count+"个链接, 取锁耗时"+iv1+"ms, 心跳时间:"+heartbeatTimeout);
							}
							Thread.sleep(sleeptime);
						} catch (Exception e)
						{
							log.error("", e);
						}
					}
				}
			}
		}, "t-aio-timer-checkheartbeat-" + id);
		checkHeartbeatThread.setDaemon(true);
		checkHeartbeatThread.setPriority(Thread.MIN_PRIORITY);
		checkHeartbeatThread.start();
	}

	//	/**
	//	 * Instantiates a new server group context.
	//	 *
	//	 * @param ip the ip
	//	 * @param port the port
	//	 * @param aioHandler the aio handler
	//	 * @param decodeExecutor the decode executor
	//	 * @param closeExecutor the close executor
	//	 * @param handlerExecutorHighPrior the handler executor high prior
	//	 * @param handlerExecutorNormPrior the handler executor norm prior
	//	 * @param sendExecutorHighPrior the send executor high prior
	//	 * @param sendExecutorNormPrior the send executor norm prior
	//	 */
	//	public ServerGroupContext(String ip, int port, AioHandler<SessionContext, P, R> aioHandler, SynThreadPoolExecutor<SynRunnableIntf> decodeExecutor,
	//			SynThreadPoolExecutor<SynRunnableIntf> closeExecutor, SynThreadPoolExecutor<SynRunnableIntf> handlerExecutorHighPrior,
	//			SynThreadPoolExecutor<SynRunnableIntf> handlerExecutorNormPrior, SynThreadPoolExecutor<SynRunnableIntf> sendExecutorHighPrior,
	//			SynThreadPoolExecutor<SynRunnableIntf> sendExecutorNormPrior)
	//	{
	//		super(aioHandler, decodeExecutor, closeExecutor, handlerExecutorHighPrior, handlerExecutorNormPrior, sendExecutorHighPrior, sendExecutorNormPrior);
	//		this.ip = ip;
	//		this.port = port;
	//	}

	/**
	 * Gets the group executor.
	 *
	 * @return the groupExecutor
	 */
	public ThreadPoolExecutor getGroupExecutor()
	{
		return groupExecutor;
	}

	/**
	 * Sets the group executor.
	 *
	 * @param groupExecutor the groupExecutor to set
	 */
	public void setGroupExecutor(ThreadPoolExecutor groupExecutor)
	{
		this.groupExecutor = groupExecutor;
	}

	/**
	 * Gets the accept executor.
	 *
	 * @return the acceptExecutor
	 */
//	public ThreadPoolExecutor getAcceptExecutor()
//	{
//		return acceptExecutor;
//	}
//
//	/**
//	 * Sets the accept executor.
//	 *
//	 * @param acceptExecutor the acceptExecutor to set
//	 */
//	public void setAcceptExecutor(ThreadPoolExecutor acceptExecutor)
//	{
//		this.acceptExecutor = acceptExecutor;
//	}

	//	/**
	//	 * @return the serverGroupStat
	//	 */
	//	public ServerGroupStat getServerGroupStat()
	//	{
	//		return serverGroupStat;
	//	}

	//	/**
	//	 * @param serverGroupStat the serverGroupStat to set
	//	 */
	//	public void setServerGroupStat(ServerGroupStat serverGroupStat)
	//	{
	//		this.serverGroupStat = serverGroupStat;
	//	}

	public ServerGroupStat getServerGroupStat()
	{
		return serverGroupStat;
	}

	/**
	 * @return the acceptCompletionHandler
	 */
	public AcceptCompletionHandler<SessionContext, P, R> getAcceptCompletionHandler()
	{
		return acceptCompletionHandler;
	}

	/**
	 * @param acceptCompletionHandler the acceptCompletionHandler to set
	 */
	public void setAcceptCompletionHandler(AcceptCompletionHandler<SessionContext, P, R> acceptCompletionHandler)
	{
		this.acceptCompletionHandler = acceptCompletionHandler;
	}

	/**
	 * @return the serverAioHandler
	 */
	public ServerAioHandler<SessionContext, P, R> getServerAioHandler()
	{
		return serverAioHandler;
	}

	/**
	 * @param serverAioHandler the serverAioHandler to set
	 */
	public void setServerAioHandler(ServerAioHandler<SessionContext, P, R> serverAioHandler)
	{
		this.serverAioHandler = serverAioHandler;
	}

	/**
	 * @return the serverAioListener
	 */
	public ServerAioListener<SessionContext, P, R> getServerAioListener()
	{
		return serverAioListener;
	}

	/**
	 * @param serverAioListener the serverAioListener to set
	 */
	public void setServerAioListener(ServerAioListener<SessionContext, P, R> serverAioListener)
	{
		this.serverAioListener = serverAioListener;
		
		if (this.serverAioListener == null)
		{
			this.serverAioListener = new DefaultServerAioListener<SessionContext, P, R>();
		}
	}

	/** 
	 * @see io.renren.jstx.core.GroupContext#getAioHandler()
	 * 
	 * @return
	 * @author: tanyaowu
	 * 2016年12月20日 上午11:34:37
	 * 
	 */
	@Override
	public AioHandler<SessionContext, P, R> getAioHandler()
	{
		return this.getServerAioHandler();
	}

	/** 
	 * @see io.renren.jstx.core.GroupContext#getGroupStat()
	 * 
	 * @return
	 * @author: tanyaowu
	 * 2016年12月20日 上午11:34:37
	 * 
	 */
	@Override
	public GroupStat getGroupStat()
	{
		return this.getServerGroupStat();
	}

	/** 
	 * @see io.renren.jstx.core.GroupContext#getAioListener()
	 * 
	 * @return
	 * @author: tanyaowu
	 * 2016年12月20日 上午11:34:37
	 * 
	 */
	@Override
	public AioListener<SessionContext, P, R> getAioListener()
	{
		return getServerAioListener();
	}
}
