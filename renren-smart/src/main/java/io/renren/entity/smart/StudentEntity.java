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
 * @date 2017-08-28 11:52:18
 */
 @TableName("student")
public class StudentEntity implements Serializable {
	private static final long serialVersionUID = 1L;

		//
	@TableId(value="id",type=IdType.AUTO)
	private Integer id;	
		
	//
	@TableField(value="student_code")
	private String studentCode;
			
	//
	@TableField(value="student_no")
	private String studentNo;
			
	//
	@TableField(value="student_name")
	private String studentName;
			
	//
	@TableField(value="sex")
	private String sex;
			
	//
	@TableField(value="student_type")
	private String studentType;
			
	//
	@TableField(value="pic")
	private String pic;
			
	//
	@TableField(value="school_id")
	private Integer schoolId;
	
	@TableField(value="class_id")
	private Integer classId;
			
	//密码
	@TableField(value="passwordd")
	private String passwordd;
	
	//微信绑定id
	@TableField(value="open_id")
	private String openId;
	
	@TableField(value="newsType")
	private Integer newsType;
	
	@TableField(value="newsId")
	private Integer newsId;
	
	//是否强制绑定手机号（0不绑定，1绑定）
	@TableField(value="bindingType")
	private Integer bindingType;
	
	//请假状态1为待处理，2为同意，3为不同意
	@TableField(value="states")
	private Integer states;
	
	@TableField(value="expireTime")
	private Date expireTime;
	
	@TableField(value="sign")
	private String sign;
	
	@TableField(value="gusername")
	private String gusername;
	
	@TableField(value="gid")
	private String gid;
	
	public String getGid() {
		return gid;
	}
	public void setGid(String gid) {
		this.gid = gid;
	}
	public String getGusername() {
		return gusername;
	}
	public void setGusername(String gusername) {
		this.gusername = gusername;
	}
	public Date getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public Integer getStates() {
		return states;
	}
	public void setStates(Integer states) {
		this.states = states;
	}
	public Integer getBindingType() {
		return bindingType;
	}
	public void setBindingType(Integer bindingType) {
		this.bindingType = bindingType;
	}
	public Integer getNewsType() {
		return newsType;
	}
	public void setNewsType(Integer newsType) {
		this.newsType = newsType;
	}
	public Integer getNewsId() {
		return newsId;
	}
	public void setNewsId(Integer newsId) {
		this.newsId = newsId;
	}
	/**
	 * 经度
	 */
	@TableField(value="longitude")
	private String longitude;
	/**
	 * 纬度
	 */
	@TableField(value="latitude")
	private String latitude;
	 
	@TableField(value="phoen")
	private String phoen;
	
	public String getPhoen() {
		return phoen;
	}
	public void setPhoen(String phoen) {
		this.phoen = phoen;
	}
	public Integer getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(Integer schoolId) {
		this.schoolId = schoolId;
	}
	/**
	 * 类别1学生，2老师
	 */
	@TableField(value="user_type")
	private String userType;
	
	
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
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
	 * 设置：
	 */
	public void setStudentCode(String studentCode) {
		this.studentCode = studentCode;
	}
	/**
	 * 获取：
	 */
	public String getStudentCode() {
		return studentCode;
	}
	/**
	 * 设置：
	 */
	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}
	/**
	 * 获取：
	 */
	public String getStudentNo() {
		return studentNo;
	}
	/**
	 * 设置：
	 */
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	/**
	 * 获取：
	 */
	public String getStudentName() {
		return studentName;
	}
	/**
	 * 设置：
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}
	/**
	 * 获取：
	 */
	public String getSex() {
		return sex;
	}
	/**
	 * 设置：
	 */
	public void setStudentType(String studentType) {
		this.studentType = studentType;
	}
	/**
	 * 获取：
	 */
	public String getStudentType() {
		return studentType;
	}
	/**
	 * 设置：
	 */
	public void setPic(String pic) {
		this.pic = pic;
	}
	/**
	 * 获取：
	 */
	public String getPic() {
		return pic;
	}
	/**
	 * 设置：
	 */
	public void setClassId(Integer classId) {
		this.classId = classId;
	}
	/**
	 * 获取：
	 */
	public Integer getClassId() {
		return classId;
	}
	/**
	 * 设置：密码
	 */
	public void setPasswordd(String passwordd) {
		this.passwordd = passwordd;
	}
	/**
	 * 获取：密码
	 */
	public String getPasswordd() {
		return passwordd;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
	
}
