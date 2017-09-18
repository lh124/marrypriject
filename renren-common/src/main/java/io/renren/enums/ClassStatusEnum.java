package io.renren.enums;

public enum ClassStatusEnum {

	STATUS_COMMON(1,"正常"), STATUS_FORBIDDEND(2, "禁用"),STATUS_HIDDEN(3, "隐藏");
	private Integer value;
	private String name;
	
	private ClassStatusEnum(Integer value,String name){
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
