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
 @TableName("photo_class_wall")
public class PhotoClassWallEntity implements Serializable {
	private static final long serialVersionUID = 1L;

		//id
	@TableId(value="id",type=IdType.AUTO)
	private Long id;	
		
	//留言内容
	@TableField(value="content")
	private String content;
			
	//
	@TableField(value="gmt_modified")
	private Date gmtModified;
			
	//
	@TableField(value="gmt_create")
	private Date gmtCreate;
			
	//班级id
	@TableField(value="class_id")
	private Long classId;
			
	//
	@TableField(value="music")
	private String music;
			
	//
	@TableField(value="vedio")
	private String vedio;
			

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
	 * 设置：留言内容
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取：留言内容
	 */
	public String getContent() {
		return content;
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
	 * 设置：班级id
	 */
	public void setClassId(Long classId) {
		this.classId = classId;
	}
	/**
	 * 获取：班级id
	 */
	public Long getClassId() {
		return classId;
	}
	/**
	 * 设置：
	 */
	public void setMusic(String music) {
		this.music = music;
	}
	/**
	 * 获取：
	 */
	public String getMusic() {
		return music;
	}
	/**
	 * 设置：
	 */
	public void setVedio(String vedio) {
		this.vedio = vedio;
	}
	/**
	 * 获取：
	 */
	public String getVedio() {
		return vedio;
	}
}
