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
 * @date 2017-12-04 16:03:16
 */
 @TableName("smart_leave")
public class SmartLeaveEntity implements Serializable {
	private static final long serialVersionUID = 1L;

		//
	@TableId(value="id",type=IdType.AUTO)
	private Integer id;	
		 
	//用户id
	@TableField(value="userId")
	private Integer userid;
			
	//
	@TableField(value="calssId")
	private Integer calssid;
			
	//请假状态1为待处理，2为同意，3为不同意
	@TableField(value="states")
	private String states;
			
	//请假详情
	@TableField(value="content")
	private String content;
			
	//请假开始时间
	@TableField(value="startDate")
	private Date startdate;
			
	//请假结束时间
	@TableField(value="endDate")
	private Date enddate;
	
	@TableField(value="beizhu")
	private String beizhu;
	
	private Integer newsType;
	
	private Integer newsId;
			

	public Integer getNewsType() {
		return newsType;
	}
	public void setNewsType(Integer newsType) {
		this.newsType = newsType;
	}
	public Integer getNewsId() {
		return newsId;
	}
	public void setNewsId(Integer newsId) {
		this.newsId = newsId;
	}
	public String getBeizhu() {
		return beizhu;
	}
	public void setBeizhu(String beizhu) {
		this.beizhu = beizhu;
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
	 * 设置：用户id
	 */
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	/**
	 * 获取：用户id
	 */
	public Integer getUserid() {
		return userid;
	}
	/**
	 * 设置：
	 */
	public void setCalssid(Integer calssid) {
		this.calssid = calssid;
	}
	/**
	 * 获取：
	 */
	public Integer getCalssid() {
		return calssid;
	}
	
	public String getStates() {
		return states;
	}
	public void setStates(String states) {
		this.states = states;
	}
	/**
	 * 设置：请假详情
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取：请假详情
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置：请假开始时间
	 */
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	/**
	 * 获取：请假开始时间
	 */
	public Date getStartdate() {
		return startdate;
	}
	/**
	 * 设置：请假结束时间
	 */
	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}
	/**
	 * 获取：请假结束时间
	 */
	public Date getEnddate() {
		return enddate;
	}
}
