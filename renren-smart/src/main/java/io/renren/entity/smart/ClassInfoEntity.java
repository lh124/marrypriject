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
 * @date 2017-09-22 09:38:52
 */
 @TableName("class_info")
public class ClassInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

		//主键id
	@TableId(value="id",type=IdType.AUTO)
	private Integer id;	
		 
	//姓名
	@TableField(value="name")
	private String name;
			
	//职务
	@TableField(value="type")
	private Integer type;
			
	//班级id
	@TableField(value="classid")
	private Integer classid;
	
	//班级寄语
	@TableField(value="content")
	private String content;
	
	@TableField(value="classPost")
	private String classPost;
	
	public String getClassPost() {
		return classPost;
	}
	public void setClassPost(String classPost) {
		this.classPost = classPost;
	}
	@TableField(value="userId")
	private Integer userId;
			

	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	 * 设置：姓名
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：姓名
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：职务
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取：职务
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * 设置：班级id
	 */
	public void setClassid(Integer classid) {
		this.classid = classid;
	}
	/**
	 * 获取：班级id
	 */
	public Integer getClassid() {
		return classid;
	}
}
