package io.renren.entity.smart;

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
 * @date 2018-01-27 10:26:32
 */
 @TableName("smart_grade")
public class SmartGradeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

		//主键id
	@TableId(value="id",type=IdType.AUTO)
	private Integer id;	
		
	//年级名字
	@TableField(value="name")
	private String name;
			
	//所在学校id
	@TableField(value="school_id")
	private Integer schoolId;
			

	/**
	 * 设置：主键id
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：主键id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：年级名字
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：年级名字
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：所在学校id
	 */
	public void setSchoolId(Integer schoolId) {
		this.schoolId = schoolId;
	}
	/**
	 * 获取：所在学校id
	 */
	public Integer getSchoolId() {
		return schoolId;
	}
}
