package io.renren.entity;

import io.renren.validator.group.AddGroup;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-05-04 15:07:49
 */
 @TableName("photo_class_msg")
public class PhotoClassMsglEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	//
	@TableId(value="id",type=IdType.AUTO)
	private Long id;	
		
	//
	@TableField(value="class_id")
	@NotNull(message="班级标识不能为空", groups={AddGroup.class})
	private Long classId;
			
	//
	@TableField(value="user_id")
	private Long userId;
			
	//
	@TableField(value="user_name")
	private String userName;
			
	//
	@TableField(value="contents")
	@NotBlank(message="内容不能为空", groups={AddGroup.class})
	private String contents;
			
	//
	@TableField(value="gmt_create")
	private Date gmtCreate;
			
	//
	@TableField(value="gmt_modified")
	private Date gmtModified;
			
	@TableField(exist=false)
	private String nickname;
	
	@TableField(exist=false)
	private String headImg;
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
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * 获取：
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * 设置：
	 */
	public void setContents(String contents) {
		this.contents = contents;
	}
	/**
	 * 获取：
	 */
	public String getContents() {
		return contents;
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
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getHeadImg() {
		return headImg;
	}
	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}
	
	
}
