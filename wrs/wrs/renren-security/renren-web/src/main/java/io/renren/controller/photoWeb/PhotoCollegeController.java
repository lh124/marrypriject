package io.renren.controller.photoWeb;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.entity.PhotoCollegeEntity;
import io.renren.service.PhotoCollegeService;
import io.renren.utils.PageUtils;
import io.renren.utils.Query;
import io.renren.utils.R;
import io.renren.xss.SQLFilter;


/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-05-04 15:07:49
 */
@RestController
@RequestMapping("/sys/photocollege")
public class PhotoCollegeController {
	@Autowired
	private PhotoCollegeService photoCollegeService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("photocollege:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
        
        Long schoolId = Long.parseLong(params.get("schoolId").toString());
        query.put("schoolId", schoolId);

		List<PhotoCollegeEntity> photoCollegeList = photoCollegeService.queryList(query);
		int total = photoCollegeService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(photoCollegeList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("photocollege:info")
	public R info(@PathVariable("id") Long id){
		PhotoCollegeEntity photoCollege = photoCollegeService.queryObject(id);
		
		return R.ok().put("photoCollege", photoCollege);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("photocollege:save")
	public R save(@RequestBody PhotoCollegeEntity photoCollege){
		photoCollegeService.save(photoCollege);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("photocollege:update")
	public R update(@RequestBody PhotoCollegeEntity photoCollege){
		photoCollegeService.update(photoCollege);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("photocollege:delete")
	public R delete(@RequestBody Long[] ids){
		photoCollegeService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
