package io.renren.controller.married;

import io.renren.entity.TokenEntity;
import io.renren.entity.married.MarriedUserEntity;
import io.renren.service.married.MarriedUserService;
import io.renren.util.WeixinUtil;
import io.renren.utils.R;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import redis.clients.jedis.Jedis;

import com.baomidou.mybatisplus.mapper.EntityWrapper;

@RestController
@RequestMapping("married/smallProgram")
public class SmallProgramController {
	
	@Autowired
	private MarriedUserService marriedUserService;
	
	private final static String JEDISPATH = "127.0.0.1";
	private final static String DATA = "data";
	private final static String FILEPATH = "http://static.gykjewm.com/";
	
	@RequestMapping("/main")
	public R main(HttpServletRequest request) throws Exception{
		String type = request.getParameter("type");
		if(type.equals("userLogin")){
			//小程序端进入首页默认登录接口
			return userLogin(request);//返回参数中有：userId（用户id），token（为后面的接口验证登录所用），list（商品列表）
		}else{
			String token = request.getParameter("token");
			if(!getToken(token)){
				return R.error("请重新登录");
			}
			
			
		}
		return null;
	}
	
	@SuppressWarnings("resource")
	private R userLogin(HttpServletRequest request){
		try {
			String code = request.getParameter("code");
			String openId = WeixinUtil.getWeixinOpenId(code);
			MarriedUserEntity u = new MarriedUserEntity();
			u.setOpenid(openId);
			EntityWrapper<MarriedUserEntity> wrapper = new EntityWrapper<MarriedUserEntity>(u);
			u = this.marriedUserService.selectOne(wrapper);
			String token = new Sha256Hash(openId+new Date()).toString();
			Integer id = 0;
			if(u == null){
				
				//新增后将用户id赋值给id
			}else{
				id = u.getId();
			}
			Jedis jedis =  new Jedis(JEDISPATH,6379,30000);
			jedis.set(token, token);
			jedis.expire(token, 24*60*60*365);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userId", id);
			map.put("token", token);
			map.put("list", null);//此处list为商品列表
			return R.ok();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("resource")
	private boolean getToken(String token){
		if(new Jedis(JEDISPATH,6379,30000).get(token) == null || "".equals(new Jedis(JEDISPATH,6379,30000).get(token))){
			return false;
		}else{
			return true;
		}
	}

}
