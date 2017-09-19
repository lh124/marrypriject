package io.renren.controller.smart;

import io.renren.annotation.CheckAuth;
import io.renren.entity.smart.PhotoExaminationEntity;
import io.renren.service.smart.PhotoExaminationService;
import io.renren.utils.PageUtils;
import io.renren.utils.Query;
import io.renren.utils.R;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-05-27 16:53:07
 */
@RestController
public class PhotoExaminationController {
	@Autowired
	private PhotoExaminationService photoExaminationService;
	
	
	/**
	 * 列表
	 */
	@RequestMapping("/smart/photoexamination/userList")
	@CheckAuth(needAuth="uniform:all")
	public R userList(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
        
        Long classId = params.containsKey("classId") ? Long.parseLong(params.get("classId").toString()) : null;
        
        if (classId != null) 
        	query.put("classId", classId);

		List<PhotoExaminationEntity> photoExaminationList = photoExaminationService.queryList(query);
		int total = photoExaminationService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(photoExaminationList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
}
