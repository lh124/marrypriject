package io.renren.model.dto;

public class SignParameter {

	private Integer type = 0;
	private String dirPrefix = "";
	private Long classifyId = 0L;
	private Long id = 0L;
	private Long userId = 0L;
	private String status = "ok";
	private String msg = "";
	private String document = "";
	private String callBackUrl = "";
	
	
	public SignParameter() {
		super();
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getDirPrefix() {
		return dirPrefix;
	}
	public void setDirPrefix(String dirPrefix) {
		this.dirPrefix = dirPrefix;
	}
	public Long getClassifyId() {
		return classifyId;
	}
	public void setClassifyId(Long classifyId) {
		this.classifyId = classifyId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public String getDocument() {
		return document;
	}
	public void setDocument(String document) {
		this.document = document;
	}
	public String getCallBackUrl() {
		return callBackUrl;
	}
	public void setCallBackUrl(String callBackUrl) {
		this.callBackUrl = callBackUrl;
	}
	@Override
	public String toString() {
		return "SignParameter [type=" + type + ", dirPrefix=" + dirPrefix
				+ ", classifyId=" + classifyId + ", id=" + id + ", userId="
				+ userId + ", status=" + status + ", msg=" + msg
				+ ", document=" + document + ", callBackUrl=" + callBackUrl
				+ "]";
	}
	
}
