package io.renren.controller.app;

import io.renren.entity.UserEntity;
import io.renren.service.TokenService;
import io.renren.service.UserService;
import io.renren.util.MsgUtil;
import io.renren.utils.R;
import io.renren.utils.dataSource.DBTypeEnum;
import io.renren.utils.dataSource.DbContextHolder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aliyuncs.exceptions.ClientException;
import com.baomidou.mybatisplus.mapper.EntityWrapper;

@RestController
@RequestMapping("appInterface/school")
public class SchoolAppInterfaceController {
	
//	private final static String JEDISPATH = "127.0.0.1";
	private final static String DATA = "data";
//	private final static String FILEPATH = "http://guanyukeji-static.oss-cn-hangzhou.aliyuncs.com/";
	
	@Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;
	
	@RequestMapping("/main")
	public R main(HttpServletRequest request) throws Exception{
		String key = (request.getParameter("key")==null || "".equals(request.getParameter("key")))?
				getJSONObject(request):request.getParameter("key").replace("&quot;", "\"");
	    System.out.println("---student-------"+key);
	    if(key == null || "".equals(key)){
	    	return R.error("参数未到服务器端");
	    }
	    JSONObject json = JSONObject.fromObject(key);
		if(json.get("type") == null || json.get("type") == "null" || "".equals(json.get("type"))){
			return R.error("请求参数中的type错误");
		}
		String type = json.getString("type");
		if(json.get("token") == null || "".equals(json.get("token")) || "null".equals(json.get("token"))){
			if(type.equals("userLogin")){
				//学生登录接口
				return login(json);
			}else if(type.equals("sendMsgBack")){
				//通过手机号码发送验证码(找回密码时）
				return sendMsg(json.getJSONObject("data"),request);
			}else if(type.equals("updatepasswrodtophone")){
				//忘记密码：通过手机号找回密码
				return updatepasswrodtophone(json.getJSONObject("data"),request);
			}
		}
		return null;
	}
	
	private R updatepasswrodtophone(JSONObject json,HttpServletRequest request){
		String  phone = json.getString("phone");
		String  password = json.getString("password");
		String code = json.getString("code");
		String code2 = request.getSession().getAttribute("randow").toString();
		if(!code.equals(code2)){
			return R.error("验证码错误");
		}
		UserEntity user = new UserEntity();
		user.setMobile(phone);
		EntityWrapper<UserEntity> wrapper = new EntityWrapper<UserEntity>(user);
		user = this.userService.selectOne(wrapper);
		DbContextHolder.setDbType(DBTypeEnum.MYSQL);
		user.setPassword(new Sha256Hash(password).toHex());
		userService.update(user);
		return R.ok().put(DATA, "密码为："+password);
	}
	
	public R login(JSONObject json){
		UserEntity user = new UserEntity();
		UserEntity student = (UserEntity)JSONObject.toBean(json.getJSONObject("data"), UserEntity.class);
		if(!student.getUsername().matches("^1[3|4|5|7|8][0-9]\\d{4,8}$")){
			user.setUsername(student.getUsername());
			EntityWrapper<UserEntity> wrapper = new EntityWrapper<UserEntity>(user);
			user = this.userService.selectOne(wrapper);
		}else{
			user.setMobile(student.getUsername());
			EntityWrapper<UserEntity> wrapper = new EntityWrapper<UserEntity>(user);
			user = this.userService.selectOne(wrapper);
		}
		return R.ok();
	}
	
	private R sendMsg(JSONObject json,HttpServletRequest request){
		String randow = getRandow();
		String phone = json.getString("phone");
		if(phone == null || "null".equals(phone) || "".equals(phone)){
			return R.error("手机号码不能为空");
		}else if(!phone.matches("^1[3|4|5|7|8][0-9]\\d{4,8}$")){
			return R.error("手机号码有误，请重新输入");
		}else{
			UserEntity user = new UserEntity();
			user.setMobile(phone);
			EntityWrapper<UserEntity> wrapper = new EntityWrapper<UserEntity>(user);
			user = this.userService.selectOne(wrapper);
			if(user == null){
				return R.error("该号码暂未绑定");
			}
			try {
				MsgUtil.sendSms(phone, randow,MsgUtil.YZMBD);
			} catch (ClientException e) {
				e.printStackTrace();
			}
		}
		request.getSession().setAttribute("randow", randow);
		return R.ok().put(DATA, randow);
	}
	
	private String getRandow(){
		String randow = "";
		 Random random = new Random();
		for(int i = 0; i < 6; i++){
			randow += random.nextInt(10);
		}
		return randow;
	}
	
	private String getJSONObject(HttpServletRequest request){
		InputStream intpu = null;
		try {
			intpu = request.getInputStream();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		BufferedReader reader = new BufferedReader(new InputStreamReader(intpu));   
        StringBuilder sb = new StringBuilder();   
        String line = null;   
        try {   
            while ((line = reader.readLine()) != null) {   
                sb.append(line);   
            }   
        } catch (IOException e) {   
            e.printStackTrace();   
        } finally { 
        }    
        String result = "";
        try {
        	result = new String(sb.toString().getBytes("gbk"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        return result;
	}

}
