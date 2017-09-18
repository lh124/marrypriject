package io.renren.service;

import io.renren.entity.PhotoCollegeEntity;
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
public interface PhotoCollegeService extends IService<PhotoCollegeEntity>{
	
	PhotoCollegeEntity queryObject(Long id);
	
	List<PhotoCollegeEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(PhotoCollegeEntity photoCollege);
	
	void update(PhotoCollegeEntity photoCollege);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
