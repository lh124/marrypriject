package io.renren.model.dto;

import io.renren.validator.group.AddGroup;

import java.util.Date;

import javax.validation.constraints.NotNull;


public class SchoolDto {

	private Long id;	
	
	@NotNull(message="学校名不能为空",groups = {AddGroup.class})
	private String name;
			
	//学校类型，1为中学，2为大学
	@NotNull(message="学校类型不能为空",groups = {AddGroup.class})
	private String schoolType;
			
	private String music;
			
	private String vedio;
			
	private String schoolDesc;
			
	private Long createrId;
			
			
	private String logo;
	@NotNull(message="所属省份不能为空",groups = {AddGroup.class})
	private Integer provinceId;
	@NotNull(message="学校所属城市不能为空",groups = {AddGroup.class})
	private Integer cityId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSchoolType() {
		return schoolType;
	}
	public void setSchoolType(String schoolType) {
		this.schoolType = schoolType;
	}
	public String getMusic() {
		return music;
	}
	public void setMusic(String music) {
		this.music = music;
	}
	public String getVedio() {
		return vedio;
	}
	public void setVedio(String vedio) {
		this.vedio = vedio;
	}
	public String getSchoolDesc() {
		return schoolDesc;
	}
	public void setSchoolDesc(String schoolDesc) {
		this.schoolDesc = schoolDesc;
	}
	public Long getCreaterId() {
		return createrId;
	}
	public void setCreaterId(Long createrId) {
		this.createrId = createrId;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public Integer getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	@Override
	public String toString() {
		return "SchoolDto [id=" + id + ", name=" + name + ", schoolType="
				+ schoolType + ", music=" + music + ", vedio=" + vedio
				+ ", schoolDesc=" + schoolDesc + ", createrId=" + createrId + 
				", logo=" + logo + ", provinceId=" + provinceId + ", cityId="
				+ cityId + "]";
	}
	
	
}
