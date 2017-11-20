package io.renren.controller.tombstone;

import io.renren.entity.tombstone.BusinessCardEntity;
import io.renren.service.tombstone.BusinessCardService;
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
 * @date 2017-10-30 15:41:05
 */
@RestController
@RequestMapping("businesscard")
public class BusinessCardController {
	@Autowired
	private BusinessCardService businessCardService;
	
	
	/**
	 * 名片下载二维码
	 */
	
	@RequestMapping("/dowloadClassQrCodemp")
	public void dowloadClassQrCodemp(Long classId, HttpServletResponse response) throws IOException{
		String url = "http://wrs.gykjewm.com/tombstone/uniform.html?id=" + classId;
		ByteArrayOutputStream baos = ZXingCodeUtil.getQRCodeByteArray(url);
		byte[] data = baos.toByteArray();
		response.reset();  
        response.setHeader("Content-Disposition", "attachment; filename=\"id" + classId  + ".png\"");  
        response.addHeader("Content-Length", "" + data.length);  
        response.setContentType("application/octet-stream; charset=UTF-8");  
        IOUtils.write(data, response.getOutputStream());
        baos.close();
	}
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("businesscard:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<BusinessCardEntity> businessCardList = businessCardService.queryList(query);
		int total = businessCardService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(businessCardList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("businesscard:info")
	public R info(@PathVariable("id") Integer id){
		BusinessCardEntity businessCard = businessCardService.queryObject(id);
		
		return R.ok().put("businessCard", businessCard);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("businesscard:save")
	public R save(@RequestBody BusinessCardEntity businessCard){
		businessCardService.insert(businessCard);
		
		return R.ok().put("id", businessCard.getId());
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("businesscard:update")
	public R update(@RequestBody BusinessCardEntity businessCard){
		businessCardService.update(businessCard);
		
		return R.ok().put("id", businessCard.getId());
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("businesscard:delete")
	public R delete(@RequestBody Integer[] ids){
		businessCardService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
