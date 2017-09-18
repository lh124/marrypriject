package io.renren.controller.smartWeb;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.entity.smart.StudentEpcEntity;
import io.renren.service.smart.StudentEpcService;
import io.renren.utils.PageUtils;
import io.renren.utils.Query;
import io.renren.utils.R;


/**
 * 
 * 
 * @author yuanbichang
 * @email 673290684@qq.com
 * @date 2017-08-28 12:02:26
 */
@RestController
@RequestMapping("/sys/uniform_studentepc")
public class StudentEpcController {
	@Autowired
	private StudentEpcService studentEpcService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("studentepc:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<StudentEpcEntity> studentEpcList = studentEpcService.queryList(query);
		int total = studentEpcService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(studentEpcList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("studentepc:info")
	public R info(@PathVariable("id") Integer id){
		StudentEpcEntity studentEpc = studentEpcService.queryObject(id);
		
		return R.ok().put("studentEpc", studentEpc);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("studentepc:save")
	public R save(@RequestBody StudentEpcEntity studentEpc){
		studentEpcService.save(studentEpc);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("studentepc:update")
	public R update(@RequestBody StudentEpcEntity studentEpc){
		studentEpcService.update(studentEpc);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("studentepc:delete")
	public R delete(@RequestBody Integer[] ids){
		studentEpcService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
