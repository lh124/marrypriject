package io.renren.service.smart;

import io.renren.entity.smart.WeixinFunctionImgEntity;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

/**
 * 
 * 
 * @author yuanbichang
 * @email 673290684@qq.com
 * @date 2017-11-23 10:34:44
 */
public interface WeixinFunctionImgService extends IService<WeixinFunctionImgEntity>{
	
	WeixinFunctionImgEntity queryObject(Integer id);
	
	List<WeixinFunctionImgEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(WeixinFunctionImgEntity weixinFunctionImg);
	
	void update(WeixinFunctionImgEntity weixinFunctionImg);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
