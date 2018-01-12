package io.renren.controller.married;

import io.renren.service.married.MarryMainService;
import io.renren.utils.R;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("married/weixin/index")
public class WeixinIndexController {
	
	@Autowired
	private MarryMainService marryMainService;
	
	//微信查询商品接口
	@RequestMapping("/findObj")
	public R findObj(HttpServletRequest request){
		Integer id = Integer.parseInt(request.getParameter("id"));
		return R.ok().put("obj", marryMainService.queryObject(id));
	}
	
	//首页查询商品列表
	@RequestMapping("/list")
	public R list(HttpServletRequest request){
		Integer offset = (Integer.parseInt(request.getParameter("page"))-1)*10;
		Integer limit = Integer.parseInt(request.getParameter("page"))*10;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sidx", null);
		map.put("order", null);
		map.put("offset", offset);
		map.put("limit", limit);
		return R.ok().put("list", marryMainService.queryList(map));
	}

}
