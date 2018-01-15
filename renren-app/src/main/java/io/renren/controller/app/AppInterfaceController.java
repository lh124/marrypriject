package io.renren.controller.app;

import io.renren.entity.TokenEntity;
import io.renren.entity.smart.StudentEntity;
import io.renren.service.TokenService;
import io.renren.service.smart.SmartExceptionService;
import io.renren.service.smart.StudentService;
import io.renren.utils.R;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jiguangtuisong.JpushClientUtil;
import jiguangtuisong.JpushClientUtil2;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("appInterface/test")
public class AppInterfaceController {
	
	@Autowired
	private TokenService tokenService;
	@Autowired
	private StudentService studentService;
	@Autowired
	private SmartExceptionService smartExceptionService;
	
	
	@RequestMapping("/main")
	public R main(HttpServletRequest request) throws Exception{
		String key = request.getParameter("key").replace("&quot;", "\"");
	    System.out.println(key);
	    JSONObject json = JSONObject.fromObject(key);
	    String type = json.getString("type");
	    if("test".equals(type)){
	    	return testTongzhi(json);
	    }else if("exceptionList".equals(type)){
	    	return exceptionList(json);
	    }else if("exceptionFind".equals(type)){
	    	return exceptionFind(json);
	    }
		return null;
	}
	
	private R exceptionFind(JSONObject json){
		return R.ok().put("obj", smartExceptionService.queryObject(json.getInt("id")));
	}
	
	private R exceptionList(JSONObject json){
		String schoolName = json.getString("schoolName"); 
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("offset", 0);
		map.put("limit", 1000);
		map.put("schoolName", schoolName);
		return R.ok().put("list", smartExceptionService.queryList(map));
	}
	
	private R testTongzhi(JSONObject json){
		Integer userType = json.getInt("userType");//用户类型
	    Integer newsType = json.getInt("newsType");//消息类型
	    Map<String, Object> m = new HashMap<String, Object>();
	    m.put("type", newsType);
	    m.put("newsId", 1);
	    m.put("id", 1);
	    StudentEntity studentEntity = studentService.queryObject(json.getInt("userId"));
	    if(studentEntity == null){
	    	return R.error("该用户不存在");
	    }
		String title = "";
	    if(newsType == 1){
	    	title = "学校通知";
	    }else if(newsType == 2){
	    	title = "老师通知";
	    }else if(newsType == 3){
	    	title = "班内消息通知";
	    }else if(newsType == 4){
	    	title = "请假申请通知";
	    }else if(newsType == 5){
	    	title = "请假申请回复通知";
	    }else if(newsType == 6){
	    	title = "进出校通知";
	    }
	    if(userType == 1){//学生
	    	if(studentEntity.getUserType().equals("2")){
	    		return R.error("该用户不是学生");
	    	}
	    	TokenEntity token = tokenService.queryByUserId(new Long(json.getString("userId")));
	    	if(token == null){
	    		return R.error("该账号需要重新登录");
	    	}
	    	JpushClientUtil2.sendToRegistrationId(json.getString("userId"), title, title,
					"", JSONObject.fromObject(m).toString());
	    	return R.ok("推送通知成功");
	    }else if(userType == 2){//老师
	    	if(studentEntity.getUserType().equals("1")){
	    		return R.error("该用户不是老师");
	    	}
	    	TokenEntity token = tokenService.queryByUserId(new Long(json.getString("userId")));
	    	if(token == null){
	    		return R.error("该账号需要重新登录");
	    	}
	    	JpushClientUtil.sendToRegistrationId(json.getString("userId"), "老师通知", "老师通知",
					"", JSONObject.fromObject(m).toString());
	    	return R.ok("推送通知成功");
	    }
		return R.ok();
	}

}
