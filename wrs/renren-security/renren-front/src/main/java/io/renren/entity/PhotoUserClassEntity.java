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
 @TableName("photo_user_class")
public class PhotoUserClassEntity implements Serializable {
	private static final long serialVersionUID = 1L;

		//
	@TableId(value="id",type=IdType.AUTO)
	private Long id;	
		
	//班级id
	@TableField(value="class_id")
	private Long classId;
			
	//前端用户id
	@TableField(value="front_user_id")
	private Long frontUserId;
			
	//用户班级状态
	@TableField(value="status")
	private Integer status;
			
	//班级角色类型
	@TableField(value="class_role_type")
	private Integer classRoleType;
			
	//
	@TableField(value="gtm_create")
	private Date gtmCreate;
			
	//
	@TableField(value="gtm_modified")
	private Date gtmModified;
			

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
	 * 设置：班级id
	 */
	public void setClassId(Long classId) {
		this.classId = classId;
	}
	/**
	 * 获取：班级id
	 */
	public Long getClassId() {
		return classId;
	}
	/**
	 * 设置：前端用户id
	 */
	public void setFrontUserId(Long frontUserId) {
		this.frontUserId = frontUserId;
	}
	/**
	 * 获取：前端用户id
	 */
	public Long getFrontUserId() {
		return frontUserId;
	}
	/**
	 * 设置：用户班级状态
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：用户班级状态
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置：班级角色类型
	 */
	public void setClassRoleType(Integer classRoleType) {
		this.classRoleType = classRoleType;
	}
	/**
	 * 获取：班级角色类型
	 */
	public Integer getClassRoleType() {
		return classRoleType;
	}
	/**
	 * 设置：
	 */
	public void setGtmCreate(Date gtmCreate) {
		this.gtmCreate = gtmCreate;
	}
	/**
	 * 获取：
	 */
	public Date getGtmCreate() {
		return gtmCreate;
	}
	/**
	 * 设置：
	 */
	public void setGtmModified(Date gtmModified) {
		this.gtmModified = gtmModified;
	}
	/**
	 * 获取：
	 */
	public Date getGtmModified() {
		return gtmModified;
	}
}
