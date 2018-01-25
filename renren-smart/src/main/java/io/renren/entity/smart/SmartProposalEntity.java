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
 * @date 2018-01-25 15:47:28
 */
 @TableName("smart_proposal")
public class SmartProposalEntity implements Serializable {
	private static final long serialVersionUID = 1L;

		//学校id
	@TableId(value="id",type=IdType.AUTO)
	private Integer id;	
		
	//标题
	@TableField(value="title")
	private String title;
			
	//建议反馈具体内容
	@TableField(value="content")
	private String content;
			
	//
	@TableField(value="school_id")
	private Integer schoolId;
			

	/**
	 * 设置：学校id
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：学校id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取：标题
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置：建议反馈具体内容
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取：建议反馈具体内容
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置：
	 */
	public void setSchoolId(Integer schoolId) {
		this.schoolId = schoolId;
	}
	/**
	 * 获取：
	 */
	public Integer getSchoolId() {
		return schoolId;
	}
}
