package io.renren.controller.married;

import io.renren.entity.married.MarryRedmoneyDetailEntity;
import io.renren.service.married.MarryRedmoneyDetailService;
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
@RequestMapping("marryredmoneydetail")
public class MarryRedmoneyDetailController {
	@Autowired
	private MarryRedmoneyDetailService marryRedmoneyDetailService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("marryredmoneydetail:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<MarryRedmoneyDetailEntity> marryRedmoneyDetailList = marryRedmoneyDetailService.queryList(query);
		int total = marryRedmoneyDetailService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(marryRedmoneyDetailList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("marryredmoneydetail:info")
	public R info(@PathVariable("id") Integer id){
		MarryRedmoneyDetailEntity marryRedmoneyDetail = marryRedmoneyDetailService.queryObject(id);
		
		return R.ok().put("marryRedmoneyDetail", marryRedmoneyDetail);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("marryredmoneydetail:save")
	public R save(@RequestBody MarryRedmoneyDetailEntity marryRedmoneyDetail){
		marryRedmoneyDetailService.save(marryRedmoneyDetail);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("marryredmoneydetail:update")
	public R update(@RequestBody MarryRedmoneyDetailEntity marryRedmoneyDetail){
		marryRedmoneyDetailService.update(marryRedmoneyDetail);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("marryredmoneydetail:delete")
	public R delete(@RequestBody Integer[] ids){
		marryRedmoneyDetailService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
