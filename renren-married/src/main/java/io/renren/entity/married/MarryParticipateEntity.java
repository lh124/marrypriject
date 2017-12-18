package io.renren.entity.married;

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
 * @date 2017-12-18 10:07:37
 */
 @TableName("marry_participate")
public class MarryParticipateEntity implements Serializable {
	private static final long serialVersionUID = 1L;

		//
	@TableId(value="id",type=IdType.AUTO)
	private Integer id;	
		
	//微信openid
	@TableField(value="openId")
	private String openid;
			
	//婚礼id
	@TableField(value="weddingId")
	private Integer weddingid;
			
	//状态
	@TableField(value="states")
	private Integer states;
			

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
	 * 设置：微信openid
	 */
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	/**
	 * 获取：微信openid
	 */
	public String getOpenid() {
		return openid;
	}
	/**
	 * 设置：婚礼id
	 */
	public void setWeddingid(Integer weddingid) {
		this.weddingid = weddingid;
	}
	/**
	 * 获取：婚礼id
	 */
	public Integer getWeddingid() {
		return weddingid;
	}
	/**
	 * 设置：状态
	 */
	public void setStates(Integer states) {
		this.states = states;
	}
	/**
	 * 获取：状态
	 */
	public Integer getStates() {
		return states;
	}
}
