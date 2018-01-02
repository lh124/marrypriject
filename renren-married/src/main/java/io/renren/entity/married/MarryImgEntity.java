package io.renren.entity.married;

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
 * @date 2017-12-28 09:15:04
 */
 @TableName("marry_img")
public class MarryImgEntity implements Serializable {
	private static final long serialVersionUID = 1L;

		//
	@TableId(value="id",type=IdType.AUTO)
	private Integer id;	
		
	//图片路径
	@TableField(value="url")
	private String url;
			
	//图片类型（1为婚礼背景图）
	@TableField(value="img_type")
	private Integer imgType;
			

	/**
	 * 设置：
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：图片路径
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * 获取：图片路径
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * 设置：图片类型（1为婚礼背景图）
	 */
	public void setImgType(Integer imgType) {
		this.imgType = imgType;
	}
	/**
	 * 获取：图片类型（1为婚礼背景图）
	 */
	public Integer getImgType() {
		return imgType;
	}
}
