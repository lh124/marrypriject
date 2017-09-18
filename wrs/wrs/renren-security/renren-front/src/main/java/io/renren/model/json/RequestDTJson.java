package io.renren.model.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
/**
 * 用于封装请求数据对象
 * <br>
 * @author yuanbichang
 *
 */
public class RequestDTJson extends BaseDTJson{

	/**
	 * 获取所有数据数据
	 */
	private String key;
	/**
	 * 获取json数据内层对象
	 */
	private JSONObject data;
	/**
	 * 将数据封装成基本json对象
	 */
	private JSONObject baseJson;
	
	public RequestDTJson(){
		super();
	}
	
	/**
	 * 通过静态方法，将获得请求封装成对象像
	 * @param jsonString 请求的数据字段
	 * @return
	 */
	public static RequestDTJson getRequestDTJson(String jsonString){
		
		RequestDTJson requestDTJson = new RequestDTJson();
		
		requestDTJson.setKey(jsonString);
		
		requestDTJson.setBaseJson(JSON.parseObject(jsonString));
		
		JSONObject json = requestDTJson.getBaseJson().containsKey("request") ? 
				requestDTJson.getBaseJson().getJSONObject("request") : null;
		requestDTJson.setData(json == null ? null : json.getJSONArray("data").getJSONObject(0));
		
		if (json != null){
			
			requestDTJson.setId(json.containsKey("id") && json.getLong("id") != null ? json.getLong("id") : null);
			requestDTJson.setType(json.containsKey("id") && json.getString("type") != null ? json.getString("type") : null);
			
		}else{
			System.out.println("===============> warm : " + " 无请求数据 ");
		}
		
		
		return requestDTJson;
	}

	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public JSONObject getData() {
		return data;
	}

	public void setData(JSONObject data) {
		this.data = data;
	}

	public JSONObject getBaseJson() {
		return baseJson;
	}

	public void setBaseJson(JSONObject baseJson) {
		this.baseJson = baseJson;
	}
	
}
