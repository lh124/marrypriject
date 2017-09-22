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
 * @date 2017-09-20 16:07:00
 */
 @TableName("freshman_guide")
public class FreshmanGuideEntity implements Serializable {
	private static final long serialVersionUID = 1L;

		//主键id
	@TableId(value="id",type=IdType.AUTO)
	private Integer id;	
		
	//步骤
	@TableField(value="title")
	private String title;
			
	//图片
	@TableField(value="freshmanPic")
	private String freshmanpic;
			
	//简要说明
	@TableField(value="content")
	private String content;
			
	//排序
	@TableField(value="orderNo")
	private Integer orderno;
	
	//学校id
	@TableField(value="schoolId")
	private Integer schoolId;
			

	public Integer getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(Integer schoolId) {
		this.schoolId = schoolId;
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
	 * 设置：步骤
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取：步骤
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置：图片
	 */
	public void setFreshmanpic(String freshmanpic) {
		this.freshmanpic = freshmanpic;
	}
	/**
	 * 获取：图片
	 */
	public String getFreshmanpic() {
		return freshmanpic;
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
	 * 设置：排序
	 */
	public void setOrderno(Integer orderno) {
		this.orderno = orderno;
	}
	/**
	 * 获取：排序
	 */
	public Integer getOrderno() {
		return orderno;
	}
}
