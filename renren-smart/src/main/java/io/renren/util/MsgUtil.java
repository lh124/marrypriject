package io.renren.util;

import java.util.Random;

import io.renren.msg.SendSmsRequest;
import io.renren.msg.SendSmsResponse;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

public class MsgUtil {
	
	public static final String YZMBD = "SMS_116565141";//发送验证码
	
	public static final String YZMPASSWORD = "SMS_116561953";//发送初始化密码
	
	//产品名称:云通信短信API产品,开发者无需替换
    static final String product = "Dysmsapi";
    //产品域名,开发者无需替换
    static final String domain = "dysmsapi.aliyuncs.com";

    //  此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
    static final String accessKeyId = "LTAISquH2fwxJ75T";
    static final String accessKeySecret = "7JdTAcnJ1mPDVihZ5SvGWUK0AFi1GR";
    
    public static void main(String[] args) {
    	try {
			sendSms("13765072164", getRandow(), YZMBD);
		} catch (ClientException e) {
			e.printStackTrace();
		}
	}
    
    private static String getRandow(){
		String randow = "";
		 Random random = new Random();
		for(int i = 0; i < 6; i++){
			randow += random.nextInt(10);
		}
		return randow;
	}

    public static SendSmsResponse sendSms(String phone,String randow,String type) throws ClientException {
        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "100000");
        System.setProperty("sun.net.client.defaultReadTimeout", "100000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(phone);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName("孙军1");
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(type);
        //可选:
        request.setTemplateParam("{\"code\":\""+randow+"\"}");

        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");

        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        //request.setOutId("yourOutId");

        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);

        return sendSmsResponse;
    }

}
