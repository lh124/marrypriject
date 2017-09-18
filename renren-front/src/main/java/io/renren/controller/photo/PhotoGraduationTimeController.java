package io.renren.controller.photo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.annotation.IgnoreAuth;
import io.renren.entity.PhotoGraduationTimeEntity;
import io.renren.enums.ClassClassifyEnum;
import io.renren.enums.ClassStatusEnum;
import io.renren.service.PhotoGraduationTimeService;
import io.renren.utils.PageUtils;
import io.renren.utils.Query;
import io.renren.utils.R;


/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-05-04 15:07:49
 */
@RestController("frontPhotoGraduationTimeController")
@RequestMapping("/front/photograduationtime")
public class PhotoGraduationTimeController {
	@Autowired
	private PhotoGraduationTimeService photoGraduationTimeService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<PhotoGraduationTimeEntity> photoGraduationTimeList = photoGraduationTimeService.queryList(query);
		int total = photoGraduationTimeService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(photoGraduationTimeList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	public R info(@PathVariable("id") Integer id){
		PhotoGraduationTimeEntity photoGraduationTime = photoGraduationTimeService.queryObject(id);
		
		return R.ok().put("photoGraduationTime", photoGraduationTime);
	}
	
	/**
	 * 信息
	 */
	@RequestMapping("/queryAllTime")
	@IgnoreAuth
	public R queryAllTime(Long schoolId, Long collegeId){
		
		List<PhotoGraduationTimeEntity> photoGraduationTimeList =  photoGraduationTimeService.queryGraduationClass(ClassStatusEnum.STATUS_COMMON.getValue(), 
				schoolId, collegeId, ClassClassifyEnum.GRADUATION_PHOTO.getValue());
		
		return R.ok().put("timeList", photoGraduationTimeList);
	}
	
	
	
}
