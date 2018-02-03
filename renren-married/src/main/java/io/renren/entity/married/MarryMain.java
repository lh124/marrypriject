package io.renren.entity.married;

import java.util.List;

public class MarryMain {
	
	private String username;
	
	private String sysId;
	
	private List<MarryMainEntity> list;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<MarryMainEntity> getList() {
		return list;
	}

	public void setList(List<MarryMainEntity> list) {
		this.list = list;
	}

	public String getSysId() {
		return sysId;
	}

	public void setSysId(String sysId) {
		this.sysId = sysId;
	}

	
}
