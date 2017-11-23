package io.renren.service.smart;

import io.renren.entity.smart.WeixinFunctionEntity;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

/**
 * 
 * 
 * @author yuanbichang
 * @email 673290684@qq.com
 * @date 2017-11-23 10:33:58
 */
public interface WeixinFunctionService extends IService<WeixinFunctionEntity>{
	
	WeixinFunctionEntity queryObject(Integer id);
	
	List<WeixinFunctionEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(WeixinFunctionEntity weixinFunction);
	
	void update(WeixinFunctionEntity weixinFunction);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
