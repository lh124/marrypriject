package io.renren.service.married.impl;

import io.renren.dao.married.MarriedUserDao;
import io.renren.entity.married.MarriedUserEntity;
import io.renren.service.married.MarriedUserService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("marriedUserService")
public class MarriedUserServiceImpl extends ServiceImpl<MarriedUserDao, MarriedUserEntity>  implements MarriedUserService {
	@Autowired
	private MarriedUserDao marriedUserDao;
	
	@Override
	public MarriedUserEntity queryObject(Integer id){
		return marriedUserDao.queryObject(id);
	}
	
	@Override
	public List<MarriedUserEntity> queryList(Map<String, Object> map){
		return marriedUserDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return marriedUserDao.queryTotal(map);
	}
	
	@Override
	public void save(MarriedUserEntity marriedUser){
		marriedUserDao.save(marriedUser);
	}
	
	@Override
	public void update(MarriedUserEntity marriedUser){
		marriedUserDao.update(marriedUser);
	}
	
	@Override
	public void delete(Integer id){
		marriedUserDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		marriedUserDao.deleteBatch(ids);
	}

	@Override
	public List<MarriedUserEntity> queryListtongji(Map<String, Object> map) {
		return marriedUserDao.queryListtongji(map);
	}
	
}
