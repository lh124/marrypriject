package io.renren.entity.app;

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
 * @date 2018-01-23 11:24:25
 */
 @TableName("work_main")
public class WorkMainEntity implements Serializable {
	private static final long serialVersionUID = 1L;

		//主键id
	@TableId(value="id",type=IdType.AUTO)
	private Integer id;	
		
	//图片
	@TableField(value="img")
	private String img;
			
	//任务详情
	@TableField(value="content")
	private String content;
			
	//用户id
	@TableField(value="user_id")
	private Integer userId;
			
	//用户名字
	@TableField(value="user_name")
	private String userName;
			
	//当前状态（0为未完成，1为已完成）
	@TableField(value="states")
	private Integer states;
	
	//处理状态（0不合格，1合格，2待处理）
	@TableField(value="handle_states")
	private Integer handleStates;
			
	//任务开始时间
	@TableField(value="gmt_modifiedtime")
	private Date gmtModifiedtime;
			
	//任务结束时间
	@TableField(value="end_time")
	private Date endTime;
			
	//父id
	@TableField(value="father_id")
	private Integer fatherId;
			
	//预计花费时间
	@TableField(value="estimate_time")
	private String estimateTime;
	
	//处理意见
	@TableField(value="handle_content")
	private String handleContent;
	
	//备注说明
	@TableField(value="beizhuContent")
	private String beizhuContent;
			

	public String getBeizhuContent() {
		return beizhuContent;
	}
	public void setBeizhuContent(String beizhuContent) {
		this.beizhuContent = beizhuContent;
	}
	public Integer getHandleStates() {
		return handleStates;
	}
	public void setHandleStates(Integer handleStates) {
		this.handleStates = handleStates;
	}
	public String getHandleContent() {
		return handleContent;
	}
	public void setHandleContent(String handleContent) {
		this.handleContent = handleContent;
	}
	/**
	 * 设置：主键id
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：主键id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：图片
	 */
	public void setImg(String img) {
		this.img = img;
	}
	/**
	 * 获取：图片
	 */
	public String getImg() {
		return img;
	}
	/**
	 * 设置：任务详情
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取：任务详情
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置：用户id
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	/**
	 * 获取：用户id
	 */
	public Integer getUserId() {
		return userId;
	}
	/**
	 * 设置：用户名字
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * 获取：用户名字
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * 设置：当前状态（0为未完成，1为已完成）
	 */
	public void setStates(Integer states) {
		this.states = states;
	}
	/**
	 * 获取：当前状态（0为未完成，1为已完成）
	 */
	public Integer getStates() {
		return states;
	}
	/**
	 * 设置：任务开始时间
	 */
	public void setGmtModifiedtime(Date gmtModifiedtime) {
		this.gmtModifiedtime = gmtModifiedtime;
	}
	/**
	 * 获取：任务开始时间
	 */
	public Date getGmtModifiedtime() {
		return gmtModifiedtime;
	}
	/**
	 * 设置：任务结束时间
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	/**
	 * 获取：任务结束时间
	 */
	public Date getEndTime() {
		return endTime;
	}
	/**
	 * 设置：父id
	 */
	public void setFatherId(Integer fatherId) {
		this.fatherId = fatherId;
	}
	/**
	 * 获取：父id
	 */
	public Integer getFatherId() {
		return fatherId;
	}
	/**
	 * 设置：预计花费时间
	 */
	public void setEstimateTime(String estimateTime) {
		this.estimateTime = estimateTime;
	}
	/**
	 * 获取：预计花费时间
	 */
	public String getEstimateTime() {
		return estimateTime;
	}
}
