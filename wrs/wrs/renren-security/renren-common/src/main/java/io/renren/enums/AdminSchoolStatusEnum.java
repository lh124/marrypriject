package io.renren.enums;

public enum AdminSchoolStatusEnum {

	STATUS_COMMON(1,"正常"), STATUS_STOP(2, "停用");
	private Integer value;
	private String name;
	
	private AdminSchoolStatusEnum(Integer value,String name){
		this.value = value;
		this.name = name;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
