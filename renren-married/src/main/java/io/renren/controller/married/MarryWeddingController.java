package io.renren.controller.married;

import io.renren.entity.married.MarryWeddingEntity;
import io.renren.service.married.MarryWeddingService;
import io.renren.utils.PageUtils;
import io.renren.utils.Query;
import io.renren.utils.R;
import io.renren.utils.ZXingCodeUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 
 * 
 * @author yuanbichang
 * @email 673290684@qq.com
 * @date 2017-12-16 13:59:48
 */
@RestController
@RequestMapping("marrywedding")
public class MarryWeddingController {
	@Autowired
	private MarryWeddingService marryWeddingService;
	
	/**
	 * 码上结婚二维码下载二维码
	 */
	
	@RequestMapping("/dowloadClassQrCodemb")
	public void dowloadClassQrCodemb(Long id, HttpServletResponse response) throws IOException{
		String url = "http://192.168.1.107:8080/wrs/married/weixin/sign_in.html?id=" + id;
		ByteArrayOutputStream baos = ZXingCodeUtil.getQRCodeByteArray(url);
		byte[] data = baos.toByteArray();
		response.reset();  
        response.setHeader("Content-Disposition", "attachment; filename=\"id" + id  + ".png\"");  
        response.addHeader("Content-Length", "" + data.length);  
        response.setContentType("application/octet-stream; charset=UTF-8");  
        IOUtils.write(data, response.getOutputStream());
        baos.close();
	}
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("marrywedding:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<MarryWeddingEntity> marryWeddingList = marryWeddingService.queryList(query);
		int total = marryWeddingService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(marryWeddingList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("marrywedding:info")
	public R info(@PathVariable("id") Integer id){
		MarryWeddingEntity marryWedding = marryWeddingService.queryObject(id);
		
		return R.ok().put("marryWedding", marryWedding);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("marrywedding:save")
	public R save(@RequestBody MarryWeddingEntity marryWedding){
		marryWeddingService.save(marryWedding);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("marrywedding:update")
	public R update(@RequestBody MarryWeddingEntity marryWedding){
		marryWeddingService.update(marryWedding);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("marrywedding:delete")
	public R delete(@RequestBody Integer[] ids){
		marryWeddingService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
