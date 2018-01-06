package io.renren.service.married;

import io.renren.entity.married.MarryHelpEntity;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

/**
 * 
 * 
 * @author yuanbichang
 * @email 673290684@qq.com
 * @date 2018-01-06 10:41:15
 */
public interface MarryHelpService extends IService<MarryHelpEntity>{
	
	MarryHelpEntity queryObject(Integer id);
	
	List<MarryHelpEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(MarryHelpEntity marryHelp);
	
	void update(MarryHelpEntity marryHelp);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
