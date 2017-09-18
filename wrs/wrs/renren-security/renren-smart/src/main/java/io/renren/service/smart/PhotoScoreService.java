package io.renren.service.smart;

import com.baomidou.mybatisplus.service.IService;

import io.renren.entity.smart.PhotoScoreEntity;

import java.util.List;
import java.util.Map;

/**
 * 分数记录
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-05-27 16:53:07
 */
public interface PhotoScoreService extends IService<PhotoScoreEntity>{
	
	PhotoScoreEntity queryObject(Long id);
	
	List<PhotoScoreEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(PhotoScoreEntity photoScore);
	
	void update(PhotoScoreEntity photoScore);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
