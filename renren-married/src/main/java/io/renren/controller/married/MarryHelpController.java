package io.renren.controller.married;

import io.renren.entity.married.MarryHelpEntity;
import io.renren.service.married.MarryHelpService;
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
 * @date 2018-01-06 10:41:15
 */
@RestController
@RequestMapping("marryhelp")
public class MarryHelpController {
	@Autowired
	private MarryHelpService marryHelpService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("marryhelp:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<MarryHelpEntity> marryHelpList = marryHelpService.queryList(query);
		int total = marryHelpService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(marryHelpList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("marryhelp:info")
	public R info(@PathVariable("id") Integer id){
		MarryHelpEntity marryHelp = marryHelpService.queryObject(id);
		
		return R.ok().put("marryHelp", marryHelp);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("marryhelp:save")
	public R save(@RequestBody MarryHelpEntity marryHelp){
		marryHelpService.save(marryHelp);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("marryhelp:update")
	public R update(@RequestBody MarryHelpEntity marryHelp){
		marryHelpService.update(marryHelp);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("marryhelp:delete")
	public R delete(@RequestBody Integer[] ids){
		marryHelpService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
