package io.renren.service.tombstone;

import io.renren.entity.tombstone.TombstoneUserEntity;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

/**
 * 
 * 
 * @author yuanbichang
 * @email 673290684@qq.com
 * @date 2017-10-19 16:59:26
 */
public interface TombstoneUserService extends IService<TombstoneUserEntity>{
	
	TombstoneUserEntity queryObject(Integer id);
	
	TombstoneUserEntity queryObjectName(Object name);
	
	List<TombstoneUserEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TombstoneUserEntity tombstoneUser);
	
	void update(TombstoneUserEntity tombstoneUser);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
