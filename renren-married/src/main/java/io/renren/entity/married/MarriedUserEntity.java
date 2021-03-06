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
 * @date 2017-12-01 13:50:42
 */
 @TableName("married_user")
public class MarriedUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

		//
	@TableId(value="id",type=IdType.AUTO)
	private Integer id;	
		
	//昵称
	@TableField(value="nickname")
	private String nickname;
			
	//用户头像
	@TableField(value="pic")
	private String pic;
			
	//时间
	@TableField(value="createTime")
	private Date createtime;
			
	//微信openId
	@TableField(value="openId")
	private String openid;
	
	//婚礼留言
	private String content;		
	
	//权限0无权限，1有权限
	@TableField(value="jurisdiction")
	private Integer jurisdiction;	

	public Integer getJurisdiction() {
		return jurisdiction;
	}
	public void setJurisdiction(Integer jurisdiction) {
		this.jurisdiction = jurisdiction;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	 * 设置：昵称
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	/**
	 * 获取：昵称
	 */
	public String getNickname() {
		return nickname;
	}
	/**
	 * 设置：用户头像
	 */
	public void setPic(String pic) {
		this.pic = pic;
	}
	/**
	 * 获取：用户头像
	 */
	public String getPic() {
		return pic;
	}
	/**
	 * 设置：时间
	 */
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	/**
	 * 获取：时间
	 */
	public Date getCreatetime() {
		return createtime;
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
		return "MarriedUserEntity [id=" + id + ", nickname=" + nickname
				+ ", pic=" + pic + ", createtime=" + createtime + ", openid="
				+ openid + ", content=" + content + "]";
	}
	
	
}
