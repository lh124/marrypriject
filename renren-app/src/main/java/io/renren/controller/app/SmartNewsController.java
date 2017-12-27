package io.renren.controller.app;

import io.renren.entity.app.SmartNewsEntity;
import io.renren.service.SmartNewsService;
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
 * @date 2017-12-26 08:58:40
 */
@RestController
@RequestMapping("smartnews")
public class SmartNewsController {
	@Autowired
	private SmartNewsService smartNewsService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("smartnews:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<SmartNewsEntity> smartNewsList = smartNewsService.queryList(query);
		int total = smartNewsService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(smartNewsList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("smartnews:info")
	public R info(@PathVariable("id") Integer id){
		SmartNewsEntity smartNews = smartNewsService.queryObject(id);
		
		return R.ok().put("smartNews", smartNews);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("smartnews:save")
	public R save(@RequestBody SmartNewsEntity smartNews){
		smartNewsService.save(smartNews);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("smartnews:update")
	public R update(@RequestBody SmartNewsEntity smartNews){
		smartNewsService.update(smartNews);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("smartnews:delete")
	public R delete(@RequestBody Integer[] ids){
		smartNewsService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
