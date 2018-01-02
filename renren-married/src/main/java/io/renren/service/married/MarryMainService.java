package io.renren.service.married;

import io.renren.entity.married.MarryMainEntity;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

/**
 * 
 * 
 * @author yuanbichang
 * @email 673290684@qq.com
 * @date 2017-12-12 10:40:29
 */
public interface MarryMainService extends IService<MarryMainEntity>{
	
	MarryMainEntity queryObject(Integer id);
	
	List<MarryMainEntity> queryList(Map<String, Object> map);
	
	List<MarryMainEntity> queryListtongji(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(MarryMainEntity marryMain);
	
	void update(MarryMainEntity marryMain);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
