package io.renren.service.tombstone;

import io.renren.entity.tombstone.PicEntity;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

/**
 * 
 * 
 * @author yuanbichang
 * @email 673290684@qq.com
 * @date 2017-10-21 17:05:56
 */
public interface PicService extends IService<PicEntity>{
	
	List<PicEntity> queryList(Map<String, Object> map);
	
}
