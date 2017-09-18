package io.renren.entity.smart;

import io.renren.validator.group.AddGroup;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-06-17 16:11:02
 */
 @TableName("photo_class_work_msg")
public class PhotoClassWorkMsgEntity implements Serializable {
	private static final long serialVersionUID = 1L;

		//
	@TableId(value="id",type=IdType.AUTO)
	private Long id;	
		
	//
	@NotNull(message="关联班级不能为空",groups={AddGroup.class})
	@TableField(value="class_id")
	private Long classId;
			
	//
	@TableField(value="user_id")
	@NotNull(message="关联用户不能为空",groups={AddGroup.class})
	private Long userId;
			
	//
	@TableField(value="content")
	private String content;
			
	//
	@TableField(value="gmt_create")
	private Date gmtCreate;
			
	//
	@TableField(value="gmt_modified")
	private Date gmtModified;
			
	//
	@TableField(value="status")
	private Integer status;
			
	//
	@TableField(value="voice")
	private String voice;
			

	//关联数据
	@TableField(exist=false)
	private List<PhotoPicWorkMsgEntity> picList;
	@TableField(exist=false)
	private StudentEntity student;
	
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
	public void setClassId(Long classId) {
		this.classId = classId;
	}
	/**
	 * 获取：
	 */
	public Long getClassId() {
		return classId;
	}
	/**
	 * 设置：
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * 获取：
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * 设置：
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取：
	 */
	public String getContent() {
		return content;
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
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置：
	 */
	public void setVoice(String voice) {
		this.voice = voice;
	}
	/**
	 * 获取：
	 */
	public String getVoice() {
		return voice;
	}
	public List<PhotoPicWorkMsgEntity> getPicList() {
		return picList;
	}
	public void setPicList(List<PhotoPicWorkMsgEntity> picList) {
		this.picList = picList;
	}
	public StudentEntity getStudent() {
		return student;
	}
	public void setStudent(StudentEntity student) {
		this.student = student;
	}
	
}
