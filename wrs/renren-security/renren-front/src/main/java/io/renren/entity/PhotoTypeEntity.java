package io.renren.entity;

import io.renren.validator.group.AddGroup;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * 相册分类表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-05-06 11:27:27
 */
 @TableName("photo_type")
public class PhotoTypeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

		//
	@TableId(value="id",type=IdType.AUTO)
	private Long id;	
		
	//标题
	@TableField(value="title")
	@NotBlank(message="标题不能为空", groups = {AddGroup.class})
	private String title;
			
	//内容
	@TableField(value="content")
	private String content;
			
	//
	@TableField(value="gtm_create")
	private Date gtmCreate;
			
	//
	@TableField(value="gtm_modified")
	private Date gtmModified;
			
	//分类
	@TableField(value="type")
	private Integer type;
			
	//关联id
	@TableField(value="related_id")
	@NotNull(message="关联id不能为空")
	private Long relatedId;
			
	//创建者id
	@TableField(value="creater_id")
	private Long createrId;
	
	//分类排序
	@TableField(value="type_order")
	@NotNull(message="排序不能为空")
	private Integer typeOrder;
			
	// 对象装载
	@TableField(exist=false)
	private List<PhotoPicClassEntity> picList;
	
	@TableField(exist=false)
	private List<PhotoPicUserEntity> userPicList;
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
	 * 设置：标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取：标题
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置：内容
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取：内容
	 */
	public String getContent() {
		return content;
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
	 * 设置：分类
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取：分类
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * 设置：关联id
	 */
	public void setRelatedId(Long relatedId) {
		this.relatedId = relatedId;
	}
	/**
	 * 获取：关联id
	 */
	public Long getRelatedId() {
		return relatedId;
	}
	/**
	 * 设置：创建者id
	 */
	public void setCreaterId(Long createrId) {
		this.createrId = createrId;
	}
	/**
	 * 获取：创建者id
	 */
	public Long getCreaterId() {
		return createrId;
	}
	public Integer getTypeOrder() {
		return typeOrder;
	}
	public void setTypeOrder(Integer typeOrder) {
		this.typeOrder = typeOrder;
	}
	
	public List<PhotoPicClassEntity> getPicList() {
		return picList;
	}
	public void setPicList(List<PhotoPicClassEntity> picList) {
		this.picList = picList;
	}
	
	public List<PhotoPicUserEntity> getUserPicList() {
		return userPicList;
	}
	public void setUserPicList(List<PhotoPicUserEntity> userPicList) {
		this.userPicList = userPicList;
	}
	
	@Override
	public String toString() {
		return "PhotoTypeEntity [id=" + id + ", title=" + title + ", content="
				+ content + ", gtmCreate=" + gtmCreate + ", gtmModified="
				+ gtmModified + ", type=" + type + ", relatedId=" + relatedId
				+ ", createrId=" + createrId + ", typeOrder=" + typeOrder
				+ ", picList=" + picList + "]";
	}
	
	
}
