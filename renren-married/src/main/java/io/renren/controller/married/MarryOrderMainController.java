package io.renren.controller.married;

import io.renren.entity.married.MarryOrderMainEntity;
import io.renren.service.married.MarryOrderMainService;
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
@RequestMapping("marryordermain")
public class MarryOrderMainController {
	@Autowired
	private MarryOrderMainService marryOrderMainService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("marryordermain:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<MarryOrderMainEntity> marryOrderMainList = marryOrderMainService.queryList(query);
		int total = marryOrderMainService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(marryOrderMainList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{orderId}")
	@RequiresPermissions("marryordermain:info")
	public R info(@PathVariable("orderId") Integer orderId){
		MarryOrderMainEntity marryOrderMain = marryOrderMainService.queryObject(orderId);
		
		return R.ok().put("marryOrderMain", marryOrderMain);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("marryordermain:save")
	public R save(@RequestBody MarryOrderMainEntity marryOrderMain){
		marryOrderMainService.save(marryOrderMain);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("marryordermain:update")
	public R update(@RequestBody MarryOrderMainEntity marryOrderMain){
		marryOrderMainService.update(marryOrderMain);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("marryordermain:delete")
	public R delete(@RequestBody Integer[] orderIds){
		marryOrderMainService.deleteBatch(orderIds);
		
		return R.ok();
	}
	
}
