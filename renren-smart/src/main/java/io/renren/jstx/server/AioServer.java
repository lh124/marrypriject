package io.renren.jstx.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.renren.jstx.core.Node;
import io.renren.jstx.core.intf.Packet;
import io.renren.jstx.core.threadpool.SynThreadPoolExecutor;
import io.renren.jstx.core.threadpool.intf.SynRunnableIntf;

/**
 * 
 * @author tanyaowu 
 *
 */
public class AioServer<SessionContext, P extends Packet, R>
{
	private static Logger log = LoggerFactory.getLogger(AioServer.class);

	private ServerGroupContext<SessionContext, P, R> serverGroupContext;

	private AsynchronousServerSocketChannel serverSocketChannel;

	private Node serverNode;

	private boolean isWaitingStop = false;

	/**
	 * 
	 * @param serverGroupContext
	 *
	 * @author: tanyaowu
	 * 2017年1月2日 下午5:53:06
	 *
	 */
	public AioServer(ServerGroupContext<SessionContext, P, R> serverGroupContext)
	{
		super();
		this.serverGroupContext = serverGroupContext;
	}

	/**
	 * @return the serverGroupContext
	 */
	public ServerGroupContext<SessionContext, P, R> getServerGroupContext()
	{
		return serverGroupContext;
	}

	/**
	 * @return the serverSocketChannel
	 */
	public AsynchronousServerSocketChannel getServerSocketChannel()
	{
		return serverSocketChannel;
	}

	/**
	 * @param serverGroupContext the serverGroupContext to set
	 */
	public void setServerGroupContext(ServerGroupContext<SessionContext, P, R> serverGroupContext)
	{
		this.serverGroupContext = serverGroupContext;
	}

	public void start(String serverIp, int serverPort) throws IOException
	{
		this.serverNode = new Node(serverIp, serverPort);
		ExecutorService groupExecutor = serverGroupContext.getGroupExecutor();

		AsynchronousChannelGroup channelGroup = AsynchronousChannelGroup.withThreadPool(groupExecutor);
		serverSocketChannel = AsynchronousServerSocketChannel.open(channelGroup);

		serverSocketChannel.setOption(StandardSocketOptions.SO_REUSEADDR, true);
		serverSocketChannel.setOption(StandardSocketOptions.SO_RCVBUF, 64 * 1024);

		InetSocketAddress listenAddress = null;

		if (StringUtils.isBlank(serverIp))
		{
			listenAddress = new InetSocketAddress(serverPort);
		} else
		{
			listenAddress = new InetSocketAddress(serverIp, serverPort);
		}

		serverSocketChannel.bind(listenAddress, 0);

		AcceptCompletionHandler<SessionContext, P, R> acceptCompletionHandler = serverGroupContext.getAcceptCompletionHandler();
		serverSocketChannel.accept(this, acceptCompletionHandler);

		log.error("t-io server started, listen on {}", this.serverNode);
	}

	/**
	 * 
	 * @return
	 *
	 * @author: tanyaowu
	 * 2017年2月11日 上午8:04:04
	 *
	 */
	public boolean stop()
	{
		isWaitingStop = true;
		boolean ret = true;
		
		try
		{
			serverSocketChannel.close();
		} catch (IOException e1)
		{
			log.error(e1.toString(), e1);
		}
		
		ExecutorService groupExecutor = serverGroupContext.getGroupExecutor();
		SynThreadPoolExecutor<SynRunnableIntf> executor = serverGroupContext.getHandlerExecutorNormPrior();
		ThreadPoolExecutor closePoolExecutor = serverGroupContext.getClosePoolExecutor();
		
		groupExecutor.shutdown();
		executor.shutdown();
		closePoolExecutor.shutdown();
		
		serverGroupContext.setStopped(true);
		try
		{
			ret = ret && groupExecutor.awaitTermination(6000, TimeUnit.SECONDS);
		} catch (InterruptedException e)
		{
			log.error(e.getLocalizedMessage(), e);
		}
		
		try
		{
			ret = ret && executor.awaitTermination(6000, TimeUnit.SECONDS);
		} catch (InterruptedException e)
		{
			log.error(e.getLocalizedMessage(), e);
		}
		
		try
		{
			ret = ret && closePoolExecutor.awaitTermination(6000, TimeUnit.SECONDS);
		} catch (InterruptedException e)
		{
			log.error(e.getLocalizedMessage(), e);
		}
		
		System.out.println(this.serverNode + " stopped");
		log.info(this.serverNode + " stopped");
		return ret;
	}

	/**
	 * @return the serverNode
	 */
	public Node getServerNode()
	{
		return serverNode;
	}

	/**
	 * @return the isWaitingStop
	 */
	public boolean isWaitingStop()
	{
		return isWaitingStop;
	}

	/**
	 * @param isWaitingStop the isWaitingStop to set
	 */
	public void setWaitingStop(boolean isWaitingStop)
	{
		this.isWaitingStop = isWaitingStop;
	}

	//	/**
	//	 * @param serverNode the serverNode to set
	//	 */
	//	public void setServerNode(Node serverNode)
	//	{
	//		this.serverNode = serverNode;
	//	}
}
