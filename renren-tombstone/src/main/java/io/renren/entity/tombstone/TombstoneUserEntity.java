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
 * @date 2017-10-19 16:59:26
 */
 @TableName("tombstone_user")
public class TombstoneUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

		//主键id
	@TableId(value="id",type=IdType.AUTO)
	private Integer id;	
		
	//登录账号
	@TableField(value="name")
	private String name;
			
	//登录密码
	@TableField(value="password")
	private String password;
			
	//昵称
	@TableField(value="nickname")
	private String nickname;
			
	//生平经历
	@TableField(value="experience")
	private String experience;
	
	//基本信息
	@TableField(value="content")
	private String content;
	
	//头像
	@TableField(value="pic")
	private String pic;
	
	//视频
	@TableField(value="shipin")
	private String shipin;
	
	//背景音乐
    @TableField(value="bgmusic")
    private String bgmusic;
			
	//时间
	@TableField(value="createtime")
	private String createtime;
	
	@TableField(value="openid")
	private String openid;
			

	public String getBgmusic() {
		return bgmusic;
	}
	public void setBgmusic(String bgmusic) {
		this.bgmusic = bgmusic;
	}
	public String getShipin() {
		return shipin;
	}
	public void setShipin(String shipin) {
		this.shipin = shipin;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getExperience() {
		return experience;
	}
	public void setExperience(String experience) {
		this.experience = experience;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 设置：主键id
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：主键id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：登录账号
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：登录账号
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：登录密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * 获取：登录密码
	 */
	public String getPassword() {
		return password;
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
	 * 设置：时间
	 */
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	/**
	 * 获取：时间
	 */
	public String getCreatetime() {
		return createtime;
	}
}
