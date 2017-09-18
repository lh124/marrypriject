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
 @TableName("photo_pic_user")
public class PhotoPicUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

		//
	@TableId(value="id",type=IdType.AUTO)
	private Long id;	
		
	//图片名
	@TableField(value="name")
	private String name;
			
	//图片路径
	@TableField(value="pic_path")
	private String picPath;
			
	//图片路径
	@TableField(value="pic_url")
	private String picUrl;
			
	//图片路径
	@TableField(value="pic_type")
	private Integer picType;
			
	//描述信息
	@TableField(value="info")
	private String info;
			
	//用户id
	@TableField(value="user_id")
	private Long userId;
			
	//图片排序
	@TableField(value="pic_order")
	private Integer picOrder;
			
	//
	@TableField(value="gmt_create")
	private Date gmtCreate;
			
	//
	@TableField(value="gmt_modified")
	private Date gmtModified;
			
	//分类id
	@TableField(value="type_id")
	private Long typeId;
	
	//分类id
	@TableField(value="pic_size")
	private String picSize;
			

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
	 * 设置：图片路径
	 */
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	/**
	 * 获取：图片路径
	 */
	public String getPicUrl() {
		return picUrl;
	}
	/**
	 * 设置：图片路径
	 */
	public void setPicType(Integer picType) {
		this.picType = picType;
	}
	/**
	 * 获取：图片路径
	 */
	public Integer getPicType() {
		return picType;
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
	 * 设置：分类id
	 */
	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}
	/**
	 * 获取：分类id
	 */
	public Long getTypeId() {
		return typeId;
	}
	public String getPicSize() {
		return picSize;
	}
	public void setPicSize(String picSize) {
		this.picSize = picSize;
	}
	
	
}
