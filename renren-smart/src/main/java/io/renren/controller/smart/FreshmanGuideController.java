package io.renren.controller.smart;

import io.renren.entity.smart.FreshmanGuideEntity;
import io.renren.service.smart.FreshmanGuideService;
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
 * @date 2017-09-20 16:07:00
 */
@RestController
@RequestMapping("freshmanguide")
public class FreshmanGuideController {
	@Autowired
	private FreshmanGuideService freshmanGuideService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("freshmanguide:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<FreshmanGuideEntity> freshmanGuideList = freshmanGuideService.queryList(query);
		int total = freshmanGuideService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(freshmanGuideList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("freshmanguide:info")
	public R info(@PathVariable("id") Integer id){
		FreshmanGuideEntity freshmanGuide = freshmanGuideService.queryObject(id);
		
		return R.ok().put("freshmanGuide", freshmanGuide);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("freshmanguide:save")
	public R save(@RequestBody FreshmanGuideEntity freshmanGuide){
		freshmanGuideService.save(freshmanGuide);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("freshmanguide:update")
	public R update(@RequestBody FreshmanGuideEntity freshmanGuide){
		freshmanGuideService.update(freshmanGuide);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("freshmanguide:delete")
	public R delete(@RequestBody Integer[] ids){
		freshmanGuideService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
