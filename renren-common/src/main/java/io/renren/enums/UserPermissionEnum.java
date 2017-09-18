package io.renren.enums;

public enum UserPermissionEnum {

	PERM_GRADUATION_PHOTO("graduate:all","毕业照"), PERM_SMART_UNIFORM("uniform:all", "智能校服"),
	PERM_FAMILY_PHOTO("family:all", "家庭照") ,PERM_BASE_LOGIN("user:logined", "登录权限");
	private String value;
	private String name;
	
	private UserPermissionEnum(String value,String name){
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
