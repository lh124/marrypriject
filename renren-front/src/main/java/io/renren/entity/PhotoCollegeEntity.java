package io.renren.entity;

import java.io.Serializable;
import java.util.Date;

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
 @TableName("photo_college")
public class PhotoCollegeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

		//id
	@TableId(value="id",type=IdType.AUTO)
	private Long id;	
		
	//学院名
	@TableField(value="name")
	private String name;
			
	//学校id
	@TableField(value="school_id")
	private Long schoolId;
			
	//描述信息
	@TableField(value="info")
	private String info;
			
	//logo
	@TableField(value="logo")
	private String logo;
			
	//背景音乐
	@TableField(value="music")
	private String music;
			
	//视频
	@TableField(value="vedio")
	private String vedio;
			
	//
	@TableField(value="gmt_create")
	private Date gmtCreate;
			
	//
	@TableField(value="gmt_modified")
	private Date gmtModified;
			

	/**
	 * 设置：id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：学院名
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：学院名
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：学校id
	 */
	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}
	/**
	 * 获取：学校id
	 */
	public Long getSchoolId() {
		return schoolId;
	}
	/**
	 * 设置：描述信息
	 */
	public void setInfo(String info) {
		this.info = info;
	}
	/**
	 * 获取：描述信息
	 */
	public String getInfo() {
		return info;
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
}
