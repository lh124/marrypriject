package io.renren.model.dto;


import com.baomidou.mybatisplus.annotations.TableField;

public class UserInfoDto {

	private String account;
			
	private String phone;
	
	private String nickname;
	
	private Integer sex;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}
	
	
}
