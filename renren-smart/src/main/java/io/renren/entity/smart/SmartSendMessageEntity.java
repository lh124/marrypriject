package io.renren.entity.smart;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * 
 * 
 * @author yuanbichang
 * @email 673290684@qq.com
 * @date 2018-02-01 09:17:27
 */
 @TableName("smart_send_message")
public class SmartSendMessageEntity implements Serializable {
	private static final long serialVersionUID = 1L;

		//
	@TableId(value="id",type=IdType.AUTO)
	private Integer id;	
		
	//发送用户id
	@TableField(value="send_user_id")
	private Integer sendUserId;
	
	@TableField(value="send_user_name")
	private String sendUserName;
	
	@TableField(value="send_user_pic")
	private String sendUserPic;
			
	//接收消息id（可以是班级id，可以是用户id）
	@TableField(value="receive_id")
	private Integer receiveId;
			
	//具体的发送消息内容
	@TableField(value="content")
	private String content;
			
	//消息类型（1为班级群聊消息，2为点对点的私聊消息）
	@TableField(value="message_type")
	private Integer messageType;
			
	//消息发送时间
	@TableField(value="createTime")
	private Date createtime;
			

	public String getSendUserName() {
		return sendUserName;
	}
	public void setSendUserName(String sendUserName) {
		this.sendUserName = sendUserName;
	}
	public String getSendUserPic() {
		return sendUserPic;
	}
	public void setSendUserPic(String sendUserPic) {
		this.sendUserPic = sendUserPic;
	}
	/**
	 * 设置：
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：发送用户id
	 */
	public void setSendUserId(Integer sendUserId) {
		this.sendUserId = sendUserId;
	}
	/**
	 * 获取：发送用户id
	 */
	public Integer getSendUserId() {
		return sendUserId;
	}
	/**
	 * 设置：接收消息id（可以是班级id，可以是用户id）
	 */
	public void setReceiveId(Integer receiveId) {
		this.receiveId = receiveId;
	}
	/**
	 * 获取：接收消息id（可以是班级id，可以是用户id）
	 */
	public Integer getReceiveId() {
		return receiveId;
	}
	/**
	 * 设置：具体的发送消息内容
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取：具体的发送消息内容
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置：消息类型（1为班级群聊消息，2为点对点的私聊消息）
	 */
	public void setMessageType(Integer messageType) {
		this.messageType = messageType;
	}
	/**
	 * 获取：消息类型（1为班级群聊消息，2为点对点的私聊消息）
	 */
	public Integer getMessageType() {
		return messageType;
	}
	/**
	 * 设置：消息发送时间
	 */
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	/**
	 * 获取：消息发送时间
	 */
	public Date getCreatetime() {
		return createtime;
	}
}
