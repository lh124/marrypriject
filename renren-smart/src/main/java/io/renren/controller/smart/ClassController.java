package io.renren.controller.smart;

import io.renren.constant.ControllerConstant;
import io.renren.entity.smart.ClassEntity;
import io.renren.entity.smart.StudentEntity;
import io.renren.service.smart.ClassService;
import io.renren.utils.R;
import io.renren.utils.dataSource.DBTypeEnum;
import io.renren.utils.dataSource.DbContextHolder;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 
 * 
 * @author yuanbichang
 * @email 673290684@qq.com
 * @date 2017-08-28 12:02:26
 */
@RestController("classControllerSmart")
@RequestMapping("/smart/class")
public class ClassController {
	@Autowired
	private ClassService classService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	public R list(@RequestParam Map<String, Object> params, HttpSession session){
		//查询列表数据
        DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
		
        StudentEntity student = (StudentEntity) session.getAttribute(ControllerConstant.SESSION_SMART_USER_KEY);
		ClassEntity cla = this.classService.selectById(student.getClassId());
		
		DbContextHolder.setDbType(DBTypeEnum.MYSQL);
		
		return R.ok().put("cla", cla);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("uniform_class:info")
	public R info(@PathVariable("id") Integer id){
		DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
		ClassEntity classe = classService.queryObject(id);
		
		DbContextHolder.setDbType(DBTypeEnum.MYSQL);
		return R.ok().put("classe", classe);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("uniform_class:save")
	public R save(@RequestBody ClassEntity classe){
		DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
		classService.save(classe);
		
		DbContextHolder.setDbType(DBTypeEnum.MYSQL);
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("uniform_class:update")
	public R update(@RequestBody ClassEntity classe){
		DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
		classService.update(classe);
		
		DbContextHolder.setDbType(DBTypeEnum.MYSQL);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("uniform_class:delete")
	public R delete(@RequestBody Integer[] ids){
		DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
		classService.deleteBatch(ids);
		
		DbContextHolder.setDbType(DBTypeEnum.MYSQL);
		return R.ok();
	}
	
}
