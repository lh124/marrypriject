package io.renren.service.married;

import io.renren.entity.married.MarryCartEntity;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

/**
 * 
 * 
 * @author yuanbichang
 * @email 673290684@qq.com
 * @date 2017-12-16 10:23:15
 */
public interface MarryCartService extends IService<MarryCartEntity>{
	
	MarryCartEntity queryObject(Integer id);
	
	List<MarryCartEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(MarryCartEntity marryCart);
	
	void update(MarryCartEntity marryCart);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
