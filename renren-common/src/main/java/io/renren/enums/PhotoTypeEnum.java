package io.renren.enums;

public enum PhotoTypeEnum {

	PHOTO_TYPE_CLASS(1,"班级照片分类"), PHOTO_TYPE_FAMILY(2, "家庭相册分类"), 
	PHOTO_TYPE_PERSONAL(3, "个人相册分类分类"),PHOTO_TYPE_SCHOOL(4, "学校相册分类分类");
	private Integer typeValue;
	private String typeName;
	
	private PhotoTypeEnum(Integer typeValue,String typeName){
		this.typeValue = typeValue;
		this.typeName = typeName;
	}

	public Integer getTypeValue() {
		return typeValue;
	}

	public void setTypeValue(Integer typeValue) {
		this.typeValue = typeValue;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
}
