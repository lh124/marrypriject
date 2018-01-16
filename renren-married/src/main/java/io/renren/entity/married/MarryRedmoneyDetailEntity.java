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
 * @date 2018-01-16 15:19:25
 */
 @TableName("marry_redmoney_detail")
public class MarryRedmoneyDetailEntity implements Serializable {
	private static final long serialVersionUID = 1L;

		//
	@TableId(value="id",type=IdType.AUTO)
	private Integer id;	
		
	//领取人id
	@TableField(value="user_id")
	private Integer userId;
			
	//红包金额
	@TableField(value="total_fee")
	private Double totalFee;
			
	//红包状态（0未领取，1已领取）
	@TableField(value="states")
	private Integer states;
			
	//婚礼id
	@TableField(value="wedding_id")
	private Integer weddingId;
			
	//
	@TableField(value="gmt_modifiedtime")
	private Date gmtModifiedtime;
			

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
	 * 设置：领取人id
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	/**
	 * 获取：领取人id
	 */
	public Integer getUserId() {
		return userId;
	}
	/**
	 * 设置：红包金额
	 */
	public void setTotalFee(Double totalFee) {
		this.totalFee = totalFee;
	}
	/**
	 * 获取：红包金额
	 */
	public Double getTotalFee() {
		return totalFee;
	}
	/**
	 * 设置：红包状态（0未领取，1已领取）
	 */
	public void setStates(Integer states) {
		this.states = states;
	}
	/**
	 * 获取：红包状态（0未领取，1已领取）
	 */
	public Integer getStates() {
		return states;
	}
	/**
	 * 设置：婚礼id
	 */
	public void setWeddingId(Integer weddingId) {
		this.weddingId = weddingId;
	}
	/**
	 * 获取：婚礼id
	 */
	public Integer getWeddingId() {
		return weddingId;
	}
	/**
	 * 设置：
	 */
	public void setGmtModifiedtime(Date gmtModifiedtime) {
		this.gmtModifiedtime = gmtModifiedtime;
	}
	/**
	 * 获取：
	 */
	public Date getGmtModifiedtime() {
		return gmtModifiedtime;
	}
}
