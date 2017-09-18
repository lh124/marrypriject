package io.renren.utils;

import io.renren.enums.TypeEnum;
import io.renren.model.dto.SignParameter;

public class AliyunFileTypeUtil {

	public static String SUCCESS = "ok";
	public static String FAIL = "error";
	
	public static SignParameter getSignParameter(Integer type){
		SignParameter paremeter = new SignParameter();
		paremeter.setType(type);
		
		
		TypeEnum[] ty = TypeEnum.values();
		
		for(TypeEnum tenum : ty){
			if (tenum.getType().equals(type)) {
				paremeter.setStatus(SUCCESS);
				paremeter.setDirPrefix(tenum.getDirPrefix());
				paremeter.setDocument(tenum.getDocument());
				paremeter.setCallBackUrl(tenum.getCallBackUrl());
				
				return paremeter;
			} 
		}
		
		paremeter.setStatus(FAIL);
		paremeter.setMsg("无此类型");
		
		
		return paremeter;
	}
}
