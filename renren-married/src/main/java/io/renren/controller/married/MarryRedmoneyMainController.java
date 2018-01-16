package io.renren.controller.married;

import io.renren.entity.married.MarryRedmoneyMainEntity;
import io.renren.service.married.MarryRedmoneyMainService;
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
@RequestMapping("marryredmoneymain")
public class MarryRedmoneyMainController {
	@Autowired
	private MarryRedmoneyMainService marryRedmoneyMainService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("marryredmoneymain:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<MarryRedmoneyMainEntity> marryRedmoneyMainList = marryRedmoneyMainService.queryList(query);
		int total = marryRedmoneyMainService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(marryRedmoneyMainList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("marryredmoneymain:info")
	public R info(@PathVariable("id") Integer id){
		MarryRedmoneyMainEntity marryRedmoneyMain = marryRedmoneyMainService.queryObject(id);
		
		return R.ok().put("marryRedmoneyMain", marryRedmoneyMain);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("marryredmoneymain:save")
	public R save(@RequestBody MarryRedmoneyMainEntity marryRedmoneyMain){
		marryRedmoneyMainService.save(marryRedmoneyMain);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("marryredmoneymain:update")
	public R update(@RequestBody MarryRedmoneyMainEntity marryRedmoneyMain){
		marryRedmoneyMainService.update(marryRedmoneyMain);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("marryredmoneymain:delete")
	public R delete(@RequestBody Integer[] ids){
		marryRedmoneyMainService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
