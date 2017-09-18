package io.renren.entity;

import io.renren.entity.smart.SchoolEntity;
import io.renren.validator.group.AddGroup;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-05-27 22:23:54
 */
 @TableName("sys_admin_school")
public class SysAdminSchoolEntity implements Serializable {
	private static final long serialVersionUID = 1L;

		//
	@TableId(value="id",type=IdType.AUTO)
	private Long id;	
		
	//管理员id
	@TableField(value="admin_id")
	@NotNull(message="用户id不能为空", groups = {AddGroup.class})
	private Long adminId;
			
	//学校id
	@TableField(value="shool_id")
	@NotNull(message="学校id不能为空", groups = {AddGroup.class})
	private Long shoolId;
			
	//
	@TableField(value="gmt_modified")
	private Date gmtModified;
			
	//
	@TableField(value="gmt_create")
	private Date gmtCreate;
			
	//使用状态
	@TableField(value="status")
	private Integer status;
	
	@TableField(exist=false)
	private SchoolEntity school;
			

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
	 * 设置：管理员id
	 */
	public void setAdminId(Long adminId) {
		this.adminId = adminId;
	}
	/**
	 * 获取：管理员id
	 */
	public Long getAdminId() {
		return adminId;
	}
	/**
	 * 设置：学校id
	 */
	public void setShoolId(Long shoolId) {
		this.shoolId = shoolId;
	}
	/**
	 * 获取：学校id
	 */
	public Long getShoolId() {
		return shoolId;
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
	public SchoolEntity getSchool() {
		return school;
	}
	public void setSchool(SchoolEntity school) {
		this.school = school;
	}
	
	
	
}
