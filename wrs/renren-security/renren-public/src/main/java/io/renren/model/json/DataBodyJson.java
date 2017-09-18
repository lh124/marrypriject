package io.renren.model.json;

import java.util.ArrayList;
import java.util.List;

public class DataBodyJson<T> {

	private List<T> data;
	/**
	 * 如果data 是空的时候会构造一个ArrayList ，避免json出现null的情况
	 * @return
	 */
	public List<T> getData() {
		
		return data == null ? new ArrayList<T>(0) : data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}
	
	
}
