package io.renren.controller.photo;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.entity.PhotoFunctionModulesEntity;
import io.renren.service.PhotoFunctionModulesService;
import io.renren.utils.PageUtils;
import io.renren.utils.Query;
import io.renren.utils.R;


/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-05-04 15:07:49
 */
@RestController
@RequestMapping("photofunctionmodules")
public class PhotoFunctionModulesController {
	@Autowired
	private PhotoFunctionModulesService photoFunctionModulesService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("photofunctionmodules:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<PhotoFunctionModulesEntity> photoFunctionModulesList = photoFunctionModulesService.queryList(query);
		int total = photoFunctionModulesService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(photoFunctionModulesList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("photofunctionmodules:info")
	public R info(@PathVariable("id") Integer id){
		PhotoFunctionModulesEntity photoFunctionModules = photoFunctionModulesService.queryObject(id);
		
		return R.ok().put("photoFunctionModules", photoFunctionModules);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("photofunctionmodules:save")
	public R save(@RequestBody PhotoFunctionModulesEntity photoFunctionModules){
		photoFunctionModulesService.save(photoFunctionModules);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("photofunctionmodules:update")
	public R update(@RequestBody PhotoFunctionModulesEntity photoFunctionModules){
		photoFunctionModulesService.update(photoFunctionModules);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("photofunctionmodules:delete")
	public R delete(@RequestBody Integer[] ids){
		photoFunctionModulesService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
