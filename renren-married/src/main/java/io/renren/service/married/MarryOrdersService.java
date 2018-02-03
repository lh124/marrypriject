package io.renren.service.married;

import io.renren.entity.married.MarryOrdersEntity;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

/**
 * 
 * 
 * @author yuanbichang
 * @email 673290684@qq.com
 * @date 2018-01-11 11:01:43
 */
public interface MarryOrdersService extends IService<MarryOrdersEntity>{
	
	MarryOrdersEntity queryObject(Integer id);
	
	List<MarryOrdersEntity> queryList(Map<String, Object> map);
	
	List<MarryOrdersEntity> queryList1(Map<String, Object> map);
	
	List<MarryOrdersEntity> queryListorder(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(MarryOrdersEntity marryOrders);
	
	void update(MarryOrdersEntity marryOrders);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
