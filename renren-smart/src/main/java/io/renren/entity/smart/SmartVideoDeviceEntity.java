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
 * @date 2017-11-27 17:07:15
 */
 @TableName("smart_video_device")
public class SmartVideoDeviceEntity implements Serializable {
	private static final long serialVersionUID = 1L;

		//
	@TableId(value="id",type=IdType.AUTO)
	private Integer id;	
		
	//学校id
	@TableField(value="school_id")
	private Integer schoolId;
			
	//
	@TableField(value="verification_code")
	private String verificationCode;
			
	//
	@TableField(value="serial_number")
	private String serialNumber;
			
	//是否允许老师查看（0否，1是）
	@TableField(value="teacher_see")
	private Integer teacherSee;
			
	//是否允许学生查看（0否，1是）
	@TableField(value="student_see")
	private Integer studentSee;
	
	//通道
	@TableField(value="cameraNo")
	private Integer cameraNo;
			

	public Integer getCameraNo() {
		return cameraNo;
	}
	public void setCameraNo(Integer cameraNo) {
		this.cameraNo = cameraNo;
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
	 * 设置：学校id
	 */
	public void setSchoolId(Integer schoolId) {
		this.schoolId = schoolId;
	}
	/**
	 * 获取：学校id
	 */
	public Integer getSchoolId() {
		return schoolId;
	}
	/**
	 * 设置：
	 */
	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}
	/**
	 * 获取：
	 */
	public String getVerificationCode() {
		return verificationCode;
	}
	/**
	 * 设置：
	 */
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	/**
	 * 获取：
	 */
	public String getSerialNumber() {
		return serialNumber;
	}
	/**
	 * 设置：是否允许老师查看（0否，1是）
	 */
	public void setTeacherSee(Integer teacherSee) {
		this.teacherSee = teacherSee;
	}
	/**
	 * 获取：是否允许老师查看（0否，1是）
	 */
	public Integer getTeacherSee() {
		return teacherSee;
	}
	/**
	 * 设置：是否允许学生查看（0否，1是）
	 */
	public void setStudentSee(Integer studentSee) {
		this.studentSee = studentSee;
	}
	/**
	 * 获取：是否允许学生查看（0否，1是）
	 */
	public Integer getStudentSee() {
		return studentSee;
	}
}
