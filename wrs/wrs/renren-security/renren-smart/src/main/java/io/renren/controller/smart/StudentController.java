package io.renren.controller.smart;

import javax.servlet.http.HttpSession;

import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.renren.constant.ControllerConstant;
import io.renren.entity.smart.StudentEntity;
import io.renren.service.smart.StudentService;
import io.renren.utils.R;
import io.renren.utils.RRException;
import io.renren.utils.dataSource.DBTypeEnum;
import io.renren.utils.dataSource.DbContextHolder;


/**
 * 
 * 
 * @author yuanbichang
 * @email 673290684@qq.com
 * @date 2017-08-28 12:02:26
 */
@RestController("studentControllerSmart")
@RequestMapping("/smart/student")
public class StudentController {
	@Autowired
	private StudentService studentService;
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	public R update(StudentEntity student, HttpSession session){
		StudentEntity studentS = (StudentEntity) session.getAttribute(ControllerConstant.SESSION_SMART_USER_KEY);
		
		// 修改密码
		if (student.getPasswordd() != null && !student.getPasswordd().equals("")){
			
			DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
			studentS = this.studentService.selectById(studentS.getId());
			if (studentS.getPasswordd().equals(new Sha256Hash(student.getOpenId()).toHex())){
				
				StudentEntity newStu = new StudentEntity();
				newStu.setId(studentS.getId());
				String newPassword = new Sha256Hash(student.getPasswordd()).toHex();
				newStu.setPasswordd(newPassword);
				
				studentService.update(newStu);
				DbContextHolder.setDbType(DBTypeEnum.MYSQL);
				return R.ok();
			} else {
				DbContextHolder.setDbType(DBTypeEnum.MYSQL);
				RRException e = new RRException("原密码错误");
				throw e;
			}
			
			
		} else {
			RRException e = new RRException("请输入密码");
			throw e;
		}
	}
	
}
