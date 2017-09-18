package io.renren.enums;

public enum ClassRoleEnum {

	CLASS_TEACHER_MOTHER(1, "班主任", 1),CLASS_TEACHER(2, "任课老师", 1),CLASS_ADMIN(3,"班长",0), CLASS_MANAGER(4, "班委", 2), CLASS_STUDENT(5, "学生", 3);
	/** 对应类型值*/
	private Integer typeValue;
	/** 角色名*/
	private String roleName;
	/** 角色权限*/
	private Integer status;
	
	private ClassRoleEnum(Integer typeValue, String roleName, Integer status){
		this.typeValue = typeValue;
		this.roleName = roleName;
		this.status = status;
	}

	public Integer getTypeValue() {
		return typeValue;
	}

	public void setTypeValue(Integer typeValue) {
		this.typeValue = typeValue;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public static Integer getValue(String name) {
		
		ClassRoleEnum[] creArray  = ClassRoleEnum.values();
		for (ClassRoleEnum cre : creArray){
			if (cre.getRoleName().equals(name))
				return cre.getTypeValue();
		}
		
		return ClassRoleEnum.CLASS_STUDENT.getTypeValue();
	}

	
}
