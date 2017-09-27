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
 * @date 2017-09-27 09:40:45
 */
 @TableName("sys_weixin")
public class SysWeixinEntity implements Serializable {
	private static final long serialVersionUID = 1L;

		//主键id
	@TableId(value="id",type=IdType.AUTO)
	private Integer id;	
		
	//名称
	@TableField(value="name")
	private String name;
			
	//
	@TableField(value="appid")
	private String appid;
			
	//
	@TableField(value="appsecret")
	private String appsecret;
			
	//时间
	@TableField(value="createtime")
	private String createtime;
			

	/**
	 * 设置：主键id
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：主键id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：
	 */
	public void setAppid(String appid) {
		this.appid = appid;
	}
	/**
	 * 获取：
	 */
	public String getAppid() {
		return appid;
	}
	/**
	 * 设置：
	 */
	public void setAppsecret(String appsecret) {
		this.appsecret = appsecret;
	}
	/**
	 * 获取：
	 */
	public String getAppsecret() {
		return appsecret;
	}
	/**
	 * 设置：时间
	 */
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	/**
	 * 获取：时间
	 */
	public String getCreatetime() {
		return createtime;
	}
}
