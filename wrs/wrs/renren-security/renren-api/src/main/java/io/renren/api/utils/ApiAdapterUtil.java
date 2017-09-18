package io.renren.api.utils;

import io.renren.Dto.RequestJson;
import io.renren.utils.RRException;
import io.renren.utils.SpringContextUtils;

public class ApiAdapterUtil {

	public String methodAdapter(RequestJson requestJson){
		
		if ( requestJson != null && requestJson.getType() != null) {
			
			String type = requestJson.getType();
			String[] typeArray = type.split("_");
			// type值不正确
			if (typeArray.length < 3)
				throw new RRException("type值不正确");
			
			switch (typeArray[1]) {
				case "user":
					SpringContextUtils.getBean("");
					break;
	
				default:
					break;
			}
			
		} else {
			 throw new RRException("数据不能为空");
		}
		
		return null;
	}
}
