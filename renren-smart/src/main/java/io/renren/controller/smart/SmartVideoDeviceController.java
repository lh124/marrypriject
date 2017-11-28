package io.renren.controller.smart;

import io.renren.entity.smart.SmartVideoDeviceEntity;
import io.renren.service.smart.SmartVideoDeviceService;
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
 * @date 2017-11-27 17:07:15
 */
@RestController
@RequestMapping("smartvideodevice")
public class SmartVideoDeviceController {
	@Autowired
	private SmartVideoDeviceService smartVideoDeviceService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("smartvideodevice:list")
	public R list(@RequestParam Map<String, Object> params){
		params.put("studentSee", null);
		params.put("teacherSee", null);
		//查询列表数据
        Query query = new Query(params);

		List<SmartVideoDeviceEntity> smartVideoDeviceList = smartVideoDeviceService.queryList(query);
		int total = smartVideoDeviceService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(smartVideoDeviceList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("smartvideodevice:info")
	public R info(@PathVariable("id") Integer id){
		SmartVideoDeviceEntity smartVideoDevice = smartVideoDeviceService.queryObject(id);
		
		return R.ok().put("smartVideoDevice", smartVideoDevice);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("smartvideodevice:save")
	public R save(@RequestBody SmartVideoDeviceEntity smartVideoDevice){
		smartVideoDeviceService.save(smartVideoDevice);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("smartvideodevice:update")
	public R update(@RequestBody SmartVideoDeviceEntity smartVideoDevice){
		smartVideoDeviceService.update(smartVideoDevice);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("smartvideodevice:delete")
	public R delete(@RequestBody Integer[] ids){
		smartVideoDeviceService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
