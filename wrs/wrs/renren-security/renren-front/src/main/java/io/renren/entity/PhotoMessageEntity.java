package io.renren.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-05-06 11:27:27
 */
 @TableName("photo_message")
public class PhotoMessageEntity implements Serializable {
	private static final long serialVersionUID = 1L;

		//
	@TableId(value="id",type=IdType.AUTO)
	private Long id;	
		
	//发送者id
	@TableField(value="user_id")
	private Long userId;
			
	//接收者id
	@TableField(value="user_get_id")
	private Long userGetId;
			
	//信息类型
	@TableField(value="msg_type")
	private Integer msgType;
			
	//信息内容
	@TableField(value="contents")
	private String contents;
			
	//
	@TableField(value="gmt_create")
	private Date gmtCreate;
			
	//
	@TableField(value="gmt_modefied")
	private Date gmtModefied;
			

	/**
	 * 设置：
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：发送者id
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * 获取：发送者id
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * 设置：接收者id
	 */
	public void setUserGetId(Long userGetId) {
		this.userGetId = userGetId;
	}
	/**
	 * 获取：接收者id
	 */
	public Long getUserGetId() {
		return userGetId;
	}
	/**
	 * 设置：信息类型
	 */
	public void setMsgType(Integer msgType) {
		this.msgType = msgType;
	}
	/**
	 * 获取：信息类型
	 */
	public Integer getMsgType() {
		return msgType;
	}
	/**
	 * 设置：信息内容
	 */
	public void setContents(String contents) {
		this.contents = contents;
	}
	/**
	 * 获取：信息内容
	 */
	public String getContents() {
		return contents;
	}
	/**
	 * 设置：
	 */
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	/**
	 * 获取：
	 */
	public Date getGmtCreate() {
		return gmtCreate;
	}
	/**
	 * 设置：
	 */
	public void setGmtModefied(Date gmtModefied) {
		this.gmtModefied = gmtModefied;
	}
	/**
	 * 获取：
	 */
	public Date getGmtModefied() {
		return gmtModefied;
	}
}
