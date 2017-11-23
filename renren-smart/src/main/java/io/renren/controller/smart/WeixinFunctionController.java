package io.renren.controller.smart;

import io.renren.entity.smart.WeixinFunctionEntity;
import io.renren.service.smart.WeixinFunctionService;
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
 * @date 2017-11-23 10:33:58
 */
@RestController
@RequestMapping("weixinfunction")
public class WeixinFunctionController {
	@Autowired
	private WeixinFunctionService weixinFunctionService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("weixinfunction:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<WeixinFunctionEntity> weixinFunctionList = weixinFunctionService.queryList(query);
		int total = weixinFunctionService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(weixinFunctionList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("weixinfunction:info")
	public R info(@PathVariable("id") Integer id){
		WeixinFunctionEntity weixinFunction = weixinFunctionService.queryObject(id);
		
		return R.ok().put("weixinFunction", weixinFunction);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("weixinfunction:save")
	public R save(@RequestBody WeixinFunctionEntity weixinFunction){
		weixinFunctionService.insert(weixinFunction);
		return R.ok().put("id", weixinFunction.getId());
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("weixinfunction:update")
	public R update(@RequestBody WeixinFunctionEntity weixinFunction){
		weixinFunctionService.update(weixinFunction);
		return R.ok().put("id", weixinFunction.getId());
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("weixinfunction:delete")
	public R delete(@RequestBody Integer[] ids){
		weixinFunctionService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
