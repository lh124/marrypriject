package io.renren.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * 省份信息表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-05-06 11:27:27
 */
 @TableName("province")
public class ProvinceEntity implements Serializable {
	private static final long serialVersionUID = 1L;

		//id
	@TableId(value="id",type=IdType.AUTO)
	private Integer id;	
		
	//省id
	@TableField(value="provinceid")
	private String provinceid;
			
	//省名
	@TableField(value="province")
	private String province;
			

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
	 * 设置：省id
	 */
	public void setProvinceid(String provinceid) {
		this.provinceid = provinceid;
	}
	/**
	 * 获取：省id
	 */
	public String getProvinceid() {
		return provinceid;
	}
	/**
	 * 设置：省名
	 */
	public void setProvince(String province) {
		this.province = province;
	}
	/**
	 * 获取：省名
	 */
	public String getProvince() {
		return province;
	}
}
