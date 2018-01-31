package io.renren.controller.smart;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.annotation.CheckAuth;
import io.renren.entity.smart.PhotoScoreEntity;
import io.renren.service.smart.PhotoScoreService;
import io.renren.utils.PageUtils;
import io.renren.utils.Query;
import io.renren.utils.R;


/**
 * 分数记录
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-05-27 16:53:07
 */
@RestController
@RequestMapping("/smart")
public class PhotoScoreController {
	@Autowired
	private PhotoScoreService photoScoreService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/photoscore/list")
	@RequiresPermissions("photoscore:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<PhotoScoreEntity> photoScoreList = photoScoreService.queryList(query);
		int total = photoScoreService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(photoScoreList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	/**
	 * 列表
	 */
	@RequestMapping("/photoscore/userList")
	@CheckAuth(needAuth="uniform:all")
	public R userList(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<PhotoScoreEntity> photoScoreList = photoScoreService.queryList(query);
		int total = photoScoreService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(photoScoreList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/photoscore/info/{id}")
	@RequiresPermissions("photoscore:info")
	public R info(@PathVariable("id") Long id){
		PhotoScoreEntity photoScore = photoScoreService.queryObject(id);
		
		return R.ok().put("photoScore", photoScore);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/photoscore/save")
	@RequiresPermissions("photoscore:save")
	public R save(@RequestBody PhotoScoreEntity photoScore){
		photoScore.setTeacherName("老师");
		photoScore.setTeacherPic("http://guanyukeji-static.oss-cn-hangzhou.aliyuncs.com/1.png");
		photoScoreService.save(photoScore);
		return R.ok();
	}
	
	/**
	 *  批量保存
	 */
	@RequestMapping("/photoscore/saveBatch")
	@RequiresPermissions("photoscore:save")
	public R saveBatch(@RequestBody List<PhotoScoreEntity> photoScore){
		
		photoScoreService.insertBatch(photoScore);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/photoscore/update")
	@RequiresPermissions("photoscore:update")
	public R update(@RequestBody PhotoScoreEntity photoScore){
		photoScoreService.update(photoScore);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/photoscore/delete")
	@RequiresPermissions("photoscore:delete")
	public R delete(@RequestBody Long[] ids){
		photoScoreService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
