package io.renren.controller.smart;

import io.renren.entity.smart.PhotoExaminationEntity;
import io.renren.entity.smart.SmartRankingEntity;
import io.renren.service.smart.PhotoExaminationService;
import io.renren.service.smart.SmartRankingService;
import io.renren.service.smart.StudentService;
import io.renren.utils.PageUtils;
import io.renren.utils.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;


/**
 * 
 * 
 * @author yuanbichang
 * @email 673290684@qq.com
 * @date 2018-01-27 14:35:12
 */
@RestController
@RequestMapping("smartranking")
public class SmartRankingController {
	@Autowired
	private SmartRankingService smartRankingService;
	@Autowired
	private StudentService studentService;
	@Autowired
	private PhotoExaminationService photoExaminationService;
	
	/**
	 * 查询上一次考试总分的情况
	 * @param id
	 * @return
	 */
	@RequestMapping("/fractionTotalLast")
	public R fractionTotalLast(HttpServletRequest request){
		String examinationId = null;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("gradeId", request.getParameter("gradeId"));
		List<PhotoExaminationEntity> examinationlist = photoExaminationService.queryList(map);//年级下面的所有考试主题列表
		if(examinationlist.size() == 0 || examinationlist.size() == 1){
			return R.ok().put("fractionTotalLast", 0);
		}
		for(int i = 0; i < examinationlist.size(); i++){
			if(Integer.parseInt(request.getParameter("examinationId")) == examinationlist.get(i).getId()){
				if((i+1)==examinationlist.size()){
					return R.ok().put("fractionTotalLast", 0);
				}else{
					examinationId = examinationlist.get(i+1).getId().toString();
				}
			}
		}
		SmartRankingEntity smartRankingEntity = new SmartRankingEntity();
		smartRankingEntity.setExaminationId(Integer.parseInt(examinationId));
		smartRankingEntity.setUserId(Integer.getInteger(request.getParameter("userId")));
		EntityWrapper<SmartRankingEntity> wrapper = new EntityWrapper<SmartRankingEntity>(smartRankingEntity);
		smartRankingEntity = smartRankingService.selectOne(wrapper);
		if(smartRankingEntity == null){
			return R.ok().put("fractionTotalLast", 0);
		}
		return R.ok().put("fractionTotalLast", smartRankingEntity.getFractionTotal());
	}
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("smartranking:list")
	public R list(@RequestParam Map<String, Object> params){
		Integer page = Integer.parseInt(params.get("page").toString());
		Integer limit = Integer.parseInt(params.get("limit").toString());
		String examinationId2 = null;
		Map<String, Object> m = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("gradeId", params.get("gradeId"));
		List<PhotoExaminationEntity> examinationlist = photoExaminationService.queryList(map);//年级下面的所有考试主题列表
		for(int i = 0; i < examinationlist.size(); i++){
			if(Integer.parseInt(params.get("examinationId").toString()) == examinationlist.get(i).getId()){
				if((i+1)==examinationlist.size()){
					examinationId2 = null;
				}else{
					examinationId2 = examinationlist.get(i+1).getId().toString();
				}
			}
		}
		params.put("examinationId2", examinationId2);
		params.put("offset", (page-1)*limit);
		m.put("offset", (page-1)*limit);
		m.put("limit", limit);
		m.put("examinationId2", examinationId2);
		m.put("classId", params.get("classId"));
		m.put("examinationId", params.get("examinationId"));
		//查询列表数据
		List<SmartRankingEntity> smartRankingList = smartRankingService.queryList(m);
		int total = smartRankingService.queryTotal(m);
		PageUtils pageUtil = new PageUtils(smartRankingList, total, limit, page);
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("smartranking:info")
	public R info(@PathVariable("id") Integer id){
		SmartRankingEntity smartRanking = smartRankingService.queryObject(id);
		return R.ok().put("smartRanking", smartRanking);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("smartranking:save")
	public R save(@RequestBody SmartRankingEntity smartRanking){
		String pic = studentService.queryObject(smartRanking.getUserId()).getPic()==null?"":studentService.queryObject(smartRanking.getUserId()).getPic();
		smartRanking.setPic(pic);
		Double fractionTotal = (new  Double(Double.valueOf(smartRanking.getFractionTotal()))).doubleValue();
		smartRanking.setFractionAverage(fractionTotal/smartRanking.getSubjectTotal());
		smartRankingService.save(smartRanking);
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("smartranking:update")
	public R update(@RequestBody SmartRankingEntity smartRanking){
		smartRankingService.update(smartRanking);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("smartranking:delete")
	public R delete(@RequestBody Integer[] ids){
		smartRankingService.deleteBatch(ids);
		return R.ok();
	}
	
}
