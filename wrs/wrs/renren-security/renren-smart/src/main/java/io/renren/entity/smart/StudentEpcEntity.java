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
 * @date 2017-08-28 11:52:18
 */
 @TableName("student_epc")
public class StudentEpcEntity implements Serializable {
	private static final long serialVersionUID = 1L;

		//
	@TableId(value="id",type=IdType.AUTO)
	private Integer id;	
		
	//
	@TableField(value="student_id")
	private Integer studentId;
			
	//
	@TableField(value="epc")
	private String epc;
			

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
	 * 设置：
	 */
	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}
	/**
	 * 获取：
	 */
	public Integer getStudentId() {
		return studentId;
	}
	/**
	 * 设置：
	 */
	public void setEpc(String epc) {
		this.epc = epc;
	}
	/**
	 * 获取：
	 */
	public String getEpc() {
		return epc;
	}
}
