package io.renren.entity;

import io.renren.validator.group.AddGroup;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


import org.hibernate.validator.constraints.NotBlank;

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
 @TableName("photo_graduation_time")
public class PhotoGraduationTimeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

		//id
	@TableId(value="id",type=IdType.AUTO)
	private Integer id;	
		
	//毕业时间数字
	@TableField(value="graduation_time")
	private String graduationTime;
			
	//毕业时间全称
	@TableField(value="graduation_name")
	@NotBlank(message="毕业时间名不能为空", groups = {AddGroup.class})
	private String graduationName;
			
	//
	@TableField(value="gmt_create")
	private Date gmtCreate;
			
	//
	@TableField(value="gmt_modified")
	private Date gmtModified;
	
	@TableField(exist=false)
	private List<PhotoClassEntity> classList;
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
	 * 设置：毕业时间数字
	 */
	public void setGraduationTime(String graduationTime) {
		this.graduationTime = graduationTime;
	}
	/**
	 * 获取：毕业时间数字
	 */
	public String getGraduationTime() {
		return graduationTime;
	}
	/**
	 * 设置：毕业时间全称
	 */
	public void setGraduationName(String graduationName) {
		this.graduationName = graduationName;
	}
	/**
	 * 获取：毕业时间全称
	 */
	public String getGraduationName() {
		return graduationName;
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
	public List<PhotoClassEntity> getClassList() {
		return classList;
	}
	public void setClassList(List<PhotoClassEntity> classList) {
		this.classList = classList;
	}
	
}
