package io.renren.enums;

public enum ClassClassifyEnum {

	GRADUATION_PHOTO(1,"毕业照", "graduate:all"), SMART_UNIFORM(2, "智能校服", "uniform:all");
	private Integer value;
	private String name;
	private String perm;
	
	private ClassClassifyEnum(Integer value,String name, String perm){
		this.value = value;
		this.name = name;
		this.perm = perm;
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

	public String getPerm() {
		return perm;
	}

	public void setPerm(String perm) {
		this.perm = perm;
	}
}
