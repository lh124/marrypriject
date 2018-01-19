package io.renren.controller.married;

import io.renren.controller.AbstractController;
import io.renren.entity.married.MarryMainEntity;
import io.renren.service.married.MarryMainService;
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
 * @date 2017-12-12 10:40:29
 */
@RestController
@RequestMapping("marrymain")
public class MarryMainController extends AbstractController{
	
	@Autowired
	private MarryMainService marryMainService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("marrymain:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
		if(Integer.parseInt(this.getUserId().toString()) != 1){
			params.put("userId", this.getUserId().toString());
		}
        Query query = new Query(params);
		List<MarryMainEntity> marryMainList = marryMainService.queryList(query);
		int total = marryMainService.queryTotal(query);
		PageUtils pageUtil = new PageUtils(marryMainList, total, query.getLimit(), query.getPage());
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("marrymain:info")
	public R info(@PathVariable("id") Integer id){
		MarryMainEntity marryMain = marryMainService.queryObject(id);
		return R.ok().put("marryMain", marryMain);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("marrymain:save")
	public R save(@RequestBody MarryMainEntity marryMain){
		marryMain.setUserId(Integer.parseInt(this.getUserId().toString()));
		marryMainService.insert(marryMain);
		return R.ok().put("id", marryMain.getId());
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("marrymain:update")
	public R update(@RequestBody MarryMainEntity marryMain){
		marryMain.setUserId(Integer.parseInt(this.getUserId().toString()));
		marryMainService.update(marryMain);
		return R.ok().put("id", marryMain.getId());
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("marrymain:delete")
	public R delete(@RequestBody Integer[] ids){
		marryMainService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
