package io.renren.controller.smart;

import io.renren.entity.smart.SmartWorkEntity;
import io.renren.service.smart.SmartWorkService;
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
 * @date 2017-09-21 13:38:19
 */
@RestController
@RequestMapping("smartwork")
public class SmartWorkController {
	@Autowired
	private SmartWorkService smartWorkService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("smartwork:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<SmartWorkEntity> smartWorkList = smartWorkService.queryList(query);
		int total = smartWorkService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(smartWorkList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("smartwork:info")
	public R info(@PathVariable("id") Integer id){
		SmartWorkEntity smartWork = smartWorkService.queryObject(id);
		
		return R.ok().put("smartWork", smartWork);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("smartwork:save")
	public R save(@RequestBody SmartWorkEntity smartWork){
		smartWork.setCreatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		smartWorkService.save(smartWork);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("smartwork:update")
	public R update(@RequestBody SmartWorkEntity smartWork){
		smartWorkService.update(smartWork);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("smartwork:delete")
	public R delete(@RequestBody Integer[] ids){
		smartWorkService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
