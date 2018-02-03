package io.renren.entity.married;

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
 * @date 2018-02-02 12:44:59
 */
 @TableName("marry_screen")
public class MarryScreenEntity implements Serializable {
	private static final long serialVersionUID = 1L;

		//
	@TableId(value="id",type=IdType.AUTO)
	private Integer id;	
		
	//
	@TableField(value="userId")
	private Integer userid;
			
	//
	@TableField(value="weddingId")
	private Integer weddingid;
			
	//
	@TableField(value="screenCount")
	private Integer screencount;
			
	@TableField(value="createTime")
	private Date createTime;
	

	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
	 * 设置：
	 */
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	/**
	 * 获取：
	 */
	public Integer getUserid() {
		return userid;
	}
	/**
	 * 设置：
	 */
	public void setWeddingid(Integer weddingid) {
		this.weddingid = weddingid;
	}
	/**
	 * 获取：
	 */
	public Integer getWeddingid() {
		return weddingid;
	}
	/**
	 * 设置：
	 */
	public void setScreencount(Integer screencount) {
		this.screencount = screencount;
	}
	/**
	 * 获取：
	 */
	public Integer getScreencount() {
		return screencount;
	}
}
