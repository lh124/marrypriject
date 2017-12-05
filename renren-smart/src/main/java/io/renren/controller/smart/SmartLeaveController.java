package io.renren.controller.smart;

import io.renren.entity.smart.SmartLeaveEntity;
import io.renren.service.smart.SmartLeaveService;
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
 * @date 2017-12-04 15:12:45
 */
@RestController
@RequestMapping("smartleave")
public class SmartLeaveController {
	@Autowired
	private SmartLeaveService smartLeaveService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("smartleave:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<SmartLeaveEntity> smartLeaveList = smartLeaveService.queryList(query);
		int total = smartLeaveService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(smartLeaveList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("smartleave:info")
	public R info(@PathVariable("id") Integer id){
		SmartLeaveEntity smartLeave = smartLeaveService.queryObject(id);
		
		return R.ok().put("smartLeave", smartLeave);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("smartleave:save")
	public R save(@RequestBody SmartLeaveEntity smartLeave){
		smartLeaveService.save(smartLeave);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("smartleave:update")
	public R update(@RequestBody SmartLeaveEntity smartLeave){
		smartLeaveService.update(smartLeave);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("smartleave:delete")
	public R delete(@RequestBody Integer[] ids){
		smartLeaveService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
