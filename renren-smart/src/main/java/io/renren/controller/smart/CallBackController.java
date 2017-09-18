package io.renren.controller.smart;

import io.renren.annotation.IgnoreAuth;
import io.renren.constant.ControllerConstant;
import io.renren.entity.smart.PhotoClassWorkMsgEntity;
import io.renren.entity.smart.PhotoPicWorkMsgEntity;
import io.renren.entity.smart.StudentEntity;
import io.renren.enums.TypeEnum;
import io.renren.service.smart.PhotoClassWorkMsgService;
import io.renren.service.smart.PhotoPicWorkMsgService;
import io.renren.service.smart.StudentService;
import io.renren.utils.OssCallBackUtil;
import io.renren.utils.dataSource.DBTypeEnum;
import io.renren.utils.dataSource.DbContextHolder;

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
	
	@ResponseBody
	@RequestMapping(value="/msgPic")
	@IgnoreAuth
	public JSONObject callBace(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		System.out.println("i getaaa");
		JSONObject jsona = OssCallBackUtil.analyzeCallBackData(request);
		JSONObject json = new JSONObject();
		
		System.out.println("i get");
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
			System.out.println("infor : " + fileName + " " + type);
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
	 * 班级消息图片上传
	 * @param json
	 */
	private void classMessagePic(JSONObject json){
		//信息图片
    	Long id = json.containsKey("id") ? json.getLong("id") : null;
    	System.out.println("messageId : " + id);
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
    	System.out.println("head : " + id);
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
}
