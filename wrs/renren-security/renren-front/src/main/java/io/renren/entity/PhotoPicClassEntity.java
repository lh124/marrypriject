package io.renren.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-05-06 11:27:27
 */
 @TableName("photo_pic_class")
public class PhotoPicClassEntity implements Serializable {
	private static final long serialVersionUID = 1L;

		//id
	@TableId(value="id",type=IdType.AUTO)
	private Long id;	
		
	//图片名
	@TableField(value="name")
	private String name;
			
	//图片类型
	@TableField(value="pic_type")
	private Integer picType;
			
	//描述信息
	@TableField(value="info")
	private String info;
			
	//班级id
	@TableField(value="class_id")
	private Long classId;
			
	//
	@TableField(value="gtm_create")
	private Date gtmCreate;
			
	//
	@TableField(value="gtm_modified")
	private Date gtmModified;
			
	//图片路径
	@TableField(value="pic_path")
	private String picPath;
			
	//用于照片顺序
	@TableField(value="pic_order")
	private Integer picOrder;
			
	//扩展
	@TableField(value="type_id")
	private Long typeId;
	
	//图片大小
	@TableField(value="pic_size")
	private String picSize;
			

	/**
	 * 设置：id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：图片名
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：图片名
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：图片类型
	 */
	public void setPicType(Integer picType) {
		this.picType = picType;
	}
	/**
	 * 获取：图片类型
	 */
	public Integer getPicType() {
		return picType;
	}
	/**
	 * 设置：描述信息
	 */
	public void setInfo(String info) {
		this.info = info;
	}
	/**
	 * 获取：描述信息
	 */
	public String getInfo() {
		return info;
	}
	/**
	 * 设置：班级id
	 */
	public void setClassId(Long classId) {
		this.classId = classId;
	}
	/**
	 * 获取：班级id
	 */
	public Long getClassId() {
		return classId;
	}
	/**
	 * 设置：
	 */
	public void setGtmCreate(Date gtmCreate) {
		this.gtmCreate = gtmCreate;
	}
	/**
	 * 获取：
	 */
	public Date getGtmCreate() {
		return gtmCreate;
	}
	/**
	 * 设置：
	 */
	public void setGtmModified(Date gtmModified) {
		this.gtmModified = gtmModified;
	}
	/**
	 * 获取：
	 */
	public Date getGtmModified() {
		return gtmModified;
	}
	/**
	 * 设置：图片路径
	 */
	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}
	/**
	 * 获取：图片路径
	 */
	public String getPicPath() {
		return picPath;
	}
	/**
	 * 设置：用于照片顺序
	 */
	public void setPicOrder(Integer picOrder) {
		this.picOrder = picOrder;
	}
	/**
	 * 获取：用于照片顺序
	 */
	public Integer getPicOrder() {
		return picOrder;
	}
	/**
	 * 设置：扩展
	 */
	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}
	/**
	 * 获取：扩展
	 */
	public Long getTypeId() {
		return typeId;
	}
	public String getPicSize() {
		return picSize;
	}
	public void setPicSize(String picSize) {
		this.picSize = picSize;
	}
	@Override
	public String toString() {
		return "PhotoPicClassEntity [id=" + id + ", name=" + name
				+ ", picType=" + picType + ", info=" + info + ", classId="
				+ classId + ", gtmCreate=" + gtmCreate + ", gtmModified="
				+ gtmModified + ", picPath=" + picPath + ", picOrder="
				+ picOrder + ", typeId=" + typeId + ", picSize=" + picSize
				+ "]";
	}
	
	
}
