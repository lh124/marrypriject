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
 * @date 2017-09-27 10:06:01
 */
 @TableName("sys_weixin_menu")
public class SysWeixinMenuEntity implements Serializable {
	private static final long serialVersionUID = 1L;

		//主键id
	@TableId(value="id",type=IdType.AUTO)
	private Integer id;	
		
	//菜单名称
	@TableField(value="name")
	private String name;
			
	//路径
	@TableField(value="url")
	private String url;
			
	//系统微信id
	@TableField(value="weixinid")
	private Integer weixinid;
			
	//父菜单id
	@TableField(value="fatherid")
	private Integer fatherid;
	
	//类型1为父菜单，2为子菜单
	private Integer menutype;
			

	public Integer getMenutype() {
		return menutype;
	}
	public void setMenutype(Integer menutype) {
		this.menutype = menutype;
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
	 * 设置：菜单名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：菜单名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：路径
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * 获取：路径
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * 设置：系统微信id
	 */
	public void setWeixinid(Integer weixinid) {
		this.weixinid = weixinid;
	}
	/**
	 * 获取：系统微信id
	 */
	public Integer getWeixinid() {
		return weixinid;
	}
	/**
	 * 设置：父菜单id
	 */
	public void setFatherid(Integer fatherid) {
		this.fatherid = fatherid;
	}
	/**
	 * 获取：父菜单id
	 */
	public Integer getFatherid() {
		return fatherid;
	}
}
