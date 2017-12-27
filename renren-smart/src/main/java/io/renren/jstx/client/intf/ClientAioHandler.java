package io.renren.jstx.client.intf;

import io.renren.jstx.core.intf.AioHandler;
import io.renren.jstx.core.intf.Packet;

/**
 * 
 * @author tanyaowu 
 * 2017年4月1日 上午9:14:24
 */
public interface ClientAioHandler <SessionContext, P extends Packet, R> extends AioHandler<SessionContext, P, R>
{
	/**
	 * 创建心跳包
	 * @return
	 * @author: tanyaowu
	 */
	P heartbeatPacket();
}
