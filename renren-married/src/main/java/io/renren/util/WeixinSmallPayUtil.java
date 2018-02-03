package io.renren.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import net.sf.json.JSONObject;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class WeixinSmallPayUtil {  
	//小程序
    public static final String appid = "wxc5ae0fb544f1aaef";//在微信开发平台登记的app应用  
    public static final String appsecret = "27f7a4ebd879b6ee1eb71d6e58d83478";  
    public static final String partner = "1447493102";//商户号  
    public static final String partnerkey ="guizhouguanyukejiyouxiangongsi88";//不是商户登录密码，是商户在微信平台设置的32位长度的api秘钥，  
    public static final String createOrderURL="https://api.mch.weixin.qq.com/pay/unifiedorder";  
    public static final String notify_url="http://wrs.gykjewm.com/married/weixin/notify";//异步通知地址  
    
    public static void main(String[] args) {
		String xml = "<xml>"+
					"<appid><![CDATA[wxb9072ff1ebcf745c]]></appid>/n"+
					"<attach><![CDATA[admin]]></attach>/n"+
					"<bank_type><![CDATA[CFT]]></bank_type>/n"+
					"<cash_fee><![CDATA[1]]></cash_fee>/n"+
					"<fee_type><![CDATA[CNY]]></fee_type>/n"+
					"<id><![CDATA[22]]></id>/n"+
					"<is_subscribe><![CDATA[Y]]></is_subscribe>/n"+
					"<mch_id><![CDATA[1447493102]]></mch_id>/n"+
					"<nonce_str><![CDATA[47942af9b3c54de183169dcb539b2e06]]></nonce_str>/n"+
					"<openid><![CDATA[oZSiWxOxNIVH7s8wxiORTchfGxEo]]></openid>/n"+
					"<out_trade_no><![CDATA[4949551516081108059]]></out_trade_no>/n"+
					"<result_code><![CDATA[SUCCESS]]></result_code>/n"+
					"<return_code><![CDATA[SUCCESS]]></return_code>/n"+
					"<sign><![CDATA[F9B43E1EDBA575A2149FDFC3DCB99FF1]]></sign>/n"+
					"<time_end><![CDATA[20180116133839]]></time_end>/n"+
					"<total_fee>1</total_fee>/n"+
					"<trade_type><![CDATA[JSAPI]]></trade_type>/n"+
					"<transaction_id><![CDATA[4200000069201801165007914044]]></transaction_id>/n"+
					"</xml>/n";
		try {
			System.out.println(JSONObject.fromObject(xmlToMap(xml.replace(">/n", ">"))));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    
    /**
     * XML格式字符串转换为Map
     *
     * @param strXML XML字符串
     * @return XML数据转换后的Map
     * @throws Exception
     */
    public static Map<String, String> xmlToMap(String strXML) throws Exception {
        try {
            Map<String, String> data = new HashMap<String, String>();
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            InputStream stream = new ByteArrayInputStream(strXML.getBytes("UTF-8"));
            org.w3c.dom.Document doc = documentBuilder.parse(stream);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getDocumentElement().getChildNodes();
            for (int idx = 0; idx < nodeList.getLength(); ++idx) {
                Node node = nodeList.item(idx);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    org.w3c.dom.Element element = (org.w3c.dom.Element) node;
                    data.put(element.getNodeName(), element.getTextContent());
                }
            }
            try {
                stream.close();
            } catch (Exception ex) {
                // do nothing
            }
            return data;
        } catch (Exception ex) {
            throw ex;
        }

    }
    
    /**
     * 将Map转换为XML格式的字符串
     *
     * @param data Map类型数据
     * @return XML格式的字符串
     * @throws Exception
     */
    public static String mapToXml(Map<String, String> data) throws Exception {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder= documentBuilderFactory.newDocumentBuilder();
        org.w3c.dom.Document document = documentBuilder.newDocument();
        org.w3c.dom.Element root = document.createElement("xml");
        document.appendChild(root);
        for (String key: data.keySet()) {
            String value = data.get(key);
            if (value == null) {
                value = "";
            }
            value = value.trim();
            org.w3c.dom.Element filed = document.createElement(key);
            filed.appendChild(document.createTextNode(value));
            root.appendChild(filed);
        }
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        DOMSource source = new DOMSource(document);
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        transformer.transform(source, result);
        String output = writer.getBuffer().toString(); //.replaceAll("\n|\r", "");
        try {
            writer.close();
        }
        catch (Exception ex) {
        }
        return output;
    }
    
    /**
     * 生成签名. 注意，若含有sign_type字段，必须和signType参数保持一致。
     *
     * @param data 待签名数据
     * @param key API密钥
     * @param signType 签名方式
     * @return 签名
     */
    public static String generateSignature(final Map<String, String> data, String key) throws Exception {
        Set<String> keySet = data.keySet();
        String[] keyArray = keySet.toArray(new String[keySet.size()]);
        Arrays.sort(keyArray);
        StringBuilder sb = new StringBuilder();
        for (String k : keyArray) {
            if (k.equals("sign")) {
                continue;
            }
            if (data.get(k).trim().length() > 0) // 参数值为空，则不参与签名
                sb.append(k).append("=").append(data.get(k).trim()).append("&");
        }
        sb.append("key=").append(key);
        return MD5(sb.toString()).toUpperCase();
    }
    
    /**
     * 生成 MD5
     *
     * @param data 待处理数据
     * @return MD5结果
     */
    public static String MD5(String data) throws Exception {
        java.security.MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] array = md.digest(data.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (byte item : array) {
            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString().toUpperCase();
    }
}
