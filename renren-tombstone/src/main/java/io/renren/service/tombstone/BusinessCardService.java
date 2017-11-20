package io.renren.service.tombstone;

import io.renren.entity.tombstone.BusinessCardEntity;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

/**
 * 
 * 
 * @author yuanbichang
 * @email 673290684@qq.com
 * @date 2017-10-30 15:41:05
 */
public interface BusinessCardService extends IService<BusinessCardEntity>{
	
	BusinessCardEntity queryObject(Integer id);
	
	List<BusinessCardEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(BusinessCardEntity businessCard);
	
	void update(BusinessCardEntity businessCard);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
