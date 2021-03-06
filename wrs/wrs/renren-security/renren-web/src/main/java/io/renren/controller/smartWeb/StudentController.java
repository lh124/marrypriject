package io.renren.controller.smartWeb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

import io.renren.entity.smart.StudentEntity;
import io.renren.model.json.ResponseDTJson;
import io.renren.service.smart.StudentService;
import io.renren.utils.POIMvcUtil;
import io.renren.utils.PageUtils;
import io.renren.utils.Query;
import io.renren.utils.R;
import io.renren.utils.dataSource.DBTypeEnum;
import io.renren.utils.dataSource.DbContextHolder;


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
		String newPassword = new Sha256Hash("000000").toHex();
		student.setPasswordd(newPassword);
		studentService.save(student);
		DbContextHolder.setDbType(DBTypeEnum.MYSQL);
		return R.ok();
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
		}catch(Exception e){
			e.printStackTrace();
			return R.error("读取Excel发生异常").put("status", ResponseDTJson.FAIL);
		}
		
		StudentEntity student = null;
		// 数字格式化
		List<StudentEntity> studentList = new ArrayList<StudentEntity>(list.size());
		for(int i=1; i<list.size(); i++){
			student = new StudentEntity();
			List<Object> objList = list.get(i);
			
			student.setStudentCode(objList.get(0).toString());
			student.setStudentNo(objList.get(1).toString());
			student.setStudentName(objList.get(2).toString());
			student.setSex(objList.get(3).toString());
			student.setStudentType(objList.get(4).toString());
			student.setClassId(Integer.parseInt(classId));
			
			
			studentList.add(student);
		}
		
		String backe = this.batchSaveUsers(studentList);
		DbContextHolder.setDbType(DBTypeEnum.MYSQL);
		return R.ok(backe).put("status", ResponseDTJson.SUCCESS);
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("uniform_student:update")
	public R update(@RequestBody StudentEntity student){
		DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
		
		if (student != null && !student.getPasswordd().equals("") && student.getPasswordd().length() <30){
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
			List<StudentEntity> newUserList = new ArrayList<StudentEntity>(studentList.size());
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
					newUserList.add(student);
				}
				
				// 如果所有账号都重复
				if (newUserList == null || newUserList.size() == 0)
					return repeatName.toString();
				
				this.studentService.insertBatch(newUserList);
			} 
		}
		return repeatName.toString();
	}
}
