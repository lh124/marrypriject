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
 @TableName("photo_family_role")
public class PhotoFamilyRoleEntity implements Serializable {
	private static final long serialVersionUID = 1L;

		//id
	@TableId(value="id",type=IdType.AUTO)
	private Long id;	
		
	//角色名
	@TableField(value="name")
	private String name;
			
	//角色类型值
	@TableField(value="role_type")
	private Integer roleType;
			
	//描述
	@TableField(value="info")
	private String info;
			
	//
	@TableField(value="gtm_create")
	private Date gtmCreate;
			
	//
	@TableField(value="gtm_modified")
	private Date gtmModified;
			

	/**
	 * 设置：id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：角色名
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：角色名
	 */
	public String getName() {
		return name;
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
	 * 设置：描述
	 */
	public void setInfo(String info) {
		this.info = info;
	}
	/**
	 * 获取：描述
	 */
	public String getInfo() {
		return info;
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
