package io.renren.controller.married;

import io.renren.entity.married.MarriedUserEntity;
import io.renren.service.married.MarriedUserService;
import io.renren.util.WeixinUtil;
import io.renren.utils.R;

import java.util.Date;

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
		String token = request.getParameter("token");
		if(type.equals("userLogin")){
			return userLogin(request);
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
			if(u == null){
				
			}else{
				
			}
			Jedis jedis =  new Jedis(JEDISPATH,6379,30000);
			jedis.set(token, token);
			jedis.expire(token, 24*60*60*365);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
