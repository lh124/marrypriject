package io.renren.service.tombstone.impl;

import io.renren.dao.tombstone.PicDao;
import io.renren.entity.tombstone.PicEntity;
import io.renren.service.tombstone.PicService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("picService")
public class PicServiceImpl extends ServiceImpl<PicDao, PicEntity>  implements PicService {
	@Autowired
	private PicDao picDao;
	
	
	@Override
	public List<PicEntity> queryList(Map<String, Object> map){
		return picDao.queryList(map);
	}
	
}
