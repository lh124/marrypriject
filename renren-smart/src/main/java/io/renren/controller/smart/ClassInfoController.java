package io.renren.controller.smart;

import io.renren.entity.smart.ClassInfoEntity;
import io.renren.service.smart.ClassInfoService;
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
 * @date 2017-09-22 09:38:52
 */
@RestController
@RequestMapping("classinfo")
public class ClassInfoController {
	@Autowired
	private ClassInfoService classInfoService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("classinfo:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<ClassInfoEntity> classInfoList = classInfoService.queryList(query);
		int total = classInfoService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(classInfoList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("classinfo:info")
	public R info(@PathVariable("id") Integer id){
		ClassInfoEntity classInfo = classInfoService.queryObject(id);
		
		return R.ok().put("classInfo", classInfo);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("classinfo:save")
	public R save(@RequestBody ClassInfoEntity classInfo){
		classInfoService.save(classInfo);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("classinfo:update")
	public R update(@RequestBody ClassInfoEntity classInfo){
		classInfoService.update(classInfo);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("classinfo:delete")
	public R delete(@RequestBody Integer[] ids){
		classInfoService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
