package io.renren.service.married;

import io.renren.entity.married.MarryScreenEntity;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

/**
 * 
 * 
 * @author yuanbichang
 * @email 673290684@qq.com
 * @date 2018-02-02 12:44:59
 */
public interface MarryScreenService extends IService<MarryScreenEntity>{
	
	MarryScreenEntity queryObject(Integer id);
	
	List<MarryScreenEntity> queryList(Map<String, Object> map);
	
	List<MarryScreenEntity> queryList1(Map<String, Object> map);
	
	MarryScreenEntity queryObjectScreen(Integer id);
	
	int queryTotal(Map<String, Object> map);
	
	void save(MarryScreenEntity marryScreen);
	
	void update(MarryScreenEntity marryScreen);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
