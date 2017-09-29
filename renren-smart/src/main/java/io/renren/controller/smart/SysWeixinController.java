package io.renren.controller.smart;

import io.renren.entity.smart.SysWeixinEntity;
import io.renren.service.smart.SysWeixinService;
import io.renren.utils.PageUtils;
import io.renren.utils.Query;
import io.renren.utils.R;

import java.text.SimpleDateFormat;
import java.util.Date;
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
 * @date 2017-09-27 09:40:45
 */
@RestController
@RequestMapping("sysweixin")
public class SysWeixinController {
	@Autowired
	private SysWeixinService sysWeixinService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sysweixin:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<SysWeixinEntity> sysWeixinList = sysWeixinService.queryList(query);
		int total = sysWeixinService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(sysWeixinList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("sysweixin:info")
	public R info(@PathVariable("id") Integer id){
		SysWeixinEntity sysWeixin = sysWeixinService.queryObject(id);
		return R.ok().put("sysWeixin", sysWeixin);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("sysweixin:save")
	public R save(@RequestBody SysWeixinEntity sysWeixin){
		sysWeixin.setCreatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		sysWeixinService.save(sysWeixin);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("sysweixin:update")
	public R update(@RequestBody SysWeixinEntity sysWeixin){
		sysWeixinService.update(sysWeixin);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("sysweixin:delete")
	public R delete(@RequestBody Integer[] ids){
		sysWeixinService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
