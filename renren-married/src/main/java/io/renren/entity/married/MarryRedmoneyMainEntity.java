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
 @TableName("marry_redmoney_main")
public class MarryRedmoneyMainEntity implements Serializable {
	private static final long serialVersionUID = 1L;

		//
	@TableId(value="id",type=IdType.AUTO)
	private Integer id;	
		
	//红包金额（1元以上）
	@TableField(value="total_fee")
	private Double totalFee;
			
	//红包个数（3个以上）
	@TableField(value="total_num")
	private Integer totalNum;
			
	//发送红包用户id
	@TableField(value="user_id")
	private Integer userId;
	
	//状态，0未成功，1成功
	@TableField(value="states")
	private Integer states;
	
	//订单号
	@TableField(value="out_trade_no")
	private String outTradeNo;
			
	//发送红包时间
	@TableField(value="gmt_modifiedtime")
	private Date gmtModifiedtime;
			

	public Integer getStates() {
		return states;
	}
	public void setStates(Integer states) {
		this.states = states;
	}
	public String getOutTradeNo() {
		return outTradeNo;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
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
	 * 设置：红包金额（1元以上）
	 */
	public void setTotalFee(Double totalFee) {
		this.totalFee = totalFee;
	}
	/**
	 * 获取：红包金额（1元以上）
	 */
	public Double getTotalFee() {
		return totalFee;
	}
	/**
	 * 设置：红包个数（3个以上）
	 */
	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}
	/**
	 * 获取：红包个数（3个以上）
	 */
	public Integer getTotalNum() {
		return totalNum;
	}
	/**
	 * 设置：发送红包用户id
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	/**
	 * 获取：发送红包用户id
	 */
	public Integer getUserId() {
		return userId;
	}
	/**
	 * 设置：发送红包时间
	 */
	public void setGmtModifiedtime(Date gmtModifiedtime) {
		this.gmtModifiedtime = gmtModifiedtime;
	}
	/**
	 * 获取：发送红包时间
	 */
	public Date getGmtModifiedtime() {
		return gmtModifiedtime;
	}
}
