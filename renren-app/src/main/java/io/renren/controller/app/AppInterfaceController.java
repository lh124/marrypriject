package io.renren.controller.app;

import io.renren.entity.TokenEntity;
import io.renren.entity.smart.ClassEntity;
import io.renren.entity.smart.ClassInfoEntity;
import io.renren.entity.smart.ClassNoticeEntity;
import io.renren.entity.smart.FreshmanGuideEntity;
import io.renren.entity.smart.IoEntity;
import io.renren.entity.smart.PhotoExaminationEntity;
import io.renren.entity.smart.PsychologicalCounselingEntity;
import io.renren.entity.smart.SchoolNoticeEntity;
import io.renren.entity.smart.SmartActivitiesEntity;
import io.renren.entity.smart.SmartCoursewareEntity;
import io.renren.entity.smart.SmartWorkEntity;
import io.renren.entity.smart.StudentEntity;
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
import io.renren.service.smart.SmartActivitiesService;
import io.renren.service.smart.SmartCoursewareService;
import io.renren.service.smart.SmartWorkService;
import io.renren.service.smart.StudentService;
import io.renren.util.OssUploadUtil;
import io.renren.utils.R;
import io.renren.utils.dataSource.DBTypeEnum;
import io.renren.utils.dataSource.DbContextHolder;

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

import net.sf.json.JSONObject;

import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

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
@SuppressWarnings("rawtypes")
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
	
	private final static String DATA = "data";
	private final static String FILEPATH = "http://guanyukeji-static.oss-cn-hangzhou.aliyuncs.com/";
	
	
	@RequestMapping("/main")
	public R main(HttpServletRequest request){
		String key = request.getParameter("key").replace("&quot;", "\"");
		JSONObject json = JSONObject.fromObject(key);//用户登录
		String type = json.getString("type");
		if(json.get("token") == null){
			if(type.equals("userLogin")){//用户登录接口
				return login(json);
			}else{
				return R.error("您还没登录了！");
			}
		}else{
			if(tokenService.queryByToken(json.getString("token")) != null){
				if(type.equals("teacherNotice")){//老师通知
					return teacherNotice(json.getJSONObject("data"));
				} else if(type.equals("smartWorkList")){//作业
					return smartWord(json.getJSONObject("data"));
				} else if(type.equals("getClassId")){//班级
					return getClassId(json.getJSONObject("data"));
				} else if(type.equals("classInfo")){//班级信息
					return classInfo(json.getJSONObject("data"));
				} else if(type.equals("smartActivites")){//竞技活动
					return smartActivites(json.getJSONObject("data"));
				} else if(type.equals("schoolNotice")){//学校通知
					return schoolNotice(json.getJSONObject("data"));
				} else if(type.equals("psychological")){//心理咨询
					return psychological(json.getJSONObject("data"));
				} else if(type.equals("freshmanGuide")){//新生指南
					return freshmanGuide(json.getJSONObject("data"));
				}else if(type.equals("examinationlist")){//考试成绩列表
					return examinationlist(json.getJSONObject("data"));
				}else if(type.equals("examinationdetail")){//考试成绩详情
					return examinationdetail(json.getJSONObject("data"));
				}else if(type.equals("smartCourseware")){//随堂课件
					return smartCourseware(json.getJSONObject("data"));
				}else if(type.equals("photoPicWorkMsg")){//班内消息
					return photoPicWorkMsg(json.getJSONObject("data"));
				}else if(type.equals("populationstatistics")){//人数统计
					return populationstatistics(json.getJSONObject("data"));
				}else if(type.equals("ioepclist")){//签到
					return ioepclist(json.getJSONObject("data"));
				}else if(type.equals("updateName")){//修改姓名
					return updateName(json.getJSONObject("data"));
				}else if(type.equals("updatePassword")){//修改密码
					return updatePassword(json.getJSONObject("data"));
				}else if(type.equals("teacherNoticeSave")){//老师通知保存
					CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
					return teacherNoticeSave(json.getJSONObject("data"),multipartResolver,request);
				}else if(type.equals("smartWorkSave")){//作业保存
					CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
					return smartWorkSave(json.getJSONObject("data"),multipartResolver,request);
				}else if(type.equals("schoolNoticeSave")){//学校通知保存
					CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
					return schoolNoticeSave(json.getJSONObject("data"),multipartResolver,request);
				}
			}else{
				return R.error("您的账号已在其它设备上登录，请重新登录。");
			}
		}
		return R.ok();
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
		map.put("studentId", json.getString("studentId"));
		DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
		List<IoEntity> list = ioService.queryList(map);
		DbContextHolder.setDbType(DBTypeEnum.MYSQL);
		return R.ok().put(DATA, list);
	}
	
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
	
	public R getClassId(JSONObject json){
		return R.ok().put(DATA, classService.queryObject(Integer.parseInt(json.getString("classId"))));
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
					token = new TokenEntity();
					token.setUserId(new Long(user.getId()));
					token.setUpdateTime(new Date());
					token.setExpireTime(new Date());
					token.setToken(tokening);
					tokenService.save(token);
				}else{
					token.setToken(tokening);
					token.setUpdateTime(new Date());
					token.setExpireTime(new Date());
					tokenService.update(token);
				}
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", user.getId());
				map.put("token", tokening);
				map.put("pic", user.getPic());
				map.put("classId", user.getClassId());
				map.put("userType", user.getUserType());
				map.put("schoolId", classService.queryObject(user.getClassId()).getSchoolId());
				map.put("studentName", user.getStudentName());
				return R.ok().put(DATA, map);
			}else{
				return R.error("密码错误");
			}
		}
	}
	
	//文件上传
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
	
}
