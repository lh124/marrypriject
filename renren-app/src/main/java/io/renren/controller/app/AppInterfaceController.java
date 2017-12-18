package io.renren.controller.app;

import io.renren.entity.TokenEntity;
import io.renren.entity.smart.ClassEntity;
import io.renren.entity.smart.ClassInfoEntity;
import io.renren.entity.smart.ClassNoticeEntity;
import io.renren.entity.smart.FreshmanGuideEntity;
import io.renren.entity.smart.IoEntity;
import io.renren.entity.smart.PhotoClassWorkMsgEntity;
import io.renren.entity.smart.PhotoExaminationEntity;
import io.renren.entity.smart.PhotoPicWorkMsgEntity;
import io.renren.entity.smart.PsychologicalCounselingEntity;
import io.renren.entity.smart.SchoolEntity;
import io.renren.entity.smart.SchoolNoticeEntity;
import io.renren.entity.smart.SmartActivitiesEntity;
import io.renren.entity.smart.SmartCoursewareEntity;
import io.renren.entity.smart.SmartLeaveEntity;
import io.renren.entity.smart.SmartVideoDeviceEntity;
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
import io.renren.service.smart.PhotoPicWorkMsgService;
import io.renren.service.smart.PhotoScoreService;
import io.renren.service.smart.PsychologicalCounselingService;
import io.renren.service.smart.SchoolNoticeService;
import io.renren.service.smart.SchoolService;
import io.renren.service.smart.SmartActivitiesService;
import io.renren.service.smart.SmartCoursewareService;
import io.renren.service.smart.SmartLeaveService;
import io.renren.service.smart.SmartVideoDeviceService;
import io.renren.service.smart.SmartWorkService;
import io.renren.service.smart.StudentService;
import io.renren.service.smart.WeixinFunctionImgService;
import io.renren.service.smart.WeixinFunctionService;
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


/**
 * 
 * 
 * @author yuanbichang
 * @email 673290684@qq.com
 * @date 2017-09-28 14:47:08
 */
@RestController
@RequestMapping("appInterface")
@SuppressWarnings("resource")
public class AppInterfaceController {
	
	@Autowired
	private TokenService tokenService;
	@Autowired
	private StudentService studentService;
	@Autowired
	private ClassNoticeService classNoticeService;
	@Autowired
	private SmartWorkService smartWorkService;
	@Autowired
	private ClassService classService;
	@Autowired
	private ClassInfoService classInfoService;
	@Autowired
	private SmartActivitiesService smartActivitiesService;
	@Autowired
	private SchoolNoticeService schoolNoticeService;
	@Autowired
	private FreshmanGuideService freshmanGuideService;
	@Autowired
	private PsychologicalCounselingService psychologicalCounselingService;
	@Autowired
	private PhotoExaminationService photoExaminationService;
	@Autowired
	private PhotoScoreService photoScoreService;
	@Autowired
	private SmartCoursewareService smartCoursewareService;
	@Autowired
	private PhotoClassWorkMsgService photoClassWorkMsgService;
	@Autowired
	private IoService ioService;
	@Autowired
	private SchoolService schoolService;
	@Autowired
	private WeixinFunctionImgService weixinFunctionImgService;
	@Autowired
	private WeixinFunctionService weixinFunctionService;
	
	@Autowired
	private PhotoPicWorkMsgService photoPicWorkMsgService;
	@Autowired
	private SmartVideoDeviceService smartVideoDeviceService;
	@Autowired
	private SmartLeaveService smartLeaveService;
	
	private final static String JEDISPATH = "127.0.0.1";
	private final static String DATA = "data";
	private final static String FILEPATH = "http://guanyukeji-static.oss-cn-hangzhou.aliyuncs.com/";
	
	
	@RequestMapping("/main")
	public R main(HttpServletRequest request){
		String key = request.getParameter("key").replace("&quot;", "\"");
		System.out.println("---"+key);
		JSONObject json = JSONObject.fromObject(key);//用户登录
		String type = json.getString("type");
		if(json.get("token") == null || "".equals(json.get("token")) || "null".equals(json.get("token"))){
			if(type.equals("userLogin")){//用户登录接口
				return login(json);
			}else{
				return R.error("您还没登录了！");
			}
		}else{
			if(getToken(json.getString("token"))){
				if(type.equals("teacherNotice")){
					//老师通知
					return teacherNotice(json.getJSONObject("data"));
				} else if(type.equals("smartWorkList")){
					//作业
					return smartWord(json.getJSONObject("data"));
				} else if(type.equals("getClassId")){
					//班级
					return getClassId(json.getJSONObject("data"));
				} else if(type.equals("classInfo")){
					//班级信息
					return classInfo(json.getJSONObject("data"));
				} else if(type.equals("smartActivites")){
					//竞技活动
					return smartActivites(json.getJSONObject("data"));
				} else if(type.equals("schoolNotice")){
					//学校通知
					return schoolNotice(json.getJSONObject("data"));
				} else if(type.equals("psychological")){
					//心理咨询
					return psychological(json.getJSONObject("data"));
				} else if(type.equals("freshmanGuide")){
					//新生指南
					return freshmanGuide(json.getJSONObject("data"));
				}else if(type.equals("examinationlist")){
					//考试成绩列表
					return examinationlist(json.getJSONObject("data"));
				}else if(type.equals("examinationdetail")){
					//考试成绩详情
					return examinationdetail(json.getJSONObject("data"));
				}else if(type.equals("smartCourseware")){
					//随堂课件
					return smartCourseware(json.getJSONObject("data"));
				}else if(type.equals("photoPicWorkMsg")){
					//班内消息
					return photoPicWorkMsg(json.getJSONObject("data"));
				}else if(type.equals("populationstatistics")){
					//人数统计
					return populationstatistics(json.getJSONObject("data"));
				}else if(type.equals("ioepclist")){
					//签到
					return ioepclist(json.getJSONObject("data"));
				}else if(type.equals("updateName")){
					//修改姓名
					return updateName(json.getJSONObject("data"));
				}else if(type.equals("updatePassword")){
					//修改密码
					return updatePassword(json.getJSONObject("data"));
				}else if(type.equals("teacherNoticeSave")){
					//老师通知保存
					CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
					return teacherNoticeSave(json.getJSONObject("data"),multipartResolver,request);
				}else if(type.equals("smartWorkSave")){
					//作业保存
					CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
					return smartWorkSave(json.getJSONObject("data"),multipartResolver,request);
				}else if(type.equals("schoolNoticeSave")){
					//学校通知保存
					CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
					return schoolNoticeSave(json.getJSONObject("data"),multipartResolver,request);
					
				}else if(type.equals("updateheadportrait")){
					//头像修改
					CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
					return updateheadportrait(json.getJSONObject("data"),multipartResolver,request);
				}else if(type.equals("shouyejiugongge")){
					//首页九宫格
					return shouyejiugongge(json.getJSONObject("data"));
				}else if(type.equals("courseware")){
					//课件上传
					CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
					return courseware(json.getJSONObject("data"),multipartResolver,request);
				}else if(type.equals("voicesave")){
					//语音上传
					CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
					return voicesave(json.getJSONObject("data"),multipartResolver,request);
				}else if(type.equals("classworkmsgsave")){
					//发布消息
					CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
					return classworkmsg(json.getJSONObject("data"),multipartResolver,request);
				}else if(type.equals("getVideoDevice")){
					//获取允许查看的摄像头
					return R.ok().put(DATA, getVideoDevice(json.getInt("userType"),json.getInt("schoolId")));
				}else if(type.equals("getallschool")){
					//学校列表
					return getallschool();
				}else if(type.equals("getallstudent")){
					//通过班级id查询所有的学生
					return getallstudent(json.getJSONObject("data"));
				}else if(type.equals("saveSmartLeave")){
					//通过学生id保存请假记录
					return saveSmartLeave(json.getJSONObject("data"));
				}else if(type.equals("getStudentIdLeave")){
					//通过学生id查询自己的请假记录
					return getStudentIdLeave(json.getJSONObject("data"));
				}else if(type.equals("getClassIdLeave")){
//					//通过班级id查询当天请假人数
//					return getClassIdLeave(json.getJSONObject("data"));
				}else if(type.equals("getSmartLeaveDetail")){
					//通过请假条id查询请假条详情
					return getSmartLeaveDetail(json.getJSONObject("data"));
				}else if(type.equals("getStudentzaixiao")){
//					//通过班级id查询在校或者不在校的学生的学生
//					return getStudentzaixiao(json.getJSONObject("data"));
				}else if(type.equals("sendMsg")){
					//通过手机号码发送验证码
					return sendMsg(json.getJSONObject("data"));
				}else if(type.equals("updatePhone")){
					//绑定或修改手机号
					return updatePhone(json.getJSONObject("data"));
				}else if(type.equals("updateLeaveType")){
					//老师是否同意请假条
					return updateLeaveType(json.getJSONObject("data"));
				}else if(type.equals("updatepasswrodtophone")){
					//忘记密码：通过手机号找回密码
					return updatepasswrodtophone(json.getJSONObject("data"));
				}else if(type.equals("getAccessToken")){
					//获取设备的AccessToken
					return getAccessToken(json.getJSONObject("data"));
				}
			}else{
				return R.error("您的账号已在其它设备上登录，请重新登录。");
			}
		}
		return R.ok();
	}
	
	private R getAccessToken(JSONObject json){
		TokenEntity token = tokenService.queryByUserId(new Long(json.getInt("userId")));
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
	
	private R updatepasswrodtophone(JSONObject json){
		String  phone = json.getString("phone");
		StudentEntity user = new StudentEntity();
		user.setPhoen(phone);
		EntityWrapper<StudentEntity> wrapper = new EntityWrapper<StudentEntity>(user);
		DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
		user = this.studentService.selectOne(wrapper);
		DbContextHolder.setDbType(DBTypeEnum.MYSQL);
		if(user == null){
			return R.error("该号码暂未绑定");
		}else{
			user.setPasswordd(new Sha256Hash("000000").toHex());
			studentService.update(user);
			try {
				MsgUtil.sendSms(phone, "000000",MsgUtil.YZMPASSWORD);
			} catch (ClientException e) {
				e.printStackTrace();
			}
		}
		return R.ok().put(DATA, "密码为：000000");
	}
	
	private R updateLeaveType(JSONObject json){
		Integer id = Integer.parseInt(json.getString("id"));
		SmartLeaveEntity leave = new SmartLeaveEntity();
		leave.setId(id);
		leave.setStates(json.getString("states")); 
		leave.setBeizhu(json.getString("beizhu"));
		smartLeaveService.update(leave);
		return R.ok().put(DATA, smartLeaveService.queryObject(id));
	}
	
	private R updatePhone(JSONObject json){
		String phone = json.getString("phone");
		Integer userId = json.getInt("userId");
		StudentEntity studnet = new StudentEntity();
		studnet.setId(userId);
		studnet.setPhoen(phone);
		studentService.update(studnet);
		return R.ok();
	}
	
	private R sendMsg(JSONObject json){
		String randow = getRandow();
		String phone = json.getString("phone");
		if(phone == null || "null".equals(phone) || "".equals(phone)){
			return R.error("手机号码不能为空");
		}else if(!phone.matches("^1[3|4|5|7|8][0-9]\\d{4,8}$")){
			return R.error("手机号码有误，请重新输入");
		}else{
			try {
				MsgUtil.sendSms(phone, randow,MsgUtil.YZMBD);
			} catch (ClientException e) {
				e.printStackTrace();
			}
		}
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
	
	private List<StudentEntity> getStudentzaixiao(Integer classId,Integer type){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("classId", classId);
		if(type == 1){
			//在校学生
			map.put("ioType", "进");
		}else if(type == 2){
			//不在校
			map.put("ioType", "出");
		}
		DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
		List<StudentEntity> list = studentService.queryListStudent(map);
		DbContextHolder.setDbType(DBTypeEnum.MYSQL);
		return list;
	}
	
	private R getSmartLeaveDetail(JSONObject json){
		Map<String, Object> map = new HashMap<String, Object>();
		SmartLeaveEntity leave = smartLeaveService.queryObject(json.getInt("id"));
		StudentEntity student = new StudentEntity();
		if(leave != null){
			student = studentService.queryObject(leave.getUserid());
		}
		map.put("leave", leave);
		map.put("student", student);
		return R.ok().put(DATA, map);
	}
	
	@SuppressWarnings("rawtypes")
	private List<StudentEntity> getClassIdLeave(Integer classId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("offset", 0);
		map.put("limit", 100);
		map.put("userId", null);
		map.put("classId", classId);
		List<StudentEntity> list = new ArrayList<StudentEntity>();
		List<SmartLeaveEntity> leaveList = smartLeaveService.queryList(map);
		for (Iterator iterator = leaveList.iterator(); iterator.hasNext();) {
			SmartLeaveEntity smartLeaveEntity = (SmartLeaveEntity) iterator.next();
			StudentEntity student = studentService.queryObject(smartLeaveEntity.getUserid());
			student.setId(smartLeaveEntity.getId());//将请假条的id设置给学生id便于后面查询请假条详情
			list.add(student);
		}
		return list;
	}
	
	private R getStudentIdLeave(JSONObject json){
		Integer id = json.getInt("userId");
		Map<String, Object> map = getMap(json);
		map.put("userId", id);
		map.put("classId", null);
		return R.ok().put(DATA, smartLeaveService.queryList(map));
	}
	
	private R saveSmartLeave(JSONObject json){
		SmartLeaveEntity smartLeave = new SmartLeaveEntity();
		Integer id = json.getInt("userId");
		StudentEntity student = studentService.queryObject(id);
		smartLeave.setUserid(id);
		smartLeave.setContent(json.getString("content"));
		try {
			smartLeave.setStartdate(new SimpleDateFormat("yyyy-MM-dd").parse(json.getString("startDate")));
			smartLeave.setEnddate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(json.getString("endDate")+" 23:59:59"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		smartLeave.setCalssid(student.getClassId());
		smartLeaveService.save(smartLeave);
		return R.ok();
	}
	
	private R getallstudent(JSONObject json){
		Integer classId = json.getInt("classId");
		DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("limit", 100);
		map.put("begin", 0);
		map.put("classId", classId);
		map.put("schoolId", null);
		map.put("userType", 1);
		List<StudentEntity> list = studentService.queryList(map);
		DbContextHolder.setDbType(DBTypeEnum.MYSQL);
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("allstudent", list);//班级所有学生
		m.put("zaixiaostudent", getStudentzaixiao(classId, 1));//在校学生
		m.put("nozaixiaostudent", getStudentzaixiao(classId, 2));//不在校学生
		m.put("leavestudent", getClassIdLeave(classId));//请假的学生
		return R.ok().put(DATA, m);
	}
	
	private R getallschool(){
		DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("limit", 10);
		map.put("begin", 0);
		List<SchoolEntity> list = schoolService.queryList(map);
		DbContextHolder.setDbType(DBTypeEnum.MYSQL);
		return R.ok().put(DATA, list);
	}
	
	private List<SmartVideoDeviceEntity> getVideoDevice(Integer userType,Integer schoolId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sidx", null);
		map.put("order", null);
		map.put("offset", 0);
		map.put("limit", 30);
		if(userType == 1){
			map.put("teacherSee", null);
			map.put("studentSee", 1);
		}else if(userType == 2){
			map.put("teacherSee", 1);
			map.put("studentSee", null);
		}
		map.put("schoolId", schoolId);
		List<SmartVideoDeviceEntity> list = smartVideoDeviceService.queryList(map);
		return list;
	}
	
	private R classworkmsg(JSONObject json,CommonsMultipartResolver multipartResolver,HttpServletRequest request){
		PhotoClassWorkMsgEntity pcwme = new PhotoClassWorkMsgEntity();
		InputStream[] is;
		try {
			is = uploadfile(multipartResolver, request);
			pcwme.setClassId(json.getLong("classId"));
			pcwme.setUserId(json.getLong("userId"));
			pcwme.setContent(json.getString("content"));
			pcwme.setGmtCreate(new Date());
			pcwme.setStatus(1);
			if(uploadmedio(multipartResolver, request) != null){
				pcwme.setVoice(FILEPATH+"smart_medio/"+OssUploadUtil.uploadObject2OSS2(uploadmedio(multipartResolver, request)[0], "smart_medio/"));
			}
			photoClassWorkMsgService.insert(pcwme);
			if(is != null){
				for (int i = 0; i < is.length; i++) {
					PhotoPicWorkMsgEntity ppwme = new PhotoPicWorkMsgEntity();
					ppwme.setGmtCreate(new Date());
					ppwme.setRelatedId(pcwme.getId());
					ppwme.setPicType(1);
					ppwme.setName(FILEPATH+"smart_msg_pic/"+OssUploadUtil.uploadObject2OSS(is[0], "smart_msg_pic/"));
					photoPicWorkMsgService.save(ppwme);
				}
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return R.ok();
	}
	
	private R voicesave(JSONObject json,CommonsMultipartResolver multipartResolver,HttpServletRequest request){
		PhotoClassWorkMsgEntity pcwme = new PhotoClassWorkMsgEntity();
		InputStream[] is;
		try {
			is = uploadfile(multipartResolver, request);
			if(is != null){
				pcwme.setVoice(FILEPATH+"smart_courseware_pic/"+OssUploadUtil.uploadObject2OSS(is[0], "smart_courseware_pic/"));
			}
			pcwme.setClassId(json.getLong("classId"));
			pcwme.setUserId(json.getLong("userId"));
			pcwme.setGmtCreate(new Date());
			pcwme.setStatus(1);
			photoClassWorkMsgService.save(pcwme);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return R.ok();
	}
	
	private R courseware(JSONObject json,CommonsMultipartResolver multipartResolver,HttpServletRequest request){
		SmartCoursewareEntity courseware = new SmartCoursewareEntity();
		InputStream[] is;
		try {
			is = uploadfile(multipartResolver, request);
			if(is != null){
				courseware.setPic(FILEPATH+"smart_courseware_pic/"+OssUploadUtil.uploadObject2OSS(is[0], "smart_courseware_pic/"));
			}
			courseware.setClassid(json.getInt("classId"));
			courseware.setName(json.getString("title"));
			courseware.setType(json.getInt("coursewareType"));
			courseware.setCreatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			smartCoursewareService.save(courseware);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return R.ok();
	}
	
	@SuppressWarnings("rawtypes")
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
	
	private boolean getToken(String token){
		if(new Jedis(JEDISPATH,6379,30000).get(token) == null){
			TokenEntity tokens = tokenService.queryByToken(token);
			if(tokens == null){
				return false;
			}else{
				Jedis jedis =  new Jedis(JEDISPATH,6379,30000);
				jedis.set(tokens.getToken(), tokens.getToken());
				return true;
			}
		}else{
			return true;
		}
	}
	
	public R updateheadportrait(JSONObject json,CommonsMultipartResolver multipartResolver,HttpServletRequest request){
		try {
			StudentEntity student = new StudentEntity();
			student.setId(Integer.parseInt(json.getString("studentId")));
			InputStream[] is = uploadfile(multipartResolver, request);
			if(is != null){
				student.setPic(FILEPATH+"head_img/"+OssUploadUtil.uploadObject2OSS(is[0], "head_img/"));
				studentService.update(student);
				return R.ok().put(DATA, studentService.queryObject(student.getId()));
			}else{
				return R.ok().put(DATA, studentService.queryObject(student.getId()));
			}
		} catch (Exception e) {
			return R.error("失败");
		}
	}
	
	public R schoolNoticeSave(JSONObject json,CommonsMultipartResolver multipartResolver,HttpServletRequest request){
		try {
			SchoolNoticeEntity schoolNotice = new SchoolNoticeEntity();
			schoolNotice.setSchoolid(Integer.parseInt(json.getString("schoolId")));
			schoolNotice.setContent(json.getString("content"));
			schoolNotice.setTitle(json.getString("title"));
			schoolNotice.setCreatetime(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			InputStream[] is = uploadfile(multipartResolver, request);
			if(is != null){
				schoolNotice.setNoticepic(FILEPATH+"smart_notice_pic/"+OssUploadUtil.uploadObject2OSS(is[0], "smart_notice_pic/"));
			}
			schoolNoticeService.save(schoolNotice);
		} catch (Exception e) {
			return R.error("失败");
		}
		return R.ok();
	}
	
	public R smartWorkSave(JSONObject json,CommonsMultipartResolver multipartResolver,HttpServletRequest request){
		try {
			SmartWorkEntity smartWork = new SmartWorkEntity();
			smartWork.setClassid(Integer.parseInt(json.getString("classId")));
			smartWork.setContent(json.getString("content"));
			smartWork.setName(json.getString("title"));
			smartWork.setType(1);
			smartWork.setCreatetime(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			InputStream[] is = uploadfile(multipartResolver, request);
			if(is != null){
				smartWork.setPic(FILEPATH+"smart_work_pic/"+OssUploadUtil.uploadObject2OSS(is[0], "smart_work_pic/"));
			}
			smartWorkService.save(smartWork);
		} catch (Exception e) {
			return R.error("失败");
		}
		return R.ok();
	}
	
	public R teacherNoticeSave(JSONObject json,CommonsMultipartResolver multipartResolver,HttpServletRequest request){
		try {
			ClassNoticeEntity classNotice = new ClassNoticeEntity();
			classNotice.setClassId(json.getString("classId") + "");
			classNotice.setContent(json.getString("content"));
			classNotice.setNoticeType(json.getString("noticeType"));
			classNotice.setTitle(json.getString("title"));
			classNotice.setCreatetime(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			InputStream[] is = uploadfile(multipartResolver, request);
			if(is != null){
				classNotice.setNoticepic(FILEPATH+"smart_notice_pic/"+OssUploadUtil.uploadObject2OSS(is[0], "smart_notice_pic/"));
			}
			classNoticeService.insert(classNotice);
		} catch (Exception e) {
			return R.error("失败");
		}
		return R.ok();
	}
	
	public R updateName(JSONObject json){
		DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
		StudentEntity studnet = studentService.queryObject(Integer.parseInt(json.getString("studentId")));
		if(studnet != null){
			studnet.setStudentName(json.getString("studentName"));
			studentService.update(studnet);
		}
		StudentEntity student = studentService.queryObject(studnet.getId());
		DbContextHolder.setDbType(DBTypeEnum.MYSQL);
		return R.ok().put(DATA, student);
	}
	
	public R updatePassword(JSONObject json){
		DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
		StudentEntity student = studentService.queryObject(Integer.parseInt(json.getString("studentId")));
		if(student != null){
			if(!student.getPasswordd().equals(new Sha256Hash(json.getString("oldPassword")).toString())){
				return R.error("原密码错误");
			}else{
				student.setPasswordd(new Sha256Hash(json.getString("newPassword")).toString());
				studentService.update(student);
			}
		}
		DbContextHolder.setDbType(DBTypeEnum.MYSQL);
		return R.ok("密码修改成功");
	}
	
	public R ioepclist(JSONObject json){
		Map<String, Object> map = getMap(json);
		Integer id = Integer.parseInt(json.getString("studentId"));
		map.put("studentId", id);
		DbContextHolder.setDbType(DBTypeEnum.SQLSERVER2);
		List<IoEntity> list = ioService.queryList(map);
		DbContextHolder.setDbType(DBTypeEnum.MYSQL);
		Map<String, Object> m = new HashMap<String, Object>();
		StudentEntity student = studentService.queryObject(id);
		m.put("list", list);
		m.put("student", student);
		ClassEntity classEntity = classService.queryObject(student.getClassId());
		if(Integer.parseInt(student.getUserType()) == 1){
			m.put("videoDeviceList", getVideoDevice(Integer.parseInt(student.getUserType()), classEntity.getSchoolId()));
		}else if(Integer.parseInt(student.getUserType()) == 2){
			m.put("videoDeviceList", getVideoDevice(Integer.parseInt(student.getUserType()), student.getSchoolId()));
		}
		return R.ok().put(DATA, m);
	}
	
	@SuppressWarnings("rawtypes")
	public R populationstatistics(JSONObject json){
		Map<String, Object> map = getMap(json);
		map.put("begin", 0);
		map.put("limit", 200);
		map.put("classId", json.getString("classId"));
		DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
		List<StudentEntity> list = studentService.queryList(map);//通过班级id查询班上所有学生
		List<Object> list2 = new ArrayList<Object>();//不在校学生
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			StudentEntity studentEntity = (StudentEntity) iterator.next();
			IoEntity ioentity = ioService.queryObjectName(studentEntity.getId());//通过学生id查询所有出入校记录
			if(ioentity != null){
				if(ioentity.getIoType().equals("出")){
					Map<String, Object> m = new HashMap<String, Object>();
					m.put("name", studentEntity.getStudentName());
					m.put("date", ioentity.getIoDate());
					list2.add(m);
				}
			}else{
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("name", studentEntity.getStudentName());
				m.put("date", new Date());
				list2.add(m);
			}
		}
		DbContextHolder.setDbType(DBTypeEnum.MYSQL);
		return R.ok().put(DATA, list2);
	}
	
	public R photoPicWorkMsg(JSONObject json){
		Map<String, Object> map = getMap(json);
		map.put("classId", json.getString("classId"));
		return R.ok().put(DATA, photoClassWorkMsgService.queryList(map));
	}
	
	@SuppressWarnings("rawtypes")
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
			List<SmartCoursewareEntity> lists = new ArrayList<SmartCoursewareEntity>();
			String[] ids = map.get("classid").toString().split(",");
			for (int i = 0; i < ids.length; i++) {
				if(!ids[i].equals("")){
					map.put("classid",ids[i] );
					List<SmartCoursewareEntity> smartCoursewareList = smartCoursewareService.queryList(map);
					for (Iterator iterator = smartCoursewareList.iterator(); iterator.hasNext();) {
						SmartCoursewareEntity smartCoursewareEntity = (SmartCoursewareEntity) iterator.next();
						lists.add(smartCoursewareEntity);
					}
				}
			}
		}
		return R.ok().put(DATA, list);
	}
	
	public R examinationdetail(JSONObject json){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sidx", null);
		map.put("offset", 0);
		map.put("limit", 100);
		map.put("examinationId", json.getString("examinationId"));
		map.put("userId", json.getString("userId"));
		return R.ok().put(DATA, photoScoreService.queryList(map));
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
	
	public R freshmanGuide(JSONObject json){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sidx", null);
		map.put("offset", 0);
		map.put("limit", 100);
		map.put("schoolId", json.getString("schoolId"));
		List<FreshmanGuideEntity> freshmanGuideList = freshmanGuideService.queryList(map);
		return R.ok().put(DATA, freshmanGuideList);
	}
	
	public R psychological(JSONObject json){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sidx", null);
		map.put("offset", 0);
		map.put("limit", 100);
		map.put("schoolId", json.getString("schoolId"));
		List<PsychologicalCounselingEntity> psychologicalCounselingList = psychologicalCounselingService.queryList(map);
		return R.ok().put(DATA, psychologicalCounselingList);
	}
	
	public R schoolNotice(JSONObject json){
		Map<String, Object> map = getMap(json);
		map.put("schoolid", json.getString("schoolId"));
		List<SchoolNoticeEntity> schoolNoticeList = schoolNoticeService.queryList(map);
		return R.ok().put(DATA, schoolNoticeList);
	}
	
	public R smartActivites(JSONObject json){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("schoolid", json.getString("schoolId"));
		map.put("sidx", null);
		map.put("offset", 0);
		map.put("limit", 100);
		List<SmartActivitiesEntity> smartActivitiesList = smartActivitiesService.queryList(map);
		return R.ok().put(DATA, smartActivitiesList);
	}
	
	@SuppressWarnings("rawtypes")
	public R getClassId(JSONObject json){
		Integer userId = json.getInt("userId");
		List<ClassEntity> list = new ArrayList<ClassEntity>();
		DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
		StudentEntity student = studentService.queryObject(userId);
		if(student == null){
			return R.error("用户登录异常");
		}else{
			if("1".equals(student.getUserType())){
				list.add(classService.queryObject(student.getClassId()));
			}else if("2".equals(student.getUserType())){
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("userId", student.getId());
				List<ClassInfoEntity> infolist = classInfoService.queryListtongji(params);
				for (Iterator iterator = infolist.iterator(); iterator.hasNext();) {
					ClassInfoEntity classInfoEntity = (ClassInfoEntity) iterator.next();
					list.add(classService.queryObject(classInfoEntity.getClassid()));
				}
			}
		}
		DbContextHolder.setDbType(DBTypeEnum.MYSQL);
		return R.ok().put("list", list);
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
		DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
		int studenttotal = this.studentService.queryList(map).size();
		DbContextHolder.setDbType(DBTypeEnum.MYSQL);
		
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
		List<ClassNoticeEntity> classNoticeList = classNoticeService.queryList(map);
		return R.ok().put(DATA, classNoticeList);
	}
	
	private Map<String, Object> getMap(JSONObject json){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("offset", (Integer.parseInt(json.getString("offset"))-1) * Integer.parseInt(json.getString("limit")));
		map.put("limit", Integer.parseInt(json.getString("offset")) * Integer.parseInt(json.getString("limit")));
		map.put("sidx", "");
		map.put("order", "");
		return map;
	}
	
	public R login(JSONObject json){
		StudentEntity student = (StudentEntity)JSONObject.toBean(json.getJSONObject("data"), StudentEntity.class);
		
		StudentEntity user = new StudentEntity();
		user.setStudentNo(student.getStudentNo());
		EntityWrapper<StudentEntity> wrapper = new EntityWrapper<StudentEntity>(user);
		DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
		user = this.studentService.selectOne(wrapper);
		DbContextHolder.setDbType(DBTypeEnum.MYSQL);
		if(user == null){
			return R.error("用户不存在");
		}else{
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
					token.setToken(tokening);
					token.setUpdateTime(new Date());
					tokenService.update(token);
				}
				Jedis jedis =  new Jedis(JEDISPATH,6379,30000);
				jedis.set(tokening, tokening);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", user.getId());
				map.put("token", tokening);
				map.put("pic", user.getPic());
				if(user.getUserType().equals("1")){
					map.put("classId", user.getClassId());
					map.put("schoolId", classService.queryObject(user.getClassId()).getSchoolId());
				}else{
					map.put("schoolId", user.getSchoolId());
				}
				map.put("userType", user.getUserType());
				map.put("studentName", user.getStudentName());
				map.put("studentNo", user.getStudentNo());
				map.put("phone", user.getPhoen());
				map.put("accessToken", token.getAccessToken());
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
	
	//语音文件上传
	public InputStream[] uploadmedio(CommonsMultipartResolver multipartResolver,HttpServletRequest request) throws IllegalStateException, IOException{
				InputStream[] is = null;
				//判断 request 是否有文件上传,即多部分请求  
		        if(multipartResolver.isMultipart(request)){
		        	MultipartHttpServletRequest m= (MultipartHttpServletRequest)request;
		        	List<MultipartFile> detail = new ArrayList<MultipartFile>();
		        	if(m.getFiles("medio").size() != 0){
		        		detail = m.getFiles("medio");
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
}
