package io.renren.controller.smart;

import io.renren.entity.smart.ClassNoticeEntity;
import io.renren.service.smart.ClassNoticeService;
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
 * @date 2017-09-20 10:04:06
 */
@RestController
@RequestMapping("classnotice")
public class ClassNoticeController {
	@Autowired
	private ClassNoticeService classNoticeService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("classnotice:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<ClassNoticeEntity> classNoticeList = classNoticeService.queryList(query);
		int total = classNoticeService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(classNoticeList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("classnotice:info")
	public R info(@PathVariable("id") Integer id){
		ClassNoticeEntity classNotice = classNoticeService.queryObject(id);
		
		return R.ok().put("classNotice", classNotice);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("classnotice:save")
	public R save(@RequestBody ClassNoticeEntity classNotice){
		classNotice.setCreatetime(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		classNoticeService.save(classNotice);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("classnotice:update")
	public R update(@RequestBody ClassNoticeEntity classNotice){
		classNoticeService.update(classNotice);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("classnotice:delete")
	public R delete(@RequestBody Integer[] ids){
		classNoticeService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
