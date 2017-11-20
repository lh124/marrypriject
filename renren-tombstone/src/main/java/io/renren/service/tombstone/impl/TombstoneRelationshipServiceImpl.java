package io.renren.service.tombstone.impl;

import io.renren.dao.tombstone.TombstoneRelationshipDao;
import io.renren.entity.tombstone.TombstoneRelationshipEntity;
import io.renren.service.tombstone.TombstoneRelationshipService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("tombstoneRelationshipService")
public class TombstoneRelationshipServiceImpl extends ServiceImpl<TombstoneRelationshipDao, TombstoneRelationshipEntity>  implements TombstoneRelationshipService {
	@Autowired
	private TombstoneRelationshipDao tombstoneRelationshipDao;
	
	@Override
	public TombstoneRelationshipEntity queryObject(Integer id){
		return tombstoneRelationshipDao.queryObject(id);
	}
	
	@Override
	public List<TombstoneRelationshipEntity> queryList(Map<String, Object> map){
		return tombstoneRelationshipDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return tombstoneRelationshipDao.queryTotal(map);
	}
	
	@Override
	public void save(TombstoneRelationshipEntity tombstoneRelationship){
		tombstoneRelationshipDao.save(tombstoneRelationship);
	}
	
	@Override
	public void update(TombstoneRelationshipEntity tombstoneRelationship){
		tombstoneRelationshipDao.update(tombstoneRelationship);
	}
	
	@Override
	public void delete(Integer id){
		tombstoneRelationshipDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		tombstoneRelationshipDao.deleteBatch(ids);
	}
	
}
