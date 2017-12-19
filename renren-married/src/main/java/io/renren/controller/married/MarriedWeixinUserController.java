package io.renren.controller.married;

import io.renren.constant.ControllerConstant;
import io.renren.entity.married.MarriedUserEntity;
import io.renren.service.married.MarriedUserService;
import io.renren.service.married.MarryCartService;
import io.renren.utils.R;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("married/user")
public class MarriedWeixinUserController {
	
	@Autowired
	private MarriedUserService marriedUserService;
	@Autowired
	private MarryCartService marryCartService;
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("/save")
	public R save(HttpServletRequest request){
		int total = 0;
		try {
//			String openId = WeixinUtil.getWeixinOpenId(request.getParameter("code"));
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("sidx", null);
			map.put("order", null);
			map.put("offset", 0);
			map.put("limit", 10);
//			map.put("openId", openId);
			map.put("states", 1);
			map.put("openId", "o7__rjjocXdATM4sz0rYbt2z7SRw");
			List<MarriedUserEntity> list = marriedUserService.queryList(map);
			if(list.size() == 0){
////				JSONObject jsonObject = WeixinUtil.getUserInfo(openId);
//				MarriedUserEntity user = new MarriedUserEntity();
//				user.setCreatetime(new Date());
//				user.setNickname(jsonObject.getString("nickname"));
//				user.setPic(jsonObject.getString("headimgurl"));
////				user.setOpenid(openId);
//				marriedUserService.insert(user);
//				request.getSession().setAttribute(ControllerConstant.SESSION_MARRIED_USER_KEY, user);
//				map.put("userId", user.getId());
			}else{
				for (Iterator iterator = list.iterator(); iterator.hasNext();) {
					MarriedUserEntity marriedUserEntity = (MarriedUserEntity) iterator.next();
					map.put("userId", marriedUserEntity.getId());
					request.getSession().setAttribute(ControllerConstant.SESSION_MARRIED_USER_KEY, marriedUserEntity);
				}
			}
			total = marryCartService.queryList(map).size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return R.ok().put("total", total);
	}
}