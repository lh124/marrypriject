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
 * @date 2017-12-07 12:40:40
 */
 @TableName("smart_exception")
public class SmartExceptionEntity implements Serializable {
	private static final long serialVersionUID = 1L;

		//
	@TableId(value="id",type=IdType.AUTO)
	private Integer id;	
		
	//
	@TableField(value="schoolName")
	private String schoolname;
			
	//
	@TableField(value="createTime")
	private Date createtime;
			
	//
	@TableField(value="modularName")
	private String modularname;
			
	//
	@TableField(value="exceptionInformation")
	private String exceptioninformation;
			

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
	public void setSchoolname(String schoolname) {
		this.schoolname = schoolname;
	}
	/**
	 * 获取：
	 */
	public String getSchoolname() {
		return schoolname;
	}
	/**
	 * 设置：
	 */
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	/**
	 * 获取：
	 */
	public Date getCreatetime() {
		return createtime;
	}
	/**
	 * 设置：
	 */
	public void setModularname(String modularname) {
		this.modularname = modularname;
	}
	/**
	 * 获取：
	 */
	public String getModularname() {
		return modularname;
	}
	/**
	 * 设置：
	 */
	public void setExceptioninformation(String exceptioninformation) {
		this.exceptioninformation = exceptioninformation;
	}
	/**
	 * 获取：
	 */
	public String getExceptioninformation() {
		return exceptioninformation;
	}
}
