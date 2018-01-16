package io.renren.controller.married;

import io.renren.constant.ControllerConstant;
import io.renren.entity.married.MarriedUserEntity;
import io.renren.entity.married.MarryBlessingEntity;
import io.renren.entity.married.MarryHelpEntity;
import io.renren.entity.married.MarryOrdersEntity;
import io.renren.entity.married.MarryParticipateEntity;
import io.renren.entity.married.MarryPhotoEntity;
import io.renren.entity.married.MarrySignEntity;
import io.renren.entity.married.MarryWeddingEntity;
import io.renren.service.married.MarriedUserService;
import io.renren.service.married.MarryBlessingService;
import io.renren.service.married.MarryCartService;
import io.renren.service.married.MarryHelpService;
import io.renren.service.married.MarryImgService;
import io.renren.service.married.MarryOrdersService;
import io.renren.service.married.MarryParticipateService;
import io.renren.service.married.MarryPhotoService;
import io.renren.service.married.MarrySignService;
import io.renren.service.married.MarryWeddingService;
import io.renren.util.WeixinUtil;
import io.renren.utils.R;

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
	@Autowired
	private MarriedUserService marriedUserService;
	@Autowired
	private MarrySignService marrySignService;
	@Autowired
	private MarryImgService marryImgService;
	@Autowired
	private MarryBlessingService marryBlessingService;
	@Autowired
	private MarryPhotoService marryPhotoService;
	@Autowired
	private MarryHelpService marryHelpService;
	@Autowired
	private MarryCartService marryCartService;
	@Autowired
	private MarryOrdersService marryOrdersService;
	
	/**
	 * 权限管理和查看购物车数据
	 * @param request
	 * @return
	 */
	@RequestMapping("/jurisdiction")
	public R jurisdiction(HttpServletRequest request){
		MarriedUserEntity user = (MarriedUserEntity)request.getSession().getAttribute(ControllerConstant.SESSION_MARRIED_USER_KEY);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sidx", null);
		map.put("order", null);
		map.put("offset", 0);
		map.put("limit", 100);
		map.put("states", 1);
		map.put("userId", user.getId());
		int total = marryCartService.queryList(map).size();
		map.put("userId", user.getId());
		map.put("offset", 0);
		map.put("limit", 10);
		map.put("states", 1);
		List<MarryOrdersEntity> list = marryOrdersService.queryList(map);
		if(list.size()==0){
			request.getSession().setAttribute("jurisdiction", 1);//无权限就不让其显示相关模块
		}else{
			request.getSession().setAttribute("jurisdiction", 1);
		}
		return R.ok().put("total", total);
	}
	
	/**
	 * 保存用户帮助记录
	 * @param request
	 * @return
	 */
	@RequestMapping("/saveHelp")
	public R saveHelp(HttpServletRequest request){
		MarriedUserEntity user = (MarriedUserEntity)request.getSession().getAttribute(ControllerConstant.SESSION_MARRIED_USER_KEY);
		MarryHelpEntity marryHelp = new MarryHelpEntity();
		marryHelp.setUserid(user.getId());
		marryHelp.setContent(request.getParameter("content"));
		marryHelp.setCreateTime(new Date());
		marryHelpService.save(marryHelp);
		return R.ok();
	}
	
	/**
	 * 保存所有的视频路径
	 * @param request
	 * @return
	 */
	@RequestMapping("/saveWeddingPhoto")
	public R saveWeddingPhoto(HttpServletRequest request){
		String[] videos = request.getParameter("video").toString().split(",");
		MarriedUserEntity user = (MarriedUserEntity)request.getSession().getAttribute(ControllerConstant.SESSION_MARRIED_USER_KEY);
		MarryWeddingEntity mw = new MarryWeddingEntity();
		mw.setUserId(user.getId());
		EntityWrapper<MarryWeddingEntity> wrapper = new EntityWrapper<MarryWeddingEntity>(mw);
		mw = marryWeddingService.selectOne(wrapper);
		MarryPhotoEntity marryPhoto = new MarryPhotoEntity();
		String weddingId = (request.getParameter("id")==null || "".equals(request.getParameter("id")))?mw.getId().toString():request.getParameter("id");
		for (int i = 0; i < videos.length; i++) {
			marryPhoto.setWeddingid(Integer.parseInt(weddingId));
			marryPhoto.setType(2);
			marryPhoto.setPic(videos[i]);
			marryPhotoService.save(marryPhoto);
		}
		return R.ok();
	}
	
	/**
	 * 通过婚礼id查询所有的摄影展图片
	 * @param request
	 * @return
	 */
	@RequestMapping("/findAllWeddingPhoto")
	public R findAllWeddingPhoto(HttpServletRequest request){
		MarriedUserEntity user = (MarriedUserEntity)request.getSession().getAttribute(ControllerConstant.SESSION_MARRIED_USER_KEY);
		MarryWeddingEntity mw = new MarryWeddingEntity();
		mw.setUserId(user.getId());
		EntityWrapper<MarryWeddingEntity> wrapper = new EntityWrapper<MarryWeddingEntity>(mw);
		mw = marryWeddingService.selectOne(wrapper);
		Map<String, Object> map = new HashMap<String, Object>();
		if(mw != null){
			String weddingId = (request.getParameter("id")==null || "".equals(request.getParameter("id")))?mw.getId().toString():request.getParameter("id");
			map.put("weddingId", weddingId);
		}else{
			map.put("weddingId", 0);
		}
		return R.ok().put("list", marryPhotoService.queryList(map));
	}
	
	/**
	 * 通过当前用户获取婚礼id
	 * @param request
	 * @return
	 */
	@RequestMapping("/getWeddingId")
	public R getWeddingId(HttpServletRequest request){
		//获取当前登录用户
		MarriedUserEntity user = (MarriedUserEntity)request.getSession().getAttribute(ControllerConstant.SESSION_MARRIED_USER_KEY);
		MarryWeddingEntity mw = new MarryWeddingEntity();
		mw.setUserId(user.getId());
		EntityWrapper<MarryWeddingEntity> wrapper = new EntityWrapper<MarryWeddingEntity>(mw);
		mw = marryWeddingService.selectOne(wrapper);
		if(mw == null){
			return R.error();
		}
		return R.ok().put("id", mw.getId());
	}
	
	/**
	 * 祝福管理
	 * @param request
	 * @return
	 */
	@RequestMapping("/blessManage")
	public R blessManage(HttpServletRequest request){
		//获取当前登录用户
		MarriedUserEntity user = (MarriedUserEntity)request.getSession().getAttribute(ControllerConstant.SESSION_MARRIED_USER_KEY);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", user.getId());
		map.put("offset", 0);
		map.put("limit", 10);
		map.put("blessingtype", 1);
		List<MarryBlessingEntity> list1 = marryBlessingService.queryList(map);//普通祝福
		map.put("blessingtype", 2);
		List<MarryBlessingEntity> list2 = marryBlessingService.queryList(map);//红包祝福
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("list1", list1);
		m.put("list2", list2);
		return R.ok().put("list", m);
	}
	
	/**
	 * 保存祝福
	 * @param request
	 * @return
	 */
	@RequestMapping("/saveBlessing")
	public R saveBlessing(HttpServletRequest request){
		MarriedUserEntity user = (MarriedUserEntity)request.getSession().getAttribute(ControllerConstant.SESSION_MARRIED_USER_KEY);
		MarryBlessingEntity mbe = new MarryBlessingEntity();
		try {
			String openId = user == null ?WeixinUtil.getWeixinOpenId(request.getParameter("code")):user.getOpenid();
			mbe.setOpenid(openId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mbe.setContent(request.getParameter("content"));
		mbe.setWeddingid(Integer.parseInt(request.getParameter("weddingId")));
		mbe.setBlessingtype(Integer.parseInt(request.getParameter("type")));
		marryBlessingService.insert(mbe);
		return R.ok().put("id", mbe.getId());
	}
	
	/**
	 * 查询所有的婚礼背景图片
	 * @param request
	 * @return
	 */
	@RequestMapping("/allweddingImg")
	public R allweddingImg(HttpServletRequest request){
		Integer page = Integer.parseInt(request.getParameter("page"));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("imgType", 1);
		map.put("offset", (page-1)*8);
		map.put("limit", page*8);
		return R.ok().put("list", marryImgService.queryList(map));
	}
	
	/**
	 * 查询所有签到记录
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/allSign")
	public R allSign(HttpServletRequest request){
		MarriedUserEntity user = (MarriedUserEntity)request.getSession().getAttribute(ControllerConstant.SESSION_MARRIED_USER_KEY);
		MarryWeddingEntity marryWedding = new MarryWeddingEntity();
		marryWedding.setUserId(user.getId());
		EntityWrapper<MarryWeddingEntity> wrapper = new EntityWrapper<MarryWeddingEntity>(marryWedding);
		marryWedding = marryWeddingService.selectOne(wrapper);
		List<MarriedUserEntity> list = new ArrayList<MarriedUserEntity>();
		if(marryWedding != null){
			Map<String , Object> map = new HashMap<String, Object>();
			map.put("weddingId", marryWedding.getId());
			list = marriedUserService.queryListtongji(map);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> m = new HashMap<String, Object>();
		map.put("weddingId", marryWedding.getId());
		map.put("states",1);
		m.put("totalPerson", marryParticipateService.queryList(map).size());//应到人数
		m.put("personwd", marryParticipateService.queryList(map).size() - list.size());//未到人数
		m.put("smtj", list);//扫码统计
		map.put("userId", user.getId());
		map.put("offset", 0);
		map.put("limit", 10);
		map.put("blessingtype", 1);
		List<MarryBlessingEntity> list1 = marryBlessingService.queryList(map);//普通祝福
		int zftotal = 0;
		int videototal = 0;
		for (Iterator iterator = list1.iterator(); iterator.hasNext();) {
			MarryBlessingEntity marryBlessingEntity = (MarryBlessingEntity) iterator.next();
			if(marryBlessingEntity.getContent() != null && !"".equals(marryBlessingEntity.getContent())){
				zftotal++;
			}
			if(marryBlessingEntity.getVideoblessing() != null && !"".equals(marryBlessingEntity.getVideoblessing())){
				videototal++;
			}
		}
		map.put("blessingtype", 2);
		List<MarryBlessingEntity> list2 = marryBlessingService.queryList(map);//红包祝福
		m.put("zftotal", zftotal);
		m.put("videototal", videototal);
		m.put("moneytotal", list2);
		return R.ok().put("list", m);
	}
	
	/**
	 * 查询自己所有的婚礼签到记录
	 * @param request
	 * @return
	 */
	@RequestMapping("/allMeSign")
	public R allMeSign(HttpServletRequest request){
		MarriedUserEntity user = (MarriedUserEntity)request.getSession().getAttribute(ControllerConstant.SESSION_MARRIED_USER_KEY);		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", user.getId());
		return R.ok().put("list", marrySignService.queryListtongji(map));
	}
	
	/**
	 * 保存签到记录
	 * @param request
	 * @return
	 */
	@RequestMapping("/saveSign")
	public R saveSign(HttpServletRequest request){
		String openId = "";
		try {
			openId = WeixinUtil.getWeixinOpenId(request.getParameter("code"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		MarriedUserEntity u = new MarriedUserEntity();
		u.setOpenid(openId);
		EntityWrapper<MarriedUserEntity> wrapper1 = new EntityWrapper<MarriedUserEntity>(u);
		u = this.marriedUserService.selectOne(wrapper1);
		MarrySignEntity marrySign = new MarrySignEntity();
		MarrySignEntity ms = new MarrySignEntity();
		if(u == null){
			JSONObject jsonObject = WeixinUtil.getUserInfo(openId);
			MarriedUserEntity user = new MarriedUserEntity();
			user.setCreatetime(new Date());
			user.setNickname(jsonObject.getString("nickname"));
			user.setPic(jsonObject.getString("headimgurl"));
			user.setOpenid(openId);
			marriedUserService.insert(user);
			marrySign.setUserid(user.getId());
			ms.setUserid(user.getId());
		}else{
			marrySign.setUserid(u.getId());
			ms.setUserid(u.getId());
		}
		marrySign.setWeddingid(Integer.parseInt(request.getParameter("id")));
		EntityWrapper<MarrySignEntity> wrapper = new EntityWrapper<MarrySignEntity>(marrySign);
		marrySign = marrySignService.selectOne(wrapper);
		if(marrySign != null){
			return R.ok("您已签到");
		}
		ms.setWeddingid(Integer.parseInt(request.getParameter("id")));
		marrySignService.save(ms);
		return R.ok("签到成功");
	}
	
	/**
	 * 查询自己所接受邀请的所有婚礼
	 * @param request
	 * @return
	 */
	@RequestMapping("/alljieshouattendawedding")
	public R alljieshouattendawedding(HttpServletRequest request){
		MarriedUserEntity user = (MarriedUserEntity)request.getSession().getAttribute(ControllerConstant.SESSION_MARRIED_USER_KEY);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", user.getOpenid());
		return R.ok().put("userlist", marryParticipateService.queryListtongji(map));
	}
	
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
		map.put("limit", 1000);
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
			MarriedUserEntity user = (MarriedUserEntity)request.getSession().getAttribute(ControllerConstant.SESSION_MARRIED_USER_KEY);
			String openId = user == null ?WeixinUtil.getWeixinOpenId(request.getParameter("code")):user.getOpenid();
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
		String url = request.getParameter("bg");
		String color = "#"+request.getParameter("color");
		Date weddingdate = new Date();
		try {
			weddingdate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(request.getParameter("weddingDate").replace("T", " "));
		} catch (Exception e) {
			e.printStackTrace();
		}
		marryWedding.setBridename(bridename);
		marryWedding.setContent(content);
		marryWedding.setGroomname(groomname);
		marryWedding.setUserId(user.getId());
		marryWedding.setWeddingaddress(weddingaddress);
		marryWedding.setWeddingdate(weddingdate);
		marryWedding.setBgcolor(color);
		marryWedding.setUrl(url);
		marryWeddingService.insert(marryWedding);
		return R.ok().put("id", marryWedding.getId());
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
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("marryWedding", marryWedding);
		map.put("user", marriedUserService.queryObject(marryWedding==null?0:marryWedding.getUserId()));
		return R.ok().put("data", map);
	}

}
