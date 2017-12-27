package io.renren.jstx.server.intf;

import io.renren.jstx.core.intf.AioHandler;
import io.renren.jstx.core.intf.Packet;

/**
 * 
 * @author tanyaowu 
 *
 */
public interface ServerAioHandler <SessionContext, P extends Packet, R> extends AioHandler<SessionContext, P, R>
{
	
}
