package io.renren.service.smart;

import com.baomidou.mybatisplus.service.IService;

import io.renren.entity.smart.PhotoExaminationEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-05-27 16:53:07
 */
public interface PhotoExaminationService extends IService<PhotoExaminationEntity>{
	
	PhotoExaminationEntity queryObject(Long id);
	
	List<PhotoExaminationEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(PhotoExaminationEntity photoExamination);
	
	void update(PhotoExaminationEntity photoExamination);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
