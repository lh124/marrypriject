package io.renren.entity.smart;

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
 * @date 2017-06-18 13:12:52
 */
 @TableName("photo_pic_work_msg")
public class PhotoPicWorkMsgEntity implements Serializable {
	private static final long serialVersionUID = 1L;

		//
	@TableId(value="id",type=IdType.AUTO)
	private Long id;	
		
	//
	@TableField(value="name")
	private String name;
			
	//
	@TableField(value="path_url")
	private String pathUrl;
			
	//
	@TableField(value="pic_size")
	private String picSize;
			
	//
	@TableField(value="info")
	private String info;
			
	//
	@TableField(value="related_id")
	private Long relatedId;
			
	//
	@TableField(value="pic_type")
	private Integer picType;
			
	//
	@TableField(value="gmt_create")
	private Date gmtCreate;
			
	//
	@TableField(value="gmt_modified")
	private Date gmtModified;
			
	//
	@TableField(value="pic_order")
	private Integer picOrder;
			

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
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：
	 */
	public void setPathUrl(String pathUrl) {
		this.pathUrl = pathUrl;
	}
	/**
	 * 获取：
	 */
	public String getPathUrl() {
		return pathUrl;
	}
	/**
	 * 设置：
	 */
	public void setPicSize(String picSize) {
		this.picSize = picSize;
	}
	/**
	 * 获取：
	 */
	public String getPicSize() {
		return picSize;
	}
	/**
	 * 设置：
	 */
	public void setInfo(String info) {
		this.info = info;
	}
	/**
	 * 获取：
	 */
	public String getInfo() {
		return info;
	}
	/**
	 * 设置：
	 */
	public void setRelatedId(Long relatedId) {
		this.relatedId = relatedId;
	}
	/**
	 * 获取：
	 */
	public Long getRelatedId() {
		return relatedId;
	}
	/**
	 * 设置：
	 */
	public void setPicType(Integer picType) {
		this.picType = picType;
	}
	/**
	 * 获取：
	 */
	public Integer getPicType() {
		return picType;
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
	 * 设置：
	 */
	public void setPicOrder(Integer picOrder) {
		this.picOrder = picOrder;
	}
	/**
	 * 获取：
	 */
	public Integer getPicOrder() {
		return picOrder;
	}
}
