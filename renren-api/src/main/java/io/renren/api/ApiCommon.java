package io.renren.api;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import io.renren.Dto.RequestJson;
import io.renren.annotation.IgnoreAuth;
import io.renren.utils.RRException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller()
@RequestMapping("/api")
public class ApiCommon {
	
	public static final String DATA_SESSION_KEY = "dataJson";
	
	// 进行接口分发
	@IgnoreAuth
	@RequestMapping("/common/input")
	public String api(@RequestBody RequestJson json, RedirectAttributes attr,HttpServletRequest request) throws Exception{
		
		if ( json == null || json.getType() == null || json.getType().equals(""))
			throw new RRException("接口参数错误");
		System.out.println(json);
		
		String type = json.getType().replaceAll("_", "/");
		attr.addAttribute("dataJson", json.toString());
		
		HttpSession session = request.getSession();
		session.setAttribute(DATA_SESSION_KEY, json);
		return "forward:/" + type;
	}
}	 
