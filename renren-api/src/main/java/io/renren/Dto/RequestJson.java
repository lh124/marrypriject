package io.renren.Dto;

import java.io.Serializable;
import java.util.Map;

public class RequestJson implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String tocken;
	private String type;
	private String app;
	private Map<String, Object> data;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTocken() {
		return tocken;
	}
	public void setTocken(String tocken) {
		this.tocken = tocken;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getApp() {
		return app;
	}
	public void setApp(String app) {
		this.app = app;
	}
	public Map<String, Object> getData() {
		return data;
	}
	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "{\"id\"=\"" + id + "\",\"tocken\"=\"" + tocken + "\",\"type\"=\""
				+ type + "\",\"app=\"" + app + "\",\"data\"=" + data + "}";
	}
}
