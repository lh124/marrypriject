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
 @TableName("school")
public class SchoolEntity implements Serializable {
	private static final long serialVersionUID = 1L;

		//
	@TableId(value="id",type=IdType.AUTO)
	private Integer id;	
		
	//
	@TableField(value="school_name")
	private String schoolName;
			
	//
	@TableField(value="pw")
	private String pw;
	
	//
	@TableField(value="city_id")
	private Integer cityId;
			

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
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	/**
	 * 获取：
	 */
	public String getSchoolName() {
		return schoolName;
	}
	/**
	 * 设置：
	 */
	public void setPw(String pw) {
		this.pw = pw;
	}
	/**
	 * 获取：
	 */
	public String getPw() {
		return pw;
	}
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	
	
}
