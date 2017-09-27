package io.renren.weixin.main;

import io.renren.weixin.pojo.Token;
import io.renren.weixin.util.CommonUtil;
import io.renren.weixin.util.MenuUtil;

/**
 * 菜单管理器类
 * 
 * @author liufeng
 * @date 2013-10-17
 */
public class MenuManager {

	/**
	 * 定义菜单结构
	 * 
	 * @return
	 */
	private static Menu getMenu() {
		ViewButton btn21 = new ViewButton();
		btn21.setName("学生相册");
		btn21.setType("view");
		btn21.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxb9072ff1ebcf745c&redirect_uri=http%3a%2f%2fwrs.gykjewm.com%2ffront%2flogin.html&response_type=code&scope=snsapi_base&state=1#wechat_redirect");

		ViewButton btn22 = new ViewButton();
		btn22.setName("家庭相册");
		btn22.setType("view");
		btn22.setUrl("http://home.gykjewm.com/phone/authorize/login.jsp");

		ViewButton btn31 = new ViewButton();
		btn31.setName("智能校服");
		btn31.setType("view");
		btn31.setUrl("http://wrs.gykjewm.com/smart/login.html");

		ComplexButton mainBtn2 = new ComplexButton();
		mainBtn2.setName("相册系统");
		mainBtn2.setSub_button(new Button[] { btn21,btn22 });

		ComplexButton mainBtn3 = new ComplexButton();
		mainBtn3.setName("智能校服");
		mainBtn3.setSub_button(new Button[] { btn31 });

		Menu menu = new Menu();
		menu.setButton(new Button[] { mainBtn2, mainBtn3 });

		return menu;
	}

	public static void main(String[] args) {
		// 第三方用户唯一凭证
		String appId = "wx948285e688ee8d66";//公司wxb9072ff1ebcf745c  b298e38e02eb3d45ca5cc22c68e9bae5
		// 第三方用户唯一凭证密钥
		String appSecret = "5c6f611bf5db5df293ead71e802a4896";// 个人wx948285e688ee8d66  5c6f611bf5db5df293ead71e802a4896

		// 调用接口获取凭证
		Token token = CommonUtil.getToken(appId, appSecret);
		if (null != token) {
			// 创建菜单
			boolean result = MenuUtil.createMenu(getMenu(), token.getAccessToken());
			// 判断菜单创建结果
			if (result)
				System.out.println("菜单创建成功！");
			else
			    System.out.println("菜单创建失败！");
		}
	}
}
