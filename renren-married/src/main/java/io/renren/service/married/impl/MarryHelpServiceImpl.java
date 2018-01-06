package io.renren.service.married.impl;

import io.renren.dao.married.MarryHelpDao;
import io.renren.entity.married.MarryHelpEntity;
import io.renren.service.married.MarryHelpService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("marryHelpService")
public class MarryHelpServiceImpl extends ServiceImpl<MarryHelpDao, MarryHelpEntity>  implements MarryHelpService {
	@Autowired
	private MarryHelpDao marryHelpDao;
	
	@Override
	public MarryHelpEntity queryObject(Integer id){
		return marryHelpDao.queryObject(id);
	}
	
	@Override
	public List<MarryHelpEntity> queryList(Map<String, Object> map){
		return marryHelpDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return marryHelpDao.queryTotal(map);
	}
	
	@Override
	public void save(MarryHelpEntity marryHelp){
		marryHelpDao.save(marryHelp);
	}
	
	@Override
	public void update(MarryHelpEntity marryHelp){
		marryHelpDao.update(marryHelp);
	}
	
	@Override
	public void delete(Integer id){
		marryHelpDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		marryHelpDao.deleteBatch(ids);
	}
	
}
