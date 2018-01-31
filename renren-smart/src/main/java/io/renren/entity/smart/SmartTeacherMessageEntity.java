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
 * @author yuanbichang
 * @email 673290684@qq.com
 * @date 2018-01-30 09:33:09
 */
 @TableName("smart_teacher_message")
public class SmartTeacherMessageEntity implements Serializable {
	private static final long serialVersionUID = 1L;

		//主键id
	@TableId(value="id",type=IdType.AUTO)
	private Integer id;	
		
	//考试主题id
	@TableField(value="examination_id")
	private Integer examinationId;
			
	//考试科目id
	@TableField(value="subject_id")
	private Integer subjectId;
			
	//用户id
	@TableField(value="user_id")
	private Integer userId;
			
	//寄语内容
	@TableField(value="content")
	private String content;
			
	//创建时间
	@TableField(value="create_time")
	private Date createTime;
			

	/**
	 * 设置：主键id
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：主键id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：考试主题id
	 */
	public void setExaminationId(Integer examinationId) {
		this.examinationId = examinationId;
	}
	/**
	 * 获取：考试主题id
	 */
	public Integer getExaminationId() {
		return examinationId;
	}
	/**
	 * 设置：考试科目id
	 */
	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}
	/**
	 * 获取：考试科目id
	 */
	public Integer getSubjectId() {
		return subjectId;
	}
	/**
	 * 设置：用户id
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	/**
	 * 获取：用户id
	 */
	public Integer getUserId() {
		return userId;
	}
	/**
	 * 设置：寄语内容
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取：寄语内容
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
}
