package io.renren.entity.married;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * 
 * 
 * @author yuanbichang
 * @email 673290684@qq.com
 * @date 2018-01-11 11:01:43
 */
 @TableName("marry_order_main")
public class MarryOrderMainEntity implements Serializable {
	private static final long serialVersionUID = 1L;

		//订单id
	@TableField(value="order_id")
	private Integer orderId;	
		
	//商品id
	@TableField(value="main_id")
	private Integer mainId;
			

	/**
	 * 设置：订单id
	 */
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	/**
	 * 获取：订单id
	 */
	public Integer getOrderId() {
		return orderId;
	}
	/**
	 * 设置：商品id
	 */
	public void setMainId(Integer mainId) {
		this.mainId = mainId;
	}
	/**
	 * 获取：商品id
	 */
	public Integer getMainId() {
		return mainId;
	}
}
