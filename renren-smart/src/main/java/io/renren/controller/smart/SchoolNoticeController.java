package io.renren.controller.smart;

import io.renren.entity.smart.SchoolNoticeEntity;
import io.renren.service.smart.SchoolNoticeService;
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
 * @date 2017-09-19 09:12:53
 */
@RestController
@RequestMapping("schoolnotice")
public class SchoolNoticeController {
	@Autowired
	private SchoolNoticeService schoolNoticeService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("schoolnotice:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<SchoolNoticeEntity> schoolNoticeList = schoolNoticeService.queryList(query);
		int total = schoolNoticeService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(schoolNoticeList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("schoolnotice:info")
	public R info(@PathVariable("id") Integer id){
		SchoolNoticeEntity schoolNotice = schoolNoticeService.queryObject(id);
		
		return R.ok().put("schoolNotice", schoolNotice);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("schoolnotice:save")
	public R save(@RequestBody SchoolNoticeEntity schoolNotice){
		schoolNotice.setCreatetime(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		schoolNoticeService.save(schoolNotice);
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("schoolnotice:update")
	public R update(@RequestBody SchoolNoticeEntity schoolNotice){
		schoolNoticeService.update(schoolNotice);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("schoolnotice:delete")
	public R delete(@RequestBody Integer[] ids){
		schoolNoticeService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
