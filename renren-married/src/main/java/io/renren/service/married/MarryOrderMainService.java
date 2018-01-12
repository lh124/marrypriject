package io.renren.service.married;

import io.renren.entity.married.MarryOrderMainEntity;

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
public interface MarryOrderMainService extends IService<MarryOrderMainEntity>{
	
	MarryOrderMainEntity queryObject(Integer orderId);
	
	List<MarryOrderMainEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(MarryOrderMainEntity marryOrderMain);
	
	void update(MarryOrderMainEntity marryOrderMain);
	
	void delete(Integer orderId);
	
	void deleteBatch(Integer[] orderIds);
}
