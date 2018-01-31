package io.renren.entity.smart;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * 分数记录
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-05-27 16:53:07
 */
 @TableName("photo_score")
public class PhotoScoreEntity implements Serializable {
	private static final long serialVersionUID = 1L;

		//
	@TableId(value="id",type=IdType.AUTO)
	private Long id;	
		
	//科目id
	@TableField(value="subject_id")
	private Long subjectId;
			
	//科目名
	@TableField(value="subject_name")
	private String subjectName;
			
	// 分数
	@TableField(value="subject_point")
	private Float subjectPoint;
			
	//学生id
	@TableField(value="front_user_id")
	private Long frontUserId;
			
	//
	@TableField(value="gmt_modified")
	private Date gmtModified;
			
	//
	@TableField(value="gmt_create")
	private Date gmtCreate;
			
	//老师评论
	@TableField(value="teacher_comment")
	private String teacherComment;
	
	@TableField(value="teacherName")
	private String teacherName;
	
	@TableField(value="teacherPic")
	private String teacherPic;
	
	//考试主题id
	@TableField(value="examination_id")
	private Long examinationId;
	
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public String getTeacherPic() {
		return teacherPic;
	}
	public void setTeacherPic(String teacherPic) {
		this.teacherPic = teacherPic;
	}
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
	 * 设置：科目id
	 */
	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}
	/**
	 * 获取：科目id
	 */
	public Long getSubjectId() {
		return subjectId;
	}
	/**
	 * 设置：科目名
	 */
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	/**
	 * 获取：科目名
	 */
	public String getSubjectName() {
		return subjectName;
	}
	/**
	 * 设置： 分数
	 */
	public void setSubjectPoint(Float subjectPoint) {
		this.subjectPoint = subjectPoint;
	}
	/**
	 * 获取： 分数
	 */
	public Float getSubjectPoint() {
		return subjectPoint;
	}
	/**
	 * 设置：学生id
	 */
	public void setFrontUserId(Long frontUserId) {
		this.frontUserId = frontUserId;
	}
	/**
	 * 获取：学生id
	 */
	public Long getFrontUserId() {
		return frontUserId;
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
	 * 设置：老师评论
	 */
	public void setTeacherComment(String teacherComment) {
		this.teacherComment = teacherComment;
	}
	/**
	 * 获取：老师评论
	 */
	public String getTeacherComment() {
		return teacherComment;
	}
	public Long getExaminationId() {
		return examinationId;
	}
	public void setExaminationId(Long examinationId) {
		this.examinationId = examinationId;
	}
	
	
}
