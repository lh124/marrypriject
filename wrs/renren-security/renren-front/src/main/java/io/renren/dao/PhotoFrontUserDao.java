package io.renren.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import io.renren.entity.PhotoFrontUserEntity;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-05-06 11:27:27
 */
public interface PhotoFrontUserDao extends BaseDao<PhotoFrontUserEntity>,BaseMapper<PhotoFrontUserEntity> {
	
	PhotoFrontUserEntity queryUserByAccount(String account);
	
	PhotoFrontUserEntity queryClassUser(@Param("userId") Long userId, @Param("classId") Long classId);
	
	List<PhotoFrontUserEntity> queryUsersByClassId(@Param("classId") Long classId, @Param("status") Integer status);
}
