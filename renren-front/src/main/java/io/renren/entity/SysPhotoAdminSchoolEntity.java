package io.renren.entity;

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
 * @date 2017-10-26 14:38:48
 */
 @TableName("sys_photo_admin_school")
public class SysPhotoAdminSchoolEntity implements Serializable {
	private static final long serialVersionUID = 1L;

		//
	@TableId(value="id",type=IdType.AUTO)
	private Integer id;	
		
	//
	@TableField(value="admin_id")
	private Integer adminId;
			
	//
	@TableField(value="school_id")
	private Integer schoolId;
			
	//
	@TableField(value="createTime")
	private String createtime;
	
	@TableField(exist=false)
	private PhotoSchoolEntity school;
			

	public PhotoSchoolEntity getSchool() {
		return school;
	}
	public void setSchool(PhotoSchoolEntity school) {
		this.school = school;
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
	 * 设置：
	 */
	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}
	/**
	 * 获取：
	 */
	public Integer getAdminId() {
		return adminId;
	}
	/**
	 * 设置：
	 */
	public void setSchoolId(Integer schoolId) {
		this.schoolId = schoolId;
	}
	/**
	 * 获取：
	 */
	public Integer getSchoolId() {
		return schoolId;
	}
	/**
	 * 设置：
	 */
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	/**
	 * 获取：
	 */
	public String getCreatetime() {
		return createtime;
	}
}
