package io.renren.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * 行政区域地州市信息表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-05-06 11:27:27
 */
 @TableName("city")
public class CityEntity implements Serializable {
	private static final long serialVersionUID = 1L;

		//id
	@TableId(value="id",type=IdType.AUTO)
	private Integer id;	
		
	//城市id
	@TableField(value="cityid")
	private String cityid;
			
	//城市名
	@TableField(value="city")
	private String city;
			
	//省份id
	@TableField(value="provinceid")
	private String provinceid;
			

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
	 * 设置：城市id
	 */
	public void setCityid(String cityid) {
		this.cityid = cityid;
	}
	/**
	 * 获取：城市id
	 */
	public String getCityid() {
		return cityid;
	}
	/**
	 * 设置：城市名
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * 获取：城市名
	 */
	public String getCity() {
		return city;
	}
	/**
	 * 设置：省份id
	 */
	public void setProvinceid(String provinceid) {
		this.provinceid = provinceid;
	}
	/**
	 * 获取：省份id
	 */
	public String getProvinceid() {
		return provinceid;
	}
}
