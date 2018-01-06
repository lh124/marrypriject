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
 * @date 2018-01-06 10:41:15
 */
 @TableName("marry_help")
public class MarryHelpEntity implements Serializable {
	private static final long serialVersionUID = 1L;

		//
	@TableId(value="id",type=IdType.AUTO)
	private Integer id;	
		
	//当前用户名
	@TableField(value="userName")
	private String username;
			
	//用户id
	@TableField(value="userId")
	private Integer userid;
			
	//用户头像
	@TableField(value="pic")
	private String pic;
			
	//帮助内容
	@TableField(value="content")
	private String content;
	
	//提交时间
	@TableField(value="createTime")
	private Date createTime;
			

	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
	 * 设置：当前用户名
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * 获取：当前用户名
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * 设置：用户id
	 */
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	/**
	 * 获取：用户id
	 */
	public Integer getUserid() {
		return userid;
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
	 * 设置：帮助内容
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取：帮助内容
	 */
	public String getContent() {
		return content;
	}
}
