package io.renren.service.smart;

import io.renren.entity.smart.SysRemarksEntity;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

/**
 * 
 * 
 * @author yuanbichang
 * @email 673290684@qq.com
 * @date 2017-11-20 12:02:46
 */
public interface SysRemarksService extends IService<SysRemarksEntity>{
	
	SysRemarksEntity queryObject(Integer id);
	
	List<SysRemarksEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(SysRemarksEntity sysRemarks);
	
	void update(SysRemarksEntity sysRemarks);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
