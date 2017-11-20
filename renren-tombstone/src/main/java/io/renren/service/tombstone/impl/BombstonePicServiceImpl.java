package io.renren.service.tombstone.impl;

import io.renren.dao.tombstone.BombstonePicDao;
import io.renren.entity.tombstone.BombstonePicEntity;
import io.renren.service.tombstone.BombstonePicService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("bombstonePicService")
public class BombstonePicServiceImpl extends ServiceImpl<BombstonePicDao, BombstonePicEntity>  implements BombstonePicService {
	@Autowired
	private BombstonePicDao bombstonePicDao;
	
	@Override
	public BombstonePicEntity queryObject(Integer id){
		return bombstonePicDao.queryObject(id);
	}
	
	@Override
	public List<BombstonePicEntity> queryList(Map<String, Object> map){
		return bombstonePicDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return bombstonePicDao.queryTotal(map);
	}
	
	@Override
	public void save(BombstonePicEntity bombstonePic){
		bombstonePicDao.save(bombstonePic);
	}
	
	@Override
	public void update(BombstonePicEntity bombstonePic){
		bombstonePicDao.update(bombstonePic);
	}
	
	@Override
	public void delete(Integer id){
		bombstonePicDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		bombstonePicDao.deleteBatch(ids);
	}
	
}
