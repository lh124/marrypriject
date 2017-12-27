package io.renren.jstx.client.intf;

import io.renren.jstx.core.ChannelContext;
import io.renren.jstx.core.GroupContext;
import io.renren.jstx.core.Node;
import io.renren.jstx.core.intf.Packet;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousSocketChannel;

/**
 * 
 * @author tanyaowu 
 * 2017骞�鏈�鏃�涓婂崍9:31:16
 */
public class ClientChannelContext<SessionContext, P extends Packet, R> extends ChannelContext<SessionContext, P, R>
{
	private String bindIp;

	private Integer bindPort;

	/**
	 * @param groupContext
	 * @param asynchronousSocketChannel
	 *
	 * @author: tanyaowu
	 * 
	 */
	public ClientChannelContext(GroupContext<SessionContext, P, R> groupContext, AsynchronousSocketChannel asynchronousSocketChannel)
	{
		super(groupContext, asynchronousSocketChannel);
	}

	/** 
	 * @see org.tio.core.ChannelContext#createClientNode(java.nio.channels.AsynchronousSocketChannel)
	 * 
	 * @param asynchronousSocketChannel
	 * @return
	 * @throws IOException 
	 * @author: tanyaowu
	 * 2016骞�2鏈�鏃�涓嬪崍12:18:08
	 * 
	 */
	@Override
	public Node createClientNode(AsynchronousSocketChannel asynchronousSocketChannel) throws IOException
	{
		InetSocketAddress inetSocketAddress = (InetSocketAddress) asynchronousSocketChannel.getLocalAddress();
		Node clientNode = new Node(inetSocketAddress.getHostString(), inetSocketAddress.getPort());
		return clientNode;
	}

	/**
	 * @return the bindIp
	 */
	public String getBindIp()
	{
		return bindIp;
	}

	/**
	 * @param bindIp the bindIp to set
	 */
	public void setBindIp(String bindIp)
	{
		this.bindIp = bindIp;
	}

	/**
	 * @return the bindPort
	 */
	public Integer getBindPort()
	{
		return bindPort;
	}

	/**
	 * @param bindPort the bindPort to set
	 */
	public void setBindPort(Integer bindPort)
	{
		this.bindPort = bindPort;
	}

}
