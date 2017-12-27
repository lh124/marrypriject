package io.renren.jstx.core;

import io.renren.jstx.core.intf.Packet;

public interface ChannelContextFilter<SessionContext, P extends Packet, R>
{

	/**
	 * 
	 * @param channelContext
	 * @return false: 排除此channelContext, true: 不排除
	 *
	 * @author: tanyaowu
	 * 2017年1月13日 下午3:28:54
	 *
	 */
	public boolean filter(ChannelContext<SessionContext, P, R> channelContext);

}
