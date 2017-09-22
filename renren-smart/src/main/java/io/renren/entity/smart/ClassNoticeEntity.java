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
 * @date 2017-09-20 10:04:06
 */
 @TableName("class_notice")
public class ClassNoticeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

		//通知id
	@TableId(value="id",type=IdType.AUTO)
	private Integer id;	
		
	//通知标题
	@TableField(value="title")
	private String title;
			
	//图片路径
	@TableField(value="noticePic")
	private String noticepic;
			
	//简要说明
	@TableField(value="content")
	private String content;
			
	//发布时间
	@TableField(value="createTime")
	private String createtime;
	
	//发布时间
	@TableField(value="classId")
	private String classId;
			

	public String getClassId() {
		return classId;
	}
	public void setClassId(String classId) {
		this.classId = classId;
	}
	/**
	 * 设置：通知id
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：通知id
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
	 * 设置：发布时间
	 */
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	/**
	 * 获取：发布时间
	 */
	public String getCreatetime() {
		return createtime;
	}
}
