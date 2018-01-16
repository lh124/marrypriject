package io.renren.controller.married;

import io.renren.constant.ControllerConstant;
import io.renren.entity.SysUserEntity;
import io.renren.entity.married.MarriedUserEntity;
import io.renren.entity.married.MarryCartEntity;
import io.renren.entity.married.MarryMainEntity;
import io.renren.entity.married.MarryOrderMainEntity;
import io.renren.entity.married.MarryOrdersEntity;
import io.renren.service.SysUserService;
import io.renren.service.married.MarryCartService;
import io.renren.service.married.MarryMainService;
import io.renren.service.married.MarryOrderMainService;
import io.renren.service.married.MarryOrdersService;
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
@RequestMapping("married/weixin/order")
public class MarryWeixinOrderController {
	
	@Autowired
	private MarryOrdersService marryOrdersService;
	@Autowired
	private MarryOrderMainService marryOrderMainService;
	@Autowired
	private MarryMainService marryMainService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private MarryCartService marryCartService;
	
	/**
	 * 用户保存订单
	 * @param request
	 * @return
	 */
	@RequestMapping("/saveOrder")
	public R saveOrder(HttpServletRequest request){
		MarriedUserEntity user = (MarriedUserEntity)request.getSession().getAttribute(ControllerConstant.SESSION_MARRIED_USER_KEY);
		Integer type = Integer.parseInt(request.getParameter("type"));
		String orderNumber = "494955"+new Date().getTime();
		Integer id = 0;
		if(type == 1){//直接下单
			MarryOrdersEntity marryOrders = new MarryOrdersEntity();
			marryOrders.setOrderNumber(orderNumber);//订单号
			marryOrders.setUserId(user.getId());
			marryOrders.setGmtModifiedtime(new Date());
			marryOrders.setStates(0); 
			Integer mainId = Integer.parseInt(request.getParameter("mainId"));
			MarryMainEntity marryMain = marryMainService.queryObject(mainId);
			SysUserEntity u = sysUserService.queryObject(new Long(marryMain.getUserId()));
			marryOrders.setBusiness(u.getUsername());
			marryOrders.setOrderType(1);
			marryOrders.setMainPrice(marryMain.getPrice());
			marryOrders.setMainDescribe(u.getUsername()+marryMain.getTitle());
			marryOrdersService.insert(marryOrders);
			id = marryOrders.getId();
			MarryOrderMainEntity marryOrderMainEntity = new MarryOrderMainEntity();
			marryOrderMainEntity.setMainId(mainId);
			marryOrderMainEntity.setOrderId(id);
			marryOrderMainService.save(marryOrderMainEntity);
		}else if(type == 2){
			String[] ids = request.getParameter("mainId").split(",");
			MarryOrdersEntity marryOrders = new MarryOrdersEntity();
			marryOrders.setOrderNumber(orderNumber);//订单号
			marryOrders.setUserId(user.getId());
			marryOrders.setGmtModifiedtime(new Date());
			marryOrders.setStates(0);
			marryOrders.setOrderType(2);
			marryOrdersService.insert(marryOrders);
			id = marryOrders.getId();
			for (int i = 0; i < ids.length; i++) {
				MarryCartEntity marryCart = marryCartService.queryObject(Integer.parseInt(ids[i]));
				MarryOrderMainEntity marryOrderMainEntity = new MarryOrderMainEntity();
				marryOrderMainEntity.setOrderId(id);
				marryOrderMainEntity.setMainId(marryCart.getMainId());
				marryOrderMainService.save(marryOrderMainEntity);
				marryCart.setStates(0);
				marryCartService.update(marryCart);
			}
			System.out.println(request.getParameter("mainId"));
		}
		return R.ok().put("id", id);
	}
	
	/**
	 * 通过订单id查询订单
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/findOrder")
	public R findOrder(HttpServletRequest request){
		Integer id = Integer.parseInt(request.getParameter("id"));
		MarryOrdersEntity marryOrders = marryOrdersService.queryObject(id);
		Integer type = marryOrders.getOrderType();
		Map<String, Object> map = new HashMap<String, Object>();
		if(type==1){//直接下单
			MarryOrderMainEntity marryOrderMain = marryOrderMainService.queryObject(id);
			MarryMainEntity marryMain = marryMainService.queryObject(marryOrderMain.getMainId());
			map.put("marryMain", marryMain);
		}else{
			List<MarryMainEntity> marryMainList = new ArrayList<MarryMainEntity>();
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("orderId", marryOrders.getId());
			List<MarryOrderMainEntity> marryOrderMainList = marryOrderMainService.queryList(m);
			for (Iterator iterator2 = marryOrderMainList.iterator(); iterator2.hasNext();) {
				MarryOrderMainEntity marryOrderMainEntity = (MarryOrderMainEntity) iterator2.next();
				marryMainList.add(marryMainService.queryObject(marryOrderMainEntity.getMainId()));
			}
			marryOrders.setMarryMainList(marryMainList);
		}
		map.put("marryOrders", marryOrders);
		map.put("type", type);
		return R.ok().put("map", map);
	}
	
	/**
	 * 用户删除订单
	 * @param request
	 * @return
	 */
	@RequestMapping("/deleteOrder")
	public R deleteOrder(HttpServletRequest request){
		Integer id = Integer.parseInt(request.getParameter("id"));
		marryOrderMainService.delete(id);
		marryOrdersService.delete(id);
		return R.ok();
	}
	
	/**
	 * 查询当前用户的所有订单
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/findOrderlist")
	public R findOrderlist(HttpServletRequest request){
		MarriedUserEntity user = (MarriedUserEntity)request.getSession().getAttribute(ControllerConstant.SESSION_MARRIED_USER_KEY);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", user.getId());
		map.put("offset", 0);
		map.put("limit", 10);
		List<MarryOrdersEntity> list = marryOrdersService.queryList(map);
		List<MarryOrdersEntity> listtotal = new ArrayList<MarryOrdersEntity>();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			MarryOrdersEntity marryOrdersEntity = (MarryOrdersEntity) iterator.next();
			if(marryOrdersEntity.getOrderType()==1){//直接下单
				List<MarryMainEntity> marryMainList = new ArrayList<MarryMainEntity>();
				MarryOrderMainEntity marryOrderMain = marryOrderMainService.queryObject(marryOrdersEntity.getId());
				marryMainList.add(marryMainService.queryObject(marryOrderMain.getMainId()));
				marryOrdersEntity.setMarryMainList(marryMainList);
				listtotal.add(marryOrdersEntity);
			}else if(marryOrdersEntity.getOrderType() == 2){//通过购物车下单
				List<MarryMainEntity> marryMainList = new ArrayList<MarryMainEntity>();
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("orderId", marryOrdersEntity.getId());
				List<MarryOrderMainEntity> marryOrderMainList = marryOrderMainService.queryList(m);
				for (Iterator iterator2 = marryOrderMainList.iterator(); iterator2.hasNext();) {
					MarryOrderMainEntity marryOrderMainEntity = (MarryOrderMainEntity) iterator2.next();
					marryMainList.add(marryMainService.queryObject(marryOrderMainEntity.getMainId()));
				}
				marryOrdersEntity.setMarryMainList(marryMainList);
				listtotal.add(marryOrdersEntity);
			}
		}
		return R.ok().put("list", listtotal);
	}

}
