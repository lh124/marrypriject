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
 * @date 2017-11-25 10:00:42
 */
 @TableName("group_chat")
public class GroupChatEntity implements Serializable {
	private static final long serialVersionUID = 1L;

		//
	@TableId(value="id",type=IdType.AUTO)
	private Integer id;	
		
	//聊天内容
	@TableField(value="content")
	private String content;
			
	//班级id
	@TableField(value="class_id")
	private Integer classId;
			
	//用户id
	@TableField(value="student_id")
	private Integer studentId;
			
	//消息发送时间
	@TableField(value="createTime")
	private Date createtime;
			

	/**
	 * 设置：
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：聊天内容
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取：聊天内容
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置：班级id
	 */
	public void setClassId(Integer classId) {
		this.classId = classId;
	}
	/**
	 * 获取：班级id
	 */
	public Integer getClassId() {
		return classId;
	}
	/**
	 * 设置：用户id
	 */
	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}
	/**
	 * 获取：用户id
	 */
	public Integer getStudentId() {
		return studentId;
	}
	/**
	 * 设置：消息发送时间
	 */
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	/**
	 * 获取：消息发送时间
	 */
	public Date getCreatetime() {
		return createtime;
	}
}
