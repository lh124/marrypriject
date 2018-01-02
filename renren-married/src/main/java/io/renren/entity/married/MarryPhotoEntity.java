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
 * @date 2018-01-02 10:37:17
 */
 @TableName("marry_photo")
public class MarryPhotoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

		//
	@TableId(value="id",type=IdType.AUTO)
	private Integer id;	
		
	//婚礼id
	@TableField(value="weddingId")
	private Integer weddingid;
			
	//图片路径
	@TableField(value="pic")
	private String pic;
	
	//类型（1摄影，2视频）
	@TableField(value="type")
	private Integer type;
			

	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
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
	 * 设置：婚礼id
	 */
	public void setWeddingid(Integer weddingid) {
		this.weddingid = weddingid;
	}
	/**
	 * 获取：婚礼id
	 */
	public Integer getWeddingid() {
		return weddingid;
	}
	/**
	 * 设置：图片路径
	 */
	public void setPic(String pic) {
		this.pic = pic;
	}
	/**
	 * 获取：图片路径
	 */
	public String getPic() {
		return pic;
	}
}
