package io.renren.service.smart;

import io.renren.entity.smart.SysWeixinMsgEntity;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

/**
 * 
 * 
 * @author yuanbichang
 * @email 673290684@qq.com
 * @date 2017-09-28 14:47:08
 */
public interface SysWeixinMsgService extends IService<SysWeixinMsgEntity>{
	
	SysWeixinMsgEntity queryObject(Integer id);
	
	List<SysWeixinMsgEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(SysWeixinMsgEntity sysWeixinMsg);
	
	void update(SysWeixinMsgEntity sysWeixinMsg);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
