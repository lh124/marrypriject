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
 @TableName("photo_family")
public class PhotoFamilyEntity implements Serializable {
	private static final long serialVersionUID = 1L;

		//
	@TableId(value="id",type=IdType.AUTO)
	private Long id;	
		
	//家庭名字
	@TableField(value="name")
	private String name;
			
	//家庭描述
	@TableField(value="info")
	private String info;
			
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
			
	//创建者id
	@TableField(value="user_id")
	private Long userId;
			
	//使用状态
	@TableField(value="status")
	private Integer status;
			
	//查看密码
	@TableField(value="password")
	private String password;
			

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
	 * 设置：家庭名字
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：家庭名字
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：家庭描述
	 */
	public void setInfo(String info) {
		this.info = info;
	}
	/**
	 * 获取：家庭描述
	 */
	public String getInfo() {
		return info;
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
	/**
	 * 设置：创建者id
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * 获取：创建者id
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * 设置：使用状态
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：使用状态
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置：查看密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * 获取：查看密码
	 */
	public String getPassword() {
		return password;
	}
}
