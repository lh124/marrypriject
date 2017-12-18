package io.renren.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;



/**
 * 用户Token
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-23 15:22:07
 */
public class TokenEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//用户ID
	@TableId(value="user_id", type=IdType.AUTO)
	private Long userId;
	//token
	@TableField(value="token")
	private String token;
	
	@TableField(value="accessToken")
	private String accessToken;
	
	//过期时间
	@TableField(value="expire_time")
	private Date expireTime;
	//更新时间
	@TableField(value="update_time")
	private Date updateTime;
	// 对应程序APPId
	@TableField(value="app")
	private Integer app;

	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	/**
	 * 设置：用户ID
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * 获取：用户ID
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * 设置：token
	 */
	public void setToken(String token) {
		this.token = token;
	}
	/**
	 * 获取：token
	 */
	public String getToken() {
		return token;
	}
	/**
	 * 设置：过期时间
	 */
	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}
	/**
	 * 获取：过期时间
	 */
	public Date getExpireTime() {
		return expireTime;
	}
	/**
	 * 设置：更新时间
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 获取：更新时间
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
	public Integer getApp() {
		return app;
	}
	public void setApp(Integer app) {
		this.app = app;
	}
	
	
}
