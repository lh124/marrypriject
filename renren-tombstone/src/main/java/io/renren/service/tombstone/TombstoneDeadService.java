package io.renren.service.tombstone;

import io.renren.entity.tombstone.TombstoneDeadEntity;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

/**
 * 
 * 
 * @author yuanbichang
 * @email 673290684@qq.com
 * @date 2017-10-23 10:54:33
 */
public interface TombstoneDeadService extends IService<TombstoneDeadEntity>{
	
	TombstoneDeadEntity queryObject(Integer id);
	
	List<TombstoneDeadEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TombstoneDeadEntity tombstoneDead);
	
	void update(TombstoneDeadEntity tombstoneDead);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
