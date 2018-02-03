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
 * @date 2017-09-21 09:34:56
 */
 @TableName("psychological_counseling")
public class PsychologicalCounselingEntity implements Serializable {
	private static final long serialVersionUID = 1L;

		//主键id
	@TableId(value="id",type=IdType.AUTO)
	private Integer id;	
		
	//咨询主题
	@TableField(value="title")
	private String title;
			
	//图片
	@TableField(value="pic")
	private String pic;
			
	//学校Id
	@TableField(value="schoolId")
	private Integer schoolid;
			
	//咨询内容简介
	@TableField(value="content")
	private String content;
	
	@TableField(value="createTime")
	private Date createTime;
			

	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
	 * 设置：咨询主题
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取：咨询主题
	 */
	public String getTitle() {
		return title;
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
	 * 设置：学校Id
	 */
	public void setSchoolid(Integer schoolid) {
		this.schoolid = schoolid;
	}
	/**
	 * 获取：学校Id
	 */
	public Integer getSchoolid() {
		return schoolid;
	}
	/**
	 * 设置：咨询内容简介
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取：咨询内容简介
	 */
	public String getContent() {
		return content;
	}
}
