package io.renren.entity.smart;

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
 * @date 2017-11-20 12:02:46
 */
 @TableName("sys_remarks")
public class SysRemarksEntity implements Serializable {
	private static final long serialVersionUID = 1L;

		//id
	@TableId(value="id",type=IdType.AUTO)
	private Integer id;	
		
	//备注值
	@TableField(value="name")
	private String name;
	
	//是否解决
	@TableField(value="solveIf")  
	private Integer solveIf;
	
	//是否解决
	@TableField(value="userId")  
	private Integer userId;
			
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getSolveIf() {
		return solveIf;
	}
	public void setSolveIf(Integer solveIf) {
		this.solveIf = solveIf;
	}
	//
	@TableField(value="createTime")
	private Date createtime;
			

	/**
	 * 设置：id
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：备注值
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：备注值
	 */
	public String getName() {
		return name;
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
