package io.renren.controller.app;

import io.renren.entity.TokenEntity;
import io.renren.entity.app.SmartAppEntity;
import io.renren.entity.app.SmartNewsEntity;
import io.renren.entity.smart.ClassEntity;
import io.renren.entity.smart.ClassInfoEntity;
import io.renren.entity.smart.ClassNoticeEntity;
import io.renren.entity.smart.FreshmanGuideEntity;
import io.renren.entity.smart.IoEntity;
import io.renren.entity.smart.PhotoClassWorkMsgEntity;
import io.renren.entity.smart.PhotoExaminationEntity;
import io.renren.entity.smart.PsychologicalCounselingEntity;
import io.renren.entity.smart.SchoolEntity;
import io.renren.entity.smart.SchoolNoticeEntity;
import io.renren.entity.smart.SmartActivitiesEntity;
import io.renren.entity.smart.SmartCoursewareEntity;
import io.renren.entity.smart.SmartExceptionEntity;
import io.renren.entity.smart.SmartLeaveEntity;
import io.renren.entity.smart.SmartWorkEntity;
import io.renren.entity.smart.StudentEntity;
import io.renren.entity.smart.WeixinFunctionEntity;
import io.renren.entity.smart.WeixinFunctionImgEntity;
import io.renren.service.TokenService;
import io.renren.service.smart.ClassInfoService;
import io.renren.service.smart.ClassNoticeService;
import io.renren.service.smart.ClassService;
import io.renren.service.smart.FreshmanGuideService;
import io.renren.service.smart.IoService;
import io.renren.service.smart.PhotoClassWorkMsgService;
import io.renren.service.smart.PhotoExaminationService;
import io.renren.service.smart.PhotoScoreService;
import io.renren.service.smart.PsychologicalCounselingService;
import io.renren.service.smart.SchoolNoticeService;
import io.renren.service.smart.SchoolService;
import io.renren.service.smart.SmartActivitiesService;
import io.renren.service.smart.SmartAppService;
import io.renren.service.smart.SmartCoursewareService;
import io.renren.service.smart.SmartExceptionService;
import io.renren.service.smart.SmartLeaveService;
import io.renren.service.smart.SmartNewsService;
import io.renren.service.smart.SmartVideoDeviceService;
import io.renren.service.smart.SmartWorkService;
import io.renren.service.smart.StudentService;
import io.renren.service.smart.WeixinFunctionImgService;
import io.renren.service.smart.WeixinFunctionService;
import io.renren.service.tombstone.TombstoneDeadService;
import io.renren.service.tombstone.TombstoneUserService;
import io.renren.util.MsgUtil;
import io.renren.util.OssUploadUtil;
import io.renren.utils.Query;
import io.renren.utils.R;
import io.renren.utils.dataSource.DBTypeEnum;
import io.renren.utils.dataSource.DbContextHolder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import jiguangtuisong.JpushClientUtil;
import net.sf.json.JSONObject;

import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import redis.clients.jedis.Jedis;

import com.aliyuncs.exceptions.ClientException;
import com.baomidou.mybatisplus.mapper.EntityWrapper;

@RestController
@RequestMapping("appInterface/student")
@SuppressWarnings("rawtypes")
public class StudentAppInterfaceController{
	
	private final static String JEDISPATH = "127.0.0.1";
	private final static String DATA = "data";
	private final static String FILEPATH = "http://static.gykjewm.com/";
	@Autowired
	private StudentService studentService;
	@Autowired
	private ClassService classService;
	@Autowired
	private TokenService tokenService;
	@Autowired
	private WeixinFunctionImgService weixinFunctionImgService;
	@Autowired
	private WeixinFunctionService weixinFunctionService;
	@Autowired
	private ClassInfoService classInfoService;
	@Autowired
	private ClassNoticeService classNoticeService;
	@Autowired
	private SmartWorkService smartWorkService;
	@Autowired
	private SmartActivitiesService smartActivitiesService;
	@Autowired
	private SchoolNoticeService schoolNoticeService;
	@Autowired
	private FreshmanGuideService freshmanGuideService;
	@Autowired
	private PsychologicalCounselingService psychologicalCounselingService;
	@Autowired
	private SmartCoursewareService smartCoursewareService;
	@Autowired
	private SchoolService schoolService;
	@Autowired
	private PhotoExaminationService photoExaminationService;
	@Autowired
	private PhotoClassWorkMsgService photoClassWorkMsgService;
	@Autowired
	private PhotoScoreService photoScoreService;
	@Autowired
	private IoService ioService;
	@Autowired
	private SmartLeaveService smartLeaveService;
	@Autowired
	private SmartNewsService smartNewsService;
	@Autowired
	private SmartVideoDeviceService smartVideoDeviceService;
	@Autowired
	private TombstoneUserService tombstoneUserService;
	@Autowired
	private TombstoneDeadService tombstoneDeadService;
	@Autowired
	private SmartAppService smartAppService;
	@Autowired
	private SmartExceptionService smartExceptionService;
	
	@SuppressWarnings("resource")
	@RequestMapping("/main")
	public R main(HttpServletRequest request) throws Exception{
//		List<SmartNewsEntity> list = smartNewsService.queryListStudent(null);
//		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
//			SmartNewsEntity smartNewsEntity = (SmartNewsEntity) iterator.next();
//			TombstoneUserEntity user = new TombstoneUserEntity();
//			user.setId(smartNewsEntity.getId());
//			user.setPic("http://guanyukeji-static.oss-cn-hangzhou.aliyuncs.com/tombstoneewm/"+smartNewsEntity.getTitle());
//			tombstoneUserService.update(user);
//			TombstoneDeadEntity dead = new TombstoneDeadEntity();
//			dead.setId(smartNewsEntity.getId());
//			dead.setImage("http://guanyukeji-static.oss-cn-hangzhou.aliyuncs.com/tombstoneewm/"+smartNewsEntity.getTitle());
//			tombstoneDeadService.update(dead);
//		}
		
		String key = (request.getParameter("key")==null || "".equals(request.getParameter("key")))?
				getJSONObject(request):request.getParameter("key").replace("&quot;", "\"");
	    System.out.println("---student-------"+key);
	    if(key == null || "".equals(key)){
	    	return R.error("参数未到服务器端");
	    }
	    JSONObject json = JSONObject.fromObject(key);
		if(json.get("type") == null || json.get("type") == "null" || "".equals(json.get("type"))){
			return R.error("请求参数中的type错误");
		}
		String type = json.getString("type");
		if(json.get("token") == null || "".equals(json.get("token")) || "null".equals(json.get("token"))){
			if(type.equals("userLogin")){
				//学生登录接口
				return login(json);
			}else if(type.equals("sendMsgBack")){
				//通过手机号码发送验证码(找回密码时）
				return sendMsg(json.getJSONObject("data"),request);
			}else if(type.equals("updatepasswrodtophone")){
				//忘记密码：通过手机号找回密码
				return updatepasswrodtophone(json.getJSONObject("data"),request);
			}else if(type.equals("exceptionSave")){
				//异常日志
				return exception(json.getJSONObject("data"));
			}else if(type.equals("checkVersion")){
				//检查版本
				return checkVersion(json.getJSONObject("data"));
			}else if(type.equals("getAccessToken")){
				//获取设备的AccessToken
				return getAccessToken(json.getJSONObject("data"));
			}else{
				return R.error("请重新登录");
			}
		}else{
			if(!getToken(json.getString("token"))){
				return R.error("请重新登录");
			}
			if(type.equals("shouyejiugongge")){
				//首页九宫格
				return shouyejiugongge(json.getJSONObject("data"));
			}else if(type.equals("teacherNotice")){
				//老师通知列表
				return teacherNotice(json.getJSONObject("data"));
			}else if(type.equals("smartWorkList")){
				//作业列表
				return smartWord(json.getJSONObject("data"));
			} else if(type.equals("classInfo")){
				//班级信息
				return classInfo(json.getJSONObject("data"));
			}else if(type.equals("smartActivites")){
				//竞技活动
				return smartActivites(json.getJSONObject("data"));
			}else if(type.equals("schoolNotice")){
				//学校通知
				return schoolNotice(json.getJSONObject("data"));
			} else if(type.equals("psychological")){
				//心理咨询
				return psychological(json.getJSONObject("data"));
			}else if(type.equals("smartCourseware")){
				//随堂课件
				return smartCourseware(json.getJSONObject("data"));
			}else if(type.equals("freshmanGuide")){
				//新生指南
				return freshmanGuide(json.getJSONObject("data"));
			}else if(type.equals("getallschool")){
				//学校列表
				return getallschool();
			}else if(type.equals("getClassId")){
				//班级
				return getClassId(json.getJSONObject("data"));
			}else if(type.equals("photoPicWorkMsg")){
				//班内消息
				return photoPicWorkMsg(json.getJSONObject("data"));
			}else if(type.equals("examinationlist")){
				//考试成绩
				return examinationlist(json.getJSONObject("data"));
			}else if(type.equals("examinationdetail")){
				//考试成绩详情
				return examinationdetail(json.getJSONObject("data"));
			}else if(type.equals("ioepclist")){
				//签到
				return ioepclist(json.getJSONObject("data"));
			}else if(type.equals("saveSmartLeave")){
				//通过学生id保存请假记录
				return saveSmartLeave(json.getJSONObject("data"));
			}else if(type.equals("getStudentIdLeave")){
				//通过学生id查询自己的请假记录
				return getStudentIdLeave(json.getJSONObject("data"));
			}else if(type.equals("getSmartLeaveDetail")){
				//通过请假条id查询请假条详情
				return getSmartLeaveDetail(json.getJSONObject("data"));
			}else if(type.equals("updateName")){
				//修改姓名
				return updateName(json.getJSONObject("data"));
			}else if(type.equals("updatePassword")){
				//修改密码
				return updatePassword(json.getJSONObject("data"));
			}else if(type.equals("updateHeadImage")){
				//头像修改
				CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
				return updateheadportrait(json.getJSONObject("data"),multipartResolver,request);
			}else if(type.equals("sendMsg")){
				//通过手机号码发送验证码(绑定手机号）
				return sendMsg2(json.getJSONObject("data"),request);
			}else if(type.equals("updatephone")){
				//绑定手机号
				return updatePhone(json.getJSONObject("data"),request);
			}else if(type.equals("updatesmartnewstates")){
				//修改通知状态
				return updatesmartnewstates(json.getJSONObject("data"));
			}else if(type.equals("getAccessToken")){
				//获取设备的AccessToken
				return getAccessToken(json.getJSONObject("data"));
			}else if(type.equals("outLogin")){
				//退出登录
				Jedis jedis =  new Jedis(JEDISPATH,6379,10000);
				jedis.set(json.getString("token"), "");
				return outLogin(json.getJSONObject("data"));
			}else if(type.equals("checkVersion")){
				//检查版本
				return checkVersion(json.getJSONObject("data"));
			}else if(type.equals("exceptionSave")){
				//异常日志
				return exception(json.getJSONObject("data"));
			}
		}
		return null;
	}
	
	private R exception(JSONObject json){
		Integer type = json.getInt("equipmentType");//11安卓学生端，21苹果学生端
		String exceptioninformation = json.get("exceptioninformation")==null?"":json.getString("exceptioninformation");//异常具体信息
		String modularname = json.get("modularname")==null?"":json.getString("modularname");//模块名
		SmartExceptionEntity smartException = new SmartExceptionEntity();
		smartException.setSchoolname(type==11?"安卓学生端":"苹果学生端");
		smartException.setExceptioninformation(exceptioninformation);
		smartException.setModularname(modularname);
		smartException.setCreatetime(new Date());
		smartExceptionService.save(smartException);
		return R.ok();
	}
	
	private R checkVersion(JSONObject json){
		Integer updateType = 0;//是否强制更新
		Integer updateIf = 0;//是否有更新0否1是
		String appPath = "";
		String remark = "";
		String editions = "";
		String packageSize="";
		if(json.get("equipmentType") != null){
			Integer equipmentType = json.getInt("equipmentType");
			Integer edition = Integer.parseInt(json.getString("edition").replace(".", ""));
			SmartAppEntity smartApp = new SmartAppEntity();
			smartApp.setEquipmentType(equipmentType);
			EntityWrapper<SmartAppEntity> wrapper = new EntityWrapper<SmartAppEntity>(smartApp);
			smartApp = smartAppService.selectOne(wrapper);
			appPath = smartApp.getEquipmentPath();
			editions = smartApp.getEdition().toString().replace("", ".").substring(1,6);
			remark = smartApp.getRemark();
			packageSize = smartApp.getPackageSize();
			updateType = smartApp.getUpdateType();
			if(edition < smartApp.getEdition()){
				updateIf = 1;
			}
		}
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("updateType", updateType);
		map.put("updateIf", updateIf);
		map.put("appPath", appPath);
		map.put("remark", remark);
		map.put("packageSize", packageSize);
		map.put("edition", editions);
		return R.ok().put(DATA, map);
	}
	
	private R outLogin(JSONObject json){
		tokenService.delete(new Long(json.getInt("studentId")));
		return R.ok();
	}
	
	private R getAccessToken(JSONObject json){
		TokenEntity token = tokenService.queryByUserId(new Long(json.getInt("studentId")));
		String data = "";
		if(token == null){
			data = "该用户未登录";
		}else{
			if(new Date().getTime() > token.getExpireTime().getTime()){
				JSONObject jso = getAccessToken("https://open.ys7.com/api/lapp/token/get");
				token.setExpireTime(new Date(new Long(jso.getJSONObject("data").getString("expireTime"))));
				token.setAccessToken(jso.getJSONObject("data").getString("accessToken"));
				tokenService.update(token);
			}
			data = token.getAccessToken();
		}
		return R.ok().put(DATA, data);
	}
	
	@SuppressWarnings("resource")
	private boolean getToken(String token){
		if(new Jedis(JEDISPATH,6379,10000).get(token) == null || "".equals(new Jedis(JEDISPATH,6379,10000).get(token))){
			TokenEntity tokens = tokenService.queryByToken(token);
			if(tokens == null){
				return false;
			}else{
				Jedis jedis =  new Jedis(JEDISPATH,6379,10000);
				jedis.set(tokens.getToken(), tokens.getToken());
				return true;
			}
		}else{
			return true;
		}
	}
	
	@SuppressWarnings("resource")
	private R updatePhone(JSONObject json,HttpServletRequest request){
		String phone = json.getString("phone");
		Integer userId = json.getInt("studentId");
		String code = json.getString("code");
		Jedis jedis =  new Jedis(JEDISPATH,6379,10000);
		String code2 = jedis.get(phone);
		if(!code.equals(code2)){
			return R.error("验证码错误");
		}
		jedis.del(phone);
		StudentEntity studnet = new StudentEntity();
		studnet.setId(userId);
		studnet.setPhoen(phone);
		studentService.update(studnet);
		return R.ok().put(DATA, studnet);
	}
	
	public R updatesmartnewstates(JSONObject json){
		Integer id = json.getInt("newsId");
		SmartNewsEntity sme = smartNewsService.queryObject(id);
		if(sme != null){
			sme.setNewstype(1);
			sme.setId(json.getInt("newsId"));
			smartNewsService.update(sme);
		}
		return R.ok();
	}
	
	public R updateheadportrait(JSONObject json,CommonsMultipartResolver multipartResolver,HttpServletRequest request){
		try {
			StudentEntity student = new StudentEntity();
			student.setId(Integer.parseInt(json.getString("studentId")));
			InputStream[] is = uploadfile(multipartResolver, request);
			if(is != null){
				student.setPic(FILEPATH+"smart_head_pic/"+OssUploadUtil.uploadObject2OSS(is[0], "smart_head_pic/"));
				studentService.update(student);
				StudentEntity studentEntity = studentService.queryObject(student.getId());
				Integer id = studentEntity.getClassId();
				studentEntity.setSchoolId(classService.queryObject(id).getSchoolId());
				return R.ok().put(DATA, studentEntity);
			}else{
				StudentEntity studentEntity = studentService.queryObject(student.getId());
				Integer id = studentEntity.getClassId();
				studentEntity.setSchoolId(classService.queryObject(id).getSchoolId());
				return R.ok().put(DATA, studentEntity);
			}
		} catch (Exception e) {
			return R.error("失败");
		}
	}
	
	//图片文件上传
	public InputStream[] uploadfile(CommonsMultipartResolver multipartResolver,HttpServletRequest request) throws IllegalStateException, IOException{
		InputStream[] is = null;
			//判断 request 是否有文件上传,即多部分请求  
		   if(multipartResolver.isMultipart(request)){
		       MultipartHttpServletRequest m= (MultipartHttpServletRequest)request;
		       List<MultipartFile> detail = new ArrayList<MultipartFile>();
		       if(m.getFiles("files").size() != 0){
		        	detail = m.getFiles("files");
		        	is = new InputStream[detail.size()];
		        	for (int i = 0; i < detail.size(); i++) {
		        		if(!detail.get(i).isEmpty()){
		        			is[i] = detail.get(i).getInputStream();
		        		}
		        	}
		        }
		     }
		    return is;
	}
	
	public R updateName(JSONObject json){
		StudentEntity studnet = studentService.queryObject(Integer.parseInt(json.getString("studentId")));
		if(studnet != null){
			studnet.setStudentName(json.getString("studentName"));
			studentService.update(studnet);
		}
		StudentEntity student = studentService.queryObject(studnet.getId());
		return R.ok().put(DATA, student);
	}
	
	public R updatePassword(JSONObject json){
		StudentEntity student = studentService.queryObject(Integer.parseInt(json.getString("studentId")));
		if(student != null){
			if(!student.getPasswordd().equals(new Sha256Hash(json.getString("oldPassword")).toString())){
				return R.error("原密码错误");
			}else{
				student.setPasswordd(new Sha256Hash(json.getString("newPassword")).toString());
				studentService.update(student);
			}
		}
		return R.ok("密码修改成功");
	}
	
	private R getSmartLeaveDetail(JSONObject json){
		Map<String, Object> map = new HashMap<String, Object>();
		SmartLeaveEntity leave = smartLeaveService.queryObject(json.getInt("leaveId"));
		StudentEntity student = new StudentEntity();
		if(leave != null){
			student = studentService.queryObject(leave.getUserid());
		}
		map.put("leave", leave);
		map.put("student", student);
		return R.ok().put(DATA, map);
	}
	
	private R getStudentIdLeave(JSONObject json){
		Integer id = json.getInt("studentId");
		Map<String, Object> map = getMap(json);
		map.put("userId", id);
		map.put("classId", null);
		return R.ok().put(DATA, smartLeaveService.queryList(map));
	}
	
	private R saveSmartLeave(JSONObject json){
		SmartLeaveEntity smartLeave = new SmartLeaveEntity();
		Integer id = json.getInt("studentId");
		StudentEntity student = studentService.queryObject(id);
		smartLeave.setUserid(id);
		smartLeave.setContent(json.getString("content"));
		smartLeave.setStates("1");
		try {
			smartLeave.setStartdate(new SimpleDateFormat("yyyy-MM-dd").parse(json.getString("startDate")));
			smartLeave.setEnddate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(json.getString("endDate")+" 23:59:59"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		smartLeave.setCalssid(student.getClassId());
		smartLeaveService.insert(smartLeave);
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("type", 4);
		m.put("id", smartLeave.getId());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("classid", student.getClassId());
		map.put("sidx", null);
		map.put("order", null);
		map.put("offset", 0);
		map.put("limit", 20);
		map.put("userType", 2);
		List<ClassInfoEntity> list = classInfoService.queryList(map);
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			ClassInfoEntity classInfoEntity = (ClassInfoEntity) iterator.next();
			SmartNewsEntity sne = new SmartNewsEntity();
			sne.setNewsid(smartLeave.getId());
			sne.setStates(4);
			sne.setNewstype(0);
			sne.setUserId(classInfoEntity.getUserId());
			smartNewsService.insert(sne);
			m.put("newsId", sne.getId());
			TokenEntity token = tokenService.queryByUserId(new Long(classInfoEntity.getUserId()));
			if(token != null){
				JpushClientUtil.sendToRegistrationId(classInfoEntity.getUserId().toString(), "请假申请通知", "请假申请",
						smartLeave.getContent(), JSONObject.fromObject(m).toString());
			}
		}
		return R.ok();
	}
	
	public R ioepclist(JSONObject json){
		Map<String, Object> map = getMap(json);
		Integer id = Integer.parseInt(json.getString("studentId"));
		map.put("studentId", id);
		List<IoEntity> list = ioService.queryListmysql(map);
		if(list.size() == 0){
			DbContextHolder.setDbType(DBTypeEnum.SQLSERVER2);
			list = ioService.queryList(map);
			DbContextHolder.setDbType(DBTypeEnum.MYSQL);
		}
		return R.ok().put(DATA, list);
	}
	
	public R examinationdetail(JSONObject json){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sidx", null);
		map.put("offset", 0);
		map.put("limit", 100);
		map.put("examinationId", json.getString("examinationId"));
		map.put("userId", json.getString("studentId"));
		return R.ok().put(DATA, photoScoreService.queryList(map));
	}
	
	public R photoPicWorkMsg(JSONObject json){
		Map<String, Object> map = getMap(json);
		map.put("classId", json.getString("classId"));
		map.put("userId", json.getString("studentId"));
		List<PhotoClassWorkMsgEntity> photoClassWorkMsgList = photoClassWorkMsgService.queryListtongji(map);
		List<PhotoClassWorkMsgEntity> list = new ArrayList<PhotoClassWorkMsgEntity>();
		for (PhotoClassWorkMsgEntity pcwm : photoClassWorkMsgList) {
			StudentEntity studnet = studentService.selectById(pcwm.getUserId());
			pcwm.setStudent(studnet);
			list.add(pcwm);
		}
		return R.ok().put(DATA, list);
	}
	
	public R examinationlist(JSONObject json){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sidx", null);
		map.put("offset", 0);
		map.put("limit", 100);
		map.put("classId", json.getString("classId"));
		List<PhotoExaminationEntity> freshmanGuideList = photoExaminationService.queryList(map);
		return R.ok().put(DATA, freshmanGuideList);
	}
	
	public R getClassId(JSONObject json){
		Integer id = json.getInt("classId");
		return R.ok().put(DATA, classService.queryObject(id));
	}
	
	private R getallschool(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("limit", 100);
		map.put("begin", 0);
		List<SchoolEntity> list = schoolService.queryList(map);
		return R.ok().put(DATA, list);
	}
	
	public R freshmanGuide(JSONObject json){
		Map<String, Object> map = getMap(json);
		map.put("schoolId", json.getString("schoolId"));
		List<FreshmanGuideEntity> freshmanGuideList = freshmanGuideService.queryList(map);
		return R.ok().put(DATA, freshmanGuideList);
	}
	
	public R smartCourseware(JSONObject json){
		List<SmartCoursewareEntity> list = null;
		Map<String, Object> map = getMap(json);
		if(json.get("schoolId") == null){
			list = new ArrayList<SmartCoursewareEntity>();
			map.put("classid", json.get("classId"));
			if(json.get("coursewareName") == null){
				map.put("name", null);
				map.put("type", null);
			}else{
				map.put("name", json.getString("coursewareName"));
			}
			list = smartCoursewareService.queryList(map);
		}else{
			list = new ArrayList<SmartCoursewareEntity>();
			map.put("begin", 0);
			map.put("schoolId", json.get("schoolId"));
			DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
			List<ClassEntity> classlist = classService.queryList(map);
			DbContextHolder.setDbType(DBTypeEnum.MYSQL);
			map.put("type", 1);
			String classid = "";
			for (Iterator iterator = classlist.iterator(); iterator.hasNext();) {
				ClassEntity classEntity = (ClassEntity) iterator.next();
				classid += classEntity.getId() + ",";
			}
			map.put("classid", classid);
			if(json.get("coursewareName") == null || json.get("coursewareName").equals("")){
				map.put("name", null);
			}else{
				map.put("name", json.getString("coursewareName"));
			}
			String[] ids = map.get("classid").toString().split(",");
			for (int i = 0; i < ids.length; i++) {
				if(!ids[i].equals("")){
					map.put("classid",ids[i] );
					List<SmartCoursewareEntity> smartCoursewareList = smartCoursewareService.queryList(map);
					for (Iterator iterator = smartCoursewareList.iterator(); iterator.hasNext();) {
						SmartCoursewareEntity smartCoursewareEntity = (SmartCoursewareEntity) iterator.next();
						list.add(smartCoursewareEntity);
					}
				}
			}
		}
		return R.ok().put(DATA, list);
	}
	
	public R psychological(JSONObject json){
		Map<String, Object> map = getMap(json);
		map.put("schoolId", json.getString("schoolId"));
		List<PsychologicalCounselingEntity> psychologicalCounselingList = psychologicalCounselingService.queryList(map);
		return R.ok().put(DATA, psychologicalCounselingList);
	}
	
	public R schoolNotice(JSONObject json){
		Map<String, Object> map = getMap(json);
		map.put("schoolid", json.getString("schoolId"));
		map.put("userId", json.get("studentId"));
		List<SchoolNoticeEntity> schoolNoticeList = schoolNoticeService.queryListtongji(map);
		return R.ok().put(DATA, schoolNoticeList);
	}
	
	public R smartActivites(JSONObject json){
		Map<String, Object> map = getMap(json);
		map.put("schoolid", json.getString("schoolId"));
		List<SmartActivitiesEntity> smartActivitiesList = smartActivitiesService.queryList(map);
		return R.ok().put(DATA, smartActivitiesList);
	}
	
	public R classInfo(JSONObject json){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sidx", null);
		map.put("offset", 0);
		map.put("limit", 100);
		map.put("classid", json.getString("classId"));
		List<ClassInfoEntity> classInfoList = classInfoService.queryList(map);
		map.put("classId", json.getString("classId"));
		map.put("begin", 0);
		map.put("begin", 0);
		int studenttotal = this.studentService.queryList(map).size();
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("list", classInfoList);
		m.put("studenttotal", studenttotal);
		return R.ok().put(DATA, m);
	}
	
	public R smartWord(JSONObject json){
		Map<String, Object> map = getMap(json);
		map.put("classid", json.getString("classId"));
		List<SmartWorkEntity> smartWorkList = smartWorkService.queryList(map);
		return R.ok().put(DATA, smartWorkList);
	}
	
	public R teacherNotice(JSONObject json){
		Map<String, Object> map = getMap(json);
		map.put("classId", json.getString("classId"));
		map.put("userId", json.get("studentId"));
		List<ClassNoticeEntity> classNoticeList = classNoticeService.queryListtongji(map);
		return R.ok().put(DATA, classNoticeList);
	}
	
	//修改通知状态
	public void updatesmartnewstates(Integer id){
		SmartNewsEntity sme = smartNewsService.queryObject(id);
		if(sme != null){
			sme.setNewstype(1);
			sme.setId(id);
			smartNewsService.update(sme);
		}
	}
	
	private Map<String, Object> getMap(JSONObject json){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("offset", (Integer.parseInt(json.getString("offset"))-1) * Integer.parseInt(json.getString("limit")));
		map.put("limit", Integer.parseInt(json.getString("limit")));
		map.put("sidx", "");
		map.put("order", "");
		return map;
	}
	
	private R shouyejiugongge(JSONObject json){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("sidx", "");
		params.put("order", "");
		params.put("page", 1);
		params.put("limit", 30);
		params.put("schoolId", json.getString("schoolId"));
		Query query = new Query(params);
		List<WeixinFunctionEntity> weixinFunctionList = weixinFunctionService.queryList(query);
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		for (Iterator iterator = weixinFunctionList.iterator(); iterator.hasNext();) {
			WeixinFunctionEntity weixinFunctionEntity = (WeixinFunctionEntity) iterator.next();
			params.put("functionId", weixinFunctionEntity.getId());
			List<WeixinFunctionImgEntity> weixinFunctionImgList = weixinFunctionImgService.queryList(params);
			if(weixinFunctionImgList.size() > 0){
				for (Iterator iterator2 = weixinFunctionImgList.iterator(); iterator2.hasNext();) {
					WeixinFunctionImgEntity weixinFunctionImgEntity = (WeixinFunctionImgEntity) iterator2.next();
					weixinFunctionEntity.setPic(weixinFunctionImgEntity.getPic());
				}
			}
			Map<String, String> map = new HashMap<String, String>();
			map.put("name", weixinFunctionEntity.getName());
			map.put("pic", weixinFunctionEntity.getPic());
			map.put("id", weixinFunctionEntity.getId()+"");
			list.add(map);
		}
		return R.ok().put(DATA, list);
	}
	
	@SuppressWarnings("resource")
	private R updatepasswrodtophone(JSONObject json,HttpServletRequest request){
		String  phone = json.getString("phone");
		String  password = json.getString("password");
		String code = json.getString("code");
		Jedis jedis =  new Jedis(JEDISPATH,6379,10000);
		String code2 = jedis.get(phone);
		if(!code.equals(code2)){
			return R.error("验证码错误");
		}
		jedis.del(phone);
		StudentEntity user = new StudentEntity();
		user.setPhoen(phone);
		EntityWrapper<StudentEntity> wrapper = new EntityWrapper<StudentEntity>(user);
		DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
		user = this.studentService.selectOne(wrapper);
		DbContextHolder.setDbType(DBTypeEnum.MYSQL);
		user.setPasswordd(new Sha256Hash(password).toHex());
		studentService.update(user);
		return R.ok().put(DATA, "密码为："+password);
	}
	
	@SuppressWarnings("resource")
	private R sendMsg2(JSONObject json,HttpServletRequest request){
		String randow = getRandow();
		String phone = json.getString("phone");
		if(phone == null || "null".equals(phone) || "".equals(phone)){
			return R.error("手机号码不能为空");
		}else if(!phone.matches("^1[3|4|5|7|8][0-9]\\d{4,8}$")){
			return R.error("手机号码有误，请重新输入");
		}else{
			StudentEntity user = new StudentEntity();
			user.setPhoen(phone);
			EntityWrapper<StudentEntity> wrapper = new EntityWrapper<StudentEntity>(user);
			user = this.studentService.selectOne(wrapper);
			if(user != null){
				return R.error("该号码已绑定");
			}
			try {
				MsgUtil.sendSms(phone, randow,MsgUtil.YZMBD);
			} catch (ClientException e) {
				e.printStackTrace();
			}
		}
		Jedis jedis =  new Jedis(JEDISPATH,6379,10000);
		jedis.set(phone, randow);
		return R.ok().put(DATA, randow);
	}
	
	@SuppressWarnings("resource")
	private R sendMsg(JSONObject json,HttpServletRequest request){
		String randow = getRandow();
		String phone = json.getString("phone");
		if(phone == null || "null".equals(phone) || "".equals(phone)){
			return R.error("手机号码不能为空");
		}else if(!phone.matches("^1[3|4|5|7|8][0-9]\\d{4,8}$")){
			return R.error("手机号码有误，请重新输入");
		}else{
			StudentEntity user = new StudentEntity();
			user.setPhoen(phone);
			EntityWrapper<StudentEntity> wrapper = new EntityWrapper<StudentEntity>(user);
			user = this.studentService.selectOne(wrapper);
			if(user == null){
				return R.error("该号码暂未绑定");
			}
			try {
				MsgUtil.sendSms(phone, randow,MsgUtil.YZMBD);
			} catch (ClientException e) {
				e.printStackTrace();
			}
		}
		Jedis jedis =  new Jedis(JEDISPATH,6379,10000);
		jedis.set(phone, randow);
		return R.ok().put(DATA, randow);
	}
	
	private String getRandow(){
		String randow = "";
		 Random random = new Random();
		for(int i = 0; i < 6; i++){
			randow += random.nextInt(10);
		}
		return randow;
	}
	
	@SuppressWarnings("resource")
	public R login(JSONObject json){
		Integer updateType = 0;//是否强制更新
		Integer updateIf = 0;//是否有更新0否1是
		String appPath = "";
		if(json.getJSONObject("data").get("equipmentType") != null){
			Integer equipmentType = json.getJSONObject("data").getInt("equipmentType");
			Integer edition = Integer.parseInt(json.getJSONObject("data").getString("edition").replace(".", ""));
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("equipmentType", equipmentType);
			List<SmartAppEntity> list = smartAppService.queryList(map);
			SmartAppEntity smartApp = new SmartAppEntity();
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				SmartAppEntity smartAppEntity = (SmartAppEntity) iterator.next();
				smartApp = smartAppEntity;
			}
			appPath = smartApp.getEquipmentPath();
			updateType = smartApp.getUpdateType();
			if(edition < smartApp.getEdition()){
				updateIf = 1;
			}
		}
		StudentEntity student = (StudentEntity)JSONObject.toBean(json.getJSONObject("data"), StudentEntity.class);
		StudentEntity user = new StudentEntity();
		if(!student.getStudentNo().matches("^1[3|4|5|7|8][0-9]\\d{4,8}$")){
			user.setStudentNo(student.getStudentNo());
			EntityWrapper<StudentEntity> wrapper = new EntityWrapper<StudentEntity>(user);
			user = this.studentService.selectOne(wrapper);
		}else{
			user.setPhoen(student.getStudentNo());
			EntityWrapper<StudentEntity> wrapper = new EntityWrapper<StudentEntity>(user);
			user = this.studentService.selectOne(wrapper);
		}
		if(user == null){
			return R.error("用户不存在");
		}else{
			if("2".equals(user.getUserType())){
				return R.error("老师账号不能登录学生APP");
			}
			if(user.getPasswordd().equals(new Sha256Hash(student.getPasswordd()).toString())){
				TokenEntity token = tokenService.queryByUserId(new Long(user.getId()));
				String tokening = new Sha256Hash(user.getPasswordd()+new Date()).toString();
				if(token == null){
					JSONObject jso = getAccessToken("https://open.ys7.com/api/lapp/token/get");
					token = new TokenEntity();
					token.setUserId(new Long(user.getId()));
					token.setUpdateTime(new Date());
					token.setToken(tokening);
					token.setExpireTime(new Date(new Long(jso.getJSONObject("data").getString("expireTime"))));
					token.setAccessToken(jso.getJSONObject("data").getString("accessToken"));
					tokenService.save(token);
				}else{
					if(new Date().getTime() > token.getExpireTime().getTime()){
						JSONObject jso = getAccessToken("https://open.ys7.com/api/lapp/token/get");
						token.setExpireTime(new Date(new Long(jso.getJSONObject("data").getString("expireTime"))));
						token.setAccessToken(jso.getJSONObject("data").getString("accessToken"));
					}
					token.setToken(tokening);
					token.setUpdateTime(new Date());
					tokenService.update(token);
				}
				Jedis jedis =  new Jedis(JEDISPATH,6379,10000);
				jedis.set(tokening, tokening);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", user.getId());
				map.put("token", tokening);
				map.put("pic", (user.getPic()== null || "".equals(user.getPic()))  ? "http://guanyukeji-static.oss-cn-hangzhou.aliyuncs.com/business_card_pic/%5D_YEAN74E%5DC5QYA%7DEI%7BQBG5.png":user.getPic());
				map.put("classId", user.getClassId());
				map.put("schoolId", classService.queryObject(user.getClassId()).getSchoolId());
				map.put("studentName", user.getStudentName());
				map.put("studentNo", user.getStudentNo());
				map.put("phone", user.getPhoen());
				map.put("accessToken", token.getAccessToken());
				map.put("updateType", updateType);
				map.put("updateIf", updateIf);
				map.put("appPath", appPath);
				map.put("bindingType", schoolService.queryObject(classService.queryObject(user.getClassId()).getSchoolId()).getBindingType());
				return R.ok().put(DATA, map);
			}else{
				return R.error("密码错误");
			}
		}
	}
	
	public JSONObject getAccessToken(String url) {
	    StringBuffer str = new StringBuffer();
        str.append("appKey=c963be80cbe0421ba77d2cf10fc1b6aa");
        str.append("&appSecret=5befd037504655c2607c45d2a78b4072");
        String param = str.toString();
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        JSONObject json = JSONObject.fromObject(result);
        return json;
	}
	
	private String getJSONObject(HttpServletRequest request){
		InputStream intpu = null;
		try {
			intpu = request.getInputStream();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		BufferedReader reader = new BufferedReader(new InputStreamReader(intpu));   
        StringBuilder sb = new StringBuilder();   
        String line = null;   
        try {   
            while ((line = reader.readLine()) != null) {   
                sb.append(line);   
            }   
        } catch (IOException e) {   
            e.printStackTrace();   
        } finally { 
        }    
        String result = "";
        try {
        	result = new String(sb.toString().getBytes("gbk"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        return result;
	}

}
