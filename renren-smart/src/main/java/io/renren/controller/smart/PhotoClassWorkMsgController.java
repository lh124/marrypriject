package io.renren.controller.smart;

import io.renren.constant.ControllerConstant;
import io.renren.entity.smart.PhotoClassWorkMsgEntity;
import io.renren.entity.smart.PhotoPicWorkMsgEntity;
import io.renren.entity.smart.StudentEntity;
import io.renren.service.smart.PhotoClassWorkMsgService;
import io.renren.service.smart.PhotoPicWorkMsgService;
import io.renren.service.smart.StudentService;
import io.renren.utils.PageUtils;
import io.renren.utils.Query;
import io.renren.utils.R;
import io.renren.utils.dataSource.DBTypeEnum;
import io.renren.utils.dataSource.DbContextHolder;
import io.renren.validator.ValidatorUtils;
import io.renren.validator.group.AddGroup;

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

import com.baomidou.mybatisplus.mapper.EntityWrapper;


/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-06-17 16:11:02
 */
@RestController
@RequestMapping("/smart/photoclassworkmsg")
public class PhotoClassWorkMsgController {
	@Autowired
	private PhotoClassWorkMsgService photoClassWorkMsgService;
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private PhotoPicWorkMsgService photoPicWorkMsgService;
	
	
	public static int STATUS_COMMON = 1;
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<PhotoClassWorkMsgEntity> photoClassWorkMsgList = photoClassWorkMsgService.queryList(query);
		int total = photoClassWorkMsgService.queryTotal(query);
		// 切换数据库
		DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
		for (PhotoClassWorkMsgEntity pcwm : photoClassWorkMsgList) {
			StudentEntity studnet = studentService.selectById(pcwm.getUserId());
			pcwm.setStudent(studnet);
		}
		DbContextHolder.setDbType(DBTypeEnum.MYSQL);
		
		// 查询照片
		for (PhotoClassWorkMsgEntity pcwm : photoClassWorkMsgList) {
			PhotoPicWorkMsgEntity ppme = new PhotoPicWorkMsgEntity();
			ppme.setRelatedId(pcwm.getId());
			
			EntityWrapper<PhotoPicWorkMsgEntity> wrapper = new EntityWrapper<PhotoPicWorkMsgEntity>(ppme);
			List<PhotoPicWorkMsgEntity> list =  this.photoPicWorkMsgService.selectList(wrapper);
			pcwm.setPicList(list);
		}
		
		
		PageUtils pageUtil = new PageUtils(photoClassWorkMsgList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil).put("url", ControllerConstant.CDN_URL);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("photoclassworkmsg:info")
	public R info(@PathVariable("id") Long id){
		PhotoClassWorkMsgEntity photoClassWorkMsg = photoClassWorkMsgService.queryObject(id);
		
		return R.ok().put("photoClassWorkMsg", photoClassWorkMsg);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	public R save(PhotoClassWorkMsgEntity photoClassWorkMsg){
		
		ValidatorUtils.validateEntity(photoClassWorkMsg, AddGroup.class);
		photoClassWorkMsg.setStatus(STATUS_COMMON);
		photoClassWorkMsg.setGmtCreate(new Date());
		//photoClassWorkMsgService.save(photoClassWorkMsg);
		
		this.photoClassWorkMsgService.insert(photoClassWorkMsg);
		//EntityWrapper<PhotoClassWorkMsgEntity> wrapper = new EntityWrapper<PhotoClassWorkMsgEntity>(photoClassWorkMsg);
		//photoClassWorkMsg = this.photoClassWorkMsgService.selectOne(wrapper);
		
		return R.ok().put("id", photoClassWorkMsg.getId());
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("photoclassworkmsg:update")
	public R update(@RequestBody PhotoClassWorkMsgEntity photoClassWorkMsg){
		photoClassWorkMsgService.update(photoClassWorkMsg);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("photoclassworkmsg:delete")
	public R delete(@RequestBody Long[] ids){
		photoClassWorkMsgService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
