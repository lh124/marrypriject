package io.renren.jstx.client.intf;

import io.renren.jstx.core.GroupContext;
import io.renren.jstx.core.intf.AioHandler;
import io.renren.jstx.core.intf.AioListener;
import io.renren.jstx.core.intf.Packet;
import io.renren.jstx.core.stat.GroupStat;
import io.renren.jstx.core.threadpool.DefaultThreadFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author tanyaowu 
 * 2017年4月1日 上午9:31:31
 */
public class ClientGroupContext<SessionContext, P extends Packet, R> extends GroupContext<SessionContext, P, R>
{
	static Logger log = LoggerFactory.getLogger(ClientGroupContext.class);

	/** The group executor. */
	private ExecutorService groupExecutor = null;

	private ClientAioHandler<SessionContext, P, R> clientAioHandler = null;

	private ClientAioListener<SessionContext, P, R> clientAioListener = null;

	private ClientGroupStat clientGroupStat = new ClientGroupStat();
	
	private ConnectionCompletionHandler<SessionContext, P, R> connectionCompletionHandler = new ConnectionCompletionHandler<>();

	/**
	 * 不重连
	 * @param aioHandler
	 * @param aioListener
	 *
	 * @author: tanyaowu
	 *
	 */
	public ClientGroupContext(ClientAioHandler<SessionContext, P, R> aioHandler, ClientAioListener<SessionContext, P, R> aioListener)
	{
		this(aioHandler, aioListener, null, new ThreadPoolExecutor(CORE_POOL_SIZE, CORE_POOL_SIZE, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(),
				DefaultThreadFactory.getInstance("t-aio-client-group")));
	}

	/**
	 * 
	 * @param aioHandler
	 * @param aioListener
	 * @param reconnConf
	 *
	 * @author: tanyaowu
	 *
	 */
	public ClientGroupContext(ClientAioHandler<SessionContext, P, R> aioHandler, ClientAioListener<SessionContext, P, R> aioListener, ReconnConf<SessionContext, P, R> reconnConf)
	{
		this(aioHandler, aioListener, reconnConf, new ThreadPoolExecutor(CORE_POOL_SIZE, CORE_POOL_SIZE, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(),
				DefaultThreadFactory.getInstance("t-aio-client-group")));
	}

	/**
	 * 
	 * @param aioHandler
	 * @param aioListener
	 * @param reconnConf 不需要重连就为空
	 * @param groupExecutor
	 *
	 * @author: tanyaowu
	 *
	 */
	public ClientGroupContext(ClientAioHandler<SessionContext, P, R> aioHandler, ClientAioListener<SessionContext, P, R> aioListener, ReconnConf<SessionContext, P, R> reconnConf, ExecutorService groupExecutor)
	{
		super();

		this.setClientAioHandler(aioHandler);
		this.setClientAioListener(aioListener);

		this.reconnConf = reconnConf;
		this.groupExecutor = groupExecutor;
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
	//	public ClientGroupContext(String ip, int port, AioHandler<SessionContext, P, R> aioHandler,
	//			SynThreadPoolExecutor<SynRunnableIntf> decodeExecutor,SynThreadPoolExecutor<SynRunnableIntf> closeExecutor, SynThreadPoolExecutor<SynRunnableIntf> handlerExecutorHighPrior,
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
	public ExecutorService getGroupExecutor()
	{
		return groupExecutor;
	}

	/**
	 * Sets the group executor.
	 *
	 * @param groupExecutor the groupExecutor to set
	 */
	public void setGroupExecutor(ExecutorService groupExecutor)
	{
		this.groupExecutor = groupExecutor;
	}

	/**
	 * @param clientGroupStat the clientGroupStat to set
	 */
	public void setClientGroupStat(ClientGroupStat clientGroupStat)
	{
		this.clientGroupStat = clientGroupStat;
	}

	public ClientGroupStat getClientGroupStat()
	{
		return clientGroupStat;
	}

	/**
	 * @return the clientAioHandler
	 */
	public ClientAioHandler<SessionContext, P, R> getClientAioHandler()
	{
		return clientAioHandler;
	}

	/**
	 * @param clientAioHandler the clientAioHandler to set
	 */
	public void setClientAioHandler(ClientAioHandler<SessionContext, P, R> clientAioHandler)
	{
		this.clientAioHandler = clientAioHandler;
	}

	/**
	 * @return the clientAioListener
	 */
	public ClientAioListener<SessionContext, P, R> getClientAioListener()
	{
		return clientAioListener;
	}

	/**
	 * @param clientAioListener the clientAioListener to set
	 */
	public void setClientAioListener(ClientAioListener<SessionContext, P, R> clientAioListener)
	{
		this.clientAioListener = clientAioListener;
		if (this.clientAioListener == null)
		{
			this.clientAioListener = new DefaultClientAioListener<SessionContext, P, R>();
		}
	}

	/** 
	 * @see org.tio.core.GroupContext#getAioHandler()
	 * 
	 * @return
	 * @author: tanyaowu
	 * 2016年12月20日 上午11:33:46
	 * 
	 */
	@Override
	public AioHandler<SessionContext, P, R> getAioHandler()
	{
		return this.getClientAioHandler();
	}

	/** 
	 * @see org.tio.core.GroupContext#getGroupStat()
	 * 
	 * @return
	 * @author: tanyaowu
	 * 2016年12月20日 上午11:33:46
	 * 
	 */
	@Override
	public GroupStat getGroupStat()
	{
		return this.getClientGroupStat();
	}

	/** 
	 * @see org.tio.core.GroupContext#getAioListener()
	 * 
	 * @return
	 * @author: tanyaowu
	 * 2016年12月20日 上午11:33:46
	 * 
	 */
	@Override
	public AioListener<SessionContext, P, R> getAioListener()
	{
		return this.getClientAioListener();
	}

	/**
	 * @param reconnConf the reconnConf to set
	 */
	public void setReconnConf(ReconnConf<SessionContext, P, R> reconnConf)
	{
		this.reconnConf = reconnConf;
	}

	/**
	 * @return the connectionCompletionHandler
	 */
	public ConnectionCompletionHandler<SessionContext, P, R> getConnectionCompletionHandler()
	{
		return connectionCompletionHandler;
	}

	/**
	 * @param connectionCompletionHandler the connectionCompletionHandler to set
	 */
	public void setConnectionCompletionHandler(ConnectionCompletionHandler<SessionContext, P, R> connectionCompletionHandler)
	{
		this.connectionCompletionHandler = connectionCompletionHandler;
	}

}
