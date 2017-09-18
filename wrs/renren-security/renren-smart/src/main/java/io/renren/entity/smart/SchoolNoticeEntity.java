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
 * @date 2017-09-15 14:54:41
 */
 @TableName("school_notice")
public class SchoolNoticeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

		//主键id
	@TableId(value="id",type=IdType.AUTO)
	private Integer id;	
		
	//通知标题
	@TableField(value="title")
	private String title;
			
	//学校id
	@TableField(value="schoolId")
	private Integer schoolid;
			
	//简要说明
	@TableField(value="content")
	private String content;
			
	//照片
	@TableField(value="noticePic")
	private String noticepic;
	
	//发布时间
	@TableField(value="createTime")
	private String createTime;
			

	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
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
	 * 设置：通知标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取：通知标题
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置：学校id
	 */
	public void setSchoolid(Integer schoolid) {
		this.schoolid = schoolid;
	}
	/**
	 * 获取：学校id
	 */
	public Integer getSchoolid() {
		return schoolid;
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
	 * 设置：照片
	 */
	public void setNoticepic(String noticepic) {
		this.noticepic = noticepic;
	}
	/**
	 * 获取：照片
	 */
	public String getNoticepic() {
		return noticepic;
	}
}
