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
 @TableName("marry_getmoney")
public class MarryGetmoneyEntity implements Serializable {
	private static final long serialVersionUID = 1L;

		//
	@TableId(value="id",type=IdType.AUTO)
	private Integer id;	
		
	//用户的微信openid
	@TableField(value="openId")
	private String openid;
			
	//账号余额（￥00.00型）
	@TableField(value="total_fee")
	private Double totalFee;
			
	//接收红包时间（或者最近一次提现时间）
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
	 * 设置：用户的微信openid
	 */
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	/**
	 * 获取：用户的微信openid
	 */
	public String getOpenid() {
		return openid;
	}
	/**
	 * 设置：账号余额（￥00.00型）
	 */
	public void setTotalFee(Double totalFee) {
		this.totalFee = totalFee;
	}
	/**
	 * 获取：账号余额（￥00.00型）
	 */
	public Double getTotalFee() {
		return totalFee;
	}
	/**
	 * 设置：接收红包时间（或者最近一次提现时间）
	 */
	public void setGmtModifiedtime(Date gmtModifiedtime) {
		this.gmtModifiedtime = gmtModifiedtime;
	}
	/**
	 * 获取：接收红包时间（或者最近一次提现时间）
	 */
	public Date getGmtModifiedtime() {
		return gmtModifiedtime;
	}
}
