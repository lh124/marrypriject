package io.renren.service.married.impl;

import io.renren.dao.married.MarryBlessingDao;
import io.renren.entity.married.MarryBlessingEntity;
import io.renren.service.married.MarryBlessingService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("marryBlessingService")
public class MarryBlessingServiceImpl extends ServiceImpl<MarryBlessingDao, MarryBlessingEntity>  implements MarryBlessingService {
	@Autowired
	private MarryBlessingDao marryBlessingDao;
	
	@Override
	public MarryBlessingEntity queryObject(Integer id){
		return marryBlessingDao.queryObject(id);
	}
	
	@Override
	public List<MarryBlessingEntity> queryList(Map<String, Object> map){
		return marryBlessingDao.queryList(map);
	}
	@Override
	public List<MarryBlessingEntity> queryList1(Map<String, Object> map){
		System.out.println("----->"+marryBlessingDao.queryList1(map));
		return marryBlessingDao.queryList1(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return marryBlessingDao.queryTotal(map);
	}
	
	@Override
	public void save(MarryBlessingEntity marryBlessing){
		marryBlessingDao.save(marryBlessing);
	}
	
	@Override
	public void update(MarryBlessingEntity marryBlessing){
		marryBlessingDao.update(marryBlessing);
	}
	
	@Override
	public void delete(Integer id){
		marryBlessingDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		marryBlessingDao.deleteBatch(ids);
	}
	
}
