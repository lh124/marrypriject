package io.renren.controller.smart;

import io.renren.constant.ControllerConstant;
import io.renren.entity.smart.GroupChatEntity;
import io.renren.entity.smart.StudentEntity;
import io.renren.jstx.client.intf.AioClient;
import io.renren.jstx.client.intf.ClientAioHandler;
import io.renren.jstx.client.intf.ClientAioListener;
import io.renren.jstx.client.intf.ClientChannelContext;
import io.renren.jstx.client.intf.ClientGroupContext;
import io.renren.jstx.client.intf.ReconnConf;
import io.renren.jstx.core.Aio;
import io.renren.jstx.core.Node;
import io.renren.jstx.examples.helloworld.client.HelloClientAioHandler;
import io.renren.jstx.examples.helloworld.common.Const;
import io.renren.jstx.examples.helloworld.common.HelloPacket;
import io.renren.service.smart.GroupChatService;
import io.renren.utils.R;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController("jstxController")
@RequestMapping("/smart/jstx")
public class JstxController {
	
	//服务器节点
	public static Node serverNode = new Node("127.0.0.1", Const.PORT);

	//handler, 包括编码、解码、消息处理
	public static ClientAioHandler<Object, HelloPacket, Object> aioClientHandler = new HelloClientAioHandler();
		
	//事件监听器，可以为null，但建议自己实现该接口，可以参考showcase了解些接口
	public static ClientAioListener<Object, HelloPacket, Object> aioListener = null;
		
	//断链后自动连接的，不想自动连接请设为null
	private static ReconnConf<Object, HelloPacket, Object> reconnConf = new ReconnConf<Object, HelloPacket, Object>(5000L);

	//一组连接共用的上下文对象
	public static ClientGroupContext<Object, HelloPacket, Object> clientGroupContext = new ClientGroupContext<>(aioClientHandler, aioListener, reconnConf);

	public static AioClient<Object, HelloPacket, Object> aioClient = null;
	public static ClientChannelContext<Object, HelloPacket, Object> clientChannelContext = null;
	
	@Autowired
	private GroupChatService groupChatService;
	
	/**
	 * 根据班级id查询所有的班级聊天记录
	 */
	@RequestMapping("/list")
	public R list(HttpSession session){
		StudentEntity student = (StudentEntity) session.getAttribute(ControllerConstant.SESSION_SMART_USER_KEY);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("limit", 100);
		map.put("offset", 0);
		map.put("classId", student.getClassId());
		List<GroupChatEntity> list = groupChatService.queryList(map);
		return R.ok().put("list", list);
	}
	
	/**
	 * 根据班级id,学生id保存聊天记录
	 */
	
	@RequestMapping("/save")
	public R save(HttpServletRequest request,HttpSession session){
		StudentEntity student = (StudentEntity) session.getAttribute(ControllerConstant.SESSION_SMART_USER_KEY);
		GroupChatEntity gce = new GroupChatEntity();
		String content = request.getParameter("content");
		gce.setClassId(student.getClassId());
		gce.setStudentId(student.getId());
		gce.setCreatetime(new Date());
		gce.setContent(content);
//		groupChatService.save(gce);
		return R.ok();
	}
//	@RequestMapping("/save")
//	public R save(HttpServletRequest request,HttpSession session){
//		StudentEntity student = (StudentEntity) session.getAttribute(ControllerConstant.SESSION_SMART_USER_KEY);
//		GroupChatEntity gce = new GroupChatEntity();
//		String content = request.getParameter("content");
//		if(send(content,request,student.getId())){
//			gce.setClassId(student.getClassId());
//			gce.setStudentId(student.getId());
//			gce.setCreatetime(new Date());
//			gce.setContent(content);
//			groupChatService.save(gce);
//			return R.ok();
//		}else{
//			return R.error();
//		}
//	}
	
	@SuppressWarnings("unused")
	private boolean send(String content,HttpServletRequest request,Integer id){
		try {
			if(clientChannelContext == null){
				aioClient = new AioClient<>(clientGroupContext);
				clientChannelContext = aioClient.connect(serverNode);
				request.getSession().setAttribute("clientChannelContext"+id, clientChannelContext);
			}else{
				if(request.getSession().getAttribute("clientChannelContext"+id) == null){
					aioClient = new AioClient<>(clientGroupContext);
					clientChannelContext = aioClient.connect(serverNode);
					request.getSession().setAttribute("clientChannelContext"+id, clientChannelContext);
				}
			}
			HelloPacket packet = new HelloPacket();
			packet.setBody(content.getBytes(HelloPacket.CHARSET));
			return Aio.send(clientChannelContext, packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
