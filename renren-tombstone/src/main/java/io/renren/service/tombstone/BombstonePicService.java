package io.renren.service.tombstone;

import io.renren.entity.tombstone.BombstonePicEntity;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

/**
 * 
 * 
 * @author yuanbichang
 * @email 673290684@qq.com
 * @date 2017-10-21 17:05:56
 */
public interface BombstonePicService extends IService<BombstonePicEntity>{
	
	BombstonePicEntity queryObject(Integer id);
	
	List<BombstonePicEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(BombstonePicEntity bombstonePic);
	
	void update(BombstonePicEntity bombstonePic);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
