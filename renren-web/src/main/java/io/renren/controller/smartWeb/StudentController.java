package io.renren.controller.smartWeb;

import io.renren.entity.smart.ClassEntity;
import io.renren.entity.smart.SchoolEntity;
import io.renren.entity.smart.StudentEntity;
import io.renren.entity.smart.StudentEpcEntity;
import io.renren.entity.smart.Tongji;
import io.renren.model.json.ResponseDTJson;
import io.renren.service.smart.ClassService;
import io.renren.service.smart.SchoolService;
import io.renren.service.smart.StudentEpcService;
import io.renren.service.smart.StudentService;
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
	
	//老师
	public final static String USER_TYPE_TEACHER = "2";
	//学生
	public final static String USER_TYPE_STUDENT = "1";
	
	
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
	public R list(@RequestParam Map<String, Object> params){
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
		studentService.save(student);
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
			String newPassword = new Sha256Hash(student.getPasswordd()).toHex();
			student.setPasswordd(newPassword);
		}
		
		studentService.update(student);
		DbContextHolder.setDbType(DBTypeEnum.MYSQL);
		return R.ok();
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
					this.studentService.save(student);
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
