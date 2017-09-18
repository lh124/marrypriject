package io.renren.model.dto;

import io.renren.validator.group.AddGroup;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

public class FrontUserDto {

	private Long id;	
	
	@NotNull(message="账号不能为空",groups = {AddGroup.class})
	@NotBlank(message="账号不能为空字符", groups = {AddGroup.class})
	private String account;
			
	private String password;
	
	@NotNull(message="手机号码不能为空",groups = {AddGroup.class})
	@NotBlank(message="手机号码不能为空字符", groups = {AddGroup.class})
	private String phone;
	
	@NotNull(message="用户角色不能为空",groups = {AddGroup.class})
	@NotBlank(message="用户角色不能为空字符", groups = {AddGroup.class})
	private Integer status;
			
	private String headImg;
	
	@NotNull(message="姓名不能为空",groups = {AddGroup.class})
	@NotBlank(message="姓名不能为空字符", groups = {AddGroup.class})
	private String nickname;
	
	private String qq;
	
	private String wechat;
	
	@NotNull(message="性别不能为空",groups = {AddGroup.class})
	private Integer sex;
	
	@NotNull(message="班级不能为空",groups = {AddGroup.class})
	private Long classId;
	
	@NotNull(message="班级角色不能为空",groups = {AddGroup.class})
	private Integer roleType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getWechat() {
		return wechat;
	}

	public void setWechat(String wechat) {
		this.wechat = wechat;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Long getClassId() {
		return classId;
	}

	public void setClassId(Long classId) {
		this.classId = classId;
	}

	public Integer getRoleType() {
		return roleType;
	}

	public void setRoleType(Integer roleType) {
		this.roleType = roleType;
	}
}
