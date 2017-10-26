package io.renren.controller.photo;

import io.renren.entity.SysPhotoAdminSchoolEntity;
import io.renren.service.SysPhotoAdminSchoolService;
import io.renren.utils.PageUtils;
import io.renren.utils.Query;
import io.renren.utils.R;

import java.util.List;
import java.util.Map;

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
 * @date 2017-10-26 14:38:48
 */
@RestController
@RequestMapping("sysphotoadminschool")
public class SysPhotoAdminSchoolController {
	@Autowired
	private SysPhotoAdminSchoolService sysPhotoAdminSchoolService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sysphotoadminschool:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<SysPhotoAdminSchoolEntity> sysPhotoAdminSchoolList = sysPhotoAdminSchoolService.queryList(query);
		int total = sysPhotoAdminSchoolService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(sysPhotoAdminSchoolList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("sysphotoadminschool:info")
	public R info(@PathVariable("id") Integer id){
		SysPhotoAdminSchoolEntity sysPhotoAdminSchool = sysPhotoAdminSchoolService.queryObject(id);
		
		return R.ok().put("sysPhotoAdminSchool", sysPhotoAdminSchool);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("sysphotoadminschool:save")
	public R save(@RequestBody SysPhotoAdminSchoolEntity sysPhotoAdminSchool){
		sysPhotoAdminSchoolService.save(sysPhotoAdminSchool);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("sysphotoadminschool:update")
	public R update(@RequestBody SysPhotoAdminSchoolEntity sysPhotoAdminSchool){
		sysPhotoAdminSchoolService.update(sysPhotoAdminSchool);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	public R delete(@RequestBody Integer[] ids){
		sysPhotoAdminSchoolService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
