package io.renren.model.json;

public class BaseDTJson {

	public static String SUCCESS = "ok";
	public static String FAIL = "error";
	/**
	 * 操作状态值,SUCCESS或FAIL，表示当前操作成功或者失败
	 */
	protected String status;
	/**
	 * 操作状信息
	 */
	protected String msg;
	/**
	 * 错误代码，便于前端调试
	 */
	protected String errorCode;
	/**
	 * 签名，以后拓展
	 */
	protected String security;
	/**
	 * 类型值
	 */
	protected String type;
	/**
	 * 用户唯一标识
	 */
	protected Long id;
	/**
	 * 登陆标识，用于以后拓展
	 */
	protected String log;
	/**
	 * 版本信息
	 */
	protected String version;
	
	public String getStatus() {
		return status == null ? "" : status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMsg() {
		
		return msg == null ? "" :msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getErrorCode() {
		return errorCode == null ? "" : errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getSecurity() {
		
		return security == null ? "" : security;
	}
	public void setSecurity(String security) {
		this.security = security;
	}
	public String getType() {
		
		return type == null ? "" : type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getId() {
		
		return id == null ? 0 : id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLog() {
		
		return log == null ? "" : log;
	}
	public void setLog(String log) {
		this.log = log;
	}
	public String getVersion() {
		return version == null ? "" : version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
}
