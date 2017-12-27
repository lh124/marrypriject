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
 * @date 2017-09-19 09:12:53
 */
 @TableName("school_notice")
public class SchoolNoticeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

		//主键ID
	@TableId(value="id",type=IdType.AUTO)
	private Integer id;	
		
	//标题
	@TableField(value="title")
	private String title;
			
	//学校id
	@TableField(value="schoolId")
	private Integer schoolid;
			
	//简要说明
	@TableField(value="content")
	private String content;
			
	//图片路径
	@TableField(value="noticePic")
	private String noticepic;
			
	//
	@TableField(value="createTime")
	private String createtime;
	
	private Integer newsType;
	
	private Integer newsId;
			

	public Integer getNewsType() {
		return newsType;
	}
	public void setNewsType(Integer newsType) {
		this.newsType = newsType;
	}
	public Integer getNewsId() {
		return newsId;
	}
	public void setNewsId(Integer newsId) {
		this.newsId = newsId;
	}
	/**
	 * 设置：主键ID
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：主键ID
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
	 * 设置：图片路径
	 */
	public void setNoticepic(String noticepic) {
		this.noticepic = noticepic;
	}
	/**
	 * 获取：图片路径
	 */
	public String getNoticepic() {
		return noticepic;
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
