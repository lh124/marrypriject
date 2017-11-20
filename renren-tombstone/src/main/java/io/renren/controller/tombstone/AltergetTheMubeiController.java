package io.renren.controller.tombstone;

import io.renren.entity.tombstone.TombstoneUserEntity;
import io.renren.service.tombstone.TombstoneUserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.baomidou.mybatisplus.mapper.EntityWrapper;

@Controller
public class AltergetTheMubeiController {
	
	@Autowired
	private TombstoneUserService tombstoneUserService;
	
	@RequestMapping("alter_getTheMubei")
	public void page(HttpServletRequest request, HttpServletResponse response ){
		try {
			TombstoneUserEntity user = new TombstoneUserEntity();
			user.setName(request.getParameter("mubei.account"));
			EntityWrapper<TombstoneUserEntity> wrapper = new EntityWrapper<TombstoneUserEntity>(user);
			user = this.tombstoneUserService.selectOne(wrapper);
			response.sendRedirect("http://wrs.gykjewm.com/tombstone/weixin/dead.html?id="+user.getId());
		} catch (Exception e) {
			
		}
	}

}
