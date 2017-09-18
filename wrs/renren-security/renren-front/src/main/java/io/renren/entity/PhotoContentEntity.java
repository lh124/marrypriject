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
 @TableName("photo_content")
public class PhotoContentEntity implements Serializable {
	private static final long serialVersionUID = 1L;

		//
	@TableId(value="id",type=IdType.AUTO)
	private Long id;	
		
	//内容
	@TableField(value="content")
	private String content;
			
	//创建者id
	@TableField(value="creater_id")
	private Long createrId;
			
	//
	@TableField(value="gmt_create")
	private Date gmtCreate;
			
	//
	@TableField(value="gmt_modified")
	private Date gmtModified;
			
	//所属分类
	@TableField(value="gmt_type")
	private Integer gmtType;
			
	//扩展id
	@TableField(value="gmt_id")
	private Long gmtId;
			

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
	 * 设置：所属分类
	 */
	public void setGmtType(Integer gmtType) {
		this.gmtType = gmtType;
	}
	/**
	 * 获取：所属分类
	 */
	public Integer getGmtType() {
		return gmtType;
	}
	/**
	 * 设置：扩展id
	 */
	public void setGmtId(Long gmtId) {
		this.gmtId = gmtId;
	}
	/**
	 * 获取：扩展id
	 */
	public Long getGmtId() {
		return gmtId;
	}
}
