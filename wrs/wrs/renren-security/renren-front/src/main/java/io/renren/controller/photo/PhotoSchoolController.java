package io.renren.controller.photo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;

import io.renren.annotation.IgnoreAuth;
import io.renren.entity.PhotoCollegeEntity;
import io.renren.entity.PhotoGraduationTimeEntity;
import io.renren.entity.PhotoSchoolEntity;
import io.renren.entity.PhotoTypeEntity;
import io.renren.enums.ClassClassifyEnum;
import io.renren.enums.ClassStatusEnum;
import io.renren.enums.PhotoTypeEnum;
import io.renren.enums.SchoolTypeEnum;
import io.renren.service.PhotoCollegeService;
import io.renren.service.PhotoGraduationTimeService;
import io.renren.service.PhotoSchoolService;
import io.renren.service.PhotoTypeService;
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
@RestController("frontPhotoSchoolController")
@RequestMapping("/front/photoschool")
public class PhotoSchoolController {
	@Autowired
	private PhotoSchoolService photoSchoolService;
	
	@Autowired
	private PhotoGraduationTimeService photoGraduationTimeService;
	
	@Autowired
	private PhotoCollegeService photoCollegeService;
	
	@Autowired
	private PhotoTypeService photoTypeService;
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
        String cityId = params.containsKey("cityId") ? SQLFilter.sqlInject(params.get("cityId").toString()) : null;
        String schoolName = params.containsKey("schoolName") ? SQLFilter.sqlInject(params.get("schoolName").toString()) : null;
        
        if(schoolName != null && schoolName != ""){
        	query.put("schoolName", schoolName);
        }
        if(cityId != null && cityId != ""){
        	query.put("cityId", cityId);
        }
        

		List<PhotoSchoolEntity> photoSchoolList = photoSchoolService.queryList(query);
		int total = photoSchoolService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(photoSchoolList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@IgnoreAuth
	public R info(@PathVariable("id") Long id){
		
		PhotoSchoolEntity photoSchool = photoSchoolService.queryObject(id);
		
		if (photoSchool != null && photoSchool.getSchoolType() != null) {
			
			//  判断是中学还是大学
			if ( photoSchool.getSchoolType().equals(SchoolTypeEnum.MIDDLE_SCHOOL.getTypeValue())) {
				List<PhotoGraduationTimeEntity> timeList = this.photoGraduationTimeService.queryGraduationClass(ClassStatusEnum.STATUS_COMMON.getValue(),
						id, null, ClassClassifyEnum.GRADUATION_PHOTO.getValue());
				photoSchool.setTimeList(timeList);
				
			} else {
				PhotoCollegeEntity college = new PhotoCollegeEntity();
				college.setSchoolId(id);
				EntityWrapper<PhotoCollegeEntity> wrapper = new EntityWrapper<PhotoCollegeEntity>(college);
				wrapper.orderBy("id", true);
				List<PhotoCollegeEntity> collegeList = this.photoCollegeService.selectList(wrapper);
				photoSchool.setCollegeList(collegeList);
			}
			
			// 获取首页图片
			List<PhotoTypeEntity> typeList = photoTypeService.queryPhotoTypes(PhotoTypeEnum.PHOTO_TYPE_SCHOOL.getTypeValue(), id, true);
			photoSchool.setTypeList(typeList);
			
			return R.ok().put("photoSchool", photoSchool).put("url", PostObjectPolicyController.CDN_URL);
		} else {
			return R.error("查询参数错误");
		}
		
		
	}
}
