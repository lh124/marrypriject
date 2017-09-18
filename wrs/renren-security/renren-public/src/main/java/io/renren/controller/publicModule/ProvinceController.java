package io.renren.controller.publicModule;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.entity.ProvinceEntity;
import io.renren.service.ProvinceService;
import io.renren.utils.PageUtils;
import io.renren.utils.Query;
import io.renren.utils.R;


/**
 * 省份信息表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-05-04 15:07:49
 */
@RestController("publicProvinceController")
@RequestMapping("/publicModule/province")
public class ProvinceController {
	@Autowired
	private ProvinceService provinceService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<ProvinceEntity> provinceList = provinceService.queryList(query);
		int total = provinceService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(provinceList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	public R info(@PathVariable("id") Integer id){
		ProvinceEntity province = provinceService.queryObject(id);
		
		return R.ok().put("province", province);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("province:save")
	public R save(@RequestBody ProvinceEntity province){
		provinceService.save(province);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("province:update")
	public R update(@RequestBody ProvinceEntity province){
		provinceService.update(province);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("province:delete")
	public R delete(@RequestBody Integer[] ids){
		provinceService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
