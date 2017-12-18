package io.renren.service.married;

import io.renren.entity.married.MarryWeddingEntity;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

/**
 * 
 * 
 * @author yuanbichang
 * @email 673290684@qq.com
 * @date 2017-12-16 13:59:48
 */
public interface MarryWeddingService extends IService<MarryWeddingEntity>{
	
	MarryWeddingEntity queryObject(Integer id);
	
	List<MarryWeddingEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(MarryWeddingEntity marryWedding);
	
	void update(MarryWeddingEntity marryWedding);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
