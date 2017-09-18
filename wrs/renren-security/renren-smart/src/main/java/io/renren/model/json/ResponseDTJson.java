package io.renren.model.json;

import java.util.ArrayList;
import java.util.List;

public class ResponseDTJson<T> extends BaseDTJson{

	
	public static String SUCCESS = "ok";
	public static String FAIL = "error";
	
	private DataBodyJson<T> response;
	
	
	
	public ResponseDTJson(){
		super();
	}
	
	/**
	 * 快捷设置返回json数据
	 * @param status
	 * @param msg
	 * @param type
	 * @param dataList
	 */
	@SuppressWarnings({ "unchecked" })
	public ResponseDTJson(String status, String msg, String type, @SuppressWarnings("rawtypes") List dataList){
		
		this.status = status;
		this.msg = msg;
		this.type = type;
		
		response = new DataBodyJson<T>();
		response.setData(dataList);
		
	}
	

	public DataBodyJson<T> getResponse() {
		return response;
	}

	public void setResponse(DataBodyJson<T> response) {
		this.response = response;
	}
	
	/**
	 * 初始化一个空的返回数据
	 * @param size
	 * @return
	 */
	public void getNewData(Integer size) {
		
		response = new DataBodyJson<T>();
		List<T> list = null;
		
		
		if (size == null){
			
			list = new  ArrayList<T>();
			response.setData(list);
		}else{
			list = new  ArrayList<T>(size);
			response.setData(list);
		}
		
	} 
	
	@SuppressWarnings("rawtypes")
	public static ResponseDTJson getResponseInstance(){
		
		ResponseDTJson response = new ResponseDTJson();
		response.getNewData(null);
		
		return response;
	}
	
	/**
	 * 快捷设置数据List
	 * @param list
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void  setDataList(List list){
		this.response.setData(list);
	}
	
	/**
	 * 快捷设置数据List中的数据
	 * @param list
	 */
	public void   setDataListData(T t){
		this.response.getData().add(t);
	}
}
