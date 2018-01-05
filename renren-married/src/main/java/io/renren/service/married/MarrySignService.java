package io.renren.service.married;

import io.renren.entity.married.MarrySignEntity;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

/**
 * 
 * 
 * @author yuanbichang
 * @email 673290684@qq.com
 * @date 2017-12-18 20:26:38
 */
public interface MarrySignService extends IService<MarrySignEntity>{
	
	MarrySignEntity queryObject(Integer id);
	
	List<MarrySignEntity> queryList(Map<String, Object> map);
	
	List<MarrySignEntity> queryListtongji(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(MarrySignEntity marrySign);
	
	void update(MarrySignEntity marrySign);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
