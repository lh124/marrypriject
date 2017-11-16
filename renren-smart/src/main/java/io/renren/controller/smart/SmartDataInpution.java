package io.renren.controller.smart;

import io.renren.entity.smart.ClassEntity;
import io.renren.entity.smart.IoEntity;
import io.renren.entity.smart.SchoolEntity;
import io.renren.entity.smart.StudentEntity;
import io.renren.entity.smart.StudentEpcEntity;
import io.renren.service.smart.ClassService;
import io.renren.service.smart.IoService;
import io.renren.service.smart.SchoolService;
import io.renren.service.smart.StudentEpcService;
import io.renren.service.smart.StudentService;
import io.renren.utils.R;
import io.renren.utils.dataSource.DBTypeEnum;
import io.renren.utils.dataSource.DbContextHolder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
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
	
	private static final String DATA = "data";
	
	@RequestMapping("/getData")
	@ResponseBody
	public R getData(HttpServletRequest request,HttpServletResponse response){
		String key = request.getParameter("key").replace("&quot;", "\"");
		JSONObject json = JSONObject.fromObject(key);
		String type = json.getString("type");
		String token = json.getString("token");
		Object obj = null;
		if(token.equals("bcb15f821479b4d5772bd0ca866c00ad5f926e3580720659cc80d39c9d09802a")){
			if(type.equals("getallclass")){//通过学校名字查询所有班级
				obj = getallclass(json);
			}else if(type.equals("getallstudent")){//通过班级id查询所有学生
				obj = getallstudent(json);
			}else if(type.equals("deleteepc")){//通过epc值进行删除相应的epc
				obj = deleteepc(json);
			}else if(type.equals("findallepcclassid")){//通过班级id查询所有的epc
				obj = findallepcclassid(json);
			}else if(type.equals("saveepc")){//通过学生id和epc值进行数据保存
				obj = saveepc(json);
			}else if(type.equals("queryEpc")){//通过epc查询是否已绑定
				obj = queryEpc(json);
			}else if(type.equals("saveepcio")){//通过epc，ioType，ioDate，rfidId，studentId保存学生进出校园数据
				obj = saveepcio(json);
			}else if(type.equals("getonschoolstudent")){//通过学校名字查询所有在校的学生
				obj = getonschool(json);
			}
			return (R)obj ;
		}else{
			return R.error("token错误");
		}
	}
	
	public Object getonschool(JSONObject json){
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
		obj = R.ok().put(DATA, getonschoolstudent(list));
		DbContextHolder.setDbType(DBTypeEnum.MYSQL);
		return obj;
	}
	
	public Map<String, Object> getonschoolstudent(List<ClassEntity> list){
		Map<String, Object> map = new HashMap<String, Object>();
		Integer i = 0,j=0;
		try {
			DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				ClassEntity classEntity = (ClassEntity) iterator.next();
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("classId", classEntity.getId());
				m.put("begin", 0);
				m.put("limit", 200);
				List<StudentEntity> list1 = studentService.queryList(m);//通过班级id查询班上所有学生
				for (Iterator iterator1 = list1.iterator(); iterator1.hasNext();) {
					StudentEntity studentEntity = (StudentEntity) iterator1.next();
					j++;
					IoEntity ioentity = ioService.queryObjectName(studentEntity.getId());//通过学生id查询所有出入校记录
					if(ioentity != null){
						if(ioentity.getIoType().equals("进")){
							i++;
						}
					}
				}
			}
			DbContextHolder.setDbType(DBTypeEnum.MYSQL);
			map.put("total", j);
			map.put("zxtotal", i);
		} catch (Exception e) {
		}
		return map;
	}
	
	public Object saveepcio(JSONObject json){
		Object obj = null;
		try {
			List<Object> list = new ArrayList<Object>();
			JSONArray array = json.getJSONArray("list");
			DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
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
				list.add("已被学校："+school.getSchoolName() +  classentity.getClassName()+" " +studnet.getStudentName()+"所绑定"); 
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
			obj = R.error("").put(DATA, data);
		}else{
			obj = R.ok();
		}
		return obj;
	}

}
