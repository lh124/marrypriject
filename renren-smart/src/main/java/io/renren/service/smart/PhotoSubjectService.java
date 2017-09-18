package io.renren.service.smart;

import com.baomidou.mybatisplus.service.IService;

import io.renren.entity.smart.PhotoSubjectEntity;

import java.util.List;
import java.util.Map;

/**
 * 科目表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-05-27 16:53:07
 */
public interface PhotoSubjectService extends IService<PhotoSubjectEntity>{
	
	PhotoSubjectEntity queryObject(Long id);
	
	List<PhotoSubjectEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(PhotoSubjectEntity photoSubject);
	
	void update(PhotoSubjectEntity photoSubject);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
