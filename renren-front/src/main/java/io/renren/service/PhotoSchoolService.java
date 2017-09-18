package io.renren.service;

import io.renren.entity.PhotoSchoolEntity;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-05-04 15:07:49
 */
public interface PhotoSchoolService extends IService<PhotoSchoolEntity>{
	
	PhotoSchoolEntity queryObject(Long id);
	
	List<PhotoSchoolEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(PhotoSchoolEntity photoSchool);
	
	void update(PhotoSchoolEntity photoSchool);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
