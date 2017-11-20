package io.renren.entity.tombstone;

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
 * @date 2017-10-30 15:41:05
 */
 @TableName("business_card")
public class BusinessCardEntity implements Serializable {
	private static final long serialVersionUID = 1L;

		//
	@TableId(value="id",type=IdType.AUTO)
	private Integer id;	
		
	//姓名
	@TableField(value="name")
	private String name;
			
	//头像
	@TableField(value="pic")
	private String pic;
			
	//职务
	@TableField(value="position")
	private String position;
			
	//电话号码
	@TableField(value="phone")
	private String phone;
			
	//个人QQ号
	@TableField(value="personqq")
	private String personqq;
	
	@TableField(value="weixinpic")
	private String weixinpic;
	
	@TableField(value="ordercard")
	private String ordercard;
	
	@TableField(value="positionenglish")
	private String positionenglish;
			

	public String getOrdercard() {
		return ordercard;
	}
	public void setOrdercard(String ordercard) {
		this.ordercard = ordercard;
	}
	public String getPositionenglish() {
		return positionenglish;
	}
	public void setPositionenglish(String positionenglish) {
		this.positionenglish = positionenglish;
	}
	public String getWeixinpic() {
		return weixinpic;
	}
	public void setWeixinpic(String weixinpic) {
		this.weixinpic = weixinpic;
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
	 * 设置：姓名
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：姓名
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：头像
	 */
	public void setPic(String pic) {
		this.pic = pic;
	}
	/**
	 * 获取：头像
	 */
	public String getPic() {
		return pic;
	}
	/**
	 * 设置：职务
	 */
	public void setPosition(String position) {
		this.position = position;
	}
	/**
	 * 获取：职务
	 */
	public String getPosition() {
		return position;
	}
	/**
	 * 设置：电话号码
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * 获取：电话号码
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * 设置：个人QQ号
	 */
	public void setPersonqq(String personqq) {
		this.personqq = personqq;
	}
	/**
	 * 获取：个人QQ号
	 */
	public String getPersonqq() {
		return personqq;
	}
}
