package io.renren.controller.married;

import io.renren.entity.SysUserEntity;
import io.renren.entity.married.MarriedUserEntity;
import io.renren.entity.married.MarryCartEntity;
import io.renren.entity.married.MarryMain;
import io.renren.entity.married.MarryMainEntity;
import io.renren.service.SysUserService;
import io.renren.service.married.MarriedUserService;
import io.renren.service.married.MarryCartService;
import io.renren.service.married.MarryMainService;
import io.renren.utils.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("married/small/weixin/cart")
public class WeixinSmallCartController {
	
	@Autowired
	private MarryCartService marryCartService;
	@Autowired
	private MarryMainService marryMainService;
	@Autowired
	private MarriedUserService marriedUserService;
	@Autowired
	private SysUserService sysUserService;
	
	/**
	 * 购物车提交结算
	 * @param request
	 * @return
	 */
	@RequestMapping("/submitCart")
	public R submitCart(HttpServletRequest request){
		Integer id=Integer.parseInt(request.getParameter("userId"));
		MarriedUserEntity entity=marriedUserService.queryObject(id);
		if(entity==null){
			return R.error("数据不存在");
		}
		String[] key = request.getParameter("ids").toString().split(",");
		List<MarryMain> data = new ArrayList<MarryMain>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("states", 0);
		map.put("userId", entity.getId());
		for (int i = 0; i < key.length; i++) {
			MarryCartEntity marryCartEntity = marryCartService.queryObject(Integer.parseInt(key[i]));
			MarryMainEntity marryMainEntity = marryMainService.queryObject(marryCartEntity.getMainId());
				SysUserEntity sysUser = sysUserService.queryObject(new Long(marryMainEntity.getUserId()));
				MarryMain marryMain = new MarryMain();
				marryMain.setUsername(sysUser.getName());
				map.put("user_id", sysUser.getUserId());
				map.put("Id", marryCartEntity.getId());
				marryMain.setList(marryMainService.queryListtongji(map));
				data.add(marryMain);
		}
		return R.ok().put("list", data);
	}
	/**
	 * 删除购物车
	 * @param request
	 * @return
	 */
	@RequestMapping("/deletemarrycart")
	public R deletemarrycart(HttpServletRequest request){
		//购物车id 
		String key = request.getParameter("ids").toString();
		System.out.println("----->"+key);
		String[] ids = key.split(",");
		System.out.println(ids.length);
		Integer[] id = new Integer[ids.length];
		for (int i = 0; i < ids.length; i++) {
			id[i] = Integer.parseInt(ids[i]);
		}
		marryCartService.deleteBatch(id);
		return R.ok();
	}
	
	@SuppressWarnings({ "rawtypes", "unused" })
	@RequestMapping("/list")
	public R list(HttpServletRequest request){
		MarriedUserEntity entity=marriedUserService.queryObject(Integer.parseInt(request.getParameter("userId")));
		System.out.println("------userId="+entity.getId());
		if(entity==null){
			return R.error("用户信息数据不存在");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("states", 1);
		map.put("userId", entity.getId());
		List<MarryMain> data = new ArrayList<MarryMain>();
		Integer id = 0;
		for (Iterator iterator = marryCartService.queryListtongji(map).iterator(); iterator.hasNext();) {
			MarryMainEntity marryMainEntity = (MarryMainEntity) iterator.next();
			if( id != marryMainEntity.getUserId()){
				id = marryMainEntity.getUserId();
				SysUserEntity sysUser = sysUserService.queryObject(new Long(marryMainEntity.getUserId()));
				MarryMain marryMain = new MarryMain();
				marryMain.setUsername(sysUser.getName());
				marryMain.setSysId(sysUser.getUserId().toString());
				map.put("user_id", marryMainEntity.getUserId());
				marryMain.setList(marryMainService.queryListtongji(map));
				data.add(marryMain);
			}
		}
		return R.ok().put("list", data);
	}
	
	@RequestMapping("/save")
	public R save(HttpServletRequest request){
		Integer main_id = Integer.parseInt(request.getParameter("id"));//商品id
		Integer userId = Integer.parseInt(request.getParameter("userId"));//用户id
		System.out.println(request.getParameter("id"));
		MarriedUserEntity queryObject = this.marriedUserService.queryObject(userId); 
		if(queryObject==null){
			return R.error("用户信息数据不存在");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("main_id", main_id);
		map.put("userId", queryObject.getId());
		map.put("states", 1);
		map.put("sidx", null);
		map.put("order", null);
		map.put("offset", 0);
		map.put("limit", 10);
		List<MarryCartEntity> list = marryCartService.queryList(map);
		if(list.size() > 0){
			return R.error("该商品已存在购物车");
		}
		MarryMainEntity marryMain = marryMainService.queryObject(main_id);
		MarryCartEntity marryCartEntity = new MarryCartEntity();
		marryCartEntity.setMainId(main_id);
		marryCartEntity.setStates(1);
		marryCartEntity.setCreatetime(new Date());
		marryCartEntity.setUserid(queryObject.getId());
		marryCartEntity.setBusinessid(marryMain.getUserId());
		marryCartService.insert(marryCartEntity);
		return R.ok();
	}

	
}
