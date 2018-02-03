package io.renren.controller.married;

import io.renren.entity.SysUserEntity;
import io.renren.entity.married.MarriedUserEntity;
import io.renren.entity.married.MarryCartEntity;
import io.renren.entity.married.MarryMainEntity;
import io.renren.entity.married.MarryOrderMainEntity;
import io.renren.entity.married.MarryOrdersEntity;
import io.renren.entity.married.OrderAndMain;
import io.renren.service.SysUserService;
import io.renren.service.married.MarriedUserService;
import io.renren.service.married.MarryCartService;
import io.renren.service.married.MarryMainService;
import io.renren.service.married.MarryOrderMainService;
import io.renren.service.married.MarryOrdersService;
import io.renren.utils.R;
import io.renren.utils.RRException;

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
@RequestMapping("married/small/weixin/order")
public class MarrySmallWeixinOrderController {

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

	@SuppressWarnings("unused")
	private  String checkDate(String str)throws Exception{
		if(str != null || !"".equals(str)){
			return str;
		}else{
			if(str==null||str.equals("")){
				throw new RRException(str + "值不存在，请检查参数是否正确");
			}else{
				throw new RRException(str + "参数不存在，请检查参数是否正确");
			}
		}
	}
	
	/**
	 * 用户保存订单
	 * @param request
	 * @return
	 */
	@RequestMapping("/saveOrder")
	public R saveOrder(HttpServletRequest request){
		String userId=request.getParameter("userId");
		MarriedUserEntity user=marryUserService.queryObject(Integer.parseInt(userId));
		if(user==null){
			R.error("还没有该用户的商品，去首页添加");
		}
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
			marryOrders.setBusiness(u.getName());
			marryOrders.setOrderType(1);
			marryOrders.setMainPrice(marryMain.getPrice());
			marryOrders.setMainDescribe(u.getName()+marryMain.getTitle());
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
			Double totalFee = 0.00;
			for (int i = 0; i < ids.length; i++) {
				MarryCartEntity marryCart = marryCartService.queryObject(Integer.parseInt(ids[i]));
				String price = marryMainService.queryObject(marryCart.getMainId()).getPrice();
				totalFee += (new  Double(Double.valueOf(price))).doubleValue();
				MarryOrderMainEntity marryOrderMainEntity = new MarryOrderMainEntity();
				marryOrderMainEntity.setOrderId(id);
				marryOrderMainEntity.setMainId(marryCart.getMainId());
				marryOrderMainService.save(marryOrderMainEntity);
				marryCart.setStates(0);
				marryCartService.update(marryCart);
			}
			marryOrders.setMainPrice(totalFee.toString());
			marryOrdersService.update(marryOrders);
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
	public R findOrder(HttpServletRequest request)throws Exception{
		Integer id = Integer.parseInt(request.getParameter("id"));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		List<MarryOrdersEntity> list = marryOrdersService.queryListorder(map);
		List<MarryOrdersEntity> listtotal = new ArrayList<MarryOrdersEntity>();
		Map<String, List<MarryMainEntity>> marryMainMap = new HashMap<String, List<MarryMainEntity>>();
		List<MarryMainEntity> marryMainList = null;
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			MarryOrdersEntity marryOrdersEntity = (MarryOrdersEntity) iterator.next();
			Map<String, Object> orderMainMap = new HashMap<String, Object>();
			orderMainMap.put("orderId", marryOrdersEntity.getId());
			List<MarryOrderMainEntity> orderMainList = marryOrderMainService.queryList(orderMainMap);
			Double sumprice=0.0;
			for (Iterator iterator2 = orderMainList.iterator(); iterator2.hasNext();) {
				MarryOrderMainEntity marryOrderMainEntity = (MarryOrderMainEntity) iterator2.next();
				MarryMainEntity marryMain = marryMainService.queryObject(marryOrderMainEntity.getMainId());
				SysUserEntity u = sysUserService.queryObject(new Long(marryMain.getUserId()));
				marryOrdersEntity.setBusiness(u.getName());
				marryOrdersEntity.setMainDescribe(u.getName()+marryMain.getTitle());
				Double price = Double.parseDouble(marryMain.getPrice());
				sumprice+=price;
				if(marryMainMap.get(marryMain.getUserId()) == null){
					marryMainList = new ArrayList<MarryMainEntity>();
				}
				marryMainList.add(marryMain);
				marryMainMap.put(marryMain.getUserId()+","+marryOrderMainEntity.getOrderId(), marryMainList);
			}
			marryOrdersEntity.setMainPrice(String.valueOf(sumprice));
			marryOrdersEntity.setCount(orderMainList.size());
		}

		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			MarryOrdersEntity marryOrdersEntity = (MarryOrdersEntity) iterator.next();
			List<OrderAndMain> orderAndMainList = new ArrayList<OrderAndMain>();
			for(String entry : marryMainMap.keySet()){
				String[] entrys = entry.split(",");
				if(Integer.parseInt(entrys[1]) == marryOrdersEntity.getId()){
					OrderAndMain andMain = new OrderAndMain();
					andMain.setUserId(Integer.parseInt(entrys[0]));
					andMain.setUserName(sysUserService.queryObject(new Long(entrys[0])).getName());
					andMain.setMainList(marryMainMap.get(entry));
					orderAndMainList.add(andMain);
				}
			}
			marryOrdersEntity.setMarryMainList(orderAndMainList);
			listtotal.add(marryOrdersEntity);

		}
		return R.ok().put("data", listtotal);
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
	@Autowired
	private MarriedUserService marryUserService;
	@SuppressWarnings("rawtypes")
	@RequestMapping("/findOrderlist")
	public R findOrderlist(HttpServletRequest request){
		//System.out.println(request.getParameter("userId"));
		Integer userid=Integer.parseInt(request.getParameter("userId"));
		System.out.println("========"+userid);
		MarriedUserEntity queryObject = marryUserService.queryObject(userid);
		if(queryObject==null){
			R.error("还没有订单，赶快去下单吧");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", queryObject.getId());
		map.put("offset", 0);
		map.put("limit", 10);
		List<MarryOrdersEntity> list = marryOrdersService.queryList(map);
		List<MarryOrdersEntity> listtotal = new ArrayList<MarryOrdersEntity>();
		Map<String, List<MarryMainEntity>> marryMainMap = new HashMap<String, List<MarryMainEntity>>();
		List<MarryMainEntity> marryMainList = null;
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			MarryOrdersEntity marryOrdersEntity = (MarryOrdersEntity) iterator.next();
			
			Map<String, Object> orderMainMap = new HashMap<String, Object>();
			orderMainMap.put("orderId", marryOrdersEntity.getId());
			List<MarryOrderMainEntity> orderMainList = marryOrderMainService.queryList(orderMainMap);
			Double sumprice=0.0;
			for (Iterator iterator2 = orderMainList.iterator(); iterator2.hasNext();) {
				MarryOrderMainEntity marryOrderMainEntity = (MarryOrderMainEntity) iterator2.next();
				MarryMainEntity marryMain = marryMainService.queryObject(marryOrderMainEntity.getMainId());
				SysUserEntity u = sysUserService.queryObject(new Long(marryMain.getUserId()));
				marryOrdersEntity.setBusiness(u.getName());
				marryOrdersEntity.setMainDescribe(u.getName()+marryMain.getTitle());
				Double price = Double.parseDouble(marryMain.getPrice());
				sumprice+=price;
				if(marryMainMap.get(marryMain.getUserId()) == null){
					marryMainList = new ArrayList<MarryMainEntity>();
				}
				marryMainList.add(marryMain);
				marryMainMap.put(marryMain.getUserId()+","+marryOrderMainEntity.getOrderId(), marryMainList);
			}
			marryOrdersEntity.setMainPrice(String.valueOf(sumprice));
			marryOrdersEntity.setCount(orderMainList.size());
//			marryOrdersService.update(marryOrdersEntity);
		}

		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			MarryOrdersEntity marryOrdersEntity = (MarryOrdersEntity) iterator.next();
			List<OrderAndMain> orderAndMainList = new ArrayList<OrderAndMain>();
			for(String entry : marryMainMap.keySet()){
				String[] entrys = entry.split(",");
				if(Integer.parseInt(entrys[1]) == marryOrdersEntity.getId()){
					OrderAndMain andMain = new OrderAndMain();
					andMain.setUserId(Integer.parseInt(entrys[0]));
					andMain.setUserName(sysUserService.queryObject(new Long(entrys[0])).getName());
					andMain.setMainList(marryMainMap.get(entry));
					orderAndMainList.add(andMain);
				}
			}
			marryOrdersEntity.setMarryMainList(orderAndMainList);
			listtotal.add(marryOrdersEntity);

		}

		System.out.println("-----------"+listtotal);
		return R.ok().put("list", listtotal);
	}
}
