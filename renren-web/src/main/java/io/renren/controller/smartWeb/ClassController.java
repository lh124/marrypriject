package io.renren.controller.smartWeb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;

import io.renren.entity.SysAdminSchoolEntity;
import io.renren.entity.smart.ClassEntity;
import io.renren.service.SysAdminSchoolService;
import io.renren.service.smart.ClassService;
import io.renren.utils.PageUtils;
import io.renren.utils.Query;
import io.renren.utils.R;
import io.renren.utils.RRException;
import io.renren.utils.ShiroUtils;
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
@RequestMapping("/sys/uniform/class")
public class ClassController {
	@Autowired
	private ClassService classService;
	
	@Autowired
	private SysAdminSchoolService sysAdminSchoolService;
	
	/**
	 * 获取当前学校下面的所有班级
	 */
	@RequestMapping("/getschoolallclass")
	public R getschoolallclass(HttpServletRequest request){
		DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
		ClassEntity classe = classService.queryObject(Integer.parseInt(request.getParameter("id")));
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("limit", 100);
		params.put("schoolId", classe.getSchoolId());
		params.put("sidx", null);
		params.put("begin", 0);
		List<ClassEntity> classList = classService.queryList(params);
		DbContextHolder.setDbType(DBTypeEnum.MYSQL);
		return R.ok().put("classList", classList);
	}
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("uniform_class:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

        int page = Integer.parseInt(params.get("page").toString());
        int limit = Integer.parseInt(params.get("limit").toString());
        query.put("begin", (page-1)*limit);
       
        
        Long adminId = ShiroUtils.getUserId();
        Long schoolId = Long.parseLong(params.get("schoolId").toString());
        
        if(schoolId == null)
        	throw new RRException("参数错误");
        
        if(adminId != 1) {
        	// 非超级管理员
        	SysAdminSchoolEntity sase = new SysAdminSchoolEntity();
        	sase.setAdminId(ShiroUtils.getUserId());
        	
        	EntityWrapper<SysAdminSchoolEntity> wrapper = new EntityWrapper<SysAdminSchoolEntity>(sase);
        	List<SysAdminSchoolEntity> asseList = this.sysAdminSchoolService.selectList(wrapper);
        	
        	
        	boolean isBelong = true;
        	for (SysAdminSchoolEntity sysAdmin : asseList){
        		if ( schoolId == sysAdmin.getShoolId() ) {
        			isBelong = false;
        			break;
        		}
        			
        	}
        	
        	if (isBelong){
        		throw new RRException("没有查询权限");
        	}
        }
        
        
        DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
		List<ClassEntity> classList = classService.queryList(query);
		int total = classService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(classList, total, query.getLimit(), query.getPage());
		
		DbContextHolder.setDbType(DBTypeEnum.MYSQL);
		return R.ok().put("page", pageUtil);
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
