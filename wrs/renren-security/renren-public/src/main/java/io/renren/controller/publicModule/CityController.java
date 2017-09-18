package io.renren.controller.publicModule;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import io.renren.annotation.CheckAuth;
import io.renren.annotation.JSONFilter;
import io.renren.entity.CityEntity;
import io.renren.service.CityService;
import io.renren.utils.PageUtils;
import io.renren.utils.Query;
import io.renren.utils.R;


/**
 * 行政区域地州市信息表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-05-04 15:07:49
 */
@Controller("publicCityController")
@RequestMapping("/publicModule/city")
public class CityController {
	@Autowired
	private CityService cityService;
	
	/**
	 * 列表
	 */
	@CheckAuth(needAuth="front:city.list")
	@RequestMapping("/list")
	@ResponseBody
	public Object list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<CityEntity> cityList = cityService.queryList(query);
		int total = cityService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(cityList, total, query.getLimit(), query.getPage());
		
		return pageUtil;
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	public R info(@PathVariable("id") Integer id){
		CityEntity city = cityService.queryObject(id);
		
		return R.ok().put("city", city);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	public R save(@RequestBody CityEntity city){
		cityService.save(city);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	public R update(@RequestBody CityEntity city){
		cityService.update(city);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("city:delete")
	public R delete(@RequestBody Integer[] ids){
		cityService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
