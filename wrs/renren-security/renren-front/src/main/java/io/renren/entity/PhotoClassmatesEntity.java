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
 * @date 2017-05-06 11:27:27
 */
 @TableName("photo_classmates")
public class PhotoClassmatesEntity implements Serializable {
	private static final long serialVersionUID = 1L;

		//id
	@TableId(value="id",type=IdType.AUTO)
	private Long id;	
		
	//写同学录的用户
	@TableField(value="user_id")
	private Long userId;
			
	//获得同学录的用户
	@NotNull(message="用户不能为空",groups={AddGroup.class})
	@TableField(value="user_get_id")
	private Long userGetId;
			
	//班级id
	@TableField(value="class_id")
	private Long classId;
			
	//同学录内容
	@NotBlank(message="内容不能为空",groups={AddGroup.class})
	@TableField(value="contents")
	private String contents;
			
	//
	@TableField(value="gmt_create")
	private Date gmtCreate;
			
	//
	@TableField(value="gmt_modified")
	private Date gmtModified;
	
	//一下是查询字段，不保存至数据库
	
	@TableField(exist=false)
	private String nickname;
	
	@TableField(exist=false)
	private String headImg;
			

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
	 * 设置：写同学录的用户
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * 获取：写同学录的用户
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * 设置：获得同学录的用户
	 */
	public void setUserGetId(Long userGetId) {
		this.userGetId = userGetId;
	}
	/**
	 * 获取：获得同学录的用户
	 */
	public Long getUserGetId() {
		return userGetId;
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
	 * 设置：同学录内容
	 */
	public void setContents(String contents) {
		this.contents = contents;
	}
	/**
	 * 获取：同学录内容
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
