package io.renren.jstx.examples.helloworld.client;

import io.renren.jstx.client.intf.ClientAioHandler;
import io.renren.jstx.core.ChannelContext;
import io.renren.jstx.examples.helloworld.common.HelloAbsAioHandler;
import io.renren.jstx.examples.helloworld.common.HelloPacket;

/**
 * 
 * @author tanyaowu 
 *
 */
public class HelloClientAioHandler extends HelloAbsAioHandler implements ClientAioHandler<Object, HelloPacket, Object>
{
	/** 
	 * 处理消息
	 */
	@Override
	public Object handler(HelloPacket packet, ChannelContext<Object, HelloPacket, Object> channelContext) throws Exception{
		byte[] body = packet.getBody();
		if (body != null){
			String str = new String(body, HelloPacket.CHARSET);
			System.out.println("收到消息：" + str);
		}
		return null;
	}

	private static HelloPacket heartbeatPacket = new HelloPacket();

	/** 
	 * 此方法如果返回null，框架层面则不会发心跳；如果返回非null，框架层面会定时发本方法返回的消息包
	 */
	@Override
	public HelloPacket heartbeatPacket()
	{
		return heartbeatPacket;
	}
}
