package io.renren.service.tombstone.impl;

import io.renren.dao.tombstone.TombstoneUserDao;
import io.renren.entity.tombstone.TombstoneUserEntity;
import io.renren.service.tombstone.TombstoneUserService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("tombstoneUserService")
public class TombstoneUserServiceImpl extends ServiceImpl<TombstoneUserDao, TombstoneUserEntity>  implements TombstoneUserService {
	@Autowired
	private TombstoneUserDao tombstoneUserDao;
	
	@Override
	public TombstoneUserEntity queryObject(Integer id){
		return tombstoneUserDao.queryObject(id);
	}
	
	@Override
	public List<TombstoneUserEntity> queryList(Map<String, Object> map){
		return tombstoneUserDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return tombstoneUserDao.queryTotal(map);
	}
	
	@Override
	public void save(TombstoneUserEntity tombstoneUser){
		tombstoneUserDao.save(tombstoneUser);
	}
	
	@Override
	public void update(TombstoneUserEntity tombstoneUser){
		tombstoneUserDao.update(tombstoneUser);
	}
	
	@Override
	public void delete(Integer id){
		tombstoneUserDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		tombstoneUserDao.deleteBatch(ids);
	}

	@Override
	public TombstoneUserEntity queryObjectName(Object name) {
		return tombstoneUserDao.queryObjectName(name);
	}
	
}
