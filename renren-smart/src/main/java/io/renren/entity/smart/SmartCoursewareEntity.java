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
 * @date 2017-09-21 15:11:15
 */
 @TableName("smart_courseware")
public class SmartCoursewareEntity implements Serializable {
	private static final long serialVersionUID = 1L;

		//主键id
	@TableId(value="id",type=IdType.AUTO)
	private Integer id;	
		
	//课件名称
	@TableField(value="name")
	private String name;
			
	//课件路径
	@TableField(value="pic")
	private String pic;
			
	//课件上传时间
	@TableField(value="createTime")
	private String createtime;
			
	//班级id
	@TableField(value="classid")
	private Integer classid;
	
	//是否共享
	@TableField(value="type")
	private Integer type;
			

	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
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
	 * 设置：课件名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：课件名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：课件路径
	 */
	public void setPic(String pic) {
		this.pic = pic;
	}
	/**
	 * 获取：课件路径
	 */
	public String getPic() {
		return pic;
	}
	/**
	 * 设置：课件上传时间
	 */
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	/**
	 * 获取：课件上传时间
	 */
	public String getCreatetime() {
		return createtime;
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
