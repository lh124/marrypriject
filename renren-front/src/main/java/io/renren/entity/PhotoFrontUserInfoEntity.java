package io.renren.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * 用户信息表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-05-06 11:27:27
 */
 @TableName("photo_front_user_info")
public class PhotoFrontUserInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

		//
	@TableId(value="id",type=IdType.AUTO)
	private Long id;	
		
	//
	@TableField(value="front_user_id")
	private Long frontUserId;
			
	//
	@TableField(value="qq")
	private String qq;
			
	//
	@TableField(value="wechat")
	private String wechat;
			
	//
	@TableField(value="address")
	private String address;
			
	//格言
	@TableField(value="motto")
	private String motto;
			
	//
	@TableField(value="gtm_create")
	private Date gtmCreate;
			
	//
	@TableField(value="gtm_modified")
	private Date gtmModified;
	
	/**
	 * 个人相册查看是否需要密码，1，不需要密码 2,需要密码
	 */
	@TableField(value="perm")
	private Integer perm;
	
	/**
	 * 相册密码
	 */
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
	 * 设置：
	 */
	public void setFrontUserId(Long frontUserId) {
		this.frontUserId = frontUserId;
	}
	/**
	 * 获取：
	 */
	public Long getFrontUserId() {
		return frontUserId;
	}
	/**
	 * 设置：
	 */
	public void setQq(String qq) {
		this.qq = qq;
	}
	/**
	 * 获取：
	 */
	public String getQq() {
		return qq;
	}
	/**
	 * 设置：
	 */
	public void setWechat(String wechat) {
		this.wechat = wechat;
	}
	/**
	 * 获取：
	 */
	public String getWechat() {
		return wechat;
	}
	/**
	 * 设置：
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * 获取：
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * 设置：格言
	 */
	public void setMotto(String motto) {
		this.motto = motto;
	}
	/**
	 * 获取：格言
	 */
	public String getMotto() {
		return motto;
	}
	/**
	 * 设置：
	 */
	public void setGtmCreate(Date gtmCreate) {
		this.gtmCreate = gtmCreate;
	}
	/**
	 * 获取：
	 */
	public Date getGtmCreate() {
		return gtmCreate;
	}
	/**
	 * 设置：
	 */
	public void setGtmModified(Date gtmModified) {
		this.gtmModified = gtmModified;
	}
	/**
	 * 获取：
	 */
	public Date getGtmModified() {
		return gtmModified;
	}
	public Integer getPerm() {
		return perm;
	}
	public void setPerm(Integer perm) {
		this.perm = perm;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
