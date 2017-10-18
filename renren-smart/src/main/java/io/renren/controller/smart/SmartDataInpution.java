package io.renren.controller.smart;

import io.renren.entity.smart.ClassEntity;
import io.renren.entity.smart.SchoolEntity;
import io.renren.entity.smart.StudentEntity;
import io.renren.entity.smart.StudentEpcEntity;
import io.renren.service.smart.ClassService;
import io.renren.service.smart.SchoolService;
import io.renren.service.smart.StudentEpcService;
import io.renren.service.smart.StudentService;
import io.renren.utils.R;
import io.renren.utils.dataSource.DBTypeEnum;
import io.renren.utils.dataSource.DbContextHolder;

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
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("/getData")
	@ResponseBody
	public R getData(HttpServletRequest request,HttpServletResponse response){
		String key = request.getParameter("key");
		JSONObject json = JSONObject.fromObject(key);
		String type = json.getString("type");
		String token = json.getString("token");
		Object obj = null;
		if(token.equals("bcb15f821479b4d5772bd0ca866c00ad5f926e3580720659cc80d39c9d09802a")){
			if(type.equals("getallclass")){
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
				obj = R.ok().put("data", list);
			}else if(type.equals("getallstudent")){
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
				obj = R.ok().put("data", list);
			}else if(type.equals("deleteepc")){
				DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
				studentEpcService.deleteEpc(json.getString("epc"));
				DbContextHolder.setDbType(DBTypeEnum.MYSQL);
				obj = R.ok();
			}else if(type.equals("saveepc")){
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
						list.add("【EPC："+se.getEpc() + "已被学校："+school.getSchoolName() +  classentity.getClassName()+" " +studnet.getStudentName()+"所绑定】"); 
					}else{
						StudentEpcEntity see = new StudentEpcEntity();
						see.setStudentId(Integer.parseInt(json.getString("student_id")));
						see.setEpc(object.getString("epc").replace(" ", ""));
						studentEpcService.save(see);
					}
				}
				DbContextHolder.setDbType(DBTypeEnum.MYSQL);
				if(list != null){
					obj = R.ok().put("data", list);
				}
			}
			return (R)obj ;
		}else{
			return R.error("token错误");
		}
	}

}
