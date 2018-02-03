package io.renren.chart;

import java.io.CharArrayReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.PrivateKey;
import java.security.Security;
import java.security.Signature;
import java.util.zip.Deflater;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.util.Arrays;

import com.alibaba.fastjson.JSONObject;

public class CloudSignHelper {
    private final static String privStr = "-----BEGIN PRIVATE KEY-----\r\n"
                                             +"MIGHAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBG0wawIBAQQguPw2G"
                                             + "NGkkO9DODyfNorGpC9WsCvc/381faDlU8V1QRKhRANCAAQEZi+QM"
                                             + "GZJpPwmQiT7pv0r4zqBPnUUWtKpCnCoWH77YItVCA3fP19qAhaf8n"
                                             + "7oNtStWHzQt44TcaTxIUCIozd4"
                                             +"\r\n-----END PRIVATE KEY-----";
     
    private final static long skdAppid = 1400067803;
    public static String GetSign(String uid){
         GenTLSSignatureResult result = null;
        try{
            //生成签名
            result = GenTLSSignatureEx(skdAppid, uid, privStr);
            if (0 == result.urlSig.length()) {
                System.out.println("GenTLSSignatureEx failed: " + result.errMessage);
                return null;
            }   
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return result.urlSig;
    }
    
    public static void main(String[] args) {
    	String s = "é?????(???)???";
    	String ss = "";
    	try {
    		String location = new String(s.getBytes("iso-8859-1"),"UTF-8");
    		System.out.println(location.toString().getBytes("UTF-8"));
    		ss = URLEncoder.encode(s , "iso-8859-1");
    		System.out.println("1111="+ss);
    		ss = URLDecoder.decode(ss , "utf-8");
    		System.out.println("2222="+ss);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
     
    /**
     * @brief 生成 tls 票据，精简参数列表，有效期默认为 180 天
     * @param skdAppid 应用的 sdkappid
     * @param identifier 用户 id
     * @param privStr 私钥文件内容
     * @return
     * @throws IOException
     */
    private static  GenTLSSignatureResult GenTLSSignatureEx(
            long skdAppid,
            String identifier,
            String privStr) throws IOException {
        return GenTLSSignatureEx(skdAppid, identifier, privStr, 3600*24*180);
    }
     
    /**
     * @brief 生成 tls 票据，精简参数列表
     * @param skdAppid 应用的 sdkappid
     * @param identifier 用户 id
     * @param privStr 私钥文件内容
     * @param expire 有效期，以秒为单位，推荐时长一个月
     * @return
     * @throws IOException
     */
    private static  GenTLSSignatureResult GenTLSSignatureEx(
            long skdAppid,
            String identifier,
            String privStr,
            long expire) throws IOException {
 
        GenTLSSignatureResult result = new GenTLSSignatureResult();
         
        Security.addProvider(new BouncyCastleProvider());
        Reader reader = new CharArrayReader(privStr.toCharArray());
        JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
        PEMParser parser = new PEMParser(reader);
        Object obj = parser.readObject();
        parser.close();
        PrivateKey privKeyStruct = converter.getPrivateKey((PrivateKeyInfo) obj);
         
        String jsonString = "{"
        + "\"TLS.account_type\":\"" + 22463 +"\","
        +"\"TLS.identifier\":\"" + identifier +"\","
        +"\"TLS.appid_at_3rd\":\"" + 0 +"\","
        +"\"TLS.sdk_appid\":\"" + skdAppid +"\","
        +"\"TLS.expire_after\":\"" + expire +"\","
        +"\"TLS.version\": \"201802300000\""
        +"}";
         
        String time = String.valueOf(System.currentTimeMillis()/1000);
        String SerialString = 
            "TLS.appid_at_3rd:" + 0 + "\n" +
            "TLS.account_type:" + 22463 + "\n" +
            "TLS.identifier:" + identifier + "\n" + 
            "TLS.sdk_appid:" + skdAppid + "\n" + 
            "TLS.time:" + time + "\n" +
            "TLS.expire_after:" + expire +"\n";
         
        try {
            //Create Signature by SerialString
            Signature signature = Signature.getInstance("SHA256withECDSA", "BC");
            signature.initSign(privKeyStruct);
            signature.update(SerialString.getBytes(Charset.forName("UTF-8")));
            byte[] signatureBytes = signature.sign();
             
            String sigTLS = Base64.encodeBase64String(signatureBytes);
             
            //Add TlsSig to jsonString
            JSONObject jsonObject= JSONObject.parseObject(jsonString);
            jsonObject.put("TLS.sig", (Object)sigTLS);
            jsonObject.put("TLS.time", (Object)time);
            jsonString = jsonObject.toString();
             
            //compression
            Deflater compresser = new Deflater();
            compresser.setInput(jsonString.getBytes(Charset.forName("UTF-8")));
 
            compresser.finish();
            byte [] compressBytes = new byte [512];
            int compressBytesLength = compresser.deflate(compressBytes);
            compresser.end();
            String userSig = new String(Base64_url.base64EncodeUrl(Arrays.copyOfRange(compressBytes,0,compressBytesLength)));
     
            result.urlSig = userSig;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            result.errMessage = "generate usersig failed";
        }
         
        return result;
    }
}
