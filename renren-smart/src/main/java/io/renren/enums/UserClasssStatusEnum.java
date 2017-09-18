package io.renren.enums;

public enum UserClasssStatusEnum {

	STATUS_WAIT(1,"等待审核"),STATUS_WAIT_(2,"未通过审核"),STATUS_COMMON(3,"审核通过"),STATUS_DELETE(43,"删除出班级");
	
	private Integer value;
	private String info;
	
	private UserClasssStatusEnum(Integer value, String info){
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
