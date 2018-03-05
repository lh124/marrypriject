package io.renren.controller.app;

import io.renren.chart.CloudSignHelper;
import io.renren.chart.TXCloudHelper;
import io.renren.chart.TXCloudUtils;
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
import io.renren.entity.smart.PhotoScoreEntity;
import io.renren.entity.smart.PsychologicalCounselingEntity;
import io.renren.entity.smart.SchoolEntity;
import io.renren.entity.smart.SchoolNoticeEntity;
import io.renren.entity.smart.SmartActivitiesEntity;
import io.renren.entity.smart.SmartCoursewareEntity;
import io.renren.entity.smart.SmartExceptionEntity;
import io.renren.entity.smart.SmartGradeEntity;
import io.renren.entity.smart.SmartLeaveEntity;
import io.renren.entity.smart.SmartProposalEntity;
import io.renren.entity.smart.SmartRankingEntity;
import io.renren.entity.smart.SmartSendMessageEntity;
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
import io.renren.service.smart.SmartGradeService;
import io.renren.service.smart.SmartLeaveService;
import io.renren.service.smart.SmartNewsService;
import io.renren.service.smart.SmartProposalService;
import io.renren.service.smart.SmartRankingService;
import io.renren.service.smart.SmartSendMessageService;
import io.renren.service.smart.SmartTeacherMessageService;
import io.renren.service.smart.SmartVideoDeviceService;
import io.renren.service.smart.SmartWorkService;
import io.renren.service.smart.StudentService;
import io.renren.service.smart.WeixinFunctionImgService;
import io.renren.service.smart.WeixinFunctionService;
import io.renren.service.tombstone.TombstoneDeadService;
import io.renren.service.tombstone.TombstoneUserService;
import io.renren.util.JiguanUtil;
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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import jiguangtuisong.JpushClientUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import redis.clients.jedis.Jedis;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jmessage.api.JMessageClient;
import cn.jmessage.api.common.model.RegisterInfo;
import cn.jmessage.api.common.model.RegisterInfo.Builder;
import cn.jmessage.api.common.model.UserPayload;
import cn.jmessage.api.common.model.cross.CrossFriendPayload;
import cn.jmessage.api.common.model.cross.CrossGroup;
import cn.jmessage.api.user.UserInfoResult;

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
	@Autowired
	private SmartProposalService smartProposalService;
	@Autowired
	private SmartGradeService smartGradeService;
	@Autowired
	private SmartRankingService smartRankingService;
	@Autowired
	private SmartTeacherMessageService smartTeacherMessageService;
	@Autowired
	private SmartSendMessageService smartSendMessageService;
	
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
				//作业列表（原）
				return smartWord(json.getJSONObject("data"));
			} else if(type.equals("smartClassWorkList")){
				//作业列表（新）
				return smartClassWorkList(json.getJSONObject("data"));
			}else if(type.equals("classInfo")){
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
			}else if(type.equals("smartProposal")){
				//意见反馈
				return smartProposal(json.getJSONObject("data"));
			}else if(type.equals("listexamination")){
				//考试主题列表
				return listexamination(json.getJSONObject("data"));
			}else if(type.equals("examinationlistnew")){
				//成绩列表（新）
				return examinationlistnew(json.getJSONObject("data"));
			}else if(type.equals("examinationdetailnew")){
				//个人成绩详情（新）
				return examinationdetailnew(json.getJSONObject("data"));
			}else if(type.equals("findAllPerson")){
				//通过班级id查询所有班级成绩（包含老师学生）
				return findAllPerson(json.getJSONObject("data"));
			}else if(type.equals("tencentCloudSaveUser")){
				//聊天开始时判断在腾讯云通信是否有账号注册
				return tencentCloudSaveUser(json.getJSONObject("data"));
			}else if(type.equals("getGroupInfo")){
				//获取群信息（若无群就创建群）
				return getGroupInfo(json.getJSONObject("data"));
			}else if(type.equals("addFriend")){
				//云通信添加好友
				return addFriend(json.getJSONObject("data"));
			}else if(type.equals("deleteFriend")){
				//云通信删除好友
				return deleteFriend(json.getJSONObject("data"));
			}else if(type.equals("getAllFriend")){
				//云通信获取所有好友列表
				return getAllFriend(json.getJSONObject("data"));
			}else if(type.equals("checkFriend")){
				//云通信检验好友
				return checkFriend(json.getJSONObject("data"));
			}else if(type.equals("getOneFriend")){
				//云通信获取单个好友信息
				return getOneFriend(json.getJSONObject("data"));
			}else if(type.equals("registerUsers")){
				//极光用户注册
				return registerUsers(json.getJSONObject("data"));
			}else if(type.equals("getGroupMembers")){
				//极光获取群成员列表
				return getGroupMembers(json.getJSONObject("data"));
			}else if(type.equals("sendMessageSave")){
				//保存聊天记录列表
				CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
				return sendMessage(json.getJSONObject("data"),multipartResolver,request);
			}else if(type.equals("findSendMessageList")){
				//查询聊天记录
				return findSendMessageList(json.getJSONObject("data"));
			}else if(type.equals("addFriends")){
				//极光添加好友
				return addFriends(json.getJSONObject("data"));
			}else if(type.equals("deleteFriends")){
				//极光删除好友
				return deleteFriends(json.getJSONObject("data"));
			}else if(type.equals("getFriendsInfo")){
				//极光查询好友列表
				return getFriendsInfo(json.getJSONObject("data"));
			}else if(type.equals("getClassIdInfo")){
				//学生端获取班级信息
				return getClassIdInfo(json.getJSONObject("data"));
			}
		}
		return R.error("暂无此接口");
	}
	
	private R getClassIdInfo(JSONObject json){
		if(json.get("classId") == null){
			return R.error("班级id未上传至服务器");
		}
		Integer classId = json.getInt("classId");
		return R.ok().put(DATA, classService.queryObject(classId));
	}
	
	private R addFriends(JSONObject json){
		if(json.get("ownUserName") == null){//自己的账号
			return R.error("请将参数ownUserName传至服务器");
		}
		if(json.get("friendUserName") == null){//好友的账号
			return R.error("请将参数friendUserName传至服务器");
		}
		String friendUserName = json.getString("friendUserName");
		StudentEntity student = new StudentEntity();
		student.setStudentNo(friendUserName);
		EntityWrapper<StudentEntity> wrapper = new EntityWrapper<StudentEntity>(student);
		student = this.studentService.selectOne(wrapper);
		String[] users = new String[1];
    	users[0] = friendUserName;
    	try {
    		if(student == null){
    			return R.error("该账号不存在");
    		}
    		JMessageClient client = new JMessageClient(JiguanUtil.STUDENTAPPKEY, JiguanUtil.STUDENTMASTERSECRET);
    		if("1".equals(student.getUserType())){
    			client.addFriends(json.getString("ownUserName"), users);
    		}else if("2".equals(student.getUserType())){
    			CrossFriendPayload payload = new CrossFriendPayload(JiguanUtil.TEACHERAPPKEY, users);
    			client.addCrossFriends(json.getString("ownUserName"), payload);
    		}
		} catch (APIConnectionException e) {
			e.printStackTrace();
		} catch (APIRequestException e) {
			if(JSONObject.fromObject(e.getMessage()).getJSONObject("error").getLong("code") == 899070){
				return R.error("请勿重复添加该账号为您的好友");
			}
		}
		return R.ok("添加好友成功");
	}
	
	private R deleteFriends(JSONObject json){
		if(json.get("ownUserName") == null){//自己的账号
			return R.error("请将参数ownUserName传至服务器");
		}
		if(json.get("friendUserName") == null){//好友的账号
			return R.error("请将参数friendUserName传至服务器");
		}
		String friendUserName = json.getString("friendUserName");
		String[] users = new String[1];
    	users[0] = friendUserName;
    	StudentEntity student = new StudentEntity();
		student.setStudentNo(friendUserName);
		EntityWrapper<StudentEntity> wrapper = new EntityWrapper<StudentEntity>(student);
		student = this.studentService.selectOne(wrapper);
    	try {
    		JMessageClient client = new JMessageClient(JiguanUtil.STUDENTAPPKEY, JiguanUtil.STUDENTMASTERSECRET);
    		if("1".equals(student.getUserType())){
    			client.deleteFriends(json.getString("ownUserName"), users);
    		}else if("2".equals(student.getUserType())){
    			CrossFriendPayload payload = new CrossFriendPayload(JiguanUtil.TEACHERAPPKEY, users);
    			client.deleteCrossFriends(json.getString("ownUserName"), payload);
    		}
		} catch (APIConnectionException e) {
			e.printStackTrace();
		} catch (APIRequestException e) {
			e.printStackTrace();
		}
		return R.ok("删除好友成功");
	}

	private R getFriendsInfo(JSONObject json){
		if(json.get("ownUserName") == null){//自己的账号
			return R.error("请将参数ownUserName传至服务器");
		}
		List<UserInfoResult> list = new ArrayList<UserInfoResult>();
		JMessageClient client = new JMessageClient(JiguanUtil.STUDENTAPPKEY, JiguanUtil.STUDENTMASTERSECRET);
		try {
			UserInfoResult[] userlist = client.getFriendsInfo(json.getString("ownUserName"));
			for (int i = 0; i < userlist.length; i++) {
				list.add(userlist[i]);
			}
		} catch (APIConnectionException e) {
			e.printStackTrace();
		} catch (APIRequestException e) {
			e.printStackTrace();
		}
		return R.ok().put(DATA, list);
	}
	
	private R findSendMessageList(JSONObject json){
		if(json.get("messageType") == null){
			return R.error("请将参数messageType传至服务器");
		}
		if(json.get("sendUserId") == null){
			return R.error("请将参数sendUserId传至服务器");
		}
		if(json.get("receiveId") == null){
			return R.error("请将参数receiveId传至服务器");
		}
		if(json.get("receiveId") == null){
			return R.error("请将参数receiveId传至服务器");
		}
		if(json.get("limit") == null){
			return R.error("请将参数limit传至服务器");
		}
		if(json.get("offset") == null){
			return R.error("请将参数offset传至服务器");
		}
		Integer messageType = json.getInt("messageType");//1为班级群聊消息，2为点对点的私聊消息
		Integer sendUserId = json.getInt("sendUserId");//发送用户id
		Integer receiveId = json.getInt("receiveId");//接收消息id（可以是班级id，可以是用户id）
		Map<String, Object> map = getMap(json);
		map.put("messageType", messageType);
		map.put("receiveId", receiveId);
		map.put("sendUserId", sendUserId);
		return R.ok().put(DATA, smartSendMessageService.queryList(map));
	}
	
	private R sendMessage(JSONObject json,CommonsMultipartResolver multipartResolver,HttpServletRequest request){
		if(json.get("sendUserId") == null){
			return R.error("请将参数sendUserId传至服务器");
		}
		if(json.get("receiveId") == null){
			return R.error("请将参数receiveId传至服务器");
		}
		if(json.get("messageType") == null){
			return R.error("请将参数messageType传至服务器");
		}
		Integer sendUserId = json.getInt("sendUserId");//发送用户id
		Integer receiveId = json.getInt("receiveId");//接收消息id（可以是班级id，可以是用户id）
		Integer messageType = json.getInt("messageType");//1为班级群聊消息，2为点对点的私聊消息
		try {
			InputStream[] is = uploadfile(multipartResolver, request);
			if(is != null){
				for (int i = 0; i < is.length; i++) {
					String path = FILEPATH+"smart_head_pic/"+OssUploadUtil.uploadObject2OSS(is[0], "smart_head_pic/");
					SmartSendMessageEntity smartSendMessage = new SmartSendMessageEntity();
					smartSendMessage.setSendUserId(sendUserId);
					smartSendMessage.setReceiveId(receiveId);
					smartSendMessage.setContent(path);
					smartSendMessage.setMessageType(messageType);
					smartSendMessage.setCreatetime(new Date());
					smartSendMessageService.save(smartSendMessage);
				}
			}else{
				if(json.get("content") == null){
					return R.error("请将参数content传至服务器");
				}
				String content = json.getString("content");
				SmartSendMessageEntity smartSendMessage = new SmartSendMessageEntity();
				smartSendMessage.setSendUserId(sendUserId);
				smartSendMessage.setReceiveId(receiveId);
				smartSendMessage.setContent(content);
				smartSendMessage.setMessageType(messageType);
				smartSendMessage.setCreatetime(new Date());
				smartSendMessageService.save(smartSendMessage);
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return R.ok("发送成功");
	}
	
	private R getGroupMembers(JSONObject json){
	    if(json.get("classId")==null){
	    	return R.error("请将参数classId上传至服务器");
	    }
	    if(json.get("userId")==null){
	    	return R.error("请将参数userId上传至服务器");
	    }
	    Integer classId = json.getInt("classId");
	    Integer userId = json.getInt("userId");
	    ClassEntity classEntity = classService.queryObject(classId);
	    StudentEntity student = studentService.queryObject(userId);
	    if(classEntity == null){
	    	return R.error("查不到该班级信息，保证classId正确");
	    }
	    if(student == null){
	    	return R.error("查不到该学生信息，保证userId正确");
	    }
	    JMessageClient client = new JMessageClient(JiguanUtil.TEACHERAPPKEY, JiguanUtil.TEACHERMASTERSECRET);
	    try {
	    	    if("".equals(student.getGid()) || student.getGid() == null ){
	    	    	CrossGroup[] groups = new CrossGroup[1];
			    	String appKey = JiguanUtil.STUDENTAPPKEY;
			    	String[] add_users = new String[1];
			    	add_users[0] = "student1";
			    	CrossGroup.Builder gb = new CrossGroup.Builder();
			    	gb.setAppKey(appKey);
			    	gb.setAddUsers(add_users);
			    	groups[0] = gb.build();
					client.addOrRemoveCrossGroupMember(classEntity.getGid(), groups);
	    	    }
				return R.ok().put(DATA, client.getCrossGroupMembers(classEntity.getGid()).getMembers());
			} catch (APIConnectionException e) {
				e.printStackTrace();
			} catch (APIRequestException e) {
				e.printStackTrace();
		}
		return R.error("请重新获取群成员列表");
	}
	
	private R registerUsers(JSONObject json){
		if(json.get("userId")==null){
			return R.error("请将参数userId传至服务器");
		}
		StudentEntity student = studentService.queryObject(json.getInt("userId"));
		JMessageClient client = new JMessageClient(JiguanUtil.STUDENTAPPKEY, JiguanUtil.STUDENTMASTERSECRET);
		if(student.getGusername() == null || "".equals(student.getGusername())){
			RegisterInfo[] users = new RegisterInfo[1];
        	Builder builder = RegisterInfo.newBuilder();
        	builder.setUsername(student.getStudentNo());
        	builder.setNickname(student.getStudentName());
        	builder.setPassword(student.getPasswordd().substring(0, 15));
        	builder.setAvatar(student.getPic());
        	users[0] = builder.build();
            try {
				client.registerUsers(users);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("username", student.getStudentNo());
				map.put("nickname", student.getStudentName());
				map.put("avatar", student.getPic());
				map.put("password", student.getPasswordd().substring(0, 15));
				student.setGusername(student.getStudentNo());
				studentService.update(student);
				String[] u = new String[1];
	        	u[0] = student.getGusername();
				String d = JSONArray.fromObject(map).toString();
				return R.ok().put(DATA, JSONObject.fromObject(d.substring(1,d.length()-1)));
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}else{
			try {
				UserInfoResult user = client.getUserInfo(student.getStudentNo());
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("username", user.getUsername());
				map.put("nickname", user.getNickname());
				map.put("password", student.getPasswordd().substring(0, 15));
				map.put("avatar", user.getAvatar());
				String d = JSONArray.fromObject(map).toString();
				return R.ok().put(DATA, JSONObject.fromObject(d.substring(1,d.length()-1)));
			} catch (APIConnectionException e) {
				e.printStackTrace();
			} catch (APIRequestException e) {
				e.printStackTrace();
			}
		}
		return R.error("请重新获取用户信息");
	}
	
	private R getOneFriend(JSONObject json){
		if(json.get("uStudentNo")==null){
			return R.error("请先上传自己的账号字段uStudentNo");
		}
		if(json.get("fStudentNo")==null){
			return R.error("请先上传好友的账号字段fStudentNo");
		}
		String uid = json.getString("uStudentNo");//自己的账号
		String fuid = json.getString("fStudentNo");//好友账号
		String sign = CloudSignHelper.GetSign(TXCloudHelper.identifier);
		String result = TXCloudUtils.getOneFriend(uid, fuid,sign);
		if(result == null){
			return R.error("您们不是好友，无法获取他的资料信息");
		}
		return R.ok().put(DATA, result.substring(1, result.length()-1));
	}
	
	private R checkFriend(JSONObject json){
		if(json.get("uStudentNo")==null){
			return R.error("请先上传自己的账号字段uStudentNo");
		}
		if(json.get("fStudentNo")==null){
			return R.error("请先上传好友的账号字段fStudentNo");
		}
		String uid = json.getString("uStudentNo");//自己的账号
		String fuid = json.getString("fStudentNo");//好友账号
		String sign = CloudSignHelper.GetSign(TXCloudHelper.identifier);
		byte result = TXCloudUtils.checkFriend(uid, fuid,sign);
		if(result == 1){
			return R.ok("您们是好友关系");
		}else if(result == 2){
			return R.ok("您们不是好友关系");
		}
		return R.error("检验失败，请重新检验");
	}
	
	private R getAllFriend(JSONObject json){
		if(json.get("uStudentNo")==null){
			return R.error("请先上传自己的账号字段uStudentNo");
		}
		String uid = json.getString("uStudentNo");//自己的账号
		String sign = CloudSignHelper.GetSign(TXCloudHelper.identifier);
		String result = TXCloudUtils.getAllFriend(uid, 0,sign);
		return R.ok().put(DATA, result.substring(1, result.length()-1));
	}
	
	private R deleteFriend(JSONObject json){
		if(json.get("uStudentNo")==null){
			return R.error("请先上传自己的账号字段uStudentNo");
		}
		if(json.get("fStudentNo")==null){
			return R.error("请先上传好友的账号字段fStudentNo");
		}
		String uid = json.getString("uStudentNo");//自己的账号
		String fuid = json.getString("fStudentNo");//好友账号
		String sign = CloudSignHelper.GetSign(TXCloudHelper.identifier);
		byte result = TXCloudUtils.deleteFriend(uid, fuid,sign);
		if(result == 1){
			return R.ok("删除好友成功");
		}
		return R.error("删除好友失败");
	}
	
	private R addFriend(JSONObject json){
		if(json.get("uStudentNo")==null){
			return R.error("请先上传自己的账号字段uStudentNo");
		}
		if(json.get("fStudentNo")==null){
			return R.error("请先上传好友的账号字段fStudentNo");
		}
		if(json.get("addSource")==null){
			return R.error("请先上传好友来源字段addSource");
		}
		String uid = json.getString("uStudentNo");//自己的账号
		String fuid = json.getString("fStudentNo");//好友账号
		String addSource=json.getString("addSource");//添加好友来源Android ，IOS
		String remark = "";
		String sign = CloudSignHelper.GetSign(TXCloudHelper.identifier);
		String u = TXCloudUtils.getPortrait(fuid,sign);
		if(u == null || "".equals(u) || u == ""){
			return R.error("您所加的账号在云通信平台暂未注册");
		}
		if(json.get("remark") == null){
			StudentEntity st = new StudentEntity();
			st.setStudentNo(json.getString("fStudentNo"));
			EntityWrapper<StudentEntity> wrapper = new EntityWrapper<StudentEntity>(st);
			remark = studentService.selectOne(wrapper).getStudentNo();
		}else{
			remark = json.getString("remark");
		}
		byte result = TXCloudUtils.addFriend(uid, fuid, remark, addSource, sign);
		if(result == 1){
			return R.ok("好友添加成功");
		}else if(result == 2){
			return R.error("已经是好友了，请勿重复添加");
		}
		return R.error("添加好友失败，请重新添加");
	}
	
	private R getGroupInfo(JSONObject json){
		if(json.get("classId")==null){
			return R.error("请上传参数classId");
		}
		ClassEntity classEntity = classService.queryObject(json.getInt("classId"));
		if(classEntity.getGroupId() == null || "".equals(classEntity.getGroupId())){//判断该班级是否在腾讯云通信创群
			String sign = CloudSignHelper.GetSign(TXCloudHelper.identifier);
			String s = TXCloudUtils.CreateGRoup(classEntity.getClassName(), classEntity.getPic(), sign);
			JSONObject j = JSONObject.fromObject(s);
			if(j.get("ActionStatus").toString().equals("OK")){
				classEntity.setGroupId(j.getString("GroupId"));
				classEntity.setExpireTime(getDate());
				classEntity.setSign(sign);
				classService.update(classEntity);
				String groupInfo = TXCloudUtils.getGroupInfo(j.getString("GroupId"),sign);
				JSONObject js = JSONObject.fromObject(groupInfo);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("FaceUrl", classEntity.getPic());
				map.put("Appid", js.getLong("Appid"));
				map.put("Name", classEntity.getClassName());
				map.put("sign", sign);
				map.put("GroupId", js.getString("GroupId"));
				map.put("OnlineMemberNum", js.getString("OnlineMemberNum"));
				map.put("MaxMemberNum", js.getInt("MaxMemberNum"));
				JSONArray array = j.getJSONArray("MemberList");
				List<StudentEntity> list = new ArrayList<StudentEntity>();
				for (Iterator iterator = array.iterator(); iterator.hasNext();) {
					JSONObject object = (JSONObject) iterator.next();
					StudentEntity st = new StudentEntity();
					st.setStudentNo(object.getString("Member_Account"));
					EntityWrapper<StudentEntity> wrapper = new EntityWrapper<StudentEntity>(st);
					list.add(studentService.selectOne(wrapper));
				}
				map.put("list", list);
				return R.ok().put(DATA, JSONObject.fromObject(map)); 
			}
		}else{
			String sign = "";
			if(classEntity.getExpireTime().getTime() < new Date().getTime()){
				sign = CloudSignHelper.GetSign(TXCloudHelper.identifier);
				classEntity.setExpireTime(getDate());
				classEntity.setSign(sign);
				classService.update(classEntity);
			}else{
				sign = classEntity.getSign();
			}
			String groupInfo = TXCloudUtils.getGroupInfo(classEntity.getGroupId(),sign);
			groupInfo = groupInfo.substring(1, groupInfo.length()-1);
			JSONObject j = JSONObject.fromObject(groupInfo);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("FaceUrl", classEntity.getPic());
			map.put("Appid", j.getLong("Appid"));
			map.put("Name", classEntity.getClassName());
			map.put("sign", sign);
			map.put("GroupId", j.getString("GroupId"));
			map.put("OnlineMemberNum", j.getString("OnlineMemberNum"));
			map.put("MaxMemberNum", j.getInt("MaxMemberNum"));
			map.put("LastMsgTime", j.getString("LastMsgTime"));
			map.put("MemberNum", j.getString("MemberNum"));
			JSONArray array = j.getJSONArray("MemberList");
			List<StudentEntity> list = new ArrayList<StudentEntity>();
			for (Iterator iterator = array.iterator(); iterator.hasNext();) {
				JSONObject object = (JSONObject) iterator.next();
				StudentEntity st = new StudentEntity();
				st.setStudentNo(object.getString("Member_Account"));
				EntityWrapper<StudentEntity> wrapper = new EntityWrapper<StudentEntity>(st);
				list.add(studentService.selectOne(wrapper));
			}
			map.put("list", list);
			return R.ok().put(DATA, JSONObject.fromObject(map)); 
		}
		return null;
	}
	
	private Date getDate(){
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, +179);//+179今天的时间加179天
		return calendar.getTime();
	}
	
	private R tencentCloudSaveUser(JSONObject json){
		if(json.get("userId")==null){
			return R.error("请上传参数userId");
		}
		StudentEntity student = studentService.queryObject(json.getInt("userId"));
		ClassEntity classEntity = classService.queryObject(student.getClassId());
		String sign = "";
		String groupId = "";
		if(classEntity.getGroupId() == null || "".equals(classEntity.getGroupId())){
			sign = CloudSignHelper.GetSign(TXCloudHelper.identifier);
			String s = TXCloudUtils.CreateGRoup(classEntity.getClassName(), classEntity.getPic(), sign);
			JSONObject j = JSONObject.fromObject(s);
			if(j.get("ActionStatus").toString().equals("OK")){
				classEntity.setGroupId(j.getString("GroupId"));
				classEntity.setExpireTime(getDate());
				classEntity.setSign(sign);
				classService.update(classEntity);
				groupId = j.getString("GroupId");
			}
		}else{
			if(classEntity.getExpireTime().getTime() < new Date().getTime()){
				sign = CloudSignHelper.GetSign(TXCloudHelper.identifier);
				classEntity.setExpireTime(getDate());
				classEntity.setSign(sign);
				classService.update(classEntity);
			}else{
				sign = classEntity.getSign();
			}
		}
		if(student.getExpireTime() == null){
			sign = CloudSignHelper.GetSign(TXCloudHelper.identifier);
			student.setExpireTime(getDate());
			student.setSign(sign);
			studentService.update(student);
		}else{
			if(student.getExpireTime().getTime() < new Date().getTime()){
				sign = CloudSignHelper.GetSign(TXCloudHelper.identifier);
				student.setExpireTime(getDate());
				student.setSign(sign);
				studentService.update(student);
			}else{
				sign = student.getSign();
			}
			String groupInfo = TXCloudUtils.getGroupInfo(classEntity.getGroupId(),sign);
			groupInfo = groupInfo.substring(1, groupInfo.length()-1);
			JSONObject j = JSONObject.fromObject(groupInfo);
			groupId = j.getString("GroupId");
		}
		String u = TXCloudUtils.getPortrait(student.getStudentNo(),sign);
		if(u == null || "".equals(u) || u == ""){
			byte result = TXCloudUtils.AccountImport(student.getStudentNo(), student.getStudentName(),
				       student.getPic(),sign);
			if(result==1){
				JSONObject js = JSONObject.fromObject(TXCloudUtils.getPortrait(student.getStudentNo(),sign));
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("image", student.getPic());
				map.put("name", js.getString("name"));
				map.put("nickname", student.getStudentName());
				map.put("sign", sign);
				TXCloudUtils.addGroupInfo(JSONObject.fromObject(mapToJson(groupId, js.getString("name"))).toString(), sign);
				return R.ok().put(DATA, JSONObject.fromObject(map));
			}
		}else{
			JSONObject js = JSONObject.fromObject(TXCloudUtils.getPortrait(student.getStudentNo(),sign));
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("image", student.getPic());
			map.put("name", js.getString("name"));
			map.put("nickname", student.getStudentName());
			map.put("sign", sign);
			TXCloudUtils.addGroupInfo(JSONObject.fromObject(mapToJson(groupId, js.getString("name"))).toString(), sign);
			return R.ok().put(DATA, JSONObject.fromObject(map));
		}
		return null;
	}
	
	private Map<String, Object> mapToJson(String GroupId,String memberAccount){
		Map<String, Object> m = new HashMap<String, Object>();
        m.put("Member_Account", memberAccount);
		Map<String,Object> map = new HashMap<String, Object>();
        String[] s = new String[1];
        s[0] = JSONObject.fromObject(m).toString();
        map.put("GroupId", GroupId);// 群组类型：Private/Public/ChatRoom/AVChatRoom/BChatRoom（必填）
        map.put("Silence", 1);// 群名称（必填）
        map.put("MemberList", s);//// 最大群成员数量（选填）
        return map;
	}
	
	private R findAllPerson(JSONObject json){
		Integer classId = json.getInt("classId");//班级id
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("classId", classId);
		map.put("userType", 1);
		map.put("begin", 0);
		map.put("limit", 150);
		List<StudentEntity> list = studentService.queryList(map);
		map.put("classid", classId);
		map.put("offset", 0);
		List<ClassInfoEntity> infoList = classInfoService.queryList(map);
		for (Iterator iterator = infoList.iterator(); iterator.hasNext();) {
			ClassInfoEntity classInfoEntity = (ClassInfoEntity) iterator.next();
			list.add(studentService.queryObject(classInfoEntity.getUserId()));
		}
		return R.ok().put(DATA, list);
	}
	
	private R examinationdetailnew(JSONObject json){
		Integer userId = json.getInt("userId");//学生id
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sidx", null);
		map.put("offset", 0);
		map.put("limit", 100);
		map.put("examinationId", json.getString("examinationId"));
		map.put("userId", userId);
		List<PhotoScoreEntity> list = photoScoreService.queryList(map);
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("list", list);
		map.put("classId", studentService.queryObject(userId).getClassId());
		List<SmartRankingEntity> student = smartRankingService.queryList(map);
		m.put("student", student.size()==0?"":student.get(0));
		return R.ok().put(DATA, m);
	}
	
	private R listexamination(JSONObject json){
		Integer classId = json.getInt("classId");//班级id
		Integer gradeId = classService.queryObject(classId).getGradeId();//年级id
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("gradeId", gradeId);
		List<PhotoExaminationEntity> examinationlist = photoExaminationService.queryList(map);//年级下面的所有考试主题列表
		return R.ok().put(DATA, examinationlist);
	}
	
	private R examinationlistnew(JSONObject json){
		Integer studentId = json.getInt("studentId");//学生id
		Integer classId = json.getInt("classId");//班级id
		Integer gradeId = classService.queryObject(classId).getGradeId();//年级id
		Integer examinationId = json.getInt("examinationId");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("gradeId", gradeId);
		List<PhotoExaminationEntity> examinationlist = photoExaminationService.queryList(map);//年级下面的所有考试主题列表
		for(int i = 0; i < examinationlist.size(); i++){
			if(json.getInt("examinationId") == examinationlist.get(i).getId()){
				if((i+1)==examinationlist.size()){
					map.put("examinationId2", null);
				}else{
					map.put("examinationId2", examinationlist.get(i+1).getId());
				}
			}
		}
		if(examinationId == 0 ){
			if(examinationlist.size()!=0){
				examinationId = Integer.parseInt(examinationlist.get(0).getId().toString());
			}
		}
		map.put("examinationId", examinationId);
		map.put("classId", classId);
		List<SmartRankingEntity> smartRankinglist = smartRankingService.queryList(map);//班级学生成绩及其排名
		map.put("userId", studentId);
		List<SmartRankingEntity> student = smartRankingService.queryList(map);
		Map<String, Object> m = new HashMap<String, Object>();
		SmartRankingEntity smartRanking = null;
		if(student.size() == 0){
			smartRanking = new SmartRankingEntity();
			smartRanking.setFractionTotal("0");
			smartRanking.setFractionAverage(new Double(0));
			smartRanking.setClassRanking(0);
			smartRanking.setGradeRanking(0);
			smartRanking.setClassRankingLast(0);
			smartRanking.setGradeRankingLast(0);
		}else{
			smartRanking = student.get(0);
		}
		m.put("student", smartRanking);//学生个人成绩
		m.put("smartRankinglist", smartRankinglist);
		return R.ok().put(DATA, m);
	}
	
	private R smartProposal(JSONObject json){
		SmartProposalEntity proposalEntity = new SmartProposalEntity();
		proposalEntity.setContent(json.getString("content"));
		proposalEntity.setTitle(json.getString("title"));
		proposalEntity.setUserId(json.getInt("userId"));
		smartProposalService.save(proposalEntity);
		return R.ok();
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
				String pic = FILEPATH+"smart_head_pic/"+OssUploadUtil.uploadObject2OSS(is[0], "smart_head_pic/");
				student.setPic(pic);
				studentService.update(student);
				StudentEntity studentEntity = studentService.queryObject(student.getId());
				Integer id = studentEntity.getClassId();
				studentEntity.setSchoolId(classService.queryObject(id).getSchoolId());
				try {
					if(student.getGusername() != null && !"".equals(student.getGusername())){
						JMessageClient client = new JMessageClient(JiguanUtil.STUDENTAPPKEY, JiguanUtil.STUDENTMASTERSECRET);
						cn.jmessage.api.common.model.UserPayload.Builder builder = UserPayload.newBuilder();
						builder.setAvatar(pic);
						UserPayload user = builder.build();
						client.updateUserInfo(student.getGusername(), user);
					}
				} catch (APIConnectionException e) {
					e.printStackTrace();
				} catch (APIRequestException e) {
					e.printStackTrace();
				}
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
			try {
				if(studnet.getGusername() != null && !"".equals(studnet.getGusername())){
					JMessageClient client = new JMessageClient(JiguanUtil.STUDENTAPPKEY, JiguanUtil.STUDENTMASTERSECRET);
					cn.jmessage.api.common.model.UserPayload.Builder builder = UserPayload.newBuilder();
					builder.setNickname(json.getString("studentName"));
					UserPayload user = builder.build();
					client.updateUserInfo(studnet.getGusername(), user);
				}
			} catch (APIConnectionException e) {
				e.printStackTrace();
			} catch (APIRequestException e) {
				e.printStackTrace();
			}
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
				try {
					if(student.getGusername() != null && !"".equals(student.getGusername())){
						JMessageClient client = new JMessageClient(JiguanUtil.STUDENTAPPKEY, JiguanUtil.STUDENTMASTERSECRET);
						client.updateUserPassword(student.getGusername(), json.getString("newPassword").substring(0, 15));
					}
				} catch (APIConnectionException e) {
					e.printStackTrace();
				} catch (APIRequestException e) {
					e.printStackTrace();
				}
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
		map.put("offset", json.get("offset")==null?0:json.getInt("offset"));
		map.put("limit", json.get("limit")==null?100:json.getInt("limit"));
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
	
	public R smartClassWorkList(JSONObject json){
		Integer workType = json.getInt("workType");//类型0为今日作业，1为历史作业记录
		Map<String, Object> map = getMap(json);
		map.put("userId", json.getString("userId"));
		List<SmartWorkEntity> smartWorkList = smartWorkService.queryListtongji(map);
		if(workType == 0){
			List<SmartWorkEntity> list = new ArrayList<SmartWorkEntity>();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
			for (Iterator iterator = smartWorkList.iterator(); iterator.hasNext();) {
				SmartWorkEntity smartWorkEntity = (SmartWorkEntity) iterator.next();
				if(sdf.format(new Date()).equals(smartWorkEntity.getCreatetime().subSequence(0, 10))){
					list.add(smartWorkEntity);
				}
			}
			return R.ok().put(DATA, list);
		}else{
			return R.ok().put(DATA, smartWorkList);
		}
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
				ClassEntity classEntity = classService.queryObject(user.getClassId());//通过学生中的班级id获取班级信息
				SmartGradeEntity smartGradeEntity = smartGradeService.queryObject(classEntity.getGradeId());//通过班级信息中的年级id获取年级信息
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", user.getId());
				map.put("token", tokening);
				map.put("pic", (user.getPic()== null || "".equals(user.getPic()))  ? "http://guanyukeji-static.oss-cn-hangzhou.aliyuncs.com/1.png":user.getPic());
				map.put("classId", user.getClassId());
				map.put("gradeId", smartGradeEntity==null?0:smartGradeEntity.getId());
				map.put("schoolId", smartGradeEntity==null?0:smartGradeEntity.getSchoolId());
				map.put("studentName", user.getStudentName());
				map.put("studentNo", user.getStudentNo());
				map.put("phone", user.getPhoen());
				map.put("accessToken", token.getAccessToken());
				map.put("updateType", updateType);
				map.put("updateIf", updateIf);
				map.put("appPath", appPath);
				if(classEntity.getGid() == 0){
					map.put("gid", 0);
					map.put("groupName", "");
					map.put("avatar", "");
				}else{
					map.put("gid", classEntity.getGid());
					map.put("groupName", classEntity.getClassName());
					map.put("avatar", classEntity.getPic());
				}
				map.put("password", user.getPasswordd().substring(0, 15));
				map.put("userName", user.getGusername());
				map.put("bindingType", smartGradeEntity==null?0:schoolService.queryObject(smartGradeEntity.getSchoolId()).getBindingType());
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
