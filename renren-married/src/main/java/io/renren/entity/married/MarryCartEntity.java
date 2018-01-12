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
 * @date 2017-12-16 10:23:15
 */
 @TableName("marry_cart")
public class MarryCartEntity implements Serializable {
	private static final long serialVersionUID = 1L;

		//
	@TableId(value="id",type=IdType.AUTO)
	private Integer id;	
		
	//产品id
	@TableField(value="main_id")
	private Integer mainId;
	
	@TableField(value="userId")
	private Integer userId;
			
	//该商品状态（0为已下单，1为未下单）
	@TableField(value="states")
	private Integer states;
			
	//
	@TableField(value="createTime")
	private Date createtime;
	
	//商户id
	@TableField(value="businessId")
	private Integer businessId;
			

	public Integer getBusinessId() {
		return businessId;
	}
	public void setBusinessId(Integer businessId) {
		this.businessId = businessId;
	}
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
	 * 设置：产品id
	 */
	public void setMainId(Integer mainId) {
		this.mainId = mainId;
	}
	/**
	 * 获取：产品id
	 */
	public Integer getMainId() {
		return mainId;
	}
	/**
	 * 设置：该商品状态（0为付款，1为未付款）
	 */
	public void setStates(Integer states) {
		this.states = states;
	}
	/**
	 * 获取：该商品状态（0为付款，1为未付款）
	 */
	public Integer getStates() {
		return states;
	}
	/**
	 * 设置：
	 */
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	/**
	 * 获取：
	 */
	public Date getCreatetime() {
		return createtime;
	}
}
