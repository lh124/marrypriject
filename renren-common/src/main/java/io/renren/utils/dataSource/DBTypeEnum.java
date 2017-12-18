package io.renren.utils.dataSource;

public enum DBTypeEnum {
	MYSQL("dataSource_mysql"), SQLSERVER("dataSource_mysql"),SQLSERVER2("dataSource_sqlserver");//dataSource_sqlserver
	private String value;
	
	DBTypeEnum(String value) {
	    this.value = value;
	}
	
	public String getValue() {
	    return value;
	}
}
