package io.renren.controller.smart;

import io.renren.constant.ControllerConstant;
import io.renren.entity.smart.ClassEntity;
import io.renren.entity.smart.ClassInfoEntity;
import io.renren.entity.smart.ClassNoticeEntity;
import io.renren.entity.smart.FreshmanGuideEntity;
import io.renren.entity.smart.PsychologicalCounselingEntity;
import io.renren.entity.smart.SchoolEntity;
import io.renren.entity.smart.SchoolNoticeEntity;
import io.renren.entity.smart.SmartActivitiesEntity;
import io.renren.entity.smart.SmartCoursewareEntity;
import io.renren.entity.smart.SmartWorkEntity;
import io.renren.entity.smart.StudentEntity;
import io.renren.service.smart.ClassInfoService;
import io.renren.service.smart.ClassNoticeService;
import io.renren.service.smart.ClassService;
import io.renren.service.smart.CoreService;
import io.renren.service.smart.FreshmanGuideService;
import io.renren.service.smart.PhotoClassWorkMsgService;
import io.renren.service.smart.PsychologicalCounselingService;
import io.renren.service.smart.SchoolNoticeService;
import io.renren.service.smart.SchoolService;
import io.renren.service.smart.SmartActivitiesService;
import io.renren.service.smart.SmartCoursewareService;
import io.renren.service.smart.SmartWorkService;
import io.renren.service.smart.StudentService;
import io.renren.service.smart.SysWeixinMsgService;
import io.renren.utils.PageUtils;
import io.renren.utils.Query;
import io.renren.utils.R;
import io.renren.utils.dataSource.DBTypeEnum;
import io.renren.utils.dataSource.DbContextHolder;
import io.renren.validator.ValidatorUtils;
import io.renren.validator.group.AddGroup;
import io.renren.weixin.main.SignEntity;
import io.renren.weixin.util.AdvancedUtil;
import io.renren.weixin.util.CommonUtil;
import io.renren.weixin.util.Sign;
import io.renren.weixin.util.SignUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * 
 * 
 * @author yuanbichang
 * @email 673290684@qq.com
 * @date 2017-08-28 12:02:26
 */
@RestController("shouyeControllerSmart")
@RequestMapping("/smart/shouye")
public class ShouyeController {
	@Autowired
	private ClassService classService;
	@Autowired
	private SchoolService schoolService;
	@Autowired
	private SchoolNoticeService schoolNoticeService;
	@Autowired
	private ClassNoticeService classNoticeService;
	@Autowired
	private FreshmanGuideService freshmanGuideService;
	@Autowired
	private PsychologicalCounselingService psychologicalCounselingService;
	@Autowired
	private SmartActivitiesService smartActivitiesService;
	@Autowired
	private SmartWorkService smartWorkService;
	@Autowired
	private SmartCoursewareService smartCoursewareService;
	@Autowired
	private ClassInfoService classInfoService;
	@Autowired
	private StudentService studentService;
	@Autowired
	private PhotoClassWorkMsgService photoClassWorkMsgService;
	@Autowired
	private CoreService coreService;
	@Autowired
	private SysWeixinMsgService sysWeixinMsgService;
	
	/**
	 * 查询所有学校
	 */
	@RequestMapping("/getallschool")
	public R getallschool(){
		DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("limit", 10);
		map.put("begin", 0);
		List<SchoolEntity> list = schoolService.queryList(map);
		DbContextHolder.setDbType(DBTypeEnum.MYSQL);
		return R.ok().put("page", list);
	}
	
//	/**
//	 * 阅读文件
//	 */
//	@RequestMapping("/yuedu")
//	public R yuedu(HttpServletRequest request,HttpSession session){
//		XDocService service = new XDocService();
//		String path = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".html";
//		try {
//			service.to(request.getParameter("path"), new File("D:\\tool\\apache-tomcat-8.0.46\\webapps\\wrs\\statics\\yuedu\\" + path));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return R.ok().put("path", path);
//	}
	
	/**
	 * 保存随堂课件
	 */
	@RequestMapping("/savesmartcourse")
	public R weixsave(HttpServletRequest request,HttpSession session){
		SmartCoursewareEntity smartCourseware = new SmartCoursewareEntity();
		StudentEntity student = (StudentEntity) session.getAttribute(ControllerConstant.SESSION_SMART_USER_KEY);
		ValidatorUtils.validateEntity(smartCourseware, AddGroup.class);
		smartCourseware.setCreatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		smartCourseware.setClassid(student.getClassId());
		smartCourseware.setName(request.getParameter("title"));
		smartCourseware.setType(Integer.parseInt(request.getParameter("type")));
		smartCoursewareService.insert(smartCourseware);
		return R.ok().put("id", smartCourseware.getId());
	}
	
	/**
	 * 添加作业
	 */
	@RequestMapping("/savesmartword")
	public R savesmartword(HttpServletRequest request,HttpSession session){
		DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
        StudentEntity student = (StudentEntity) session.getAttribute(ControllerConstant.SESSION_SMART_USER_KEY);
		DbContextHolder.setDbType(DBTypeEnum.MYSQL);
		SmartWorkEntity classNotice = new SmartWorkEntity();
		classNotice.setClassid(student.getClassId());
		classNotice.setContent(request.getParameter("content"));
		classNotice.setName(request.getParameter("title"));
		classNotice.setType(1);
		classNotice.setCreatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		smartWorkService.save(classNotice);
		return R.ok().put("path", null);
	}
	
	/**
	 * 添加学校通知
	 */
	@RequestMapping("/saveschoolnotice")
	public R saveschoolnotice(HttpServletRequest request,HttpSession session){
		DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
        StudentEntity student = (StudentEntity) session.getAttribute(ControllerConstant.SESSION_SMART_USER_KEY);
        ClassEntity classEntity = classService.queryObject(student.getClassId());
		DbContextHolder.setDbType(DBTypeEnum.MYSQL);
		SchoolNoticeEntity classNotice = new SchoolNoticeEntity();
		classNotice.setSchoolid(classEntity.getSchoolId());
		classNotice.setContent(request.getParameter("content"));
		classNotice.setTitle(request.getParameter("title"));
		classNotice.setCreatetime(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		schoolNoticeService.save(classNotice);
		return R.ok().put("path", null);
	}
	
	/**
	 * 添加班级通知
	 */
	@RequestMapping("/saveclassnotice")
	public R saveclassnotice(HttpServletRequest request,HttpSession session){
		DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
        StudentEntity student = (StudentEntity) session.getAttribute(ControllerConstant.SESSION_SMART_USER_KEY);
		DbContextHolder.setDbType(DBTypeEnum.MYSQL);
		ClassNoticeEntity classNotice = new ClassNoticeEntity();
		classNotice.setClassId(student.getClassId() + "");
		classNotice.setContent(request.getParameter("content"));
		classNotice.setTitle(request.getParameter("title"));
		classNotice.setCreatetime(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		classNoticeService.save(classNotice);
		return R.ok().put("path", null);
	}
	
	
	/**
	 * 请求校验（确认请求来自微信服务器）
	 * @throws IOException 
	 */
	@RequestMapping(value = "handler", method = RequestMethod.GET)
	public @ResponseBody
	void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 微信加密签名
				String signature = request.getParameter("signature");
				// 时间戳
				String timestamp = request.getParameter("timestamp");
				// 随机数
				String nonce = request.getParameter("nonce");
				// 随机字符串
				String echostr = request.getParameter("echostr");

				PrintWriter out = response.getWriter();
				// 请求校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
				if (SignUtil.checkSignature(signature, timestamp, nonce)) {
					out.print(echostr);
				}
				out.close();
				out = null;
	}


	/**
	 * 处理微信服务器发来的消息
	 */
	@RequestMapping(value = "handler", method = RequestMethod.POST)
	public @ResponseBody
	void doPost(HttpServletRequest request,HttpServletResponse response, String signature, String timestamp, String nonce) {
		try {
			// 将请求、响应的编码均设置为UTF-8（防止中文乱码）
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			
			// 调用核心业务类接收消息、处理消息
			String respXml = null;
			if (SignUtil.checkSignature(signature, timestamp, nonce)) {
				respXml = coreService.processRequest(request,sysWeixinMsgService);
			}
			// 响应消息
			PrintWriter out = response.getWriter();
			out.print(respXml);
			out.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 通过微信服务器id下载语音
	 */
	@RequestMapping("/uploadMedio")
	public R uploadMedio(HttpServletRequest request){
		String accessToken = CommonUtil.getToken("wxb9072ff1ebcf745c", "b298e38e02eb3d45ca5cc22c68e9bae5").getAccessToken();
		String path = AdvancedUtil.getMedia(accessToken, request.getParameter("serverId"), "E:/web/webroot/wrs/statics/video");
		System.out.println(path + "----------------------------");
		return R.ok().put("path", path);
	}
	
	/**
	 * 获取签名
	 */
	@RequestMapping("/sign")
	public R getSign(HttpServletRequest request){
		//1、获取AccessToken  
        String accessToken = Sign.getAccessToken();  
        //2、获取Ticket  
        String jsapi_ticket = Sign.getTicket(accessToken);  
        //3、时间戳和随机字符串  
        String noncestr = UUID.randomUUID().toString().replace("-", "").substring(0, 16);//随机字符串  
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);//时间戳  
        //4、获取url  
        String url="http://wrs.gykjewm.com/smart/user/classMessageList.html?classId="+request.getParameter("classId");  
        //5、将参数排序并拼接字符串
        String str = "jsapi_ticket="+jsapi_ticket+"&noncestr="+noncestr+"&timestamp="+timestamp+"&url="+url;  
        //6、将字符串进行sha1加密  
        String signature =Sign.SHA1(str);  
        SignEntity se = new SignEntity();
        se.setTimestamp(timestamp);
        se.setNoncestr(noncestr);
        se.setSignature(signature);
		return R.ok().put("signentity", se);
	}
	
	/**
	 * 班级信息
	 */
	@RequestMapping("/list_4")
	public R list_4(@RequestParam Map<String, Object> params, HttpSession session){
		//查询列表数据
        DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
        StudentEntity student = (StudentEntity) session.getAttribute(ControllerConstant.SESSION_SMART_USER_KEY);
		DbContextHolder.setDbType(DBTypeEnum.MYSQL);
		params.put("classid", student.getClassId());
		Query query = new Query(params);
		List<ClassInfoEntity> classInfoList = classInfoService.queryList(query);
		int total = classInfoService.queryTotal(query);
		PageUtils pageUtil = new PageUtils(classInfoList, total, query.getLimit(), query.getPage());
		query.put("classId", student.getClassId());
		query.put("begin", 0);
		DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
		int studenttotal = this.studentService.queryList(query).size();
		DbContextHolder.setDbType(DBTypeEnum.MYSQL);
		pageUtil.setTotalCount(studenttotal);
		return R.ok().put("page", pageUtil);
	}
	/**
	 * 随堂课件列表
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/list_8")
	public R list_8(@RequestParam Map<String, Object> params, HttpSession session){
		//查询列表数据
        DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
        if(params.get("name").equals("")){
        	params.put("name", null);
        }else{
        	params.put("name", params.get("name"));
        }
        StudentEntity student = (StudentEntity) session.getAttribute(ControllerConstant.SESSION_SMART_USER_KEY);
		if(params.get("schoolId") != null && !params.get("schoolId").equals("")){
			params.put("begin", 0);
			List<ClassEntity> classlist = classService.queryList(params);
			String classid = "";
			for (Iterator iterator = classlist.iterator(); iterator.hasNext();) {
				ClassEntity classEntity = (ClassEntity) iterator.next();
				classid += classEntity.getId() + ",";
			}
			params.put("classid", classid);
			params.put("type", 1);
		}else{
			params.put("classid", student.getClassId());
		}
		DbContextHolder.setDbType(DBTypeEnum.MYSQL);
		Query query = new Query(params);
		String[] ids = params.get("classid").toString().split(",");
		List<SmartCoursewareEntity> list = new ArrayList<SmartCoursewareEntity>();
		for (int i = 0; i < ids.length; i++) {
			if(!ids[i].equals("")){
				params.put("classid",ids[i] );
				if(Integer.parseInt(ids[i]) == student.getClassId()){
					params.put("type", null);
				}else{
					params.put("type", 1);
				}
				query = new Query(params);
				List<SmartCoursewareEntity> smartCoursewareList = smartCoursewareService.queryList(query);
				for (Iterator iterator = smartCoursewareList.iterator(); iterator
						.hasNext();) {
					SmartCoursewareEntity smartCoursewareEntity = (SmartCoursewareEntity) iterator
							.next();
					list.add(smartCoursewareEntity);
				}
			}
		}
		PageUtils pageUtil = new PageUtils(list, 0, query.getLimit(), query.getPage());
		return R.ok().put("page", pageUtil);
	}
	/**
	 * 作业列表
	 */
	@RequestMapping("/list_2")
	public R list_2(@RequestParam Map<String, Object> params, HttpSession session){
		//查询列表数据
        DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
        StudentEntity student = (StudentEntity) session.getAttribute(ControllerConstant.SESSION_SMART_USER_KEY);
		DbContextHolder.setDbType(DBTypeEnum.MYSQL);
		params.put("classid", student.getClassId());
		Query query = new Query(params);
		List<SmartWorkEntity> smartWorkList = smartWorkService.queryList(query);
		int total = smartWorkService.queryTotal(query);
		PageUtils pageUtil = new PageUtils(smartWorkList, total, query.getLimit(), query.getPage());
		return R.ok().put("page", pageUtil);
	}
	
	/**
	 * 竞技活动列表
	 */
	@RequestMapping("/list_5")
	public R list_5(@RequestParam Map<String, Object> params, HttpSession session){
		//查询列表数据
        DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
        StudentEntity student = (StudentEntity) session.getAttribute(ControllerConstant.SESSION_SMART_USER_KEY);
        ClassEntity cla = this.classService.selectById(student.getClassId());
		DbContextHolder.setDbType(DBTypeEnum.MYSQL);
		params.put("schoolid", cla.getSchoolId());
		Query query = new Query(params);
		List<SmartActivitiesEntity> smartActivitiesList = smartActivitiesService.queryList(query);
		int total = smartActivitiesService.queryTotal(query);
		PageUtils pageUtil = new PageUtils(smartActivitiesList, total, query.getLimit(), query.getPage());
		return R.ok().put("page", pageUtil);
	}
	
	/**
	 * 班级通知列表
	 */
	@RequestMapping("/list_1")
	public R list_1(@RequestParam Map<String, Object> params, HttpSession session){
		//查询列表数据
        DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
        StudentEntity student = (StudentEntity) session.getAttribute(ControllerConstant.SESSION_SMART_USER_KEY);
		DbContextHolder.setDbType(DBTypeEnum.MYSQL);
		params.put("classId", student.getClassId());
		Query query = new Query(params);
		List<ClassNoticeEntity> classNoticeList = classNoticeService.queryList(query);
		int total = schoolNoticeService.queryTotal(query);
		PageUtils pageUtil = new PageUtils(classNoticeList, total, query.getLimit(), query.getPage());
		return R.ok().put("page", pageUtil);
	}
	
	/**
	 * 心理咨询列表
	 */
	@RequestMapping("/list_7")
	public R list_7(@RequestParam Map<String, Object> params, HttpSession session){
		//查询列表数据
        DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
        StudentEntity student = (StudentEntity) session.getAttribute(ControllerConstant.SESSION_SMART_USER_KEY);
		ClassEntity cla = this.classService.selectById(student.getClassId());
		DbContextHolder.setDbType(DBTypeEnum.MYSQL);
		params.put("schoolId", cla.getSchoolId());
		Query query = new Query(params);
		List<PsychologicalCounselingEntity> psychologicalCounselingList = psychologicalCounselingService.queryList(query);
		int total = psychologicalCounselingService.queryTotal(query);
		PageUtils pageUtil = new PageUtils(psychologicalCounselingList, total, query.getLimit(), query.getPage());
		return R.ok().put("page", pageUtil);
	}
	
	/**
	 * 新生指南列表
	 */
	@RequestMapping("/list_9")
	public R list_9(@RequestParam Map<String, Object> params, HttpSession session){
		//查询列表数据
        DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
        StudentEntity student = (StudentEntity) session.getAttribute(ControllerConstant.SESSION_SMART_USER_KEY);
		ClassEntity cla = this.classService.selectById(student.getClassId());
		DbContextHolder.setDbType(DBTypeEnum.MYSQL);
		params.put("schoolId", cla.getSchoolId());
		Query query = new Query(params);
		List<FreshmanGuideEntity> freshmanGuideList = freshmanGuideService.queryList(query);
		int total = freshmanGuideService.queryTotal(query);
		PageUtils pageUtil = new PageUtils(freshmanGuideList, total, query.getLimit(), query.getPage());
		return R.ok().put("page", pageUtil);
	}
	
	/**
	 * 学校通知列表
	 */
	@RequestMapping("/list_6")
	public R list_6(@RequestParam Map<String, Object> params, HttpSession session){
		//查询列表数据
        DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
        StudentEntity student = (StudentEntity) session.getAttribute(ControllerConstant.SESSION_SMART_USER_KEY);
		ClassEntity cla = this.classService.selectById(student.getClassId());
		DbContextHolder.setDbType(DBTypeEnum.MYSQL);
		params.put("schoolid", cla.getSchoolId());
		Query query = new Query(params);
		List<SchoolNoticeEntity> schoolNoticeList = schoolNoticeService.queryList(query);
		int total = schoolNoticeService.queryTotal(query);
		PageUtils pageUtil = new PageUtils(schoolNoticeList, total, query.getLimit(), query.getPage());
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/shouyeInfo")
	public R info(@RequestParam("type") Integer type,@RequestParam("id") Integer id){
		if(type == 1){
			ClassNoticeEntity classNoticeEntity = this.classNoticeService.selectById(id);
			return R.ok().put("obj", classNoticeEntity);
		}else if(type == 2){
			SmartWorkEntity smartWork = this.smartWorkService.selectById(id);
			return R.ok().put("obj", smartWork);
		}else if(type == 6){
			SchoolNoticeEntity schoolnotice = this.schoolNoticeService.selectById(id);
			return R.ok().put("obj", schoolnotice);
		}else if(type == 8){
			SmartCoursewareEntity smartCourseware = this.smartCoursewareService.selectById(id);
			return R.ok().put("obj", smartCourseware);
		}
		return null;
	}
	
}
