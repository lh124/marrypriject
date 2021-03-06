package io.renren.controller.smartWeb;

import io.renren.entity.app.SmartNewsEntity;
import io.renren.entity.smart.ClassEntity;
import io.renren.entity.smart.ClassNoticeEntity;
import io.renren.entity.smart.PhotoClassWorkMsgEntity;
import io.renren.entity.smart.SchoolEntity;
import io.renren.entity.smart.SchoolNoticeEntity;
import io.renren.entity.smart.SmartGradeEntity;
import io.renren.entity.smart.StudentEntity;
import io.renren.entity.smart.StudentEpcEntity;
import io.renren.entity.smart.Studenttongji;
import io.renren.entity.smart.Tongji;
import io.renren.model.json.ResponseDTJson;
import io.renren.service.smart.ClassNoticeService;
import io.renren.service.smart.ClassService;
import io.renren.service.smart.PhotoClassWorkMsgService;
import io.renren.service.smart.SchoolNoticeService;
import io.renren.service.smart.SchoolService;
import io.renren.service.smart.SmartGradeService;
import io.renren.service.smart.SmartNewsService;
import io.renren.service.smart.StudentEpcService;
import io.renren.service.smart.StudentService;
import io.renren.util.JiguanUtil;
import io.renren.utils.POIMvcUtil;
import io.renren.utils.PageUtils;
import io.renren.utils.Query;
import io.renren.utils.R;
import io.renren.utils.dataSource.DBTypeEnum;
import io.renren.utils.dataSource.DbContextHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jmessage.api.JMessageClient;
import cn.jmessage.api.common.model.RegisterInfo;
import cn.jmessage.api.common.model.RegisterInfo.Builder;
import cn.jmessage.api.common.model.cross.CrossGroup;

import com.baomidou.mybatisplus.mapper.EntityWrapper;


/**
 * 
 * 
 * @author yuanbichang
 * @email 673290684@qq.com
 * @date 2017-08-28 12:02:26
 */
@RestController
@RequestMapping("/sys/uniform/student")
public class StudentController {
	@Autowired
	private StudentService studentService;
	@Autowired
	private StudentEpcService studentEpcService;
	@Autowired
	private ClassService classService;
	@Autowired
	private SchoolService schoolService;
	@Autowired
	private SmartNewsService smartNewsService;
	@Autowired
	private ClassNoticeService classNoticeService;
	@Autowired
	private SchoolNoticeService schoolNoticeService;
	@Autowired
	private PhotoClassWorkMsgService photoClassWorkMsgService;
	@Autowired
	private SmartGradeService smartGradeService;
	
	//老师
	public final static String USER_TYPE_TEACHER = "2";
	//学生
	public final static String USER_TYPE_STUDENT = "1";
	
	/**
	 * 头像上传与校服绑定情况统计
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/getimageandxiaofutongji")
	public R getimageandxiaofutongji(HttpServletRequest request){
		//查询列表数据
        DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("classname", classService.queryObject(Integer.parseInt(request.getParameter("id"))).getClassName());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("begin", 0);
		map.put("page", 1);
		map.put("limit", 1000);
		map.put("classId", Integer.parseInt(request.getParameter("id")));
		List<StudentEntity> student = studentService.queryList(map);
		m.put("total", student.size());
		map.put("classId", " class_id = " +request.getParameter("id")+" and  pic is null ");
		m.put("noimage", studentService.queryListtongjiimgxf(map));
		List<Studenttongji> liststudenttongji = new ArrayList<Studenttongji>();
		for (Iterator iterator = student.iterator(); iterator.hasNext();) {
			StudentEntity studentEntity = (StudentEntity) iterator.next();
			map.put("student_id", studentEntity.getId());
			Studenttongji stj = new Studenttongji();
			stj.setTotal(studentEpcService.queryListtongji(map).size());
			stj.setId(studentEntity.getId());
			stj.setStudentName(studentEntity.getStudentName());
			liststudenttongji.add(stj);
		}
		m.put("xiaofutotal", liststudenttongji);
		DbContextHolder.setDbType(DBTypeEnum.MYSQL);
		return R.ok().put("tongji", JSONObject.fromObject(m));
	}
	
	
	/**
	 * 通过学生id保存epc
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/getstudentepc")
	public R getstudentepc(HttpServletRequest request){
		DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("student_id", Integer.parseInt(request.getParameter("id")));
		List<StudentEpcEntity> list = studentEpcService.queryListtongji(map);
		DbContextHolder.setDbType(DBTypeEnum.MYSQL);
		StringBuffer sb = new StringBuffer();
		if(list.size() > 0){
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				StudentEpcEntity studentEpcEntity = (StudentEpcEntity) iterator.next();
				sb.append(studentEpcEntity.getEpc()+", ");
			}
			return R.ok().put("epc", sb.toString().substring(0, sb.toString().length()-1));
		}else{
			return R.ok().put("epc", null);
		}
	}
	/**
	 * 通过学生id保存epc
	 */
	@RequestMapping("/saveepc")
	public R saveEPC(HttpServletRequest request){
		String[] epclist = request.getParameter("epc").split(",");
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < epclist.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("epc", epclist[i].replace(" ", ""));
			DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
			StudentEpcEntity se = studentEpcService.queryObjectIdEpc(map);
			if(se != null){
				StudentEntity studnet = studentService.queryObject(se.getStudentId());
				ClassEntity classentity = classService.queryObject(studnet.getClassId());
				SchoolEntity school = schoolService.queryObject(classentity.getSchoolId());
				sb.append("【EPC："+se.getEpc() + "已被学校："+school.getSchoolName() +  classentity.getClassName()+" " +studnet.getStudentName()+"所绑定】"); 
			}else{
				StudentEpcEntity see = new StudentEpcEntity();
				see.setStudentId(Integer.parseInt(request.getParameter("id")));
				see.setEpc(epclist[i].replace(" ", ""));
				studentEpcService.save(see);
			}
			DbContextHolder.setDbType(DBTypeEnum.MYSQL);
		}
		if(sb.toString() != null && !sb.toString().equals("")){
			return R.ok().put("faile", sb.toString());
		}else{
			return R.ok().put("faile", null);
		}
	}
	
	/**
	 * 微信绑定统计列表
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/list_tongji")
	public R list_tongji(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("classId", null);
		map.put("schoolId", request.getParameter("schoolId"));
		Tongji tongji = new Tongji();
		String data1 = "";
		String data2 = "";
		String name = "";
		DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
		List<StudentEntity> classList = studentService.queryListtongji(map);
		for (Iterator iterator = classList.iterator(); iterator.hasNext();) {
			StudentEntity studentEntity = (StudentEntity) iterator.next();
			name += studentEntity.getStudentName() + ",";
			map.put("classId", studentEntity.getId());
			List<StudentEntity> studentList = studentService.queryListtongji(map);
			for (Iterator iterator2 = studentList.iterator(); iterator2.hasNext();) {
				StudentEntity student = (StudentEntity) iterator2.next();
				data1 +=  studentEntity.getStudentName() + ","+student.getStudentCode()+",";
				data2 += studentEntity.getStudentName() + ","+(Integer.parseInt(student.getStudentNo()) - Integer.parseInt(student.getStudentCode()))+",";
			}
		}
		DbContextHolder.setDbType(DBTypeEnum.MYSQL);
		tongji.setData1(data1.substring(0, data1.length() - 1));
		tongji.setData2(data2.substring(0, data2.length() - 1));
		tongji.setName(name.substring(0, name.length() - 1));
		return R.ok().put("tongji", tongji);
	}
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("uniform_student:list")
	public R list(@RequestParam Map<String, Object> params,HttpServletRequest request){
		if(request.getParameter("gradeId") != null && !"".equals(request.getParameter("gradeId"))){
			String id = request.getParameter("gradeId");
			params.put("schoolId", smartGradeService.queryObject(Integer.parseInt(id)).getSchoolId());
		}
		if(request.getParameter("userType") != null && !"".equals(request.getParameter("userType"))){
			params.put("userType", request.getParameter("userType"));
		}
		//查询列表数据
        Query query = new Query(params);
        int page = Integer.parseInt(params.get("page").toString());
        int limit = Integer.parseInt(params.get("limit").toString());
        query.put("begin", (page-1)*limit);
        DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
		List<StudentEntity> studentList = studentService.queryList(query);
		int total = studentService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(studentList, total, query.getLimit(), query.getPage());
		
		DbContextHolder.setDbType(DBTypeEnum.MYSQL);
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("uniform_student:info")
	public R info(@PathVariable("id") Integer id){
		DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
		StudentEntity student = studentService.queryObject(id);
		DbContextHolder.setDbType(DBTypeEnum.MYSQL);
		return R.ok().put("student", student);
	}
	
	/**
	 * 保存
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/save")
	@RequiresPermissions("uniform_student:save")
	public R save(@RequestBody StudentEntity student){
		DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
		EntityWrapper<StudentEntity> wrraper = new EntityWrapper<StudentEntity>(student);
		StudentEntity st = this.studentService.selectOne(wrraper);
		if(st != null){
			return R.ok().put("fail", "学号：" + st.getStudentNo() + " 重复");
		}
		String newPassword = new Sha256Hash("000000").toHex();
		student.setPasswordd(newPassword);
		studentService.insert(student);
		userJiguang(student.getId());
		if(student.getUserType().equals("1")){
			ClassEntity classEntity = classService.queryObject(student.getClassId());//通过学生id获取班级信息
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("offset", 0);
			map.put("limit", 99999999);
			map.put("sidx", null);
			map.put("order", null);
			map.put("classId", classEntity.getId());
			List<ClassNoticeEntity> classNoticeList = classNoticeService.queryList(map);
			for (Iterator iterator = classNoticeList.iterator(); iterator.hasNext();) {
				ClassNoticeEntity classNoticeEntity = (ClassNoticeEntity) iterator.next();
				SmartNewsEntity sne = new SmartNewsEntity();
				sne.setNewsid(classNoticeEntity.getId());
				sne.setNewstype(0);
				sne.setStates(2);
				sne.setUserId(student.getId());
				smartNewsService.save(sne);
			}
			
			List<PhotoClassWorkMsgEntity> photoClassWorkList = photoClassWorkMsgService.queryList(map);
			for (Iterator iterator = photoClassWorkList.iterator(); iterator.hasNext();) {
				PhotoClassWorkMsgEntity classNoticeEntity = (PhotoClassWorkMsgEntity) iterator.next();
				SmartNewsEntity sne = new SmartNewsEntity();
				sne.setNewsid(Integer.parseInt(classNoticeEntity.getId().toString()));
				sne.setNewstype(0);
				sne.setStates(3);
				sne.setUserId(student.getId());
				smartNewsService.save(sne);
			}
			
			SmartGradeEntity gradeEntity = smartGradeService.queryObject(classEntity.getGradeId());
			map.put("schoolid", gradeEntity.getSchoolId());
			List<SchoolNoticeEntity> schoolNoticeList = schoolNoticeService.queryList(map);
			for (Iterator iterator = schoolNoticeList.iterator(); iterator.hasNext();) {
				SchoolNoticeEntity schoolNoticeEntity = (SchoolNoticeEntity) iterator.next();
				SmartNewsEntity sne = new SmartNewsEntity();
				sne.setNewsid(schoolNoticeEntity.getId());
				sne.setNewstype(0);
				sne.setStates(1);
				sne.setUserId(student.getId());
				smartNewsService.save(sne);
			}
			
		}else{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("offset", 0);
			map.put("limit", 99999999);
			map.put("sidx", null);
			map.put("order", null);
			SchoolEntity schoolEntity = schoolService.queryObject(student.getSchoolId());//通过学校id获取学校信息
			map.put("schoolid", schoolEntity.getId());
			List<SchoolNoticeEntity> schoolNoticeList = schoolNoticeService.queryList(map);
			for (Iterator iterator = schoolNoticeList.iterator(); iterator.hasNext();) {
				SchoolNoticeEntity schoolNoticeEntity = (SchoolNoticeEntity) iterator.next();
				SmartNewsEntity sne = new SmartNewsEntity();
				sne.setNewsid(schoolNoticeEntity.getId());
				sne.setNewstype(0);
				sne.setStates(1);
				sne.setUserId(student.getId());
				smartNewsService.save(sne);
			}
		}
		DbContextHolder.setDbType(DBTypeEnum.MYSQL); 
		return R.ok().put("fail", "操作成功");
	}
	
	@ResponseBody
	@RequestMapping(value="/saveByExcel",method=RequestMethod.POST)
	@RequiresPermissions("uniform_student:save")
	public R unifromSaveByExcel(@RequestParam(value="file", required=false) MultipartFile file, String classId){
		DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
		if (file == null ) {
			return R.error("为选择文件").put("status", ResponseDTJson.FAIL);
		}
		
		String fileName = file.getOriginalFilename();
		POIMvcUtil poi = new POIMvcUtil();
		List<List<Object>> list = null;
		
		try{
			list = poi.getBankListByExcel(file.getInputStream(), fileName);
			System.out.println(list.size() + "---------------------"+file.getInputStream());
		}catch(Exception e){
			e.printStackTrace();
			return R.error("读取Excel发生异常").put("status", ResponseDTJson.FAIL);
		}
		
		StudentEntity student = null;
		// 数字格式化
		List<StudentEntity> studentList = new ArrayList<StudentEntity>(list.size());
		System.out.println(list.size() + "-----------------------");
		for(int i=1; i<list.size(); i++){
			student = new StudentEntity();
			List<Object> objList = list.get(i);
			if(objList != null && objList.size() > 2){
				
				student.setStudentCode(objList.get(0).toString());
				student.setStudentNo(objList.get(1).toString());
				student.setStudentName(objList.get(2).toString());
				student.setSex(objList.get(3).toString());
				student.setStudentType(objList.get(4).toString());
				//判断是否存在用户类型
				if(objList.size() == 6){
					//判断是否是乱填
					if(!objList.get(5).toString().equals(USER_TYPE_STUDENT) && !objList.get(5).toString().equals(USER_TYPE_TEACHER)){
						student.setUserType(USER_TYPE_STUDENT);
					}else{
						student.setUserType(objList.get(5).toString());
					}
				}else{
					student.setUserType(USER_TYPE_STUDENT);
				}
				student.setClassId(Integer.parseInt(classId));
				studentList.add(student);
			}
		}
		String backe = this.batchSaveUsers(studentList);
		DbContextHolder.setDbType(DBTypeEnum.MYSQL);
		return R.ok(backe).put("status", ResponseDTJson.SUCCESS);
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/updateclassid")
	public R updateclassid(HttpServletRequest request){
		DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
		StudentEntity student = new StudentEntity();
		student.setId(Integer.parseInt(request.getParameter("id")));
		student.setClassId(Integer.parseInt(request.getParameter("classid")));
		studentService.update(student);
		DbContextHolder.setDbType(DBTypeEnum.MYSQL);
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("uniform_student:update")
	public R update(@RequestBody StudentEntity student){
		DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
		if (student != null && !student.getPasswordd().equals("")){
			String newPassword = "";
			if(student.getPasswordd().length() < 30){
				newPassword = new Sha256Hash(student.getPasswordd()).toHex();
			}else{
				newPassword = student.getPasswordd();
			}
			student.setPasswordd(newPassword);
		}
		studentService.update(student);
		DbContextHolder.setDbType(DBTypeEnum.MYSQL);
		userJiguang(student.getId());
		return R.ok();
	}
	
	public void userJiguang(Integer id){
		StudentEntity studentEntity = studentService.queryObject(id);
		JMessageClient client = null;
		if(studentEntity.getGusername() == null || "".equals(studentEntity.getGusername())){
			if("1".equals(studentEntity.getUserType())){
				client = new JMessageClient(JiguanUtil.STUDENTAPPKEY, JiguanUtil.STUDENTMASTERSECRET);
				try {
					ClassEntity classEntity = classService.queryObject(studentEntity.getClassId());
					RegisterInfo[] users = new RegisterInfo[1];
					Builder builder = RegisterInfo.newBuilder();
		        	builder.setUsername(studentEntity.getStudentNo());
		        	builder.setNickname(studentEntity.getStudentName());
		        	builder.setPassword(studentEntity.getPasswordd().substring(0, 15));
		        	builder.setAvatar(studentEntity.getPic());
		        	users[0] = builder.build();
					client.registerUsers(users);
					String[] add_users = new String[1];
					CrossGroup[] groups = new CrossGroup[1];
					add_users[0] = studentEntity.getStudentNo();
					CrossGroup.Builder gb = new CrossGroup.Builder();
			    	gb.setAppKey(JiguanUtil.STUDENTAPPKEY);
			    	gb.setAddUsers(add_users);
			    	groups[0] = gb.build();
			    	client = new JMessageClient(JiguanUtil.TEACHERAPPKEY, JiguanUtil.TEACHERMASTERSECRET);
					client.addOrRemoveCrossGroupMember(classEntity.getGid(), groups);
					studentEntity.setGusername(studentEntity.getStudentNo());
					studentEntity.setGid(classEntity.getGid()+"");
					studentService.update(studentEntity);
				} catch (APIConnectionException e) {
					e.printStackTrace();
				} catch (APIRequestException e) {
					 
				}
			}else if("2".equals(studentEntity.getUserType())){
				try {
					client = new JMessageClient(JiguanUtil.TEACHERAPPKEY, JiguanUtil.TEACHERMASTERSECRET);
					RegisterInfo[] users = new RegisterInfo[1];
					Builder builder = RegisterInfo.newBuilder();
		        	builder.setUsername(studentEntity.getStudentNo());
		        	builder.setNickname(studentEntity.getStudentName());
		        	builder.setPassword(studentEntity.getPasswordd().substring(0, 15));
		        	builder.setAvatar(studentEntity.getPic());
		        	users[0] = builder.build();
					client.registerUsers(users);
					studentEntity.setGusername(studentEntity.getStudentNo());
					studentService.update(studentEntity);
				} catch (APIConnectionException e) {
					e.printStackTrace();
				} catch (APIRequestException e) {
				}
			}
		}else{
			if("1".equals(studentEntity.getUserType()) && ("".equals(studentEntity.getGid()) || studentEntity.getGid()==null)){
				try {
					client = new JMessageClient(JiguanUtil.TEACHERAPPKEY, JiguanUtil.TEACHERMASTERSECRET);
					ClassEntity classEntity = classService.queryObject(studentEntity.getClassId());
					String[] add_users = new String[1];
					CrossGroup[] groups = new CrossGroup[1];
					add_users[0] = studentEntity.getGusername();
					CrossGroup.Builder gb = new CrossGroup.Builder();
			    	gb.setAppKey(JiguanUtil.STUDENTAPPKEY);
			    	gb.setAddUsers(add_users);
			    	groups[0] = gb.build();
					client.addOrRemoveCrossGroupMember(classEntity.getGid(), groups);
					studentEntity.setGusername(studentEntity.getStudentNo());
					studentEntity.setGid(classEntity.getGid()+"");
					studentService.update(studentEntity);
				} catch (APIConnectionException e) {
					e.printStackTrace();
				} catch (APIRequestException e) {
				}
			}
		}
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("uniform_student:delete")
	public R delete(@RequestBody Integer[] ids){
		DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
		studentService.deleteBatch(ids);
		DbContextHolder.setDbType(DBTypeEnum.MYSQL);
		return R.ok();
	}
	
	public String batchSaveUsers(List<StudentEntity> studentList){
		
		StringBuffer repeatName = new StringBuffer("已存在的账号：");
		if ( studentList != null && studentList.size() > 0) {
			// 存储有效用户
//			List<StudentEntity> newUserList = new ArrayList<StudentEntity>(studentList.size());
			// 存储有效用户，同步数据到sqlserver
			//List<Student> studentList = new ArrayList<Student>(userList.size());
			for(StudentEntity student : studentList){
				// 进行账号重复查询
				StudentEntity stud = new StudentEntity();
				if( student.getStudentNo() == null || student.getStudentNo().equals("")){
					repeatName.append(student.getStudentName() + " (学号错误); ");
					continue;
				}
				stud.setStudentNo(student.getStudentNo());
				EntityWrapper<StudentEntity> wrraper = new EntityWrapper<StudentEntity>(stud);
				StudentEntity st = this.studentService.selectOne(wrraper);
				if (st != null) {
					repeatName.append(student.getStudentName() + "(学号重复); ");
				} else {
					String newPassword = new Sha256Hash("000000").toHex();
					student.setPasswordd(newPassword);
					this.studentService.insert(student);
					userJiguang(student.getId());
//					newUserList.add(student);
				}
				
			} 
//			// 如果所有账号都重复
//			if (newUserList == null || newUserList.size() == 0)
//				return repeatName.toString();
//			this.studentService.insertBatch(newUserList);
		}
		if(repeatName.toString().length() == 7){
			return "上传成功";
		}else{
			return repeatName.toString();
		}
	}
}
