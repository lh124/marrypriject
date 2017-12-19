package io.renren.controller.married;

import io.renren.constant.ControllerConstant;
import io.renren.entity.married.MarriedUserEntity;
import io.renren.entity.married.MarryParticipateEntity;
import io.renren.entity.married.MarryWeddingEntity;
import io.renren.service.married.MarryParticipateService;
import io.renren.service.married.MarryWeddingService;
import io.renren.util.WeixinUtil;
import io.renren.utils.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
@RestController
@RequestMapping("married/weixin/me")
public class WeixinMeController {
	
	@Autowired
	private MarryWeddingService marryWeddingService;
	@Autowired
	private MarryParticipateService marryParticipateService;
	
	/**
	 * 查询所有参加婚礼的人
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/allattendawedding")
	public R allattendawedding(HttpServletRequest request){
		MarryWeddingEntity marryWedding = new MarryWeddingEntity();
		MarriedUserEntity user = (MarriedUserEntity)request.getSession().getAttribute(ControllerConstant.SESSION_MARRIED_USER_KEY);
		marryWedding.setUserId(user.getId());
		EntityWrapper<MarryWeddingEntity> wrapper = new EntityWrapper<MarryWeddingEntity>(marryWedding);
		marryWedding = this.marryWeddingService.selectOne(wrapper);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("weddingId", marryWedding.getId());
		map.put("offset", 0);
		map.put("limit", 10);
		List<MarryParticipateEntity> list = marryParticipateService.queryList(map);
		List<MarriedUserEntity> list0 = new ArrayList<MarriedUserEntity>();
		List<MarriedUserEntity> list1 = new ArrayList<MarriedUserEntity>();
		List<MarriedUserEntity> list2 = new ArrayList<MarriedUserEntity>();
		List<MarriedUserEntity> list3 = new ArrayList<MarriedUserEntity>();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			MarryParticipateEntity marryParticipateEntity = (MarryParticipateEntity) iterator.next();
			JSONObject jsonObject = WeixinUtil.getUserInfo(marryParticipateEntity.getOpenid());
			MarriedUserEntity marrieduser = new MarriedUserEntity();
			marrieduser.setNickname(jsonObject.getString("nickname"));
			marrieduser.setId(marryWedding.getId());
			marrieduser.setPic(jsonObject.getString("headimgurl"));
			marrieduser.setOpenid(marryParticipateEntity.getOpenid());
			list3.add(marrieduser);
			if(marryParticipateEntity.getStates() == 0){
				list0.add(marrieduser);
			}else if(marryParticipateEntity.getStates() == 1){
				list1.add(marrieduser);
			}else if(marryParticipateEntity.getStates() == 2){
				list2.add(marrieduser);
			}
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("list0", list0);
		m.put("list1", list1);
		m.put("list2", list2);
		m.put("list3", list3);
		return R.ok().put("data", m);
	}
	
	/**
	 * 保存参加婚礼
	 * @param request
	 * @return
	 */
	@RequestMapping("/attendawedding")
	public R attendawedding(HttpServletRequest request){
		try {
			String openId = WeixinUtil.getWeixinOpenId(request.getParameter("code"));
			Integer states = Integer.parseInt(request.getParameter("states"));
			Integer id = Integer.parseInt(request.getParameter("id"));
			MarryParticipateEntity marryParticipate = new MarryParticipateEntity();
			marryParticipate.setOpenid(openId);
			marryParticipate.setWeddingid(id);
			EntityWrapper<MarryParticipateEntity> wrapper = new EntityWrapper<MarryParticipateEntity>(marryParticipate);
			marryParticipate = marryParticipateService.selectOne(wrapper);
			if(marryParticipate == null){
				MarryParticipateEntity mp = new MarryParticipateEntity();
				mp.setOpenid(openId);
				mp.setWeddingid(id);
				mp.setStates(states);
				marryParticipateService.save(mp);
			}else{
				marryParticipate.setStates(states);
				marryParticipateService.update(marryParticipate);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return R.ok();
	}
	
	
	/**
	 * 保存婚礼记录
	 * @param request
	 * @return
	 */
	@RequestMapping("/saveWedding")
	public R saveWedding(HttpServletRequest request){
		MarriedUserEntity user = (MarriedUserEntity)request.getSession().getAttribute(ControllerConstant.SESSION_MARRIED_USER_KEY);
		MarryWeddingEntity marryWedding = new MarryWeddingEntity();
		String bridename = request.getParameter("brideName");
		String content = request.getParameter("content");
		String groomname = request.getParameter("groomName");
		String weddingaddress = request.getParameter("weddingAddress");
		Date weddingdate = new Date();
		try {
			weddingdate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(request.getParameter("weddingDate").replace("T", " "));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		marryWedding.setBridename(bridename);
		marryWedding.setContent(content);
		marryWedding.setGroomname(groomname);
		marryWedding.setUserId(user.getId());
		marryWedding.setWeddingaddress(weddingaddress);
		marryWedding.setWeddingdate(weddingdate);
		marryWeddingService.save(marryWedding);
		return R.ok();
	}
	
	/**
	 * 查询婚礼记录
	 * @param request
	 * @return
	 */
	@RequestMapping("/findWedding")
	public R findWedding(HttpServletRequest request){
		MarryWeddingEntity marryWedding = new MarryWeddingEntity();
		if(request.getParameter("id") == null || "null".equals(request.getParameter("id"))){
			MarriedUserEntity user = (MarriedUserEntity)request.getSession().getAttribute(ControllerConstant.SESSION_MARRIED_USER_KEY);
			marryWedding.setUserId(user.getId());
		}else{
			marryWedding.setId(Integer.parseInt(request.getParameter("id")));
		}
		EntityWrapper<MarryWeddingEntity> wrapper = new EntityWrapper<MarryWeddingEntity>(marryWedding);
		marryWedding = this.marryWeddingService.selectOne(wrapper);
		return R.ok().put("marryWedding", marryWedding);
	}

}