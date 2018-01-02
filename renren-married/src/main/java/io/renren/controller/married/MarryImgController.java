package io.renren.controller.married;

import io.renren.entity.married.MarryImgEntity;
import io.renren.service.married.MarryImgService;
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
 * @date 2017-12-28 09:15:04
 */
@RestController
@RequestMapping("marryimg")
public class MarryImgController {
	@Autowired
	private MarryImgService marryImgService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("marryimg:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<MarryImgEntity> marryImgList = marryImgService.queryList(query);
		int total = marryImgService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(marryImgList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("marryimg:info")
	public R info(@PathVariable("id") Integer id){
		MarryImgEntity marryImg = marryImgService.queryObject(id);
		
		return R.ok().put("marryImg", marryImg);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("marryimg:save")
	public R save(@RequestBody MarryImgEntity marryImg){
		marryImgService.save(marryImg);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("marryimg:update")
	public R update(@RequestBody MarryImgEntity marryImg){
		marryImgService.update(marryImg);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("marryimg:delete")
	public R delete(@RequestBody Integer[] ids){
		marryImgService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
