package io.renren.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import io.renren.entity.PhotoClassEntity;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-05-06 11:27:27
 */
public interface PhotoClassDao extends BaseDao<PhotoClassEntity>,BaseMapper<PhotoClassEntity> {
	List<HashMap<String, Object>> queryMapObject();
	
	List<PhotoClassEntity> querAdminClass(@Param("userId") Long userId, @Param("roleType") int roleType);
}
