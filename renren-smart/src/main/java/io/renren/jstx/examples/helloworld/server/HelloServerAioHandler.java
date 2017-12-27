package io.renren.jstx.examples.helloworld.server;

import io.renren.jstx.core.Aio;
import io.renren.jstx.core.ChannelContext;
import io.renren.jstx.examples.helloworld.common.HelloAbsAioHandler;
import io.renren.jstx.examples.helloworld.common.HelloPacket;
import io.renren.jstx.server.intf.ServerAioHandler;

/**
 * 
 * @author tanyaowu 
 *
 */
public class HelloServerAioHandler extends HelloAbsAioHandler implements ServerAioHandler<Object, HelloPacket, Object>
{
	/** 
	 * 处理消息
	 */
	@Override
	public Object handler(HelloPacket packet, ChannelContext<Object, HelloPacket, Object> channelContext) throws Exception{
		byte[] body = packet.getBody();
		if (body != null){
			String str = new String(body, HelloPacket.CHARSET);
			HelloPacket resppacket = new HelloPacket();
			resppacket.setBody((str).getBytes(HelloPacket.CHARSET));
			Aio.send(channelContext, resppacket);
		}
		return null;
	}
}
