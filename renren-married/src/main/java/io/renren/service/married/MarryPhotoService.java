package io.renren.service.married;

import io.renren.entity.married.MarryPhotoEntity;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

/**
 * 
 * 
 * @author yuanbichang
 * @email 673290684@qq.com
 * @date 2018-01-02 10:37:17
 */
public interface MarryPhotoService extends IService<MarryPhotoEntity>{
	
	MarryPhotoEntity queryObject(Integer id);
	
	List<MarryPhotoEntity> queryList(Map<String, Object> map);
	
	List<MarryPhotoEntity> queryList1(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(MarryPhotoEntity marryPhoto);
	
	void update(MarryPhotoEntity marryPhoto);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
