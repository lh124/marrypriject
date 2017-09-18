package io.renren.enums;

public enum CallBackUrlEnum {

	SMART_MESSAGE_PIC("asdfsa", "sdsafd");
	private String value;
	private String name;
	
	private CallBackUrlEnum(String value,String name){
		this.value = value;
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
