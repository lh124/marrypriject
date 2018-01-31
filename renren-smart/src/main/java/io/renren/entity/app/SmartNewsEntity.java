package io.renren.entity.app;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * 
 * 
 * @author yuanbichang
 * @email 673290684@qq.com
 * @date 2017-12-26 08:58:40
 */
 @TableName("smart_news")
public class SmartNewsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

		//
	@TableId(value="id",type=IdType.AUTO)
	private Integer id;	
		
	//消息类型（1学校通知，2老师通知，3，班内消息，4，请假申请，5申请回复，6进出校通知,7作业通知）
	@TableField(value="states")
	private Integer states;
			
	//各种消息id
	@TableField(value="newsId")
	private Integer newsid;
	
	//各种消息id
	@TableField(value="userId")
	private Integer userId;
	
	//通知标题
	@TableField(value="title")
	private String title;
			
	//消息状态（0为未读，1为已读)
	@TableField(value="newsType")
	private Integer newstype;
			

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
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
	 * 设置：消息类型（1学校通知，2老师通知，3，班内消息，4，请假申请，5申请回复，6进出校通知）
	 */
	public void setStates(Integer states) {
		this.states = states;
	}
	/**
	 * 获取：消息类型（1学校通知，2老师通知，3，班内消息，4，请假申请，5申请回复，6进出校通知）
	 */
	public Integer getStates() {
		return states;
	}
	/**
	 * 设置：各种消息id
	 */
	public void setNewsid(Integer newsid) {
		this.newsid = newsid;
	}
	/**
	 * 获取：各种消息id
	 */
	public Integer getNewsid() {
		return newsid;
	}
	/**
	 * 设置：消息状态（0为未读，1为已读)
	 */
	public void setNewstype(Integer newstype) {
		this.newstype = newstype;
	}
	/**
	 * 获取：消息状态（0为未读，1为已读)
	 */
	public Integer getNewstype() {
		return newstype;
	}
}
