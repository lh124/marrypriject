package io.renren.controller.married;

import io.renren.entity.married.MarryOrdersEntity;
import io.renren.service.married.MarryOrdersService;
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
 * @date 2018-01-11 11:01:43
 */
@RestController
@RequestMapping("marryorders")
public class MarryOrdersController {
	@Autowired
	private MarryOrdersService marryOrdersService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("marryorders:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<MarryOrdersEntity> marryOrdersList = marryOrdersService.queryList(query);
		int total = marryOrdersService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(marryOrdersList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("marryorders:info")
	public R info(@PathVariable("id") Integer id){
		MarryOrdersEntity marryOrders = marryOrdersService.queryObject(id);
		
		return R.ok().put("marryOrders", marryOrders);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("marryorders:save")
	public R save(@RequestBody MarryOrdersEntity marryOrders){
		marryOrdersService.save(marryOrders);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("marryorders:update")
	public R update(@RequestBody MarryOrdersEntity marryOrders){
		marryOrdersService.update(marryOrders);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("marryorders:delete")
	public R delete(@RequestBody Integer[] ids){
		marryOrdersService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
