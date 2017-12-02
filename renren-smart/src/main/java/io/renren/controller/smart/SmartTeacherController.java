package io.renren.controller.smart;

import io.renren.entity.smart.SmartTeacherEntity;
import io.renren.service.smart.SmartTeacherService;
import io.renren.utils.PageUtils;
import io.renren.utils.Query;
import io.renren.utils.R;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Sha256Hash;
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
 * @date 2017-12-01 15:34:43
 */
@RestController
@RequestMapping("smartteacher")
public class SmartTeacherController {
	@Autowired
	private SmartTeacherService smartTeacherService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("smartteacher:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<SmartTeacherEntity> smartTeacherList = smartTeacherService.queryList(query);
		int total = smartTeacherService.queryTotal(query);
		PageUtils pageUtil = new PageUtils(smartTeacherList, total, query.getLimit(), query.getPage());
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("smartteacher:info")
	public R info(@PathVariable("id") Integer id){
		SmartTeacherEntity smartTeacher = smartTeacherService.queryObject(id);
		return R.ok().put("smartTeacher", smartTeacher);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("smartteacher:save")
	public R save(@RequestBody SmartTeacherEntity smartTeacher){
		if(smartTeacher.getPassword() == null || "".equals(smartTeacher.getPassword())){
			smartTeacher.setPassword(new Sha256Hash("000000").toHex());
		}else{
			smartTeacher.setPassword(new Sha256Hash(smartTeacher.getPassword()).toHex());
		}
		smartTeacherService.insert(smartTeacher);
		return R.ok().put("id", smartTeacher.getId());
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("smartteacher:update")
	public R update(@RequestBody SmartTeacherEntity smartTeacher){
		if(smartTeacher.getPassword().length() < 30){
			smartTeacher.setPassword(new Sha256Hash(smartTeacher.getPassword()).toHex());
		}
		smartTeacherService.update(smartTeacher);
		return R.ok().put("id", smartTeacher.getId());
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("smartteacher:delete")
	public R delete(@RequestBody Integer[] ids){
		smartTeacherService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
