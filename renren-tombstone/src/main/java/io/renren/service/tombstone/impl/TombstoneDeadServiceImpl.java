package io.renren.service.tombstone.impl;

import io.renren.dao.tombstone.TombstoneDeadDao;
import io.renren.entity.tombstone.TombstoneDeadEntity;
import io.renren.service.tombstone.TombstoneDeadService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("tombstoneDeadService")
public class TombstoneDeadServiceImpl extends ServiceImpl<TombstoneDeadDao, TombstoneDeadEntity>  implements TombstoneDeadService {
	@Autowired
	private TombstoneDeadDao tombstoneDeadDao;
	
	@Override
	public TombstoneDeadEntity queryObject(Integer id){
		return tombstoneDeadDao.queryObject(id);
	}
	
	@Override
	public List<TombstoneDeadEntity> queryList(Map<String, Object> map){
		return tombstoneDeadDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return tombstoneDeadDao.queryTotal(map);
	}
	
	@Override
	public void save(TombstoneDeadEntity tombstoneDead){
		tombstoneDeadDao.save(tombstoneDead);
	}
	
	@Override
	public void update(TombstoneDeadEntity tombstoneDead){
		tombstoneDeadDao.update(tombstoneDead);
	}
	
	@Override
	public void delete(Integer id){
		tombstoneDeadDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		tombstoneDeadDao.deleteBatch(ids);
	}
	
}
