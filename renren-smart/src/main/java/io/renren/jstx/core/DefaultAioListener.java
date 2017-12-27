package io.renren.jstx.core;

import io.renren.jstx.client.intf.ClientAioListener;
import io.renren.jstx.core.intf.Packet;
import io.renren.jstx.server.intf.ServerAioListener;

/**
 * 
 * @author tanyaowu 
 */
public class DefaultAioListener<SessionContext, P extends Packet, R> implements ClientAioListener<SessionContext, P, R>, ServerAioListener<SessionContext, P, R>
{
	/** 
	 * @see io.renren.jstx.core.intf.AioListener#onAfterConnected(io.renren.jstx.core.ChannelContext, boolean, boolean)
	 * 
	 * @param channelContext
	 * @param isConnected
	 * @param isReconnect
	 * @author: tanyaowu
	 * 2017年2月4日 下午9:40:14
	 * 
	 */
	@Override
	public void onAfterConnected(ChannelContext<SessionContext, P, R> channelContext, boolean isConnected, boolean isReconnect){}

	/** 
	 * @see io.renren.jstx.core.intf.AioListener#onAfterSent(io.renren.jstx.core.ChannelContext, io.renren.jstx.core.intf.Packet, boolean)
	 * 
	 * @param channelContext
	 * @param packet
	 * @param isSentSuccess
	 * @author: tanyaowu
	 * 2017年2月4日 下午9:40:14
	 * 
	 */
	@Override
	public void onAfterSent(ChannelContext<SessionContext, P, R> channelContext, P packet, boolean isSentSuccess){}

	/** 
	 * @see io.renren.jstx.core.intf.AioListener#onAfterReceived(io.renren.jstx.core.ChannelContext, io.renren.jstx.core.intf.Packet, int)
	 * 
	 * @param channelContext
	 * @param packet
	 * @param packetSize
	 * @author: tanyaowu
	 * 2017年2月4日 下午9:40:14
	 * 
	 */
	@Override
	public void onAfterReceived(ChannelContext<SessionContext, P, R> channelContext, P packet, int packetSize){}

	/** 
	 * @see io.renren.jstx.core.intf.AioListener#onAfterClose(io.renren.jstx.core.ChannelContext, java.lang.Throwable, java.lang.String, boolean)
	 * 
	 * @param channelContext
	 * @param throwable
	 * @param remark
	 * @param isRemove
	 * @author: tanyaowu
	 * 2017年2月4日 下午9:40:14
	 * 
	 */
	@Override
	public void onAfterClose(ChannelContext<SessionContext, P, R> channelContext, Throwable throwable, String remark, boolean isRemove){}
}
