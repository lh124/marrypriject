package io.renren.controller.photoWeb;

import io.renren.controller.photo.PostObjectPolicyController;
import io.renren.entity.PhotoClassEntity;
import io.renren.entity.PhotoFrontUserEntity;
import io.renren.entity.PhotoPicClassEntity;
import io.renren.entity.PhotoPicUserEntity;
import io.renren.entity.PhotoSchoolEntity;
import io.renren.enums.TypeEnum;
import io.renren.model.dto.SignParameter;
import io.renren.service.PhotoClassService;
import io.renren.service.PhotoFrontUserService;
import io.renren.service.PhotoPicClassService;
import io.renren.service.PhotoPicHeadService;
import io.renren.service.PhotoPicPublicService;
import io.renren.service.PhotoPicSchoolService;
import io.renren.service.PhotoPicUserService;
import io.renren.service.PhotoSchoolService;
import io.renren.service.PhotoTypeService;
import io.renren.service.smart.StudentService;
import io.renren.utils.OssCallBackUtil;
import io.renren.utils.dataSource.DBTypeEnum;
import io.renren.utils.dataSource.DbContextHolder;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/sys/aliOss")
public class AliyunOssController {
	
	// 日志操作
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	/** * 回调参数文件名 */
	public static String FILE_NAME = "filename";
	
	// 注入业务数据操作类
	@Autowired
	private PhotoPicClassService photoPicClassService;
	@Autowired
	private PhotoPicHeadService photoPicHeadService;
	@Autowired
	private PhotoPicUserService photoPicUserService;
	@Autowired
	private PhotoPicSchoolService photoPicSchoolService;
	@Autowired
	private PhotoPicPublicService photoPicPublicService;
	@Autowired
	private PhotoFrontUserService photoFrontUserService;
	@Autowired
	private PhotoSchoolService photoSchoolService;
	@Autowired 
	private PhotoTypeService photoTypeService;
	@Autowired
	private PhotoClassService photoClassService;
	@Autowired
	private StudentService studentService;
	
	
	/**
	 * 获取图片上传签名
	 * @param request
	 * @param response
	 * @param userId 用户的id
	 * @param type 判断是那种类型图片，设置回调地址
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getSign")
	public Map<String, String> getSignTimeOut(HttpServletRequest request, HttpServletResponse response, SignParameter sign){
		PostObjectPolicyController policy = new PostObjectPolicyController();
		Map<String, String> map = policy.getSignTimeOut(request, response, sign);
		
		System.out.println(JSONObject.fromObject(map).toString());
		return map;
	}
	
	@ResponseBody
	@RequestMapping(value="/callBack") 
	public JSONObject callBace(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String ossCallbackBody =OssCallBackUtil.GetPostBody(request.getInputStream(), Integer.parseInt(request.getHeader("content-length")));
		boolean ret = OssCallBackUtil.VerifyOSSCallbackRequest(request, ossCallbackBody);
		System.out.println("verify resultWeb:" + ret);
		System.out.println("OSS Callback Body:" + ossCallbackBody);
        
		//OSS Callback Body:{"format":"jpg","related_id":null,"classifyId":null,"type":3,"bucket":"second-hand",
		//"object":"class_1492827709000.jpg","filename":"class_1492827709000.jpg","size":62807,"mimeType":"image/jpeg",
		//"height":700,"width":700}
		JSONObject json = new JSONObject();
		JSONObject jsona = JSONObject.fromObject(ossCallbackBody);
		if (ret)
		{
			// 返回阿里云通知信息
			json.element("Status", "OK");
			response.setStatus(HttpServletResponse.SC_OK);
			response.addHeader("Content-Length", String.valueOf(json.toString().length()));
			
			// 从json中获取响应的数据
			
			Integer type = jsona.containsKey("type") && jsona.getString("type") != null ? 
					jsona.getInt("type") : null;
			String fileName = jsona.containsKey("filename") ? jsona.getString("filename") : null;
			String size = jsona.containsKey("size") ? jsona.getString("size") : null;
			/*Long classifyId = jsona.containsKey("classifyId") && jsona.getString("classifyId") != null ? 
					jsona.getLong("classifyId") : null;
			Long relatedId = jsona.containsKey("related_id") && jsona.getString("related_id") != null ? 
					jsona.getLong("related_id") : null;*/
			
			// 进行参数判断
			if (type == null){
				
				logger.error("type值为空" + ossCallbackBody);
				
				return json;
			}else{
				try{
					
					
					if (type.equals(TypeEnum.PHOTO_CLASS.getType())){
			        	// 班级照片
						Long id = jsona.containsKey("id") ? jsona.getLong("id") : null;
						if (id != null) {
							PhotoPicClassEntity ppce = new PhotoPicClassEntity();
							ppce.setTypeId(id);
							ppce.setGtmCreate(new Date());
							ppce.setGtmModified(new Date());
							ppce.setPicSize(size);
							ppce.setName(fileName);
							this.photoPicClassService.save(ppce);
						}
						
			        }else if (type.equals(TypeEnum.PHOTO_CLASS_VEDIO.getType())){
			        	// 班级视频
			        	Long classId = jsona.containsKey("classId") ? jsona.getLong("classId") : null;
			        	if (classId != null) {
			        		PhotoClassEntity pClass = this.photoClassService.queryObject(classId);
			        		if (pClass != null) {
			        			pClass.setVideo(fileName);
			        			this.photoClassService.update(pClass);
			        		}
			        	}
			        	
			        }else if (type.equals(TypeEnum.PHOTO_CLASS_MUSIC.getType())){
			        	// 班级音乐
			        	Long classId = jsona.containsKey("classId") ? jsona.getLong("classId") : null;
			        	if (classId != null) {
			        		PhotoClassEntity pClass = this.photoClassService.queryObject(classId);
			        		if (pClass != null) {
			        			pClass.setMusic(fileName);
			        			this.photoClassService.update(pClass);
			        		}
			        	}
			        	
			        	
			        } else if (type.equals(TypeEnum.PHOTO_HOME.getType())){
			        	// 家庭照片
			        	
			        	
			        } else if (type.equals(TypeEnum.PHOTO_SCHOOL.getType())){
			        	// 学校照片
			        	
			        }else if (type.equals(TypeEnum.PHOTO_SCHOOL_MUSIC.getType())){
			        	// 学校音乐
			        	Long schoolId = jsona.containsKey("schoolId") ? jsona.getLong("schoolId") : null;
			        	System.out.println("---------->a" + schoolId + "  ++ " + fileName);
			        	if(schoolId != null && fileName != null){
			        		PhotoSchoolEntity school = this.photoSchoolService.queryObject(schoolId);
			        		if(school != null){
			        			school.setMusic(fileName);
			        			this.photoSchoolService.update(school);
			        		}
			        	}
			        	
			        }else if (type.equals(TypeEnum.PHOTO_SCHOOL_VEDIO.getType())){
			        	// 学校视频
						Long schoolId = jsona.containsKey("schoolId") ? jsona.getLong("schoolId") : null;
									        	
			        	if(schoolId != null && fileName != null){
			        		PhotoSchoolEntity school = this.photoSchoolService.queryObject(schoolId);
			        		if(school != null){
			        			school.setVedio(fileName);;
			        			this.photoSchoolService.update(school);
			        		}
			        	}
			        	
			        } else if (type.equals(TypeEnum.PHOTO_USER.getType())){
			        	// 用户照片
			        	Long id = jsona.containsKey("id") ? jsona.getLong("id") : null;
						if (id != null) {
							PhotoPicUserEntity ppue = new PhotoPicUserEntity();
							ppue.setGmtCreate(new Date());
							ppue.setGmtModified(new Date());
							ppue.setName(fileName);
							ppue.setPicSize(size);
							ppue.setTypeId(id);
							
							this.photoPicUserService.save(ppue);
						}
			        	
			        }  else if (type.equals(TypeEnum.PHOTO_USER_HEAD.getType())){
			        	// 用户头像
			        	
			        	// 先判断是否已经上传头像
			        	//PhotoPicHeadEntity head = this.photoPicHeadService.selectById(jsona.getInt("user_id"));
			        	PhotoFrontUserEntity user = this.photoFrontUserService.selectById(jsona.getInt("user_id"));
			        	
			        	
			        	if(user != null){
			        		
			        		PhotoFrontUserEntity userUpdate = new PhotoFrontUserEntity();
			        		userUpdate.setId(user.getId());
			        		userUpdate.setHeadImg(jsona.getString(FILE_NAME));
			        		
			        		this.photoFrontUserService.updateById(userUpdate);
			        		//this.sysUserService.updateById()
				        	//this.sysUserService.update(user);
			        	}
			        	
			        	// 同步到sqlserver
			        	/*DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
			        	Student student = this.studentService.selectById(user.getId());
			        	if (student != null ) {
			        		// 构建一个新对象
			        		Student stu = new Student();
			        		stu.setId(student.getId());
			        		String name = PostObjectPolicyController.CDN_URL + fileName;
			        		stu.setPic(name);
			        		this.studentService.updateById(stu);
			        	}*/
			        	DbContextHolder.setDbType(DBTypeEnum.MYSQL);
			        }  else {
			        	
			        	// 无此类型值
			        }
				} catch(Exception e){
					e.printStackTrace();
					logger.error("回调异常", e);
					logger.error(ossCallbackBody);
				}
				
			}
			
			return json;
		} else {
			json.element("Status", "verdify not ok");
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.addHeader("Content-Length", String.valueOf(json.toString().length()));
			return json;
		}
	}
}
