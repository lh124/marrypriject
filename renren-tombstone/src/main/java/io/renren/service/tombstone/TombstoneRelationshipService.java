package io.renren.service.tombstone;

import io.renren.entity.tombstone.TombstoneRelationshipEntity;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

/**
 * 
 * 
 * @author yuanbichang
 * @email 673290684@qq.com
 * @date 2017-10-23 13:06:48
 */
public interface TombstoneRelationshipService extends IService<TombstoneRelationshipEntity>{
	
	TombstoneRelationshipEntity queryObject(Integer id);
	
	List<TombstoneRelationshipEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TombstoneRelationshipEntity tombstoneRelationship);
	
	void update(TombstoneRelationshipEntity tombstoneRelationship);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
