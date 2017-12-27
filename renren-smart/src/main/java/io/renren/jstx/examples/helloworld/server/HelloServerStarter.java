package io.renren.jstx.examples.helloworld.server;

import java.io.IOException;

import io.renren.jstx.examples.helloworld.common.Const;
import io.renren.jstx.examples.helloworld.common.HelloPacket;
import io.renren.jstx.server.AioServer;
import io.renren.jstx.server.ServerGroupContext;
import io.renren.jstx.server.intf.ServerAioHandler;
import io.renren.jstx.server.intf.ServerAioListener;

/**
 * 
 * @author tanyaowu 
 * 2017年4月4日 下午12:22:58
 */
public class HelloServerStarter
{
	//handler, 包括编码、解码、消息处理
	public static ServerAioHandler<Object, HelloPacket, Object> aioHandler = new HelloServerAioHandler();
	
	//事件监听器，可以为null，但建议自己实现该接口，可以参考showcase了解些接口
	public static ServerAioListener<Object, HelloPacket, Object> aioListener = null;
	
	//一组连接共用的上下文对象
	public static ServerGroupContext<Object, HelloPacket, Object> serverGroupContext = new ServerGroupContext<>(aioHandler, aioListener);
	
	//aioServer对象
	public static AioServer<Object, HelloPacket, Object> aioServer = new AioServer<>(serverGroupContext);
	
	//有时候需要绑定ip，不需要则null
	public static String serverIp = null;
	
	//监听的端口
	public static int serverPort = Const.PORT;

	/**
	 * 启动程序入口
	 */
	public static void main(String[] args) throws IOException{
		aioServer.start(serverIp, serverPort);
	}
}