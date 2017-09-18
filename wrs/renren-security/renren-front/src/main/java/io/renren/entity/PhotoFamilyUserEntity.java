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
 @TableName("photo_family_user")
public class PhotoFamilyUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

		//
	@TableId(value="id",type=IdType.AUTO)
	private Long id;	
		
	//用户id
	@TableField(value="user_id")
	private Long userId;
			
	//家庭id
	@TableField(value="family_id")
	private Long familyId;
			
	//角色类型值
	@TableField(value="role_type")
	private Integer roleType;
			
	//在家庭中的状态
	@TableField(value="status")
	private Integer status;
			
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
	 * 设置：用户id
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * 获取：用户id
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * 设置：家庭id
	 */
	public void setFamilyId(Long familyId) {
		this.familyId = familyId;
	}
	/**
	 * 获取：家庭id
	 */
	public Long getFamilyId() {
		return familyId;
	}
	/**
	 * 设置：角色类型值
	 */
	public void setRoleType(Integer roleType) {
		this.roleType = roleType;
	}
	/**
	 * 获取：角色类型值
	 */
	public Integer getRoleType() {
		return roleType;
	}
	/**
	 * 设置：在家庭中的状态
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：在家庭中的状态
	 */
	public Integer getStatus() {
		return status;
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
