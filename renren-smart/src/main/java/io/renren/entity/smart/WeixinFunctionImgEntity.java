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
 * @date 2017-11-23 10:34:44
 */
 @TableName("weixin_function_img")
public class WeixinFunctionImgEntity implements Serializable {
	private static final long serialVersionUID = 1L;

		//
	@TableId(value="id",type=IdType.AUTO)
	private Integer id;	
		
	//功能图标
	@TableField(value="pic")
	private String pic;
			
	//所属学校Id
	@TableField(value="school_id")
	private Integer schoolId;
	
	//所属学校Id
	@TableField(value="function_id")
	private Integer functionId;
			

	public Integer getFunctionId() {
		return functionId;
	}
	public void setFunctionId(Integer functionId) {
		this.functionId = functionId;
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
	 * 设置：功能图标
	 */
	public void setPic(String pic) {
		this.pic = pic;
	}
	/**
	 * 获取：功能图标
	 */
	public String getPic() {
		return pic;
	}
	/**
	 * 设置：所属学校Id
	 */
	public void setSchoolId(Integer schoolId) {
		this.schoolId = schoolId;
	}
	/**
	 * 获取：所属学校Id
	 */
	public Integer getSchoolId() {
		return schoolId;
	}
}
