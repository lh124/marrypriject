package io.renren.entity.smart;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * 
 * 
 * @author yuanbichang
 * @email 673290684@qq.com
 * @date 2017-09-21 13:38:19
 */
 @TableName("smart_work")
public class SmartWorkEntity implements Serializable {
	private static final long serialVersionUID = 1L;

		//主键id
	@TableId(value="id",type=IdType.AUTO)
	private Integer id;	
		
	//作业名称
	@TableField(value="name")
	private String name;
			
	//作业类型（1为家庭作业，2课堂作业
	@TableField(value="type")
	private Integer type;
	
	@TableField(value="classid")
	private Integer classid;
			
	//图片
	@TableField(value="pic")
	private String pic;
			
	//简要说明
	@TableField(value="content")
	private String content;
	
	//科目
	@TableField(value="subject")
	private String subject;
	
	//发布人id
	@TableField(value="teacherId")
	private Integer teacherId;
	
	//发布人名字
	@TableField(value="teacherName")
	private String teacherName;
	//消息状态（0为未读，1为已读)
	@TableField(value="newsType")
	private Integer newsType;
	
	//发布人头像
	@TableField(value="teacherPic")
	private String teacherPic;
	
	public String getTeacherPic() {
		return teacherPic;
	}
	public void setTeacherPic(String teacherPic) {
		this.teacherPic = teacherPic;
	}
	@TableField(value="newsId")
	private Integer newsId;
			
	public Integer getNewsId() {
		return newsId;
	}
	public void setNewsId(Integer newsId) {
		this.newsId = newsId;
	}
	public Integer getNewsType() {
		return newsType;
	}
	public void setNewsType(Integer newsType) {
		this.newsType = newsType;
	}
	public Integer getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	//
	@TableField(value="createTime")
	private String createtime;
			

	public Integer getClassid() {
		return classid;
	}
	public void setClassid(Integer classid) {
		this.classid = classid;
	}
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
	 * 设置：作业名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：作业名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：作业类型（1为家庭作业，2课堂作业）
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取：作业类型（1为家庭作业，2课堂作业）
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * 设置：图片
	 */
	public void setPic(String pic) {
		this.pic = pic;
	}
	/**
	 * 获取：图片
	 */
	public String getPic() {
		return pic;
	}
	/**
	 * 设置：简要说明
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取：简要说明
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置：
	 */
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	/**
	 * 获取：
	 */
	public String getCreatetime() {
		return createtime;
	}
}
