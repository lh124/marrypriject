package io.renren.controller.smart;

import io.renren.entity.smart.ClassEntity;
import io.renren.entity.smart.IoEntity;
import io.renren.entity.smart.SchoolEntity;
import io.renren.entity.smart.SmartExceptionEntity;
import io.renren.entity.smart.StudentEntity;
import io.renren.entity.smart.StudentEpcEntity;
import io.renren.service.smart.ClassService;
import io.renren.service.smart.IoService;
import io.renren.service.smart.SchoolService;
import io.renren.service.smart.SmartExceptionService;
import io.renren.service.smart.StudentEpcService;
import io.renren.service.smart.StudentService;
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
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jiguangtuisong.JpushClientUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;

@SuppressWarnings("rawtypes")
@RestController("smartDataController")
@RequestMapping("/smart/datainpution")
public class SmartDataInpution {
	@Autowired
	private ClassService classService;
	@Autowired
	private SchoolService schoolService;
	@Autowired
	private StudentService studentService;
	@Autowired
	private StudentEpcService studentEpcService;
	@Autowired
	private IoService ioService;
	@Autowired
	private SmartExceptionService smartExceptionService;
	
	private static final String DATA = "data";
	private final static String FILEPATH = "http://guanyukeji-static.oss-cn-hangzhou.aliyuncs.com/";
	//阿里云API的内或外网域名
	private static String ENDPOINT = "oss-cn-hangzhou.aliyuncs.com";;
	//阿里云API的密钥Access Key ID
	private static String ACCESS_KEY_ID = "LTAIyY1y6mvPjVip";
	//阿里云API的密钥Access Key Secret
	private static String ACCESS_KEY_SECRET = "zio4dKlkF6424JE3gUxa8vzPyBcAaF";
	//阿里云API的bucket名称
	private static String BACKET_NAME = "guanyukeji-static";
	
	@RequestMapping("/getData")
	@ResponseBody
	public R getData(HttpServletRequest request,HttpServletResponse response){
		String key = request.getParameter("key").replace("&quot;", "\"");
		JSONObject json = JSONObject.fromObject(key);
		System.out.println(json);
		String type = json.getString("type");
		String token = json.getString("token");
		Object obj = null;
		if(token.equals("bcb15f821479b4d5772bd0ca866c00ad5f926e3580720659cc80d39c9d09802a")){
			if(type.equals("getallclass")){
				//通过学校名字查询所有班级
				obj = getallclass(json);
			}else if(type.equals("getallstudent")){
				//通过班级id查询所有学生
				obj = getallstudent(json);
			}else if(type.equals("deleteepc")){
				//通过epc值进行删除相应的epc
				obj = deleteepc(json);
			}else if(type.equals("findallepcclassid")){
				//通过班级id查询所有的epc
				obj = findallepcclassid(json);
			}else if(type.equals("saveepc")){
				//通过学生id和epc值进行数据保存
				obj = saveepc(json);
			}else if(type.equals("queryEpc")){
				//通过epc查询是否已绑定
				obj = queryEpc(json);
			}else if(type.equals("saveepcio")){
				//通过epc，ioType，ioDate，rfidId，studentId保存学生进出校园数据
				obj = saveepcio(json);
			}else if(type.equals("getonschoolstudent")){
				//通过学校名字查询所有在校的学生
				obj = getonschool(json);
			}else if(type.equals("exceptionSave")){
				//异常信息上传
				obj = exceptionSave(json);
			}else if(type.equals("getallexception")){
				//通过学校名查询所有的异常信息
				obj = getallexception(json);
			}else if(type.equals("exceptionSaveFile")){
				//异常文件上传
				CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
				obj = exceptionSaveFile(json,multipartResolver,request);
			}
			return (R)obj ;
		}else{
			return R.error("token错误");
		}
	}
	
	public Object exceptionSaveFile(JSONObject json,CommonsMultipartResolver multipartResolver,HttpServletRequest request){
		String schoolName = json.getString("schoolName");
		String modularName = json.getString("modularName");
		SmartExceptionEntity exception = new SmartExceptionEntity();
		exception.setCreatetime(new Date());
		exception.setSchoolname(schoolName);
		exception.setModularname(modularName);
		InputStream[] is;
		try {
			is = uploadfile(multipartResolver, request);
			if(is != null){
				exception.setExceptioninformation(FILEPATH+"exception/"+uploadObject2OSS(is[0], "exception/"));
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		smartExceptionService.save(exception);
		return R.ok();
	}
	
	public Object getallexception(JSONObject json){
		Object obj = null;
		String schoolName = json.getString("schoolName");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("schoolName", schoolName);
		map.put("sidx", null);
		map.put("order", null);
		map.put("offset", 0);
		map.put("limit", 100);
		obj = R.ok().put(DATA, smartExceptionService.queryList(map));
		return obj;
	}
	
	public Object exceptionSave(JSONObject json){
		String schoolName = json.getString("schoolName");
		String modularName = json.getString("modularName");
		String exceptionInformation = json.getString("exceptionInformation");
		SmartExceptionEntity exception = new SmartExceptionEntity();
		exception.setCreatetime(new Date());
		exception.setSchoolname(schoolName);
		exception.setModularname(modularName);
		exception.setExceptioninformation(exceptionInformation);
		smartExceptionService.save(exception);
		return R.ok();
	}
	
	public Object getonschool(JSONObject json){
		Object obj = null;
		String schoolName = json.getString("schoolName");
		DbContextHolder.setDbType(DBTypeEnum.SQLSERVER2);
		SchoolEntity schoolEntity = schoolService.queryObjectName(schoolName);
		if(schoolEntity != null){
			Map<String, Object> map = new HashMap<String, Object>();
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("schoolId", schoolEntity.getId());
			map.put("total", ioService.queryListtongjiimgxf(m).size());
			map.put("zxtotal", ioService.queryListtongji(m).size());
			obj = R.ok().put(DATA, map);
			DbContextHolder.setDbType(DBTypeEnum.MYSQL);
		}else{
			obj = R.error("暂无此学校");
		}
		return obj;
	}
	
	//文件上传
	public InputStream[] uploadfile(CommonsMultipartResolver multipartResolver,HttpServletRequest request) throws IllegalStateException, IOException{
		InputStream[] is = null;
				//判断 request 是否有文件上传,即多部分请求  
		        if(multipartResolver.isMultipart(request)){
		        	MultipartHttpServletRequest m= (MultipartHttpServletRequest)request;
		        	List<MultipartFile> detail = new ArrayList<MultipartFile>();
		        	if(m.getFiles("file").size() != 0){
		        		detail = m.getFiles("file");
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
	
	public Object saveepcio(JSONObject json){
		Object obj = null;
		try {
			List<Object> list = new ArrayList<Object>();
			JSONArray array = json.getJSONArray("list");
			DbContextHolder.setDbType(DBTypeEnum.SQLSERVER2);
			for (Iterator iterator = array.iterator(); iterator.hasNext();) {
				JSONObject object = (JSONObject) iterator.next();
				String epc = object.getString("epc");
				String ioType = object.getString("ioType");
				String rfidId = object.getString("rfidId");
				String studentId = object.getString("userId");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				IoEntity io = new IoEntity();
				io.setEpc(epc);
				io.setIoDate(sdf.parse(object.getString("ioDate").replace("T", " ")));
				io.setIoType(ioType);
				io.setRfidId(rfidId);
				io.setStudentId(Integer.parseInt(studentId));
				ioService.save(io);
				list.add(object.getString("id"));
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("type", 6);
				JpushClientUtil.sendToRegistrationId(studentId, "进出校通知", "进出校记录", ioType+"校门", m.toString());
			}
			DbContextHolder.setDbType(DBTypeEnum.MYSQL);
			obj = R.ok().put(DATA, list);
		} catch (Exception e) {
			obj = R.error();
		}
		return obj;
	}
	
	public Object getallclass(JSONObject json){
		Object obj = null;
		String schoolName = json.getString("schoolName");
		DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
		SchoolEntity schoolEntity = schoolService.queryObjectName(schoolName);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("schoolId", schoolEntity.getId());
		map.put("order", "");
		map.put("sidx", "");
		map.put("begin", 0);
		map.put("limit", 100);
		List<ClassEntity> list = classService.queryList(map);
		DbContextHolder.setDbType(DBTypeEnum.MYSQL);
		obj = R.ok().put(DATA, list);
		return obj;
	}
	
	public Object getallstudent(JSONObject json){
		Object obj = null;
		String classId = json.getString("classId");
		DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("classId", classId);
		map.put("userType", 1);
		map.put("order", "");
		map.put("sidx", "");
		map.put("begin", 0);
		map.put("limit", 150);
		List<StudentEntity> list = studentService.queryList(map);
		DbContextHolder.setDbType(DBTypeEnum.MYSQL);
		obj = R.ok().put(DATA, list);
		return obj;
	}
	
	public Object deleteepc(JSONObject json){
		Object obj = null;
		DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("epc", json.getString("epc").replace(" ", ""));
		StudentEpcEntity se = studentEpcService.queryObjectIdEpc(map);
		if(se == null){
			obj = R.error("该epc数据没有绑定");
		}
		studentEpcService.delete(se.getId());
		DbContextHolder.setDbType(DBTypeEnum.MYSQL);
		obj = R.ok();
		return obj;
	}
	
	public Object findallepcclassid(JSONObject json){
		Object obj = null;
		DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("begin", 0);
		map.put("page", 1);
		map.put("limit", 1000);
		map.put("userType", 1);
		map.put("classId", Integer.parseInt(json.getString("classId")));
		List<StudentEntity> studentlist = studentService.queryList(map);
		List<StudentEpcEntity> epclist = new ArrayList<StudentEpcEntity>();
		for (Iterator iterator = studentlist.iterator(); iterator.hasNext();) {
			StudentEntity studentEntity = (StudentEntity) iterator.next();
			map.put("student_id", studentEntity.getId());
			List<StudentEpcEntity> list = studentEpcService.queryListtongji(map);
			for (Iterator iterator2 = list.iterator(); iterator2
					.hasNext();) {
				StudentEpcEntity studentEpcEntity = (StudentEpcEntity) iterator2.next();
				epclist.add(studentEpcEntity);
			}
		}
		DbContextHolder.setDbType(DBTypeEnum.MYSQL);
		obj = R.ok().put(DATA, epclist);
		return obj;
	}
	
	public Object saveepc(JSONObject json){
		Object obj = null;
		DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
		JSONArray array = json.getJSONArray("epclist");
		List<String> list = new ArrayList<String>();
		for (Iterator iterator = array.iterator(); iterator.hasNext();) {
			JSONObject object = (JSONObject) iterator.next();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("epc", object.getString("epc").replace(" ", ""));
			StudentEpcEntity se = studentEpcService.queryObjectIdEpc(map);
			if(se != null){
				StudentEntity studnet = studentService.queryObject(se.getStudentId());
				ClassEntity classentity = classService.queryObject(studnet.getClassId());
				SchoolEntity school = schoolService.queryObject(classentity.getSchoolId());
				list.add(se.getEpc()+"已被学校："+school.getSchoolName() +  classentity.getClassName()+" " +studnet.getStudentName()+"所绑定"); 
			}else{
				StudentEpcEntity see = new StudentEpcEntity();
				see.setStudentId(Integer.parseInt(json.getString("student_id")));
				see.setEpc(object.getString("epc").replace(" ", ""));
				studentEpcService.save(see);
			}
		}
		DbContextHolder.setDbType(DBTypeEnum.MYSQL);
		if(list != null){
			obj = R.ok().put(DATA, list);
		}
		return obj;
	}
	
	public Object queryEpc(JSONObject json){
		Object obj = null;
		System.out.println(new Date());
		DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("epc", json.getString("epc").replace(" ", ""));
		StudentEpcEntity se = studentEpcService.queryObjectIdEpc(map);
		String data = "";
		if(se != null){
			StudentEntity studnet = studentService.queryObject(se.getStudentId());
			ClassEntity classentity = classService.queryObject(studnet.getClassId());
			SchoolEntity school = schoolService.queryObject(classentity.getSchoolId());
			data = "已被学校："+school.getSchoolName() +  classentity.getClassName()+" " +studnet.getStudentName()+"所绑定"; 
		}
		DbContextHolder.setDbType(DBTypeEnum.MYSQL);
		if(data != null && !data.equals("")){
			obj = R.error("已被绑定").put(DATA, data);
		}else{
			obj = R.ok();
		}
		return obj;
	}
	
	/**
	 * 上传图片至OSS
	 * @param ossClient  oss连接
	 * @param folder 模拟文件夹名 如"qj_nanjing/"
	 * @return String 返回的唯一MD5数字签名
	 * */
	public  String uploadObject2OSS(InputStream is, String folder) {
		OSSClient ossClient = new OSSClient(ENDPOINT, ACCESS_KEY_ID,ACCESS_KEY_SECRET);
		String bucketName = BACKET_NAME;
		String fileName = null;
		try {
			//文件名
			fileName = UUID.randomUUID().toString().replace("-", "") + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".txt";
			//文件大小
//            Long fileSize = file.length(); 
            //创建上传Object的Metadata  
			ObjectMetadata metadata = new ObjectMetadata();
			//上传的文件的长度
            metadata.setContentLength(is.available());  
            //指定该Object被下载时的网页的缓存行为
            metadata.setCacheControl("no-cache"); 
            //指定该Object下设置Header
            metadata.setHeader("Pragma", "no-cache");  
            //指定该Object被下载时的内容编码格式
            metadata.setContentEncoding("utf-8");  
            //文件的MIME，定义文件的类型及网页编码，决定浏览器将以什么形式、什么编码读取文件。如果用户没有指定则根据Key或文件名的扩展名生成，
            //如果没有扩展名则填默认值application/octet-stream
            //指定该Object被下载时的名称（指示MINME用户代理如何显示附加的文件，打开或下载，及文件名称）
//            metadata.setContentDisposition("filename/filesize=" + fileName + "/" + fileSize + "Byte.");  
            //上传文件   (上传文件流的形式)
//            PutObjectResult putResult = ossClient.putObject(bucketName, folder + fileName, is, metadata);  
            ossClient.putObject(bucketName, folder + fileName, is, metadata);  
			//解析结果
//			resultStr = putResult.getETag();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileName;
	}

}
