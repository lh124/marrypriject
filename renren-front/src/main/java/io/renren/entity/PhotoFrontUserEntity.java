package io.renren.entity;

import io.renren.validator.group.AddGroup;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-05-04 15:07:49
 */
 @TableName("photo_front_user")
public class PhotoFrontUserEntity implements Serializable  {
	private static final long serialVersionUID = 1L;

		//
	@TableId(value="id",type=IdType.AUTO)
	private Long id;	
		
	//
	@TableField(value="account")
	@NotBlank(message="账号不能为空", groups = {AddGroup.class})
	private String account;
			
	//
	@TableField(value="password")
	private String password;
			
	//
	@TableField(value="phone")
	@Length(message = "号码长度不对，请输入11位号码",min = 11, max = 11, groups = {AddGroup.class})
	private String phone;
			
	//用户状态，0表示被禁用，1表示普通用户
	@TableField(value="status")
	private Integer status;
			
	//
	@TableField(value="gmt_create")
	private Date gmtCreate;
			
	//
	@TableField(value="gmt_modified")
	private Date gmtModified;
			
	//
	@TableField(value="head_img")
	private String headImg;
	
	@TableField(value="nickname")
	@NotBlank(message = "姓名不能为空", groups = {AddGroup.class})
	private String nickname;
	
	@TableField(value="sex")
	@NotNull(message = "性别不为空", groups = {AddGroup.class})
	@Range(min=1,max=2)
	private Integer sex;
	
	
	@TableField(value="openid")
	private String openid;
	
	@TableField(value="student_type")
	private String studentType;
	
	@TableField(value="student_code")
	private String studentCode;
	
	@TableField(value="student_no")
	private String studentNo;
	
	
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
	
	@TableField(exist=false)
	private Integer roleId;
	
	@TableField(exist=false)
	private String perm;
	// ------集合成员变量开始-------
	
	// 用户功能权限
	@TableField(exist=false)
	private List<PhotoFunctionModulesEntity> authList;
	
	// 用户功能权限
	@TableField(exist=false)
	private PhotoUserClassEntity userClass;
	// ------集合成员变量结束-------
	
	
	/**
	 * 设置：
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：
	 */
	public void setAccount(String account) {
		this.account = account;
	}
	/**
	 * 获取：
	 */
	public String getAccount() {
		return account;
	}
	/**
	 * 设置：
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * 获取：
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * 设置：
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * 获取：
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * 设置：用户状态，0表示被禁用，1表示普通用户
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：用户状态，0表示被禁用，1表示普通用户
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置：
	 */
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	/**
	 * 获取：
	 */
	public Date getGmtCreate() {
		return gmtCreate;
	}
	/**
	 * 设置：
	 */
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
	/**
	 * 获取：
	 */
	public Date getGmtModified() {
		return gmtModified;
	}
	/**
	 * 设置：
	 */
	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}
	/**
	 * 获取：
	 */
	public String getHeadImg() {
		return headImg;
	}
	
	public List<PhotoFunctionModulesEntity> getAuthList() {
		return authList;
	}
	public void setAuthList(List<PhotoFunctionModulesEntity> authList) {
		this.authList = authList;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public PhotoUserClassEntity getUserClass() {
		return userClass;
	}
	public void setUserClass(PhotoUserClassEntity userClass) {
		this.userClass = userClass;
	}
	public String getPerm() {
		return perm;
	}
	public void setPerm(String perm) {
		this.perm = perm;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
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
	public String getStudentType() {
		return studentType;
	}
	public void setStudentType(String studentType) {
		this.studentType = studentType;
	}
	public String getStudentCode() {
		return studentCode;
	}
	public void setStudentCode(String studentCode) {
		this.studentCode = studentCode;
	}
	public String getStudentNo() {
		return studentNo;
	}
	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}
	@Override
	public String toString() {
		return "PhotoFrontUserEntity [id=" + id + ", account=" + account
				+ ", password=" + password + ", phone=" + phone + ", status="
				+ status + ", gmtCreate=" + gmtCreate + ", gmtModified="
				+ gmtModified + ", headImg=" + headImg + ", nickname="
				+ nickname + ", sex=" + sex + ", openid=" + openid
				+ ", studentType=" + studentType + ", studentCode="
				+ studentCode + ", studentNo=" + studentNo + ", longitude="
				+ longitude + ", latitude=" + latitude + ", roleId=" + roleId
				+ ", perm=" + perm + ", authList=" + authList + ", userClass="
				+ userClass + "]";
	}
}
