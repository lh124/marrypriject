package io.renren.utils;

import java.util.ArrayList;
import java.util.List;

public class ResponseUtil<T> {

	private List<T> list ;
	
	public ResponseUtil(){
		
	}
	
	public ResponseUtil(Integer size){
		this.list = new ArrayList<T>(size);
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}
	
	
}
