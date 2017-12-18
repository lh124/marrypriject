package io.renren.controller.married;

import io.renren.constant.ControllerConstant;
import io.renren.entity.married.MarriedUserEntity;
import io.renren.entity.married.MarryCartEntity;
import io.renren.service.married.MarryCartService;
import io.renren.utils.R;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("married/weixin/cart")
public class WeixinCartController {
	
	@Autowired
	private MarryCartService marryCartService;
	
	@RequestMapping("/list")
	public R list(HttpServletRequest request){
		MarriedUserEntity user = (MarriedUserEntity)request.getSession().getAttribute(ControllerConstant.SESSION_MARRIED_USER_KEY);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("states", 1);
		map.put("sidx", null);
		map.put("order", null);
		map.put("offset", 0);
		map.put("limit", 10);
		map.put("userId", user.getId());
		List<MarryCartEntity> list = marryCartService.queryList(map);
		return R.ok().put("list", list);
	}
	
	@RequestMapping("/save")
	public R save(HttpServletRequest request){
		Integer main_id = Integer.parseInt(request.getParameter("id"));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("main_id", main_id);
		map.put("states", 1);
		map.put("sidx", null);
		map.put("order", null);
		map.put("offset", 0);
		map.put("limit", 10);
		List<MarryCartEntity> list = marryCartService.queryList(map);
		if(list.size() > 0){
			return R.error("该商品已存在购物车");
		}
		MarryCartEntity marryCartEntity = new MarryCartEntity();
		marryCartEntity.setMainId(main_id);
		marryCartEntity.setCreatetime(new Date());
		MarriedUserEntity user = (MarriedUserEntity)request.getSession().getAttribute(ControllerConstant.SESSION_MARRIED_USER_KEY);
		marryCartEntity.setUserId(user.getId());
		marryCartService.save(marryCartEntity);
		return R.ok();
	}

}
