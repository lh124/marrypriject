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
 * @date 2017-08-28 11:52:18
 */
 @TableName("class")
public class ClassEntity implements Serializable {
	private static final long serialVersionUID = 1L;

		//
	@TableId(value="id",type=IdType.AUTO)
	private Integer id;	
		
	//
	@TableField(value="class_name")
	private String className;
			
	//
	@TableField(value="school_id")
	private Integer schoolId;
	
	@TableField(value="pic")
	private String pic;
			

	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
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
	public void setClassName(String className) {
		this.className = className;
	}
	/**
	 * 获取：
	 */
	public String getClassName() {
		return className;
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
}
