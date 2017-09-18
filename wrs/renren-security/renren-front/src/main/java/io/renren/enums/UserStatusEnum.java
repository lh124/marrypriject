package io.renren.enums;

public enum UserStatusEnum {

	STATUS_FORBIDDEN(0,"等待审核"),STATUS_COMMON(1,"正常用户");
	
	private Integer value;
	private String info;
	
	private UserStatusEnum(Integer value, String info){
		this.value = value;
		this.info = info;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
}
