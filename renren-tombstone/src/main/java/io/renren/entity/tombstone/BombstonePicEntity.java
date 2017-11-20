package io.renren.entity.tombstone;

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
 * @date 2017-10-21 17:05:56
 */
 @TableName("bombstone_pic")
public class BombstonePicEntity implements Serializable {
	private static final long serialVersionUID = 1L;

		//主键id
	@TableId(value="id",type=IdType.AUTO)
	private Integer id;	
		
	//姓名
	@TableField(value="pic")
	private String pic;
			
	//头像
	@TableField(value="userid")
	private String userid;
			
	//
	@TableField(value="createtime")
	private Date createtime;
			

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
	public void setPic(String pic) {
		this.pic = pic;
	}
	/**
	 * 获取：姓名
	 */
	public String getPic() {
		return pic;
	}
	/**
	 * 设置：头像
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}
	/**
	 * 获取：头像
	 */
	public String getUserid() {
		return userid;
	}
	/**
	 * 设置：
	 */
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	/**
	 * 获取：
	 */
	public Date getCreatetime() {
		return createtime;
	}
}
