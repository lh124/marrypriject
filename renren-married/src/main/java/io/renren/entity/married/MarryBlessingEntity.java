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
	
	private String nickname;
	
	private String pic;
			

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
}
