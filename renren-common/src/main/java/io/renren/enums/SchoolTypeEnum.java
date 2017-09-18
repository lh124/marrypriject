package io.renren.enums;

public enum SchoolTypeEnum {

	MIDDLE_SCHOOL("1","幼儿园，小学，初中，高中"), UNIVISITY_SCHOOL("2", "大学");
	private String typeValue;
	private String typeName;
	
	private SchoolTypeEnum(String typeValue,String typeName){
		this.typeValue = typeValue;
		this.typeName = typeName;
	}

	public String getTypeValue() {
		return typeValue;
	}

	public void setTypeValue(String typeValue) {
		this.typeValue = typeValue;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
}
