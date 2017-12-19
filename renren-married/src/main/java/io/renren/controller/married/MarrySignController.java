package io.renren.controller.married;

import io.renren.entity.married.MarrySignEntity;
import io.renren.service.married.MarrySignService;
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
 * @date 2017-12-18 20:26:38
 */
@RestController
@RequestMapping("marrysign")
public class MarrySignController {
	@Autowired
	private MarrySignService marrySignService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("marrysign:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<MarrySignEntity> marrySignList = marrySignService.queryList(query);
		int total = marrySignService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(marrySignList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("marrysign:info")
	public R info(@PathVariable("id") Integer id){
		MarrySignEntity marrySign = marrySignService.queryObject(id);
		
		return R.ok().put("marrySign", marrySign);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("marrysign:save")
	public R save(@RequestBody MarrySignEntity marrySign){
		marrySignService.save(marrySign);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("marrysign:update")
	public R update(@RequestBody MarrySignEntity marrySign){
		marrySignService.update(marrySign);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("marrysign:delete")
	public R delete(@RequestBody Integer[] ids){
		marrySignService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
