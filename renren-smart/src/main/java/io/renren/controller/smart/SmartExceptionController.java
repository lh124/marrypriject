package io.renren.controller.smart;

import io.renren.entity.smart.SmartExceptionEntity;
import io.renren.service.smart.SmartExceptionService;
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
 * @date 2017-12-07 12:40:40
 */
@RestController
@RequestMapping("smartexception")
public class SmartExceptionController {
	@Autowired
	private SmartExceptionService smartExceptionService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("smartexception:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<SmartExceptionEntity> smartExceptionList = smartExceptionService.queryList(query);
		int total = smartExceptionService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(smartExceptionList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("smartexception:info")
	public R info(@PathVariable("id") Integer id){
		SmartExceptionEntity smartException = smartExceptionService.queryObject(id);
		
		return R.ok().put("smartException", smartException);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("smartexception:save")
	public R save(@RequestBody SmartExceptionEntity smartException){
		smartExceptionService.save(smartException);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("smartexception:update")
	public R update(@RequestBody SmartExceptionEntity smartException){
		smartExceptionService.update(smartException);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("smartexception:delete")
	public R delete(@RequestBody Integer[] ids){
		smartExceptionService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
