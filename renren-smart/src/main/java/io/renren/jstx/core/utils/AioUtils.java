package io.renren.jstx.core.utils;

import java.nio.channels.AsynchronousSocketChannel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.renren.jstx.core.Aio;
import io.renren.jstx.core.ChannelContext;
import io.renren.jstx.core.intf.Packet;
import io.renren.jstx.core.task.HandlerRunnable;
import io.renren.jstx.core.task.SendRunnable;
import io.renren.jstx.core.threadpool.SynThreadPoolExecutor;
import io.renren.jstx.core.threadpool.intf.SynRunnableIntf;

public class AioUtils
{
	private static Logger log = LoggerFactory.getLogger(AioUtils.class);

	public static <SessionContext, P extends Packet, R> boolean checkBeforeIO(ChannelContext<SessionContext, P, R> channelContext)
	{
		if (channelContext == null)
		{
			log.error("channelContext is null, {}", ThreadUtils.stackTrace());
			return false;
		}

		boolean isClosed = channelContext.isClosed();
		boolean isRemoved = channelContext.isRemoved();

		AsynchronousSocketChannel asynchronousSocketChannel = channelContext.getAsynchronousSocketChannel();
		Boolean isopen = null;
		if (asynchronousSocketChannel != null)
		{
			isopen = asynchronousSocketChannel.isOpen();

			if (isClosed || isRemoved)
			{
				if (isopen)
				{
					try
					{
						Aio.close(channelContext, "asynchronousSocketChannel is open, but channelContext isClosed: " + isClosed + ", isRemoved: " + isRemoved);
					} catch (Exception e)
					{
						log.error(e.toString(), e);
					}
				}
				return false;
			}
		} else
		{
					ThreadUtils.stackTrace();
			return false;
		}

		if (!isopen)
		{
			Aio.close(channelContext, "asynchronousSocketChannel is not open, 可能对方关闭了连接");
			return false;
		}
		return true;
	}

	public static <SessionContext, P extends Packet, R> SendRunnable<SessionContext, P, R> selectSendRunnable(ChannelContext<SessionContext, P, R> channelContext, Packet packet)
	{
//		byte priority = packet.getPriority();
//		if (priority == io.renren.jstx.core.intf.Packet.PRIORITY_HIGH)
//		{
//			return channelContext.getSendRunnableHighPrior();
//		} else
//		{
			return channelContext.getSendRunnableNormPrior();
//		}
	}

	public static <SessionContext, P extends Packet, R> SynThreadPoolExecutor<SynRunnableIntf> selectSendExecutor(ChannelContext<SessionContext, P, R> channelContext, Packet packet)
	{
//		byte priority = packet.getPriority();
//		if (priority == io.renren.jstx.core.intf.Packet.PRIORITY_HIGH)
//		{
//			return channelContext.getGroupContext().getSendExecutorHighPrior();
//		} else
//		{
			return channelContext.getGroupContext().getSendExecutorNormPrior();
//		}
	}

	public static <SessionContext, P extends Packet, R> HandlerRunnable<SessionContext, P, R> selectHandlerRunnable(ChannelContext<SessionContext, P, R> channelContext, Packet packet)
	{
//		byte priority = packet.getPriority();
//		if (priority == io.renren.jstx.core.intf.Packet.PRIORITY_HIGH)
//		{
//			return channelContext.getHandlerRunnableHighPrior();
//		} else
//		{
			return channelContext.getHandlerRunnableNormPrior();
//		}
	}

	public static <SessionContext, P extends Packet, R> SynThreadPoolExecutor<SynRunnableIntf> selectHandlerExecutor(ChannelContext<SessionContext, P, R> channelContext, Packet packet)
	{
//		byte priority = packet.getPriority();
//		if (priority == io.renren.jstx.core.intf.Packet.PRIORITY_HIGH)
//		{
//			return channelContext.getGroupContext().getHandlerExecutorHighPrior();
//		} else
//		{
			return channelContext.getGroupContext().getHandlerExecutorNormPrior();
//		}
	}

}
