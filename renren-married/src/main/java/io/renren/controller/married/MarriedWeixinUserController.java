package io.renren.controller.married;

import io.renren.constant.ControllerConstant;
import io.renren.entity.married.MarriedUserEntity;
import io.renren.entity.married.MarryOrdersEntity;
import io.renren.service.married.MarriedUserService;
import io.renren.service.married.MarryCartService;
import io.renren.service.married.MarryOrdersService;
import io.renren.util.WeixinUtil;
import io.renren.utils.R;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;

@RestController
@RequestMapping("married/user")
public class MarriedWeixinUserController {
	
	@Autowired
	private MarriedUserService marriedUserService;
	@Autowired
	private MarryCartService marryCartService;
	@Autowired
	private MarryOrdersService marryOrdersService;
	/**
	 * 微信首页授权用户登录保存用户信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/save")
	public R save(HttpServletRequest request){
		int total = 0;
		try {
			MarriedUserEntity us = (MarriedUserEntity)request.getSession().getAttribute(ControllerConstant.SESSION_MARRIED_USER_KEY);
			String code = request.getParameter("code");
			if(code != null && !"".equals(code) && !"null".equals(code)){
				String openId = WeixinUtil.getWeixinOpenId(code);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("sidx", null);
				map.put("order", null);
				map.put("offset", 0);
				map.put("limit", 100);
				map.put("states", 1);
				MarriedUserEntity u = new MarriedUserEntity();
				u.setOpenid(openId);
				EntityWrapper<MarriedUserEntity> wrapper = new EntityWrapper<MarriedUserEntity>(u);
				u = this.marriedUserService.selectOne(wrapper);
				if(u == null){
					JSONObject jsonObject = WeixinUtil.getUserInfo(openId);
					MarriedUserEntity user = new MarriedUserEntity();
					user.setCreatetime(new Date());
					user.setNickname(jsonObject.getString("nickname"));
					user.setPic(jsonObject.getString("headimgurl"));
					user.setOpenid(openId);
					marriedUserService.insert(user);
					request.getSession().setAttribute(ControllerConstant.SESSION_MARRIED_USER_KEY, user);
					map.put("userId", user.getId());
				}else{
					map.put("userId", u.getId());
					map.put("offset", 0);
					map.put("limit", 10);
					map.put("states", 1);
					List<MarryOrdersEntity> list = marryOrdersService.queryList(map);
					if(list.size()==0){
						request.getSession().setAttribute("jurisdiction", 0);//无权限就不让其显示相关模块
					}else{
						request.getSession().setAttribute("jurisdiction", 1);
					}
					request.getSession().setAttribute(ControllerConstant.SESSION_MARRIED_USER_KEY, u);
				}
				total = marryCartService.queryList(map).size();
				List<MarryOrdersEntity> list = marryOrdersService.queryList(map);
				if(list.size()==0){
					request.getSession().setAttribute("jurisdiction", 0);//无权限就不让其显示相关模块
				}else{
					request.getSession().setAttribute("jurisdiction", 1);
				}
			}else if(us != null){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("sidx", null);
				map.put("order", null);
				map.put("offset", 0);
				map.put("limit", 100);
				map.put("states", 1);
				map.put("userId", us.getId());
				total = marryCartService.queryList(map).size();
				
				List<MarryOrdersEntity> list = marryOrdersService.queryList(map);
				if(list.size()==0){
					request.getSession().setAttribute("jurisdiction", 0);//无权限就不让其显示相关模块
				}else{
					request.getSession().setAttribute("jurisdiction", 1);
				}
			}else{
				String openId = "o7__rjj8Iq1k8Uu52TnNP2YIUa04";
				MarriedUserEntity u = new MarriedUserEntity();
				u.setOpenid(openId);
				EntityWrapper<MarriedUserEntity> wrapper = new EntityWrapper<MarriedUserEntity>(u);
				u = this.marriedUserService.selectOne(wrapper);
				request.getSession().setAttribute(ControllerConstant.SESSION_MARRIED_USER_KEY, u);
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("sidx", null);
				map.put("order", null);
				map.put("offset", 0);
				map.put("limit", 100);
				map.put("states", 1);
				map.put("userId", u.getId());
				List<MarryOrdersEntity> list = marryOrdersService.queryList(map);
				if(list.size()==0){
					request.getSession().setAttribute("jurisdiction", 0);//无权限就不让其显示相关模块
				}else{
					request.getSession().setAttribute("jurisdiction", 1);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return R.ok().put("total", total);
	}
}
