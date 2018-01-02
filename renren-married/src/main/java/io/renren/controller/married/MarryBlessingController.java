package io.renren.controller.married;

import io.renren.entity.married.MarryBlessingEntity;
import io.renren.service.married.MarryBlessingService;
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
 * @date 2017-12-28 15:08:23
 */
@RestController
@RequestMapping("marryblessing")
public class MarryBlessingController {
	@Autowired
	private MarryBlessingService marryBlessingService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("marryblessing:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<MarryBlessingEntity> marryBlessingList = marryBlessingService.queryList(query);
		int total = marryBlessingService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(marryBlessingList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("marryblessing:info")
	public R info(@PathVariable("id") Integer id){
		MarryBlessingEntity marryBlessing = marryBlessingService.queryObject(id);
		
		return R.ok().put("marryBlessing", marryBlessing);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("marryblessing:save")
	public R save(@RequestBody MarryBlessingEntity marryBlessing){
		marryBlessingService.save(marryBlessing);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("marryblessing:update")
	public R update(@RequestBody MarryBlessingEntity marryBlessing){
		marryBlessingService.update(marryBlessing);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("marryblessing:delete")
	public R delete(@RequestBody Integer[] ids){
		marryBlessingService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
