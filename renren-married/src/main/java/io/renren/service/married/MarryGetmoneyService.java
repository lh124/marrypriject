package io.renren.service.married;

import io.renren.entity.married.MarryGetmoneyEntity;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

/**
 * 
 * 
 * @author yuanbichang
 * @email 673290684@qq.com
 * @date 2018-01-16 15:19:25
 */
public interface MarryGetmoneyService extends IService<MarryGetmoneyEntity>{
	
	MarryGetmoneyEntity queryObject(Integer id);
	
	List<MarryGetmoneyEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(MarryGetmoneyEntity marryGetmoney);
	
	void update(MarryGetmoneyEntity marryGetmoney);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
