package io.renren.controller.smart;

import io.renren.entity.smart.SmartActivitiesEntity;
import io.renren.service.smart.SmartActivitiesService;
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
 * @date 2017-09-21 10:22:32
 */
@RestController
@RequestMapping("smartactivities")
public class SmartActivitiesController {
	@Autowired
	private SmartActivitiesService smartActivitiesService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("smartactivities:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<SmartActivitiesEntity> smartActivitiesList = smartActivitiesService.queryList(query);
		int total = smartActivitiesService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(smartActivitiesList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("smartactivities:info")
	public R info(@PathVariable("id") Integer id){
		SmartActivitiesEntity smartActivities = smartActivitiesService.queryObject(id);
		
		return R.ok().put("smartActivities", smartActivities);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("smartactivities:save")
	public R save(@RequestBody SmartActivitiesEntity smartActivities){
		smartActivitiesService.save(smartActivities);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("smartactivities:update")
	public R update(@RequestBody SmartActivitiesEntity smartActivities){
		smartActivitiesService.update(smartActivities);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("smartactivities:delete")
	public R delete(@RequestBody Integer[] ids){
		smartActivitiesService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
