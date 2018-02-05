package io.renren.entity.married;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * 
 * 
 * @author yuanbichang
 * @email 673290684@qq.com
 * @date 2018-01-11 11:01:43
 */
 @TableName("marry_orders")
public class MarryOrdersEntity implements Serializable {
	private static final long serialVersionUID = 1L;

		//
	@TableId(value="id",type=IdType.AUTO)
	private Integer id;	
		
	
	//订单号
	@TableField(value="order_number")
	private String orderNumber;
			
	//商家
	@TableField(value="business")
	private String business;
			
	//商品价格
	@TableField(value="main_price")
	private String mainPrice;
			
	//下单时间
	@TableField(value="gmt_modifiedtime")
	private Date gmtModifiedtime;
			
	//订单状态（0未付款，1已付款，2申请退款（审核中），3已退款，4未通过退款）
	@TableField(value="states")
	private Integer states;
			
	//用户id
	@TableField(value="user_id")
	private Integer userId;
	
	//订单类型（1直接下单，2通过购物车下单）
	@TableField(value="order_type")
	private Integer orderType;
			
	//审核退款意见
	@TableField(value="content")
	private String content;
	
	//订单描述
	@TableField(value="main_describe")
	private String mainDescribe;
	//订单描述
	@TableField(value="count")
	private Integer count;
	
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	private List<OrderAndMain> marryMainList;
	
	public Integer getOrderType() {
		return orderType;
	}
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
	public String getMainDescribe() {
		return mainDescribe;
	}
	public void setMainDescribe(String mainDescribe) {
		this.mainDescribe = mainDescribe;
	}

	public List<OrderAndMain> getMarryMainList() {
		return marryMainList;
	}
	public void setMarryMainList(List<OrderAndMain> marryMainList) {
		this.marryMainList = marryMainList;
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
	 * 设置：订单号
	 */
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	/**
	 * 获取：订单号
	 */
	public String getOrderNumber() {
		return orderNumber;
	}
	/**
	 * 设置：商家
	 */
	public void setBusiness(String business) {
		this.business = business;
	}
	/**
	 * 获取：商家
	 */
	public String getBusiness() {
		return business;
	}
	/**
	 * 设置：商品价格
	 */
	public void setMainPrice(String mainPrice) {
		this.mainPrice = mainPrice;
	}
	/**
	 * 获取：商品价格
	 */
	public String getMainPrice() {
		return mainPrice;
	}
	/**
	 * 设置：下单时间
	 */
	public void setGmtModifiedtime(Date gmtModifiedtime) {
		this.gmtModifiedtime = gmtModifiedtime;
	}
	/**
	 * 获取：下单时间
	 */
	public Date getGmtModifiedtime() {
		return gmtModifiedtime;
	}
	/**
	 * 设置：订单状态（0未付款，1已付款，2申请退款（审核中），3已退款，4未通过退款）
	 */
	public void setStates(Integer states) {
		this.states = states;
	}
	/**
	 * 获取：订单状态（0未付款，1已付款，2申请退款（审核中），3已退款，4未通过退款）
	 */
	public Integer getStates() {
		return states;
	}
	/**
	 * 设置：用户id
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	/**
	 * 获取：用户id
	 */
	public Integer getUserId() {
		return userId;
	}
	/**
	 * 设置：审核退款意见
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取：审核退款意见
	 */
	public String getContent() {
		return content;
	}
	@Override
	public String toString() {
		return "MarryOrdersEntity [id=" + id + ", orderNumber=" + orderNumber
				+ ", business=" + business + ", mainPrice=" + mainPrice
				+ ", gmtModifiedtime=" + gmtModifiedtime + ", states=" + states
				+ ", userId=" + userId + ", orderType=" + orderType
				+ ", content=" + content + ", mainDescribe=" + mainDescribe
				+ ", marryMainList=" + marryMainList + "]";
	}
	
	
	
}
