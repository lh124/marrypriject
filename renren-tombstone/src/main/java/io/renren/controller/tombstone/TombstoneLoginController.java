package io.renren.controller.tombstone;

import io.renren.annotation.IgnoreAuth;
import io.renren.constant.ControllerConstant;
import io.renren.entity.tombstone.BusinessCardEntity;
import io.renren.entity.tombstone.LeavingMessageEntity;
import io.renren.entity.tombstone.Obj;
import io.renren.entity.tombstone.PicEntity;
import io.renren.entity.tombstone.TombstoneDeadEntity;
import io.renren.entity.tombstone.TombstoneUserEntity;
import io.renren.model.json.ResponseDTJson;
import io.renren.service.tombstone.BombstonePicService;
import io.renren.service.tombstone.BusinessCardService;
import io.renren.service.tombstone.LeavingMessageService;
import io.renren.service.tombstone.PicService;
import io.renren.service.tombstone.TombstoneDeadService;
import io.renren.service.tombstone.TombstoneRelationshipService;
import io.renren.service.tombstone.TombstoneUserService;
import io.renren.utils.R;
import io.renren.utils.dataSource.DBTypeEnum;
import io.renren.utils.dataSource.DbContextHolder;
import io.renren.validator.Assert;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.code.kaptcha.Producer;

/**
 * API登录授权
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-23 15:31
 */
@RestController("LoginControllerTombstone")
@RequestMapping("/tombstone")
@SuppressWarnings("rawtypes")
public class TombstoneLoginController{
	
	public static String GET_TOCKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";
	public static String WECHAT_APPID = "wxb9072ff1ebcf745c";
	public static String WECHAT_SECRTE = "b298e38e02eb3d45ca5cc22c68e9bae5";
    
	
	@Autowired
	private Producer producer;
	
	@Autowired
	private TombstoneUserService tombstoneUserService;
	@Autowired
	private BombstonePicService bombstonePicService;
	@Autowired
	private TombstoneDeadService tombstoneDeadService;
	@Autowired
	private TombstoneRelationshipService tombstoneRelationshipService;
	@Autowired
	private BusinessCardService businessCardService;
	@Autowired
	private LeavingMessageService leavingMessageService;
	@Autowired
	private PicService picService;
	
	/**
	 * 获取所有名片
	 */
	@RequestMapping("/getallbusinessCard")
	public R getallbusinessCard(HttpServletRequest request){
		List<PicEntity> piclist = picService.queryList(null);
		for (Iterator iterator = piclist.iterator(); iterator.hasNext();) {
			PicEntity picEntity = (PicEntity) iterator.next();
			TombstoneUserEntity user = new TombstoneUserEntity();
			user.setId(picEntity.getId());
//			user.setShipin("http://guanyukeji-static.oss-cn-hangzhou.aliyuncs.com/tombstoneewm/"+picEntity.getPic());
			user.setBgmusic("http://guanyukeji-static.oss-cn-hangzhou.aliyuncs.com/tombstoneewm/"+picEntity.getPic());
			tombstoneUserService.update(user);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		List<BusinessCardEntity> list = businessCardService.queryList(map);
		return R.ok().put("list", list);
	}
	
	/**
	 * 获取个人名片的相关信息
	 */
	@RequestMapping("/getbusinessCard")
	public R getbusinessCard(HttpServletRequest request){
		BusinessCardEntity businessCard = businessCardService.queryObject(Integer.parseInt(request.getParameter("id")));
		return R.ok().put("businessCard", businessCard);
	}
	
	/**
	 * 微信端删除个人图片
	 */
	@RequestMapping("/deleteimage")
	public R deleteimage(HttpServletRequest request){
		bombstonePicService.delete(Integer.parseInt(request.getParameter("id")));
		return R.ok();
	}
	
	/**
	 * 微信端保存扫墓留言
	 */
	@RequestMapping("/savemessage")
	public R savemessage(HttpServletRequest request){
		LeavingMessageEntity message = new LeavingMessageEntity();
		message.setUserid(Integer.parseInt(request.getParameter("id")));
		message.setContent(request.getParameter("content"));
		message.setCreatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		leavingMessageService.save(message);
		return R.ok();
	}
	
	/**
	 * 微信端通过二维码扫描获取的数据
	 */
	@RequestMapping("/geterweima")
	public R geterweima(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userid", request.getParameter("id"));
		map.put("offset", 0);
		map.put("order", "desc");
		map.put("sidx", null);
		map.put("limit", 100);
		Map<String, Object> maplist = new HashMap<String, Object>();
		maplist.put("messagelist", leavingMessageService.queryList(map));
		maplist.put("img", bombstonePicService.queryList(map));
		maplist.put("user", tombstoneUserService.queryObject(Integer.parseInt(request.getParameter("id"))));
		map.put("userid", "userid="+request.getParameter("id"));
		List<TombstoneDeadEntity> list = tombstoneDeadService.queryList(map);
		if(list.size() > 0){
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				TombstoneDeadEntity tombstoneDeadEntity = (TombstoneDeadEntity) iterator.next();
				maplist.put("deaduser", tombstoneDeadEntity);
			}
		}else{
			maplist.put("deaduser", null);
		}
		map.put("userid", "userid="+request.getParameter("id") + " and parentid = 0");
		TombstoneDeadEntity tb = getfirst(map);//获取第一代
		map.put("userid", "parentid="+tb.getId() + " and relationtype = '0'");
		List<Obj> lists = getsecond(map);//获取下一代
		tb.setChildren(lists);
		maplist.put("dead", tb);
		return R.ok().put("list", maplist);
	}
	
	/**
	 * 微信端保存族谱数据
	 */
	@RequestMapping("/saveDeadEntity")
	public R saveDeadEntity(HttpServletRequest request){
		TombstoneDeadEntity tombstoneDead = new TombstoneDeadEntity();
		tombstoneDead.setParentid(Integer.parseInt(request.getParameter("parentid")));
		tombstoneDead.setUsertype(request.getParameter("usertype"));
		tombstoneDead.setName(request.getParameter("name"));
		tombstoneDead.setRelation(request.getParameter("relation"));
		tombstoneDead.setCreatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		if(tombstoneDead.getUsertype() == null || tombstoneDead.getUsertype().equals("")){
			tombstoneDead.setUsertype("-1");
			tombstoneDead.setRelationtype("0");
		}else{
			if(tombstoneDead.getUsertype().equals("-1")){
				tombstoneDead.setRelationtype("0");
			}else{
				tombstoneDead.setRelationtype("1");
			}
		}
		TombstoneUserEntity tbue = (TombstoneUserEntity)request.getSession().getAttribute(ControllerConstant.SESSION_TOMBSTONE_USER_KEY);
		tombstoneDead.setUserid(tbue.getId());
		tombstoneDeadService.insert(tombstoneDead);
		if(!tombstoneDead.getUsertype().equals("-1")){
			TombstoneDeadEntity tb = tombstoneDeadService.queryObject(Integer.parseInt(tombstoneDead.getUsertype()));
			tb.setUsertype(tombstoneDead.getId()+"");
			tombstoneDeadService.update(tb);
		}
		return R.ok().put("id", tombstoneDead.getId());
	}
	
	/**
	 * 获取族谱关系数据
	 */
	@RequestMapping("/getdeaddata")
	public R getdeaddata(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("offset", 0);
		map.put("order", "desc");
		map.put("sidx", null);
		map.put("limit", 100);
		TombstoneUserEntity tbue = (TombstoneUserEntity)request.getSession().getAttribute(ControllerConstant.SESSION_TOMBSTONE_USER_KEY);
		if(request.getParameter("id") != null && !request.getParameter("id").equals("")){
			if(request.getParameter("type") != null && !request.getParameter("type").equals("")){
				map.put("userid", "userid="+request.getParameter("id") + " and parentid = 0");
			}else{
				map.put("userid", "id="+request.getParameter("id"));
			}
		}else{
			map.put("userid", "userid="+tbue.getId() + " and parentid = 0");//当前登录用户id
		}
		
		TombstoneDeadEntity tb = getfirst(map);//获取第一代
		map.put("userid", "parentid="+tb.getId() + " and relationtype = '0'");
		List<Obj> list = getsecond(map);//获取下一代
		tb.setChildren(list);
		return R.ok().put("list", tb);
	}
	
	public TombstoneDeadEntity getfirst(Map<String, Object> map){//获取第一代
		TombstoneDeadEntity tbsd = new TombstoneDeadEntity();
		List<TombstoneDeadEntity> list1 = tombstoneDeadService.queryList(map);
		for (Iterator iterator = list1.iterator(); iterator.hasNext();) {
			tbsd = (TombstoneDeadEntity) iterator.next();
		}
		return tbsd;
	}
	
	public List<Obj> getsecond(Map<String, Object> map){//获取第二代
		List<Obj> list = new ArrayList<Obj>();
		List<TombstoneDeadEntity> list1 = tombstoneDeadService.queryList(map);
		for (Iterator iterator = list1.iterator(); iterator.hasNext();) {
			Obj obj = new Obj();
			TombstoneDeadEntity tombstoneDeadEntity = (TombstoneDeadEntity) iterator.next();
			map.put("userid", "usertype="+tombstoneDeadEntity.getId() + " and relationtype = '1'");
			List<TombstoneDeadEntity> list2 = tombstoneDeadService.queryList(map);
			if(list2.size() > 0){
				for (Iterator iterator2 = list2.iterator(); iterator2.hasNext();) {
					TombstoneDeadEntity tb = (TombstoneDeadEntity) iterator2.next();
					obj.setZn(getthird(tombstoneDeadEntity.getId(),tb.getId()));
					map.put("userid", "parentid="+tombstoneDeadEntity.getId());
					if(tombstoneDeadService.queryList(map) != null && !tombstoneDeadService.queryList(map).equals("")){
						obj.setId(tombstoneDeadEntity.getId());
						obj.setParentid(tombstoneDeadEntity.getParentid());
					}else{
						obj.setId(tb.getId());
						obj.setParentid(tb.getParentid());
					}
				}
			}else{
				obj.setId(tombstoneDeadEntity.getId());
				obj.setParentid(tombstoneDeadEntity.getParentid());
				obj.setZn(getthird(tombstoneDeadEntity.getId(),-1));
			}
			list2.add(tombstoneDeadEntity);
			obj.setFm(list2);
			list.add(obj);
		}
		return list;
	}
	
	public List<Obj> getthird(Integer parentid1,Integer parentid2){//获取第三代
		List<Obj> list = new ArrayList<Obj>();
		Integer id = 0;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("offset", 0);
		map.put("order", "desc");
		map.put("sidx", null);
		map.put("limit", 100);
		map.put("userid", "parentid="+parentid1 + " and relationtype = '0'");
		List<TombstoneDeadEntity> list1 = tombstoneDeadService.queryList(map);
		for (Iterator iterator = list1.iterator(); iterator.hasNext();) {
			TombstoneDeadEntity tombstoneDeadEntity = (TombstoneDeadEntity) iterator.next();
			map.put("userid", "usertype="+tombstoneDeadEntity.getId() + " and relationtype = '1'");
			Obj obj = new Obj();
			List<TombstoneDeadEntity> list2 = tombstoneDeadService.queryList(map);
			if(list2 != null && !list.equals("")){
				for (Iterator iterator2 = list2.iterator(); iterator2.hasNext();) {
					TombstoneDeadEntity tombstoneDeadEntity2 = (TombstoneDeadEntity) iterator2.next();
					map.put("userid", "parentid="+tombstoneDeadEntity2.getId());
					if(tombstoneDeadService.queryList(map) != null && !tombstoneDeadService.queryList(map).equals("")){
						obj.setId(tombstoneDeadEntity2.getId());
						obj.setParentid(tombstoneDeadEntity2.getParentid());
						id = tombstoneDeadEntity2.getId();
					}else{
						obj.setId(tombstoneDeadEntity.getId());
						obj.setParentid(tombstoneDeadEntity.getParentid());
						id = tombstoneDeadEntity.getId();
					}
				}
			}else{
				obj.setId(tombstoneDeadEntity.getId());
				obj.setParentid(tombstoneDeadEntity.getParentid());
				id = tombstoneDeadEntity.getId();
			}
			list2.add(tombstoneDeadEntity);
			obj.setFm(list2);
			list.add(obj);
		}
		map.put("userid", "parentid="+parentid2 + " and relationtype = '0'");
		List<TombstoneDeadEntity> list2 = tombstoneDeadService.queryList(map);
		for (Iterator iterator = list2.iterator(); iterator.hasNext();) {
			TombstoneDeadEntity tombstoneDeadEntity = (TombstoneDeadEntity) iterator.next();
			map.put("userid", "usertype="+tombstoneDeadEntity.getId() + " and relationtype = '1'");
			Obj obj = new Obj();
			List<TombstoneDeadEntity> list3 = tombstoneDeadService.queryList(map);
			if(list3 != null && !list3.equals("")){
				for (Iterator iterator2 = list2.iterator(); iterator2.hasNext();) {
					TombstoneDeadEntity tombstoneDeadEntity2 = (TombstoneDeadEntity) iterator2.next();
					map.put("userid", "parentid="+tombstoneDeadEntity2.getId());
					if(tombstoneDeadService.queryList(map) != null && !tombstoneDeadService.queryList(map).equals("")){
						obj.setId(tombstoneDeadEntity2.getId());
						obj.setParentid(tombstoneDeadEntity2.getParentid());
						id = tombstoneDeadEntity2.getId();
					}else{
						obj.setId(tombstoneDeadEntity.getId());
						obj.setParentid(tombstoneDeadEntity.getParentid());
						id = tombstoneDeadEntity.getId();
					}
				}
			}else{
				obj.setId(tombstoneDeadEntity.getId());
				obj.setParentid(tombstoneDeadEntity.getParentid());
				id = tombstoneDeadEntity.getId();
				
			}
			list3.add(tombstoneDeadEntity);
			obj.setFm(list3);
			list.add(obj);
		}
		List<TombstoneDeadEntity> l = new ArrayList<TombstoneDeadEntity>();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Obj obj = (Obj) iterator.next();
			List<TombstoneDeadEntity> list3 = obj.getFm();
			for (Iterator iterator2 = list3.iterator(); iterator2.hasNext();) {
				TombstoneDeadEntity tombstoneDeadEntity = (TombstoneDeadEntity) iterator2.next();
				l.add(tombstoneDeadEntity);
			}
		}
		Obj obj = new Obj();
		obj.setFm(l);
		obj.setParentid(parentid1);
		obj.setId(id);
		List<Obj> ll = new ArrayList<Obj>();
		ll.add(obj);
		return ll;
	}
	
	/**
	 * 获取关系表数据
	 */
	@RequestMapping("/getrelationship")
	public R getrelationship(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("offset", 0);
		map.put("order", "desc");
		map.put("sidx", null);
		map.put("limit", 100);
		return R.ok().put("list", tombstoneRelationshipService.queryList(map));
	}
	
	/**
	 * 获取一个用户下的所有数据
	 */
	@RequestMapping("/getalluser")
	public R getalluser(HttpServletRequest request){
		TombstoneUserEntity tbue = (TombstoneUserEntity)request.getSession().getAttribute(ControllerConstant.SESSION_TOMBSTONE_USER_KEY);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("offset", 0);
		map.put("order", "desc");
		map.put("sidx", null);
		map.put("limit", 100);
		map.put("userid", "userid="+tbue.getId());//当前登录用户id
		List<TombstoneDeadEntity> list = tombstoneDeadService.queryList(map);
		return R.ok().put("list", list);
	}
	
	
	/**
	 * 获取死者个人的所有照片
	 */
	@RequestMapping("/getallimg")
	public R getallimg(HttpServletRequest request){
		TombstoneUserEntity tbue = (TombstoneUserEntity)request.getSession().getAttribute(ControllerConstant.SESSION_TOMBSTONE_USER_KEY);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userid", tbue.getId());
		map.put("offset", 0);
		map.put("order", "desc");
		map.put("sidx", null);
		map.put("limit", 100);
		Map<String, Object> maplist = new HashMap<String, Object>();
		maplist.put("messagelist", leavingMessageService.queryList(map));
		maplist.put("img", bombstonePicService.queryList(map));
		maplist.put("user", tombstoneUserService.queryObject(tbue.getId()));
		map.put("userid", "userid="+tbue.getId());
		List<TombstoneDeadEntity> list = tombstoneDeadService.queryList(map);
		if(list.size() > 0){
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				TombstoneDeadEntity tombstoneDeadEntity = (TombstoneDeadEntity) iterator.next();
				maplist.put("deaduser", tombstoneDeadEntity);
			}
		}else{
			maplist.put("deaduser", null);
		}
		return R.ok().put("list", maplist);
	}
	
	
	/**
	 * 修改生平经历
	 */
	@RequestMapping("/updataexperience")
	public R updataexperience(HttpServletRequest request,String experience){
		TombstoneUserEntity tbue = (TombstoneUserEntity)request.getSession().getAttribute(ControllerConstant.SESSION_TOMBSTONE_USER_KEY);
		TombstoneUserEntity t = tombstoneUserService.queryObject(tbue.getId());
		t.setExperience(experience);
		tombstoneUserService.update(t);
		return R.ok();
	}
	
	/**
	 * 修改基本信息
	 */
	@RequestMapping("/updatacontent")
	public R updatacontent(HttpServletRequest request,String content){
		TombstoneUserEntity tbue = (TombstoneUserEntity)request.getSession().getAttribute(ControllerConstant.SESSION_TOMBSTONE_USER_KEY);
		TombstoneUserEntity t = tombstoneUserService.queryObject(tbue.getId());
		t.setContent(content);
		tombstoneUserService.update(t);
		return R.ok();
	}
	
	/**
	 * 获取登录用户
	 */
	@RequestMapping("/getloginuser")
	public R getloginuser(HttpServletRequest request){
		TombstoneUserEntity tbue = (TombstoneUserEntity)request.getSession().getAttribute(ControllerConstant.SESSION_TOMBSTONE_USER_KEY);
		return R.ok().put("user", tombstoneUserService.queryObject(tbue.getId()));
	}
	
	/**
	 * 修改密码
	 */
	@RequestMapping("/updatepassword")
	public R updatepassword(HttpServletRequest request,String oripassword,String newpassword){
		TombstoneUserEntity tbue = (TombstoneUserEntity)request.getSession().getAttribute(ControllerConstant.SESSION_TOMBSTONE_USER_KEY);
		if(!tbue.getPassword().equals(new Sha256Hash(oripassword).toHex())){
			return R.error("原密码错误");
		}else{
			tbue.setPassword(new Sha256Hash(newpassword).toHex());
			tombstoneUserService.update(tbue);
		}
		return R.ok();
	}
	
    /**
     * 登录
     */
    @IgnoreAuth
    @RequestMapping("/userLogin")
    public R login(HttpServletRequest request,String mobile, String password, String state){
        Assert.isBlank(mobile, "账号不能为空");
        Assert.isBlank(password, "密码不能为空");
        
        // 验证验证码
        Map<String, Object> map = new HashMap<String, Object>();
        
      //进行用户验证
     // 墓碑二维码
        TombstoneUserEntity user = new TombstoneUserEntity();
		user.setName(mobile);
		EntityWrapper<TombstoneUserEntity> wrapper = new EntityWrapper<TombstoneUserEntity>(user);
		user = this.tombstoneUserService.selectOne(wrapper);
		
		if ( user == null){
        	return R.error("用户不存在").put("status", ResponseDTJson.FAIL);
        }
        
        if (user.getPassword() != null && user.getPassword().equals(new Sha256Hash(password).toHex())) {
        	map.put("status", ResponseDTJson.SUCCESS);
        	map.put("msg","登录成功");
        	request.getSession().setAttribute(ControllerConstant.SESSION_TOMBSTONE_USER_KEY, user);
        } else {
        	return R.error("密码错误").put("status", ResponseDTJson.FAIL);
        }
        
        return R.ok(map);
    }
    
    /**
     * 微信绑定
     * @throws Exception 
     */
    @RequestMapping("/user/weChatBinding")
    public R weChatBinding(HttpServletRequest request) throws Exception{
        String openid = request.getSession().getAttribute("fromUserName").toString();
        if (openid != null) {
        	TombstoneUserEntity user = (TombstoneUserEntity) request.getSession().getAttribute(ControllerConstant.SESSION_TOMBSTONE_USER_KEY);
        	
        	TombstoneUserEntity userNew = new TombstoneUserEntity();
        	userNew.setId(user.getId());
        	userNew.setOpenid(openid);
        	DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
        	this.tombstoneUserService.update(userNew);
        	DbContextHolder.setDbType(DBTypeEnum.MYSQL);
        	
        	return R.ok("绑定成功");
        } else {
        	return R.error().put("status", "oppenid无法获取");
        }
        
        
    }
//    
//    /**
//     * 微信绑定
//     * @throws Exception 
//     */
//    @RequestMapping("/user/cancelWeChatBinding")
//    public R cancelWeChatBinding(HttpServletRequest request, String code) throws Exception{
//    	StudentEntity user = (StudentEntity) request.getSession().getAttribute(ControllerConstant.SESSION_SMART_USER_KEY);
//    	
//    	DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
//    	this.studentService.updateOpenId(user.getId().longValue());
//    	DbContextHolder.setDbType(DBTypeEnum.MYSQL);
//    	
//    	return R.ok("取消绑定绑定成功");
//    }
//    
//    /**
//     * 微信登录
//     * @throws Exception 
//     */
//    @RequestMapping("/user/weChatuserLogin")
//    @IgnoreAuth
//    public R weChatuserLogin(HttpServletRequest request,String code) throws Exception{
//    	Assert.isBlank(code, "code不能为空");
//        
//        StringBuffer str = new StringBuffer(GET_TOCKEN_URL);
//        str.append("?");
//        str.append("appid=" + WECHAT_APPID);
//        str.append("&secret=" + WECHAT_SECRTE);
//        str.append("&code=" + code);
//        str.append("&grant_type=authorization_code");
//        
//        String back = HttpsClient.httpsGet(str.toString());
//        System.out.println(back);
//        JSONObject json = JSONObject.fromObject(back);
//        String openid = json.containsKey("openid") ? json.getString("openid") : null;
//        
//        if (openid != null) {
//        	
//        	StudentEntity userNew = new StudentEntity();
//        	userNew.setOpenId(openid);
//        	EntityWrapper<StudentEntity> wrapper = new EntityWrapper<StudentEntity>(userNew);
//        	
//        	DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
//        	userNew = this.studentService.selectOne(wrapper);
//        	DbContextHolder.setDbType(DBTypeEnum.MYSQL);
//        	
//        	if ( userNew != null && userNew.getStudentNo() != null ) {
//        		
//        		request.getSession().setAttribute(ControllerConstant.SESSION_SMART_USER_KEY, userNew);
//        		
//        		return R.ok("登录成功");
//        	} else {
//        		return R.error("未绑定微信").put("status", ResponseDTJson.FAIL);
//        	}
//        } else {
//        	return R.error().put("status", "oppenid无法获取");
//        }
//        
//        
//    }
//    
//    
//    @RequestMapping("/user/setUserPosition")
//    public R setUserPosition(HttpServletRequest request, String latitude, String longitude) throws Exception{
//    	
//    	Assert.isBlank(latitude, "纬度不能为空");
//    	Assert.isBlank(longitude, "经度不能为空");
//  		
//    	logger.error(latitude + " " + longitude);
//    	
//    	StudentEntity user = (StudentEntity) request.getSession().getAttribute(ControllerConstant.SESSION_SMART_USER_KEY);
//    	
//    	StudentEntity userNew = new StudentEntity();
//    	userNew.setId(user.getId());
//    	userNew.setLatitude(latitude);
//    	userNew.setLongitude(longitude);
//    	
//    	DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
//    	this.studentService.update(userNew);
//    	DbContextHolder.setDbType(DBTypeEnum.MYSQL);
//  		return R.ok().put("status", ResponseDTJson.SUCCESS);
//    }
//    
//    @RequestMapping("/user/userInfo")
//    public R userInfo(HttpSession session){
//    	
//    	DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
//    	StudentEntity student = (StudentEntity) session.getAttribute(ControllerConstant.SESSION_SMART_USER_KEY);
//    	student = this.studentService.selectById(student.getId());
//    	DbContextHolder.setDbType(DBTypeEnum.MYSQL);
//    	student.setPasswordd("");
//    	
//    	return R.ok("查询成功").put("info", student);
//    }
//    
//    @RequestMapping("/user/loginOut")
//	public R loginOut(HttpServletRequest request){
//		HttpSession session = request.getSession();
//		StudentEntity user = (StudentEntity) session.getAttribute(ControllerConstant.SESSION_SMART_USER_KEY);
//		
//		if (user != null) {
//			session.removeAttribute(ControllerConstant.SESSION_SMART_USER_KEY);
//			return R.ok("注销成功");
//		} else {
//			return R.error("未登录");
//		}
//		
//	}
    
    
}
