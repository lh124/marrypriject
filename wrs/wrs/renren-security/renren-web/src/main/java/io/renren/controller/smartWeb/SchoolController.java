package io.renren.controller.smartWeb;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.entity.smart.SchoolEntity;
import io.renren.service.smart.SchoolService;
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
@RequestMapping("/sys/uniform/school")
public class SchoolController {
	@Autowired
	private SchoolService schoolService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("uniform_school:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
		DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
        Query query = new Query(params);
        
        int page = Integer.parseInt(params.get("page").toString());
        int limit = Integer.parseInt(params.get("limit").toString());
        query.put("begin", (page-1)*limit);

		List<SchoolEntity> schoolList = schoolService.queryList(query);
		int total = schoolService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(schoolList, total, query.getLimit(), query.getPage());
		
		DbContextHolder.setDbType(DBTypeEnum.MYSQL);
		return R.ok().put("page", pageUtil);
	}
	
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("uniform_school:info")
	public R info(@PathVariable("id") Integer id){
		DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
		SchoolEntity school = schoolService.queryObject(id);
		DbContextHolder.setDbType(DBTypeEnum.MYSQL);
		return R.ok().put("school", school);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("uniform_school:save")
	public R save(@RequestBody SchoolEntity school){
		DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
		schoolService.save(school);
		DbContextHolder.setDbType(DBTypeEnum.MYSQL);
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("uniform_school:update")
	public R update(@RequestBody SchoolEntity school){
		DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
		schoolService.update(school);
		DbContextHolder.setDbType(DBTypeEnum.MYSQL);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("uniform_school:delete")
	public R delete(@RequestBody Integer[] ids){
		DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
		schoolService.deleteBatch(ids);
		DbContextHolder.setDbType(DBTypeEnum.MYSQL);
		return R.ok();
	}
	
	
}
