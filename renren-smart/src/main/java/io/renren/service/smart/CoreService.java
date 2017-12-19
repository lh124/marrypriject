package io.renren.service.smart;

import io.renren.entity.smart.SysWeixinMsgEntity;
import io.renren.utils.Query;
import io.renren.weixin.message.resp.TextMessage;
import io.renren.weixin.util.MessageUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * 核心服务�?
 * 
 * @author liufeng
 * @date 2013-10-17
 */

@Service
@Repository("coreService")
public class CoreService {
	
	@Autowired
	private static SysWeixinMsgService sysWeixinMsgService;
	
	/**
	 * 处理微信发来的请求
	 * 
	 * @param request
	 * @return xml
	 */
	@SuppressWarnings("rawtypes")
	public String processRequest(HttpServletRequest request,SysWeixinMsgService sysWeixinMsgService) {
		// xml格式的消息数据
		String respXml = null;
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("page", 1);
			params.put("limit", 1);
			params.put("sidx", "");
			params.put("order", "");
			// 调用parseXml方法解析请求消息
			Map<String, String> requestMap = MessageUtil.parseXml(request);
			// 发送方帐号
			String fromUserName = requestMap.get("FromUserName");
			request.getSession().setAttribute("fromUserName", fromUserName);
			// 开发者微信号
			String toUserName = requestMap.get("ToUserName");
			// 消息类型
			String msgType = requestMap.get("MsgType");
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			// 事件推送
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				// 事件类型
				String eventType = requestMap.get("Event");
				//关注
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
					params.put("weixinid", "where sendtype = 1 and weixinid = '" + toUserName + "'");
					String content = "";
					List<SysWeixinMsgEntity> sysWeixinMsgList = sysWeixinMsgService.queryList(params);
					for (Iterator iterator = sysWeixinMsgList.iterator(); iterator.hasNext();) {
						SysWeixinMsgEntity sysWeixinMsgEntity = (SysWeixinMsgEntity) iterator.next();
						content = sysWeixinMsgEntity.getContent();
					}
					textMessage.setContent(content);
					// 将消息对象转换成xml
					respXml = MessageUtil.messageToXml(textMessage);
				}//取消关注
				else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
				}//自定义菜单点击事件
				else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
					//事件KEY值，与创建菜单时的key值对应
				}
			}// 当用户发消息时
			else {
				if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)){
					String sendcontent = requestMap.get("Content");//用户发送过来的消息
					params.put("limit", 1000);
					params.put("weixinid", "where sendtype = 2 and weixinid = '" + toUserName + "'");
					String content = "";
					Query query = new Query(params);
					List<SysWeixinMsgEntity> sysWeixinMsgList = sysWeixinMsgService.queryList(query);
					for (Iterator iterator = sysWeixinMsgList.iterator(); iterator.hasNext();) {
						SysWeixinMsgEntity sysWeixinMsgEntity = (SysWeixinMsgEntity) iterator.next();
						if(sendcontent.indexOf(sysWeixinMsgEntity.getKeyword()) > -1){
							content = sysWeixinMsgEntity.getContent();
						}
					}
					if(content.equals("") || content == null){
						content = "尊敬的用户:根据您发送的内容，我们判断您可能需要了解以下问题：\n"+
								   "1.登录账号是什么？\n"+
								   "2.账号密码是什么？\n"+
								   "3.怎么登录？\n"+
								   "4.了解更多产品信息？\n"+
								   "请回复相应数字进行查询";
					}
					textMessage.setContent(content);
					respXml = MessageUtil.messageToXml(textMessage);
				}else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)){
				    String sendcontent = requestMap.get("Recognition");//用户通过语音发送过来的消息
					params.put("limit", 1000);
					params.put("weixinid", "where sendtype = 2 and weixinid = '" + toUserName + "'");
					String content = "";
					Query query = new Query(params);
					List<SysWeixinMsgEntity> sysWeixinMsgList = sysWeixinMsgService.queryList(query);
					for (Iterator iterator = sysWeixinMsgList.iterator(); iterator.hasNext();) {
						SysWeixinMsgEntity sysWeixinMsgEntity = (SysWeixinMsgEntity) iterator.next();
						if(sendcontent.indexOf(sysWeixinMsgEntity.getKeyword()) > -1){
							content = sysWeixinMsgEntity.getContent();
						}
					}
					if(content.equals("") || content == null){
						content = "尊敬的用户:根据您发送的内容，我们判断您可能需要了解以下问题：\n"+
								   "1.登录账号是什么？\n"+
								   "2.账号密码是什么？\n"+
								   "3.怎么登录？\n"+
								   "4.了解更多产品信息？\n"+
								   "请回复相应数字进行查询";
					}
					textMessage.setContent(content);
					respXml = MessageUtil.messageToXml(textMessage);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respXml;
	}
}
