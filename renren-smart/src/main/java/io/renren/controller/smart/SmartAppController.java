package io.renren.controller.smart;

import io.renren.entity.app.SmartAppEntity;
import io.renren.service.smart.SmartAppService;
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
 * @date 2018-01-13 08:59:53
 */
@RestController
@RequestMapping("smartapp")
public class SmartAppController {
	@Autowired
	private SmartAppService smartAppService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("smartapp:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<SmartAppEntity> smartAppList = smartAppService.queryList(query);
		int total = smartAppService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(smartAppList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("smartapp:info")
	public R info(@PathVariable("id") Integer id){
		SmartAppEntity smartApp = smartAppService.queryObject(id);
		return R.ok().put("smartApp", smartApp);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("smartapp:save")
	public R save(@RequestBody SmartAppEntity smartApp){
		smartAppService.insert(smartApp);
		return R.ok().put("id", smartApp.getId());
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("smartapp:update")
	public R update(@RequestBody SmartAppEntity smartApp){
		smartAppService.update(smartApp);
		return R.ok().put("id", smartApp.getId());
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("smartapp:delete")
	public R delete(@RequestBody Integer[] ids){
		smartAppService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
