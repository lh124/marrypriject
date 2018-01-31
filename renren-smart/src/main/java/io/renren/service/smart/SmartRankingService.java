package io.renren.service.smart;

import io.renren.entity.smart.SmartRankingEntity;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

/**
 * 
 * 
 * @author yuanbichang
 * @email 673290684@qq.com
 * @date 2018-01-27 14:35:12
 */
public interface SmartRankingService extends IService<SmartRankingEntity>{
	
	SmartRankingEntity queryObject(Integer id);
	
	List<SmartRankingEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(SmartRankingEntity smartRanking);
	
	void update(SmartRankingEntity smartRanking);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
