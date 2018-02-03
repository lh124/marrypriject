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
 * @date 2018-01-27 17:21:10
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
			
	//用户Id
	@TableField(value="userId")
	private Integer userid;
			
	//该商品状态（0为已下单，1为未下单）
	@TableField(value="states")
	private Integer states;
			
	//
	@TableField(value="createTime")
	private Date createtime;
			
	//商户id
	@TableField(value="businessId")
	private Integer businessid;
			

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
	 * 设置：用户Id
	 */
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	/**
	 * 获取：用户Id
	 */
	public Integer getUserid() {
		return userid;
	}
	/**
	 * 设置：该商品状态（0为已下单，1为未下单）
	 */
	public void setStates(Integer states) {
		this.states = states;
	}
	/**
	 * 获取：该商品状态（0为已下单，1为未下单）
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
	/**
	 * 设置：商户id
	 */
	public void setBusinessid(Integer businessid) {
		this.businessid = businessid;
	}
	/**
	 * 获取：商户id
	 */
	public Integer getBusinessid() {
		return businessid;
	}
}
