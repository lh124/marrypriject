package io.renren.entity.smart;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * 科目表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-05-27 16:53:07
 */
 @TableName("photo_subject")
public class PhotoSubjectEntity implements Serializable {
	private static final long serialVersionUID = 1L;

		//
	@TableId(value="id",type=IdType.AUTO)
	private Long id;	
		
	//科目名
	@TableField(value="name")
	private String name;
			
	//总分
	@TableField(value="total_points")
	private String totalPoints;
			
	//
	@TableField(value="gmt_modified")
	private Date gmtModified;
			
	//
	@TableField(value="gmt_create")
	private Date gmtCreate;
			
	//科目描述
	@TableField(value="info")
	private String info;
			
	//使用状态
	@TableField(value="status")
	private Integer status;
			

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
	 * 设置：科目名
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：科目名
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：总分
	 */
	public void setTotalPoints(String totalPoints) {
		this.totalPoints = totalPoints;
	}
	/**
	 * 获取：总分
	 */
	public String getTotalPoints() {
		return totalPoints;
	}
	/**
	 * 设置：
	 */
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
	/**
	 * 获取：
	 */
	public Date getGmtModified() {
		return gmtModified;
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
	 * 设置：科目描述
	 */
	public void setInfo(String info) {
		this.info = info;
	}
	/**
	 * 获取：科目描述
	 */
	public String getInfo() {
		return info;
	}
	/**
	 * 设置：使用状态
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：使用状态
	 */
	public Integer getStatus() {
		return status;
	}
}
