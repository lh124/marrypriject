package io.renren.entity.smart;

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
 * @date 2017-09-28 14:47:08
 */
 @TableName("sys_weixin_msg")
public class SysWeixinMsgEntity implements Serializable {
	private static final long serialVersionUID = 1L;

		//主键id
	@TableId(value="id",type=IdType.AUTO)
	private Integer id;	
		
	//关键词
	@TableField(value="keyword")
	private String keyword;
			
	//自动回复语句
	@TableField(value="content")
	private String content;
			
	//类型1为关注语，2为其他
	@TableField(value="sendtype")
	private Integer sendtype;
	
	@TableField(value="weixinid")
	private String weixinid;
	
	public String getWeixinid() {
		return weixinid;
	}
	public void setWeixinid(String weixinid) {
		this.weixinid = weixinid;
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
	 * 设置：关键词
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	/**
	 * 获取：关键词
	 */
	public String getKeyword() {
		return keyword;
	}
	/**
	 * 设置：自动回复语句
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取：自动回复语句
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置：类型1为关注语，2为其他
	 */
	public void setSendtype(Integer sendtype) {
		this.sendtype = sendtype;
	}
	/**
	 * 获取：类型1为关注语，2为其他
	 */
	public Integer getSendtype() {
		return sendtype;
	}
}
