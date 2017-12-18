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
 * @date 2017-12-16 13:59:48
 */
 @TableName("marry_wedding")
public class MarryWeddingEntity implements Serializable {
	private static final long serialVersionUID = 1L;

		//
	@TableId(value="id",type=IdType.AUTO)
	private Integer id;	
	
	@TableField(value="userId")
	private Integer userId;
		
	//新郎姓名
	@TableField(value="groomName")
	private String groomname;
			
	//新娘姓名
	@TableField(value="brideName")
	private String bridename;
			
	//婚礼日期
	@TableField(value="weddingDate")
	private Date weddingdate;
			
	//婚礼地址
	@TableField(value="weddingAddress")
	private String weddingaddress;
			
	//婚礼留言
	@TableField(value="content")
	private String content;
			

	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
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
	 * 设置：新郎姓名
	 */
	public void setGroomname(String groomname) {
		this.groomname = groomname;
	}
	/**
	 * 获取：新郎姓名
	 */
	public String getGroomname() {
		return groomname;
	}
	/**
	 * 设置：新娘姓名
	 */
	public void setBridename(String bridename) {
		this.bridename = bridename;
	}
	/**
	 * 获取：新娘姓名
	 */
	public String getBridename() {
		return bridename;
	}
	/**
	 * 设置：婚礼日期
	 */
	public void setWeddingdate(Date weddingdate) {
		this.weddingdate = weddingdate;
	}
	/**
	 * 获取：婚礼日期
	 */
	public Date getWeddingdate() {
		return weddingdate;
	}
	/**
	 * 设置：婚礼地址
	 */
	public void setWeddingaddress(String weddingaddress) {
		this.weddingaddress = weddingaddress;
	}
	/**
	 * 获取：婚礼地址
	 */
	public String getWeddingaddress() {
		return weddingaddress;
	}
	/**
	 * 设置：婚礼留言
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取：婚礼留言
	 */
	public String getContent() {
		return content;
	}
}
