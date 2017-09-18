package io.renren.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
 @TableName("photo_school")
public class PhotoSchoolEntity implements Serializable {
	private static final long serialVersionUID = 1L;

		//
	@TableId(value="id",type=IdType.AUTO)
	private Long id;	
		
	//学校名
	@TableField(value="name")
	private String name;
			
	//学校类型，1为中学，2为大学
	@TableField(value="school_type")
	private String schoolType;
			
	//背景音乐
	@TableField(value="music")
	private String music;
			
	//视频
	@TableField(value="vedio")
	private String vedio;
			
	//学校描述
	@TableField(value="school_desc")
	private String schoolDesc;
			
	//
	@TableField(value="creater_id")
	private Long createrId;
			
	//
	@TableField(value="gmt_create")
	private Date gmtCreate;
			
	//
	@TableField(value="gmt_modefied")
	private Date gmtModefied;
			
	//logo
	@TableField(value="logo")
	private String logo;
			
	//省id
	@TableField(value="province_id")
	private Integer provinceId;
			
	//城市
	@TableField(value="city_id")
	private Integer cityId;
	
	//  集合成员变量
	@TableField(exist=false)
	private List<PhotoGraduationTimeEntity> timeList;
	
	@TableField(exist=false)
	private List<PhotoCollegeEntity> collegeList;
	
	@TableField(exist=false)
	private List<PhotoTypeEntity> typeList;
			

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
	 * 设置：学校名
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：学校名
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：学校类型，1为中学，2为大学
	 */
	public void setSchoolType(String schoolType) {
		this.schoolType = schoolType;
	}
	/**
	 * 获取：学校类型，1为中学，2为大学
	 */
	public String getSchoolType() {
		return schoolType;
	}
	/**
	 * 设置：背景音乐
	 */
	public void setMusic(String music) {
		this.music = music;
	}
	/**
	 * 获取：背景音乐
	 */
	public String getMusic() {
		return music;
	}
	/**
	 * 设置：视频
	 */
	public void setVedio(String vedio) {
		this.vedio = vedio;
	}
	/**
	 * 获取：视频
	 */
	public String getVedio() {
		return vedio;
	}
	/**
	 * 设置：学校描述
	 */
	public void setSchoolDesc(String schoolDesc) {
		this.schoolDesc = schoolDesc;
	}
	/**
	 * 获取：学校描述
	 */
	public String getSchoolDesc() {
		return schoolDesc;
	}
	/**
	 * 设置：
	 */
	public void setCreaterId(Long createrId) {
		this.createrId = createrId;
	}
	/**
	 * 获取：
	 */
	public Long getCreaterId() {
		return createrId;
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
	public void setGmtModefied(Date gmtModefied) {
		this.gmtModefied = gmtModefied;
	}
	/**
	 * 获取：
	 */
	public Date getGmtModefied() {
		return gmtModefied;
	}
	/**
	 * 设置：logo
	 */
	public void setLogo(String logo) {
		this.logo = logo;
	}
	/**
	 * 获取：logo
	 */
	public String getLogo() {
		return logo;
	}
	/**
	 * 设置：省id
	 */
	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}
	/**
	 * 获取：省id
	 */
	public Integer getProvinceId() {
		return provinceId;
	}
	/**
	 * 设置：城市
	 */
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	/**
	 * 获取：城市
	 */
	public Integer getCityId() {
		return cityId;
	}
	public List<PhotoGraduationTimeEntity> getTimeList() {
		return timeList;
	}
	public void setTimeList(List<PhotoGraduationTimeEntity> timeList) {
		this.timeList = timeList;
	}
	public List<PhotoCollegeEntity> getCollegeList() {
		return collegeList;
	}
	public void setCollegeList(List<PhotoCollegeEntity> collegeList) {
		this.collegeList = collegeList;
	}
	public List<PhotoTypeEntity> getTypeList() {
		return typeList;
	}
	public void setTypeList(List<PhotoTypeEntity> typeList) {
		this.typeList = typeList;
	}
	
}
