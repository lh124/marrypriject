package io.renren.controller.married;

import io.renren.entity.married.MarryGetmoneyEntity;
import io.renren.service.married.MarryGetmoneyService;
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
 * @date 2018-01-16 15:19:25
 */
@RestController
@RequestMapping("marrygetmoney")
public class MarryGetmoneyController {
	@Autowired
	private MarryGetmoneyService marryGetmoneyService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("marrygetmoney:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<MarryGetmoneyEntity> marryGetmoneyList = marryGetmoneyService.queryList(query);
		int total = marryGetmoneyService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(marryGetmoneyList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("marrygetmoney:info")
	public R info(@PathVariable("id") Integer id){
		MarryGetmoneyEntity marryGetmoney = marryGetmoneyService.queryObject(id);
		
		return R.ok().put("marryGetmoney", marryGetmoney);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("marrygetmoney:save")
	public R save(@RequestBody MarryGetmoneyEntity marryGetmoney){
		marryGetmoneyService.save(marryGetmoney);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("marrygetmoney:update")
	public R update(@RequestBody MarryGetmoneyEntity marryGetmoney){
		marryGetmoneyService.update(marryGetmoney);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("marrygetmoney:delete")
	public R delete(@RequestBody Integer[] ids){
		marryGetmoneyService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
