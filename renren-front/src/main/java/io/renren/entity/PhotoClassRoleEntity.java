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
 @TableName("photo_class_role")
public class PhotoClassRoleEntity implements Serializable {
	private static final long serialVersionUID = 1L;

		//id
	@TableId(value="id",type=IdType.AUTO)
	private Integer id;	
		
	//角色名
	@TableField(value="name")
	private String name;
			
	//类型值
	@TableField(value="role_type")
	private Integer roleType;
			
	//
	@TableField(value="gmt_create")
	private Date gmtCreate;
			
	//
	@TableField(value="gmt_modified")
	private Date gmtModified;
			

	/**
	 * 设置：id
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：id
	 */
	public Integer getId() {
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
	 * 设置：类型值
	 */
	public void setRoleType(Integer roleType) {
		this.roleType = roleType;
	}
	/**
	 * 获取：类型值
	 */
	public Integer getRoleType() {
		return roleType;
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
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
	/**
	 * 获取：
	 */
	public Date getGmtModified() {
		return gmtModified;
	}
}
