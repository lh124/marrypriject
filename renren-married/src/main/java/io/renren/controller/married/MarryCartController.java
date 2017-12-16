package io.renren.controller.married;

import io.renren.entity.married.MarryCartEntity;
import io.renren.service.married.MarryCartService;
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
 * @date 2017-12-16 10:23:15
 */
@RestController
@RequestMapping("marrycart")
public class MarryCartController {
	@Autowired
	private MarryCartService marryCartService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("marrycart:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<MarryCartEntity> marryCartList = marryCartService.queryList(query);
		int total = marryCartService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(marryCartList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("marrycart:info")
	public R info(@PathVariable("id") Integer id){
		MarryCartEntity marryCart = marryCartService.queryObject(id);
		
		return R.ok().put("marryCart", marryCart);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("marrycart:save")
	public R save(@RequestBody MarryCartEntity marryCart){
		marryCartService.save(marryCart);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("marrycart:update")
	public R update(@RequestBody MarryCartEntity marryCart){
		marryCartService.update(marryCart);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("marrycart:delete")
	public R delete(@RequestBody Integer[] ids){
		marryCartService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
