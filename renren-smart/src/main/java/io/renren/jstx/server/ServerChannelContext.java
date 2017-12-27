package io.renren.jstx.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousSocketChannel;

import io.renren.jstx.core.ChannelContext;
import io.renren.jstx.core.GroupContext;
import io.renren.jstx.core.Node;
import io.renren.jstx.core.intf.Packet;

/**
 * 
 * @author tanyaowu 
 *
 */
public class ServerChannelContext<SessionContext, P extends Packet, R> extends ChannelContext<SessionContext, P, R>
{

	/**
	 * @param groupContext
	 * @param asynchronousSocketChannel
	 *
	 * @author: tanyaowu
	 * 2016年12月6日 下午12:17:59
	 * 
	 */
	public ServerChannelContext(GroupContext<SessionContext, P, R> groupContext, AsynchronousSocketChannel asynchronousSocketChannel)
	{
		super(groupContext, asynchronousSocketChannel);
	}

	/** 
	 * @see io.renren.jstx.core.ChannelContext#createClientNode(java.nio.channels.AsynchronousSocketChannel)
	 * 
	 * @param asynchronousSocketChannel
	 * @return
	 * @throws IOException 
	 * @author: tanyaowu
	 * 2016年12月6日 下午12:18:08
	 * 
	 */
	@Override
	public Node createClientNode(AsynchronousSocketChannel asynchronousSocketChannel) throws IOException
	{
		InetSocketAddress inetSocketAddress = (InetSocketAddress) asynchronousSocketChannel.getRemoteAddress();
		Node clientNode = new Node(inetSocketAddress.getHostString(), inetSocketAddress.getPort());
		return clientNode;
	}

}
