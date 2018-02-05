package io.renren.controller.married;

import io.renren.entity.married.MarriedUserEntity;
import io.renren.entity.married.MarryBlessingEntity;
import io.renren.entity.married.MarryHelpEntity;
import io.renren.entity.married.MarryMainEntity;
import io.renren.entity.married.MarryOrderMainEntity;
import io.renren.entity.married.MarryOrdersEntity;
import io.renren.entity.married.MarryParticipateEntity;
import io.renren.entity.married.MarryPhotoEntity;
import io.renren.entity.married.MarryScreenEntity;
import io.renren.entity.married.MarrySignEntity;
import io.renren.entity.married.MarryWeddingEntity;
import io.renren.service.married.MarriedUserService;
import io.renren.service.married.MarryBlessingService;
import io.renren.service.married.MarryCartService;
import io.renren.service.married.MarryHelpService;
import io.renren.service.married.MarryImgService;
import io.renren.service.married.MarryMainService;
import io.renren.service.married.MarryOrderMainService;
import io.renren.service.married.MarryOrdersService;
import io.renren.service.married.MarryParticipateService;
import io.renren.service.married.MarryPhotoService;
import io.renren.service.married.MarryScreenService;
import io.renren.service.married.MarrySignService;
import io.renren.service.married.MarryWeddingService;
import io.renren.util.OssUploadUtil;
import io.renren.util.WeixinUtil;
import io.renren.util.WeixinUtilApp;
import io.renren.utils.R;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
@RestController
@RequestMapping("married/small/weixin/me")
public class WeixinSmallMeController {

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
	@Autowired
	private MarryMainService marryMainService;
	@Autowired
	private MarryOrderMainService marryOrderMainService;
	@Autowired
	private MarryScreenService marryScreenService;

	private final static String FILEPATH = "http://static.gykjewm.com/";
	/**
	 * 屏幕管理
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping("/saveScreen")
	public R saveScreen(HttpServletRequest request){
		Integer userId = Integer.parseInt(request.getParameter("userid"));//用户id
		Integer weddingId = Integer.parseInt(request.getParameter("weddingid"));//婚礼
		Integer screenCount = Integer.parseInt(request.getParameter("screencount"));//请求参数
		MarryScreenEntity marryScreenEntity=new MarryScreenEntity();
		marryScreenEntity.setUserid(userId);
		marryScreenEntity.setWeddingid(weddingId);
		marryScreenEntity.setScreencount(screenCount);
		marryScreenEntity.setCreateTime(new Date());
		marryScreenService.save(marryScreenEntity);
		return R.ok();
	}
	/**
	 * 获取屏幕管理保存的内容  根据screenCount倒序取出 
	 * @param request
	 * @return
	 */
	@RequestMapping("/getScreen")
	public R getScreen(HttpServletRequest request){
		Integer userId=Integer.parseInt(request.getParameter("userId"));
		Integer weddingId=Integer.parseInt(request.getParameter("weddingId"));
		System.out.println("===>"+weddingId);
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("weddingId", weddingId);
		List<MarryScreenEntity> queryList1 = marryScreenService.queryList1(map);
		return R.ok().put("data", queryList1.get(queryList1.size()-1));
	}

	/**
	 * 权限管理和查看购物车数据
	 * @param request
	 * @return
	 */
	@RequestMapping("/jurisdiction")
	public R jurisdiction(HttpServletRequest request){
		Integer userId = Integer.parseInt(request.getParameter("userId"));//用户id
		MarriedUserEntity user = this.marriedUserService.queryObject(userId); 
		if(user==null){
			return R.error("用户信息数据不存在");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sidx", null);
		map.put("order", null);
		map.put("offset", 0);
		map.put("limit", 100);
		map.put("states", 1);
		map.put("userId", user.getId());
		int total = marryCartService.queryList(map).size();
		if(marriedUserService.queryObject(user.getId()).getJurisdiction()==0){
			request.getSession().setAttribute("jurisdiction", 0);//无权限就不让其显示相关模块
		}else{
			request.getSession().setAttribute("jurisdiction", 1);
		}
		MarryWeddingEntity marryWedding = new MarryWeddingEntity();
		marryWedding.setUserId(user.getId());
		EntityWrapper<MarryWeddingEntity> wrapper = new EntityWrapper<MarryWeddingEntity>(marryWedding);
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("total", total);
		if(marryWeddingService.selectOne(wrapper) == null){
			m.put("obj", null);
		}else{
			m.put("obj", marryWeddingService.selectOne(wrapper).getId());
		}
		return R.ok().put("map", m);
	}

	/**
	 * 上传单张图片
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/fileweddingphoto")
	public R weddingphoto(HttpServletRequest request, HttpServletResponse response) {
		MarryWeddingEntity marryWeddingEntities=new MarryWeddingEntity();
		try {
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
			InputStream[] is = uploadfile(multipartResolver, request);
			System.out.println(is);
			if(is != null){
				marryWeddingEntities.setPhoto(FILEPATH+"married_photo/"+OssUploadUtil.uploadObject2OSS(is[0], "married/photo/"));
			}
			marryWeddingService.insert(marryWeddingEntities);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return R.ok().put("id", marryWeddingEntities.getId());
	}

	/**
	 * 上传资料图片
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/fileupload")
	public R config(HttpServletRequest request, HttpServletResponse response) {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		MarryPhotoEntity marryPhotoEntity=new MarryPhotoEntity();
		try {
			InputStream[] is = uploadfile(multipartResolver, request);

			if(is != null){
				for (int i = 0; i < is.length; i++) {
					marryPhotoEntity.setPic(FILEPATH+"married_photo/"+OssUploadUtil.uploadObject2OSS(is[i], "married/photo/"));
					marryPhotoService.save(marryPhotoEntity);
				}
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return R.ok();
	}


	//图片上传
	public InputStream[] uploadfile(CommonsMultipartResolver multipartResolver,HttpServletRequest request) throws IllegalStateException, IOException{
		InputStream[] is = null;
		if(multipartResolver.isMultipart(request)){
			MultipartHttpServletRequest m= (MultipartHttpServletRequest)request;
			List<MultipartFile> detail=new ArrayList<MultipartFile>();
			//			if(m.getFiles("files").size() != 0){
			detail = m.getFiles("files");
			System.out.println(m.getFiles("files").size() + "-------------");
			is = new InputStream[detail.size()];
			for (int i = 0; i < detail.size(); i++) {
				if(!detail.get(i).isEmpty()){
					is[i] = detail.get(i).getInputStream();
				}
				//				}
			}
		}
		return is;
	}



	/**
	 * 保存用户帮助记录
	 * @param request
	 * @return
	 */
	@RequestMapping("/saveHelp")	
	public R saveHelp(HttpServletRequest request){
		Integer userId = Integer.parseInt(request.getParameter("userId"));//用户id
		MarriedUserEntity user = this.marriedUserService.queryObject(userId); 
		if(user==null){
			return R.error("用户信息数据不存在");
		}
		MarryHelpEntity marryHelp = new MarryHelpEntity();
		marryHelp.setUsername(user.getNickname());
		marryHelp.setPic(user.getPic());
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
	public R saveWeddingPhoto(HttpServletRequest request,HttpSession session){
		System.out.println("==="+request.getSession().getAttribute(session.getId()));
		String[] videos = request.getParameter("video").toString().split(",");
		System.out.println(videos+"---->");
		MarriedUserEntity user = marriedUserService.queryObject(Integer.parseInt(request.getParameter("userId")));
		if(user==null){
			R.error("没有查到数据");
		}
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
	@SuppressWarnings("rawtypes")
	@RequestMapping("/findAllWeddingPhoto")
	public R findAllWeddingPhoto(HttpServletRequest request){
		MarriedUserEntity user = marriedUserService.queryObject(Integer.parseInt(request.getParameter("userId")));
		if(user==null){
			R.error("没有查到数据");
		}
		MarryWeddingEntity mw = new MarryWeddingEntity();
		MarryOrdersEntity entity=new MarryOrdersEntity();

		mw.setUserId(user.getId());
		entity.setUserId(user.getId());
		EntityWrapper<MarryOrdersEntity> entityWrapper=new EntityWrapper<MarryOrdersEntity>(entity);
		EntityWrapper<MarryWeddingEntity> wrapper = new EntityWrapper<MarryWeddingEntity>(mw);
		mw = marryWeddingService.selectOne(wrapper);
		entity = marryOrdersService.selectOne(entityWrapper);
		Map<String,Object> maps=new HashMap<String, Object>();
		System.out.println("----->"+entity.getId());
		maps.put("orderId", entity.getId());
		List<MarryOrderMainEntity> mainorder=marryOrderMainService.queryList(maps);
		System.out.println("=====>"+mainorder.size());
		Map<String,Object> mainmap=null;
		for (Iterator iterator = mainorder.iterator(); iterator.hasNext();) {
			MarryOrderMainEntity entity3 = (MarryOrderMainEntity) iterator.next();
			System.out.println("====>"+marryOrderMainService.queryObject(entity3.getMainId()));
			MarryMainEntity entity4=marryMainService.queryObject(entity3.getMainId());
			System.out.println("商品 图片="+entity4);
			System.out.println("main="+marryOrderMainService.queryObject(entity3.getMainId()));
			mainmap=new HashMap<String, Object>();
			mainmap.put("mainlist", entity4);
		}

		System.out.println("wedding--->"+entity);
		Map<String, Object> map = new HashMap<String, Object>();
		if(mw != null){
			String weddingId = (request.getParameter("id")==null || "".equals(request.getParameter("id")))?mw.getId().toString():request.getParameter("id");
			map.put("weddingId", weddingId);
		}else{
			String weddingId = (request.getParameter("id")==null || "".equals(request.getParameter("id")))?"0":request.getParameter("id");
			map.put("weddingId", weddingId);
		}
		List<MarryPhotoEntity> queryList = marryPhotoService.queryList(map);
		List<MarryPhotoEntity> list1=new ArrayList<MarryPhotoEntity>();
		List<MarryPhotoEntity> list2=new ArrayList<MarryPhotoEntity>();
		for (Iterator iterator = queryList.iterator(); iterator.hasNext();) {
			MarryPhotoEntity marryPhotoEntity = (MarryPhotoEntity) iterator.next();
			if(marryPhotoEntity.getType()==1){
				list1.add(marryPhotoEntity);
			}else if(marryPhotoEntity.getType()==2){
				list2.add(marryPhotoEntity);
			}
		}
		Map<String,Object> allmap=new HashMap<String, Object>();
		allmap.put("list1", list1);
		allmap.put("list2", list2);
		allmap.put("list3",mainmap);
		return R.ok().put("list", allmap);
	}

	/**
	 * 播放图片视频
	 * 通过婚礼id查询所有的摄影展图片
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/getAllWeddingPhoto")
	public R getAllWeddingPhoto(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		String weddingId = request.getParameter("id");
		if(weddingId==null){
			R.error("婚礼id不存在");
		}
		map.put("weddingId", weddingId);
		List<MarryPhotoEntity> queryList = marryPhotoService.queryList(map);
		List<MarryPhotoEntity> list1=new ArrayList<MarryPhotoEntity>();
		List<MarryPhotoEntity> list2=new ArrayList<MarryPhotoEntity>();
		for (Iterator iterator = queryList.iterator(); iterator.hasNext();) {
			MarryPhotoEntity marryPhotoEntity = (MarryPhotoEntity) iterator.next();
			if(marryPhotoEntity.getType()==1){
				list1.add(marryPhotoEntity);
			}else if(marryPhotoEntity.getType()==2){
				list2.add(marryPhotoEntity);
			}
		}
		Map<String,Object> allmap=new HashMap<String, Object>();
		allmap.put("list1", list1);
		allmap.put("list2", list2);
		return R.ok().put("list", allmap);
	}

	/**
	 * 上传资料之前获取当前登录婚礼id
	 * 通过当前用户获取婚礼id
	 * @param request
	 * @return
	 */
	@RequestMapping("/getWeddingId")
	public R getWeddingId(HttpServletRequest request){
		//获取当前登录用户
		MarriedUserEntity user = marriedUserService.queryObject(Integer.parseInt(request.getParameter("userId")));
		if(user==null){
			R.error("没有查到数据");
		}
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
	@SuppressWarnings({ "rawtypes", "unused" })
	@RequestMapping("/blessManage")
	public R blessManage(HttpServletRequest request){
		MarriedUserEntity user = marriedUserService.queryObject(Integer.parseInt(request.getParameter("userId")));
		if(user==null){
			R.error("没有查到数据");
		}
		System.out.println("---->"+request.getParameter("userId"));
		System.out.println("----00"+request.getParameter("token"));
		//获取当前登录用户
		MarryWeddingEntity marryWedding = new MarryWeddingEntity();
		marryWedding.setUserId(user.getId());
		EntityWrapper<MarryWeddingEntity> wrapper = new EntityWrapper<MarryWeddingEntity>(marryWedding);
		marryWedding = marryWeddingService.selectOne(wrapper);
		System.out.println("=========="+marryWedding);
		if(marryWedding == null){
			return R.error("暂无婚礼记录");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", user.getId());
		map.put("offset", 0);
		map.put("limit", 10);
		map.put("weddingId", 26);
		//		map.put("weddingId", marryWedding.getId());
		map.put("blessingtype", 1);
		map.put("states", 0);
		List<MarryBlessingEntity> list1 = marryBlessingService.queryList(map);//普通祝福
		List<MarryBlessingEntity> list3 = new ArrayList<MarryBlessingEntity>();//视频祝福
		for (Iterator iterator = list1.iterator(); iterator.hasNext();) {
			MarryBlessingEntity marryBlessingEntity = (MarryBlessingEntity) iterator.next();
			if(marryBlessingEntity.getVideoblessing() != null && !"".equals(marryBlessingEntity.getVideoblessing())){
				list3.add(marryBlessingEntity);
			}
		}
		map.put("blessingtype", 2);
		map.put("states", 1);
		List<MarryBlessingEntity> list2 = marryBlessingService.queryList(map);//红包祝福
		Iterator<MarryBlessingEntity> content=list2.iterator();
		Double sum=0.0;//红包总额
		while(content.hasNext()){
			MarryBlessingEntity counthongbao=content.next();
			Double   count=Double.parseDouble(counthongbao.getContent());
			sum+=count;
		}
		System.out.println("--->"+sum);
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("list1", list1);
		m.put("list2", list2);
		m.put("list3", list3);
		m.put("sum", sum);
		//红包个数
		m.put("counts", list2.size());
		return R.ok().put("list", m);
	}

	/**
	 * 上传视频
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/filevideo")
	public R bessingVideo(HttpServletRequest request, HttpServletResponse response) {
		MarryBlessingEntity blessingEntity=new MarryBlessingEntity();
		try {
			Integer id=Integer.parseInt(request.getParameter("id"));
			MarryBlessingEntity queryObject = marryBlessingService.queryObject(id);
			if(queryObject==null){
				R.error("数据不存在");
			}
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
			InputStream[] is = uploadfile(multipartResolver, request);
			System.out.println("----->"+request.getParameter("files"));
			System.out.println(is);
			if(is != null){
				blessingEntity.setId(id);
				blessingEntity.setVideoblessing(FILEPATH+"married_video/"+OssUploadUtil.uploadObject2OSSVideo(is[0], "married/video/"));
			}
			marryBlessingService.update(blessingEntity);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return R.ok();
	}

	/**
	 * 保存祝福
	 * @param request
	 * @return
	 */
	@RequestMapping("/saveBlessing")
	public R saveBlessing(HttpServletRequest request){
		System.out.println("saveBlessing="+request.getParameter("userId"));
		Integer id=Integer.parseInt(request.getParameter("userId"));
		MarriedUserEntity	user = this.marriedUserService.queryObject(id);
		if(user==null){
			R.error("没有查询到该用户的数据");
		}
		MarryBlessingEntity mbe = new MarryBlessingEntity();
		if(user!=null){
			mbe.setNickname(user.getNickname());
			mbe.setOpenid(user.getOpenid());
			mbe.setPic(user.getPic());
		}
		mbe.setContent(request.getParameter("content"));
		mbe.setWeddingid(Integer.parseInt(request.getParameter("weddingId")));
		mbe.setBlessingtype(Integer.parseInt(request.getParameter("type")));
		mbe.setGmtModifiedtime(new Date());
		marryBlessingService.save(mbe);
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
		//		map.put("offset", (page-1)*8);
		//		map.put("limit", 2);
		map.put("offset", (page-1)*2);
		map.put("limit", page*2);
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
		Integer id=Integer.parseInt(request.getParameter("userId"));
		MarriedUserEntity user=marriedUserService.queryObject(id);
		if(user==null){
			R.error("没有查询到该用户的数据");
		}
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
		map.put("states",null);
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
		map.put("states",1);
		map.put("blessingtype", 2);
		List<MarryBlessingEntity> list2 = marryBlessingService.queryList(map);//红包祝福
		Iterator<MarryBlessingEntity> content=list2.iterator();
		Double sum=0.0;//红包总额
		while(content.hasNext()){
			MarryBlessingEntity counthongbao=content.next();
			Double   count=Double.parseDouble(counthongbao.getContent());
			sum+=count;
		}
		m.put("zftotal", zftotal);
		m.put("videototal", videototal);
		m.put("moneytotal", list2);
		m.put("redcount", list2.size());//红包个数
		m.put("sum", sum);//红包总金额

		return R.ok().put("list", m);
	}

	/**
	 * 互动列表
	 * 查询自己所有的婚礼签到记录
	 * @param request
	 * @return
	 */
	@RequestMapping("/allMeSign")
	public R allMeSign(HttpServletRequest request){
		Integer id=Integer.parseInt(request.getParameter("userId"));
		MarriedUserEntity user=marriedUserService.queryObject(id);
		if(user==null){
			R.error("没有查询到该用户的数据");
		}		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", user.getId());
		return R.ok().put("list", marrySignService.queryListtongji(map));
	}

	/**
	 * 获取用户头像
	 * @param request
	 * @return
	 */
	@RequestMapping("/getUserPhoto")
	public R getUserPhoto(HttpServletRequest request){
		Integer id=Integer.parseInt(request.getParameter("userId"));
		MarriedUserEntity user=marriedUserService.queryObject(id);
		if(user==null){
			R.error("没有查询到该用户的数据");
		}		
		return R.ok().put("list", user);
	}

	/**
	 * 保存签到记录
	 * @param request
	 * @return
	 */
	@RequestMapping("/saveSign")
	public R saveSign(HttpServletRequest request){
//		String openId = "";
//		String nickName="";
//		String avatarUrl="";
//		try {
//			openId = WeixinUtilApp.getWeixinOpenId(request.getParameter("js_code"));
//			nickName=request.getParameter("nickName");
//			avatarUrl=request.getParameter("avatarUrl");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		String userId=request.getParameter("userId");
		System.out.println("===>userId="+userId);
		MarriedUserEntity u = marriedUserService.queryObject(Integer.parseInt(userId));
		u.setOpenid(u.getOpenid());
		EntityWrapper<MarriedUserEntity> wrapper1 = new EntityWrapper<MarriedUserEntity>(u);
		u = this.marriedUserService.selectOne(wrapper1);
		MarrySignEntity marrySign = new MarrySignEntity();
		MarrySignEntity ms = new MarrySignEntity();
		if(u == null){
			MarriedUserEntity user = new MarriedUserEntity();
			user.setCreatetime(new Date());
			user.setNickname(u.getNickname());
			user.setPic(u.getPic());
			user.setOpenid(u.getOpenid());
			marriedUserService.insert(user);
			marrySign.setUserid(user.getId());
			ms.setUserid(user.getId());
		}else{
			marrySign.setUserid(u.getId());
			ms.setUserid(u.getId());
		}
		marrySign.setWeddingid(Integer.parseInt(request.getParameter("id")));
		MarryWeddingEntity entity=marryWeddingService.queryObject(Integer.parseInt(request.getParameter("id")));
		Map<String,Object> map=new HashMap<String, Object>();
		
		EntityWrapper<MarrySignEntity> wrapper = new EntityWrapper<MarrySignEntity>(marrySign);
		marrySign = marrySignService.selectOne(wrapper);
		if(marrySign != null){
			map.put("sign", "您已签到");
			map.put("list", entity);
			return R.ok().put("data", map);
		}
		ms.setWeddingid(Integer.parseInt(request.getParameter("id")));
		marrySignService.save(ms);
		map.put("sign", "签到成功");
		map.put("list", entity);
		return R.ok().put("data", map);
	}

	/**
	 * 查询自己所接受邀请的所有婚礼
	 * @param request
	 * @return
	 */
	@RequestMapping("/alljieshouattendawedding")
	public R alljieshouattendawedding(HttpServletRequest request){
		Integer id=Integer.parseInt(request.getParameter("userId"));
		MarriedUserEntity user=marriedUserService.queryObject(id);
		if(user==null){
			R.error("没有查询到该用户的数据");
		}
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
		String  id=request.getParameter("userId");
		MarriedUserEntity user=marriedUserService.queryObject(Integer.parseInt(id));
		if(user==null){
			return R.error("数据不存在");
		}
		MarryWeddingEntity marryWedding = new MarryWeddingEntity();
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
			MarriedUserEntity marrieduser = new MarriedUserEntity();
			marrieduser.setNickname(user.getNickname());
			marrieduser.setPic(user.getPic());
			marrieduser.setOpenid(marryParticipateEntity.getOpenid());
			list3.add(marrieduser); 
						if(marryParticipateEntity.getStates() == 1){
							list1.add(marrieduser);
						}else if(marryParticipateEntity.getStates() == 2){
							list2.add(marrieduser);
						}
//			if(marryParticipateEntity.getStates() == 0){
//				list0.add(marrieduser);
//			}else if(marryParticipateEntity.getStates() == 1){
//				list1.add(marrieduser);
//			}else if(marryParticipateEntity.getStates() == 2){
//				list2.add(marrieduser);
//			}
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("list1", list1);
		m.put("list2", list2);
		m.put("list3", list3);
//		m.put("list0", list0);
		return R.ok().put("data", m);
	}

	/**
	 * 保存参加婚礼
	 * @param request
	 * @return
	 */
	@RequestMapping("/attendawedding")
	public R attendawedding(HttpServletRequest request){
		Integer userid=Integer.parseInt(request.getParameter("userId"));
		MarriedUserEntity user=marriedUserService.queryObject(userid);
		try {
			if(user==null){
				R.error("没有查询到该用户的数据");
			}
			Integer states = Integer.parseInt(request.getParameter("states"));
			System.out.println("==>states="+states);
			Integer id = Integer.parseInt(request.getParameter("id"));
			MarryParticipateEntity marryParticipate = new MarryParticipateEntity();
			marryParticipate.setOpenid(user.getOpenid());
			marryParticipate.setWeddingid(id);
			EntityWrapper<MarryParticipateEntity> wrapper = new EntityWrapper<MarryParticipateEntity>(marryParticipate);
			marryParticipate = marryParticipateService.selectOne(wrapper);
			if(marryParticipate == null){
				MarryParticipateEntity mp = new MarryParticipateEntity();
				mp.setOpenid(user.getOpenid());
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
		return R.ok().put("openId", user.getOpenid());
	}


	/**
	 * 保存婚礼信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/saveWeddingInfo")
	public R saveWedding(HttpServletRequest request,HttpSession session){
		MarryWeddingEntity marryWedding = new MarryWeddingEntity();
		System.out.println("userId="+request.getParameter("userId"));
		MarriedUserEntity userEntity=marriedUserService.queryObject(Integer.parseInt(request.getParameter("userId")));
		if(userEntity==null){
			R.error("没有查询到该条数据");
		}
		String id=request.getParameter("id");
		MarryWeddingEntity queryObject = marryWeddingService.queryObject(Integer.parseInt(id));
		if(queryObject==null){
			return R.error("数据不存在");
		}
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		try{
			InputStream[] is = uploadfile(multipartResolver, request);
			System.out.println(is);
			if(is != null){
				marryWedding.setPhoto(FILEPATH+"married_photo/"+OssUploadUtil.uploadObject2OSS(is[0], "married/photo/"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		String bridename = request.getParameter("brideName");
		String content = request.getParameter("content");
		String groomname = request.getParameter("groomName");
		String weddingaddress = request.getParameter("weddingAddress");
		String url = request.getParameter("bg");
		String color = "#"+request.getParameter("color");

		Date weddingdate = new Date();
		try {
			weddingdate = new SimpleDateFormat("yyyy/MM/dd HH:mm").parse(request.getParameter("weddingDate").replace("T", " "));
		} catch (Exception e) {
			e.printStackTrace();
		}
		marryWedding.setBridename(bridename);
		marryWedding.setContent(content);
		marryWedding.setGroomname(groomname);
		marryWedding.setUserId(userEntity.getId());
		marryWedding.setWeddingaddress(weddingaddress);
		marryWedding.setWeddingdate(weddingdate);
		marryWedding.setBgcolor(color);
		marryWedding.setUrl(url);
		marryWedding.setId(Integer.parseInt(id));
		marryWeddingService.update(marryWedding);
		System.out.println("wedding"+marryWedding.getId());
		return R.ok().put("id", marryWedding.getId());
	}

	/**
	 * 查询婚礼记录
	 * @param request
	 * @return
	 */


	@RequestMapping("/findWedding")
	public R findWedding(HttpServletRequest request){
		Integer type = 0;
		String openId = "";
		MarryWeddingEntity marryWedding = new MarryWeddingEntity();
		MarriedUserEntity user = marriedUserService.queryObject(Integer.parseInt(request.getParameter("userId")));
		if(request.getParameter("id") == null || "".equals(request.getParameter("id")) || "null".equals(request.getParameter("id"))){
			System.out.println("----"+request.getParameter("id"));
			marryWedding.setUserId(user.getId());//邀请好友时通过当前用户id查询婚礼记录
			//				marryWedding.setUserId(user.getId());//邀请好友时通过当前用户id查询婚礼记录
		}else{
			marryWedding.setId(Integer.parseInt(request.getParameter("id")));//参加婚礼模块中通过婚礼id查询婚礼记录
		}
		if(request.getParameter("js_code")!=null&& !"".equals(request.getParameter("js_code")) && !"null".equals(request.getParameter("js_code"))){
			try {//好友通过邀请函中带的婚礼id查询婚礼记录
				MarryParticipateEntity marryParticipate = new MarryParticipateEntity();
				marryParticipate.setOpenid(user.getOpenid());
				marryParticipate.setWeddingid(Integer.parseInt(request.getParameter("id")));
				EntityWrapper<MarryParticipateEntity> wrapper = new EntityWrapper<MarryParticipateEntity>(marryParticipate);
				if(marryParticipateService.selectOne(wrapper) != null){
					type = 1;
				}
				marryWedding.setId(Integer.parseInt(request.getParameter("id")));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		EntityWrapper<MarryWeddingEntity> wrapper = new EntityWrapper<MarryWeddingEntity>(marryWedding);
		marryWedding = this.marryWeddingService.selectOne(wrapper);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("marryWedding", marryWedding);
		map.put("user", marriedUserService.queryObject(marryWedding==null?0:marryWedding.getUserId()));
		map.put("type", type);
		map.put("openId", openId);
		return R.ok().put("data", map);
	}


	//	@RequestMapping("/findWedding")
	//	public R findWedding(HttpServletRequest request){
	//		Integer type = 0;
	//		String openId = "";
	//		MarryWeddingEntity marryWedding = new MarryWeddingEntity();
	//		MarriedUserEntity user = (MarriedUserEntity)request.getSession().getAttribute(ControllerConstant.SESSION_MARRIED_USER_KEY);
	//		if(user != null){
	//			if(request.getParameter("id") == null || "".equals(request.getParameter("id")) || "null".equals(request.getParameter("id"))){
	//				marryWedding.setUserId(3);//邀请好友时通过当前用户id查询婚礼记录
	////				marryWedding.setUserId(user.getId());//邀请好友时通过当前用户id查询婚礼记录
	//			}else{
	//				marryWedding.setId(Integer.parseInt(request.getParameter("id")));//参加婚礼模块中通过婚礼id查询婚礼记录
	//			}
	//		}else{
	//			if(request.getParameter("code")!=null&& !"".equals(request.getParameter("code")) && !"null".equals(request.getParameter("code"))){
	//				try {//好友通过邀请函中带的婚礼id查询婚礼记录
	//					openId = WeixinUtil.getWeixinOpenId(request.getParameter("code"));
	//					JSONObject jsonObject = WeixinUtil.getUserInfo(openId);
	//					if(jsonObject.getInt("subscribe")==0){
	//						return R.error("请先关注微信公众号：贵州冠宇科技");
	//					}
	//					MarryParticipateEntity marryParticipate = new MarryParticipateEntity();
	//					marryParticipate.setOpenid(openId);
	//					marryParticipate.setWeddingid(Integer.parseInt(request.getParameter("id")));
	//					EntityWrapper<MarryParticipateEntity> wrapper = new EntityWrapper<MarryParticipateEntity>(marryParticipate);
	//					if(marryParticipateService.selectOne(wrapper) != null){
	//						type = 1;
	//					}
	//					marryWedding.setId(Integer.parseInt(request.getParameter("id")));
	//				} catch (Exception e) {
	//					e.printStackTrace();
	//				}
	//			}else{
	//				return R.error("您未授权获取微信信息");
	//			}
	//		}
	//		EntityWrapper<MarryWeddingEntity> wrapper = new EntityWrapper<MarryWeddingEntity>(marryWedding);
	//		marryWedding = this.marryWeddingService.selectOne(wrapper);
	//		
	//		Map<String, Object> map = new HashMap<String, Object>();
	//		map.put("marryWedding", marryWedding);
	//		map.put("user", marriedUserService.queryObject(marryWedding==null?0:marryWedding.getUserId()));
	//		map.put("type", type);
	//		map.put("openId", openId);
	//		return R.ok().put("data", map);
	//	}





	//	 private static final Logger logger = LoggerFactory.getLogger(WeixinMeController.class);
	//	  @RequestMapping("/picture")
	//	    public void uploadPicture(HttpServletRequest request, HttpServletResponse response) throws Exception {
	//		  System.out.println("----==="+request.getParameter("filename"));
	//	        //获取文件需要上传到的路径
	//	        String path = request.getRealPath("/upload") + "/";
	//	        File dir = new File(path);
	//	        if (!dir.exists()) {
	//	            dir.mkdir();
	//	        }
	//	        logger.debug("path=" + path);
	//
	//	        request.setCharacterEncoding("utf-8");  //设置编码
	//	        //获得磁盘文件条目工厂
	//	        DiskFileItemFactory factory = new DiskFileItemFactory();
	//
	//	        //如果没以下两行设置的话,上传大的文件会占用很多内存，
	//	        //设置暂时存放的存储室,这个存储室可以和最终存储文件的目录不同
	//	        /**
	//	         * 原理: 它是先存到暂时存储室，然后再真正写到对应目录的硬盘上，
	//	         * 按理来说当上传一个文件时，其实是上传了两份，第一个是以 .tem 格式的
	//	         * 然后再将其真正写到对应目录的硬盘上
	//	         */
	//	        factory.setRepository(dir);
	//	        //设置缓存的大小，当上传文件的容量超过该缓存时，直接放到暂时存储室
	//	        factory.setSizeThreshold(1024 * 1024);
	//	        //高水平的API文件上传处理
	//	        ServletFileUpload upload = new ServletFileUpload(factory);
	//	        try {
	//	            List<FileItem> list = upload.parseRequest(request);
	//	            FileItem picture = null;
	//	            for (FileItem item : list) {
	//	                //获取表单的属性名字
	//	                String name = item.getFieldName();
	//	                //如果获取的表单信息是普通的 文本 信息
	//	                if (item.isFormField()) {
	//	                    //获取用户具体输入的字符串
	//	                    String value = item.getString();
	//	                    request.setAttribute(name, value);
	//	                    logger.debug("name=" + name + ",value=" + value);
	//	                } else {
	//	                    picture = item;
	//	                }
	//	            }
	//
	//	            //自定义上传图片的名字为userId.jpg
	//	            String fileName = request.getAttribute("filename") + ".jpg";
	//	            String destPath = path + fileName;
	//	            logger.debug("destPath=" + destPath);
	//
	//	            //真正写到磁盘上
	//	            File file = new File(destPath);
	//	            OutputStream out = new FileOutputStream(file);
	//	            InputStream in = picture.getInputStream();
	//	            int length = 0;
	//	            byte[] buf = new byte[1024];
	//	            // in.read(buf) 每次读到的数据存放在buf 数组中
	//	            while ((length = in.read(buf)) != -1) {
	//	                //在buf数组中取出数据写到（输出流）磁盘上
	//	                out.write(buf, 0, length);
	//	            }
	//	            in.close();
	//	            out.close();
	//	        } catch (FileUploadException e1) {
	//	            logger.error("", e1);
	//	        } catch (Exception e) {
	//	            logger.error("", e);
	//	        }
	//
	//
	//	        PrintWriter printWriter = response.getWriter();
	//	        response.setContentType("application/json");
	//	        response.setCharacterEncoding("utf-8");
	//	        HashMap<String, Object> res = new HashMap<String, Object>();
	//	        res.put("success", true);
	//	        printWriter.write(JSON.toJSONString(res));
	//	        printWriter.flush();
	//	    }


}
