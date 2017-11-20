package io.renren.entity.tombstone;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * 
 * 
 * @author yuanbichang
 * @email 673290684@qq.com
 * @date 2017-10-23 10:54:33
 */
 @TableName("tombstone_dead")
public class TombstoneDeadEntity implements Serializable {
	private static final long serialVersionUID = 1L;

		//主键id
	@TableId(value="id",type=IdType.AUTO)
	private Integer id;	
		
	//姓名
	@TableField(value="name")
	private String name;
			
	//头像
	@TableField(value="image")
	private String image;
			
	//生卒年
	@TableField(value="birthdayanddeath")
	private String birthdayanddeath;
			
	//个人简介
	@TableField(value="content")
	private String content;
			
	//
	@TableField(value="userid")
	private Integer userid;
			
	//父id
	@TableField(value="parentid")
	private Integer parentid;
			
	//与死者关系
	@TableField(value="relation")
	private String relation;
			
	//第二代的下一代
	@TableField(value="children")
	private List<Obj> children;
	
	//
	@TableField(value="createtime")
	private String createtime;
	
	@TableField(value="usertype")
	private String usertype;
	
	@TableField(value="relationtype")
	private String relationtype;
	
	@TableField(value="account")
	private String account;
			

	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getRelationtype() {
		return relationtype;
	}
	public void setRelationtype(String relationtype) {
		this.relationtype = relationtype;
	}
	public List<Obj> getChildren() {
		return children;
	}
	public void setChildren(List<Obj> children) {
		this.children = children;
	}
	public String getUsertype() {
		return usertype;
	}
	public void setUsertype(String usertype) {
		this.usertype = usertype;
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
	public void setImage(String image) {
		this.image = image;
	}
	/**
	 * 获取：头像
	 */
	public String getImage() {
		return image;
	}
	/**
	 * 设置：生卒年
	 */
	public void setBirthdayanddeath(String birthdayanddeath) {
		this.birthdayanddeath = birthdayanddeath;
	}
	/**
	 * 获取：生卒年
	 */
	public String getBirthdayanddeath() {
		return birthdayanddeath;
	}
	/**
	 * 设置：个人简介
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取：个人简介
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置：
	 */
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	/**
	 * 获取：
	 */
	public Integer getUserid() {
		return userid;
	}
	/**
	 * 设置：父id
	 */
	public void setParentid(Integer parentid) {
		this.parentid = parentid;
	}
	/**
	 * 获取：父id
	 */
	public Integer getParentid() {
		return parentid;
	}
	/**
	 * 设置：与死者关系
	 */
	public void setRelation(String relation) {
		this.relation = relation;
	}
	/**
	 * 获取：与死者关系
	 */
	public String getRelation() {
		return relation;
	}
	/**
	 * 设置：
	 */
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	/**
	 * 获取：
	 */
	public String getCreatetime() {
		return createtime;
	}
}
