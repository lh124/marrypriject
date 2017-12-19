package io.renren.entity.married;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableName;

/**
 * 
 * 
 * @author yuanbichang
 * @email 673290684@qq.com
 * @date 2017-12-18 10:07:37
 */
 @TableName("marry_participate")
public class MarryParticipateEntityDo implements Serializable {
	private static final long serialVersionUID = 1L;

		//
	private Integer id;	
		
	private Integer userId;
			
	//婚礼id
	private Integer weddingid;
			
	//状态
	private Integer states;
	
	private String nickname;
	
	private String pic;
	
	private String content;
	
	private String createTime;
			

	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
