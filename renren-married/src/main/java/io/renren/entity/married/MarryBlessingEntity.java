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
 * @date 2017-12-28 15:08:23
 */
 @TableName("marry_blessing")
public class MarryBlessingEntity implements Serializable {
	private static final long serialVersionUID = 1L;

		//
	@TableId(value="id",type=IdType.AUTO)
	private Integer id;	
		
	//祝福语
	@TableField(value="content")
	private String content;
			
	//视频祝福
	@TableField(value="videoblessing")
	private String videoblessing;
			
	//类型（1普通祝福，2红包祝福）
	@TableField(value="blessingtype")
	private Integer blessingtype;
			
	//婚礼id
	@TableField(value="weddingId")
	private Integer weddingid;
			
	//微信openId
	@TableField(value="openId")
	private String openid;
	
	//nickname
	@TableField(value="nickname")
	private String nickname;
	
	@TableField(value="pic")
	private String pic;
	
	//红包祝福时的订单号
	@TableField(value="ordernumber")
	private String ordernumber;
	
	//红包时的状态（0为未付款，1为已付款）
	@TableField(value="states")
	private Integer states;
	
	@TableField(value="gmt_modifiedtime")
	private Date gmtModifiedtime;
	//请求模式（1.公众号，2.小程序）
	@TableField(value="type")		
	private Integer type;

	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Date getGmtModifiedtime() {
		return gmtModifiedtime;
	}
	public void setGmtModifiedtime(Date gmtModifiedtime) {
		this.gmtModifiedtime = gmtModifiedtime;
	}
	public Integer getStates() {
		return states;
	}
	public void setStates(Integer states) {
		this.states = states;
	}
	public String getOrdernumber() {
		return ordernumber;
	}
	public void setOrdernumber(String ordernumber) {
		this.ordernumber = ordernumber;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
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
	 * 设置：祝福语
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取：祝福语
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置：视频祝福
	 */
	public void setVideoblessing(String videoblessing) {
		this.videoblessing = videoblessing;
	}
	/**
	 * 获取：视频祝福
	 */
	public String getVideoblessing() {
		return videoblessing;
	}
	/**
	 * 设置：类型（1普通祝福，2红包祝福）
	 */
	public void setBlessingtype(Integer blessingtype) {
		this.blessingtype = blessingtype;
	}
	/**
	 * 获取：类型（1普通祝福，2红包祝福）
	 */
	public Integer getBlessingtype() {
		return blessingtype;
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
	 * 设置：微信openId
	 */
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	/**
	 * 获取：微信openId
	 */
	public String getOpenid() {
		return openid;
	}
	@Override
	public String toString() {
		return "MarryBlessingEntity [id=" + id + ", content=" + content
				+ ", videoblessing=" + videoblessing + ", blessingtype="
				+ blessingtype + ", weddingid=" + weddingid + ", openid="
				+ openid + ", nickname=" + nickname + ", pic=" + pic
				+ ", ordernumber=" + ordernumber + ", states=" + states
				+ ", gmtModifiedtime=" + gmtModifiedtime + ", type=" + type
				+ "]";
	}
	
	
}
