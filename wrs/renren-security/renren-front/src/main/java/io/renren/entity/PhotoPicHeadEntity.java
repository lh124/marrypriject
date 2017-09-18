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
 @TableName("photo_pic_head")
public class PhotoPicHeadEntity implements Serializable {
	private static final long serialVersionUID = 1L;

		//id
	@TableId(value="id",type=IdType.AUTO)
	private Long id;	
		
	//图片名
	@TableField(value="name")
	private String name;
			
	//描述信息
	@TableField(value="info")
	private String info;
			
	//图片分类
	@TableField(value="pic_type")
	private Integer picType;
			
	//
	@TableField(value="gtm_create")
	private Date gtmCreate;
			
	//
	@TableField(value="gtm_modified")
	private Date gtmModified;
			
	//图片路径
	@TableField(value="pic_path")
	private String picPath;
			
	//用户id
	@TableField(value="user_id")
	private Long userId;
			
	//图片排序
	@TableField(value="pic_order")
	private Integer picOrder;
			
	//拓展id
	@TableField(value="type_id")
	private Long typeId;
			

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
	 * 设置：图片名
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：图片名
	 */
	public String getName() {
		return name;
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
	 * 设置：图片分类
	 */
	public void setPicType(Integer picType) {
		this.picType = picType;
	}
	/**
	 * 获取：图片分类
	 */
	public Integer getPicType() {
		return picType;
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
	/**
	 * 设置：图片路径
	 */
	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}
	/**
	 * 获取：图片路径
	 */
	public String getPicPath() {
		return picPath;
	}
	/**
	 * 设置：用户id
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * 获取：用户id
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * 设置：图片排序
	 */
	public void setPicOrder(Integer picOrder) {
		this.picOrder = picOrder;
	}
	/**
	 * 获取：图片排序
	 */
	public Integer getPicOrder() {
		return picOrder;
	}
	/**
	 * 设置：拓展id
	 */
	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}
	/**
	 * 获取：拓展id
	 */
	public Long getTypeId() {
		return typeId;
	}
}
