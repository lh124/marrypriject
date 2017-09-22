package io.renren.service.smart;

import io.renren.entity.smart.FreshmanGuideEntity;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

/**
 * 
 * 
 * @author yuanbichang
 * @email 673290684@qq.com
 * @date 2017-09-20 16:07:00
 */
public interface FreshmanGuideService extends IService<FreshmanGuideEntity>{
	
	FreshmanGuideEntity queryObject(Integer id);
	
	List<FreshmanGuideEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(FreshmanGuideEntity freshmanGuide);
	
	void update(FreshmanGuideEntity freshmanGuide);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
