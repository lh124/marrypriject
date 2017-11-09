package io.renren.controller.smart;

import io.renren.entity.smart.SmartCoursewareEntity;
import io.renren.service.smart.SmartCoursewareService;
import io.renren.utils.PageUtils;
import io.renren.utils.Query;
import io.renren.utils.R;

import java.text.SimpleDateFormat;
import java.util.Date;
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
 * @date 2017-09-21 15:11:15
 */
@RestController
@RequestMapping("smartcourseware")
public class SmartCoursewareController {
	@Autowired
	private SmartCoursewareService smartCoursewareService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("smartcourseware:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<SmartCoursewareEntity> smartCoursewareList = smartCoursewareService.queryList(query);
		int total = smartCoursewareService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(smartCoursewareList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("smartcourseware:info")
	public R info(@PathVariable("id") Integer id){
		SmartCoursewareEntity smartCourseware = smartCoursewareService.queryObject(id);
		
		return R.ok().put("smartCourseware", smartCourseware);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("smartcourseware:save")
	public R save(@RequestBody SmartCoursewareEntity smartCourseware){
		smartCourseware.setCreatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		smartCoursewareService.save(smartCourseware);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("smartcourseware:update")
	public R update(@RequestBody SmartCoursewareEntity smartCourseware){
		smartCoursewareService.update(smartCourseware);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("smartcourseware:delete")
	public R delete(@RequestBody Integer[] ids){
		smartCoursewareService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
