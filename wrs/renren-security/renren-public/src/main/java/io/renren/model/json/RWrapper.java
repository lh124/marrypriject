package io.renren.model.json;

import io.renren.utils.PageUtils;

public class RWrapper {
	
	private Integer code = 0;
	private String status = "ok";
	private String msg = "操作成功";
	
	private PageUtils page;
	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
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
	public PageUtils getPage() {
		return page;
	}
	public void setPage(PageUtils page) {
		this.page = page;
	}
	
}
