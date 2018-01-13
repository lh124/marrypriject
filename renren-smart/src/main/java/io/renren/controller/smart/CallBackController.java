package io.renren.controller.smart;

import io.renren.annotation.IgnoreAuth;
import io.renren.constant.ControllerConstant;
import io.renren.entity.app.SmartAppEntity;
import io.renren.entity.smart.ClassNoticeEntity;
import io.renren.entity.smart.FreshmanGuideEntity;
import io.renren.entity.smart.PhotoClassWorkMsgEntity;
import io.renren.entity.smart.PhotoPicWorkMsgEntity;
import io.renren.entity.smart.PsychologicalCounselingEntity;
import io.renren.entity.smart.SchoolNoticeEntity;
import io.renren.entity.smart.SmartActivitiesEntity;
import io.renren.entity.smart.SmartCoursewareEntity;
import io.renren.entity.smart.SmartWorkEntity;
import io.renren.entity.smart.StudentEntity;
import io.renren.entity.smart.WeixinFunctionEntity;
import io.renren.entity.smart.WeixinFunctionImgEntity;
import io.renren.enums.TypeEnum;
import io.renren.service.smart.ClassNoticeService;
import io.renren.service.smart.FreshmanGuideService;
import io.renren.service.smart.PhotoClassWorkMsgService;
import io.renren.service.smart.PhotoPicWorkMsgService;
import io.renren.service.smart.PsychologicalCounselingService;
import io.renren.service.smart.SchoolNoticeService;
import io.renren.service.smart.SmartActivitiesService;
import io.renren.service.smart.SmartAppService;
import io.renren.service.smart.SmartCoursewareService;
import io.renren.service.smart.SmartWorkService;
import io.renren.service.smart.StudentService;
import io.renren.service.smart.WeixinFunctionImgService;
import io.renren.service.smart.WeixinFunctionService;
import io.renren.utils.OssCallBackUtil;
import io.renren.utils.dataSource.DBTypeEnum;
import io.renren.utils.dataSource.DbContextHolder;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/smart/callBack/")
public class CallBackController {

	// 日志操作
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private PhotoPicWorkMsgService photoPicWorkMsgService;
	
	@Autowired
	private PhotoClassWorkMsgService photoClassWorkMsgService;
	
	@Autowired
	private StudentService studentService;
	
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
	private WeixinFunctionImgService weixinFunctionImgService;
	@Autowired
	private WeixinFunctionService weixinFunctionService;
	@Autowired
	private SmartAppService smartAppService;
	
	@ResponseBody
	@RequestMapping(value="/msgPic")
	@IgnoreAuth
	public JSONObject callBace(HttpServletRequest request, HttpServletResponse response) throws Exception{
		JSONObject jsona = OssCallBackUtil.analyzeCallBackData(request);
		JSONObject json = new JSONObject();
		if (jsona.getBoolean(OssCallBackUtil.VERIFY))
		{
			
			// 返回阿里云通知信息
			json.element("Status", "OK");
			response.setStatus(HttpServletResponse.SC_OK);
			response.addHeader("Content-Length", String.valueOf(json.toString().length()));
			
			// 从json中获取响应的数据
			
			String fileName = jsona.containsKey("filename") ? jsona.getString("filename") : null;
			String size = jsona.containsKey("size") ? jsona.getString("size") : null;
			Integer type = jsona.containsKey("type") && jsona.getString("type") != null ? 
					jsona.getInt("type") : null;
			// 异常捕获
			try{
				if (type != null && fileName != null && size != null){
					// 班级消息图片
					if (type.equals(TypeEnum.PHOTO_SMART_MSG_PIC.getType())){
						classMessagePic(jsona);
						return json;
					}
					// 学生头像上传
					if (type.equals(TypeEnum.PHOTO_SMART_HEAD_PIC.getType())){
						studentHeadPic(jsona);
						return json;
					}
					// 学校通知上传
					if (type.equals(TypeEnum.PHOTO_SMART_NOTICE_PIC.getType())){
						schoolNoticePic(jsona);
						return json;
					}
					// 班级通知上传
					if (type.equals(TypeEnum.PHOTO_SMART_CLASSNOTICE_PIC.getType())){
						classNoticePic(jsona);
						return json;
					}
					// 班级通知上传（微信端上传）
					if (type.equals(TypeEnum.PHOTO_SMART_PHONE_NOTICE_PIC.getType())){
						classNoticePicweixin(jsona);
						return json;
					}
					// 新生指南上传
					if (type.equals(TypeEnum.PHOTO_SMART_FRESHMANGUIDE_PIC.getType())){
						freshmanGuidePic(jsona);
						return json;
					}
					// 心理咨询上传
					if (type.equals(TypeEnum.PHOTO_SMART_PSYCHOLOGICAL_PIC.getType())){
						psychologicalPic(jsona);
						return json;
					}
					// 竞技活动上传
					if (type.equals(TypeEnum.PHOTO_SMART_ACTIVITE_PIC.getType())){
						activityPic(jsona);
						return json;
					}
					// 作业上传
					if (type.equals(TypeEnum.PHOTO_SMART_WORK_PIC.getType())){
						workPic(jsona);
						return json;
					}
					// 随堂课件上传
					if (type.equals(TypeEnum.PHOTO_SMART_COURSEWARE_PIC.getType())){
						coursewarePic(jsona);
						return json;
					}
					// 语音上传
					if (type.equals(TypeEnum.PHOTO_SMART_VIDEO_PIC.getType())){
						uploadvoideo(jsona);
						return json;
					}
					// 图标上传
					if (type.equals(TypeEnum.SMART_TUBIAO_PIC.getType())){
						uploadtubiao(jsona);
						return json;
					}
					// APP软件上传
					if (type.equals(TypeEnum.SMART_APP_PATH.getType())){
						smartAppUplown(jsona);
						return json;
					}
				}
				
			} catch(Exception e){
				e.printStackTrace();
				logger.error("回调异常", e);
				logger.error(jsona.toString());
			} 
			return json;
		} else {
			json.element("Status", "verdify not ok");
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.addHeader("Content-Length", String.valueOf(json.toString().length()));
			return json;
		}
	}
	
	/**
	 * APP软件上传
	 * @param json
	 */
	private void smartAppUplown(JSONObject json){
		Integer id = json.containsKey("id") ? json.getInt("id") : null;
		if (id != null) {
			SmartAppEntity smartAppEntity = smartAppService.queryObject(id);
			if(smartAppEntity != null){
				smartAppEntity.setEquipmentPath(ControllerConstant.CDN_URL + json.getString("filename"));
				smartAppService.update(smartAppEntity);
			}
		}
	}
	
	/**
	 * 语音上传
	 * @param json
	 */
	private void uploadtubiao(JSONObject json){
		Long id = json.containsKey("id") ? json.getLong("id") : null;
		Long type2 = json.containsKey("type2") ? json.getLong("type2") : null;
		if (id != null) {
			if(type2 != null){
				if(type2 == 2){
					WeixinFunctionImgEntity weixinFunctionImgEntity = this.weixinFunctionImgService.selectById(id);
					if (weixinFunctionImgEntity != null) {
						weixinFunctionImgEntity.setPic(ControllerConstant.CDN_URL + json.getString("filename"));
						this.weixinFunctionImgService.update(weixinFunctionImgEntity);
					} else {
						logger.error("图标回掉失败");
						logger.error(json.toString());
					}
				}else if(type2 == 1){
					WeixinFunctionEntity  weixinFunctionEntity = this.weixinFunctionService.selectById(id);
					if(weixinFunctionEntity != null){
						weixinFunctionEntity.setPic(ControllerConstant.CDN_URL + json.getString("filename"));
						this.weixinFunctionService.update(weixinFunctionEntity);
					} else {
						logger.error("图标回掉失败");
						logger.error(json.toString());
					}
				}
			}
		}
	}
	
	/**
	 * 语音上传
	 * @param json
	 */
	private void uploadvoideo(JSONObject json){
		PhotoClassWorkMsgEntity photoClassWorkMsg = new PhotoClassWorkMsgEntity();
		photoClassWorkMsg.setClassId(Long.parseLong(json.getString("classId")));
		photoClassWorkMsg.setUserId(Long.parseLong(json.getString("userId")));
		photoClassWorkMsg.setVoice(ControllerConstant.CDN_URL + json.getString("filename"));
		photoClassWorkMsg.setGmtCreate(new Date());
		photoClassWorkMsgService.insert(photoClassWorkMsg);
	}
	
	/**
	 * 随堂课件上传
	 * @param json
	 */
	private void coursewarePic(JSONObject json){
		//信息图片
    	Long id = json.containsKey("id") ? json.getLong("id") : null;
		if (id != null) {
			SmartCoursewareEntity smartCourseware = this.smartCoursewareService.selectById(id);
			if (smartCourseware != null) {
				smartCourseware.setPic(ControllerConstant.CDN_URL + json.getString("filename"));
				this.smartCoursewareService.update(smartCourseware);
			} else {
				logger.error("智能校服随堂课件回调关联数据不存在");
				logger.error(json.toString());
			}
		}
	}
	/**
	 * 作业上传图片上传
	 * @param json
	 */
	private void workPic(JSONObject json){
		//信息图片
    	Long id = json.containsKey("id") ? json.getLong("id") : null;
		if (id != null) {
			SmartWorkEntity smartWork = this.smartWorkService.selectById(id);
			if (smartWork != null) {
				smartWork.setPic(ControllerConstant.CDN_URL + json.getString("filename"));
				this.smartWorkService.update(smartWork);
			} else {
				logger.error("智能校服作业图片回调关联数据不存在");
				logger.error(json.toString());
			}
		}
	}
	/**
	 * 竞技活动图片上传
	 * @param json
	 */
	private void activityPic(JSONObject json){
		//信息图片
    	Long id = json.containsKey("id") ? json.getLong("id") : null;
		if (id != null) {
			SmartActivitiesEntity smartActivities = this.smartActivitiesService.selectById(id);
			if (smartActivities != null) {
				smartActivities.setPic(ControllerConstant.CDN_URL + json.getString("filename"));
				this.smartActivitiesService.update(smartActivities);
			} else {
				logger.error("智能校服竞技活动图片回调关联数据不存在");
				logger.error(json.toString());
			}
		}
	}
	/**
	 * 心理咨询图片上传
	 * @param json
	 */
	private void psychologicalPic(JSONObject json){
		//信息图片
    	Long id = json.containsKey("id") ? json.getLong("id") : null;
		if (id != null) {
			PsychologicalCounselingEntity psychological = this.psychologicalCounselingService.selectById(id);
			if (psychological != null) {
				psychological.setPic(ControllerConstant.CDN_URL + json.getString("filename"));
				this.psychologicalCounselingService.update(psychological);
			} else {
				logger.error("智能校服心理咨询图片回调关联数据不存在");
				logger.error(json.toString());
			}
		}
	}
	
	/**
	 * 新生指南图片上传
	 * @param json
	 */
	private void freshmanGuidePic(JSONObject json){
		//信息图片
    	Long id = json.containsKey("id") ? json.getLong("id") : null;
		if (id != null) {
			FreshmanGuideEntity freshmanGuide = this.freshmanGuideService.selectById(id);
			if (freshmanGuide != null) {
				freshmanGuide.setFreshmanpic(ControllerConstant.CDN_URL + json.getString("filename"));
				this.freshmanGuideService.update(freshmanGuide);
			} else {
				logger.error("智能校服新生指南图片回调关联数据不存在");
				logger.error(json.toString());
			}
		}
	}
	
	/**
	 * 班级消息图片上传
	 * @param json
	 */
	private void classMessagePic(JSONObject json){
		//信息图片
    	Long id = json.containsKey("id") ? json.getLong("id") : null;
		if (id != null) {
			
			PhotoClassWorkMsgEntity msg = this.photoClassWorkMsgService.selectById(id);
			if (msg != null) {
				PhotoPicWorkMsgEntity ppme = new PhotoPicWorkMsgEntity();
				ppme.setGmtCreate(new Date());
				ppme.setName(json.getString("filename"));
				ppme.setPicSize(json.getString("size"));
				ppme.setRelatedId(id);
				ppme.setPicType(ControllerConstant.PIC_TYPE_CLASS_MSG);
				
				this.photoPicWorkMsgService.insert(ppme);
			} else {
				logger.error("智能校服班级消息图片回调关联数据不存在");
				logger.error(json.toString());
			}
		}
	}
	
	/**
	 * 学生上传头像
	 * @param json
	 */
	private void studentHeadPic(JSONObject json){
		//信息图片
    	Long id = json.containsKey("id") ? json.getLong("id") : null;
    	if (id != null) {
    		DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
    		StudentEntity student = this.studentService.selectById(id);
    		if (student != null) {
    			StudentEntity studentUpdate = new StudentEntity();
    			studentUpdate.setId(student.getId());
    			// 学生头像是完整地址
    			studentUpdate.setPic(ControllerConstant.CDN_URL + json.getString("filename"));
    			
    			this.studentService.update(studentUpdate);
    		}
    		
    		DbContextHolder.setDbType(DBTypeEnum.MYSQL);
    	} else {
    		logger.error("智能校服学生头像图片回调关联数据不存在");
			logger.error(json.toString());
    	}
	}
	
	/**
	 * 学校图片上传
	 * @param json
	 */
	private void schoolNoticePic(JSONObject json){
		//信息图片
    	Long id = json.containsKey("id") ? json.getLong("id") : null;
    	if (id != null) {
    		SchoolNoticeEntity schoolNotice = this.schoolNoticeService.selectById(id);
    		if(schoolNotice != null){
    			schoolNotice.setNoticepic(ControllerConstant.CDN_URL + json.getString("filename"));
    			this.schoolNoticeService.update(schoolNotice);
    		}
    	} else {
    		logger.error("智能校服学校通知图片回调关联数据不存在");
			logger.error(json.toString());
    	}
	}
	
	/**
	 * 班级图片上传
	 * @param json
	 */
	private void classNoticePic(JSONObject json){
    	Long id = json.containsKey("id") ? json.getLong("id") : null;
    	if(id !=null){
			ClassNoticeEntity classNoticeEntity = this.classNoticeService.selectById(id);
			if(classNoticeEntity != null){
				classNoticeEntity.setNoticepic(ControllerConstant.CDN_URL + json.getString("filename"));
				this.classNoticeService.update(classNoticeEntity);
			}
		} else {
    		logger.error("智能校服班级通知图片回调关联数据不存在");
			logger.error(json.toString());
    	}
	}
	
	/**classNoticePicweixin
	 * 班级图片上传
	 * @param json
	 */
	private void classNoticePicweixin(JSONObject json){
    	try {
    		ClassNoticeEntity classNoticeEntity = new ClassNoticeEntity();
    		classNoticeEntity.setNoticepic(ControllerConstant.CDN_URL + json.getString("filename"));
    		classNoticeEntity.setClassId(json.getString("classId"));
    		classNoticeEntity.setContent(json.getString("content"));
    		classNoticeEntity.setTitle(json.getString("title"));
    		classNoticeEntity.setCreatetime(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
    		this.classNoticeService.save(classNoticeEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
