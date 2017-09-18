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
 @TableName("photo_function_modules")
public class PhotoFunctionModulesEntity implements Serializable {
	private static final long serialVersionUID = 1L;

		//id
	@TableId(value="id",type=IdType.AUTO)
	private Integer id;	
		
	//功能名
	@TableField(value="name")
	private String name;
			
	//跳转路径
	@TableField(value="url")
	private String url;
			
	//位置
	@TableField(value="function_order")
	private Integer functionOrder;
			
	//
	@TableField(value="gmt_create")
	private Date gmtCreate;
			
	//
	@TableField(value="gmt_modified")
	private Date gmtModified;
			
	//权限
	@TableField(value="perm")
	private String perm;
	
	//图标
	@TableField(value="icon")
	private String icon;
			

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
	 * 设置：功能名
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：功能名
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：跳转路径
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * 获取：跳转路径
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * 设置：位置
	 */
	public void setFunctionOrder(Integer functionOrder) {
		this.functionOrder = functionOrder;
	}
	/**
	 * 获取：位置
	 */
	public Integer getFunctionOrder() {
		return functionOrder;
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
	 * 设置：权限
	 */
	public void setPerm(String perm) {
		this.perm = perm;
	}
	/**
	 * 获取：权限
	 */
	public String getPerm() {
		return perm;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	
}
