package io.renren.utils;

import java.io.File;
import java.nio.charset.Charset;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;


import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class HttpsClient {

	//提交方式为https 的get方法
	public static String httpsGet(String url) throws Exception{
			
			TrustManager[] tm = {new MyX509TrustManager()}; 
	    	SSLContext sslContext = SSLContext.getInstance("SSL","SunJSSE"); 
	    	sslContext.init(null, tm, new java.security.SecureRandom()); 
	    	CloseableHttpResponse response = null;
	    	String forBack = "";
	
	    	//从上述SSLContext对象中得到SSLSocketFactory对象
	    	
	        // Allow TLSv1 protocol only
	        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
	        		sslContext,
	                new String[] { "TLSv1" },
	                null,
	                SSLConnectionSocketFactory.getDefaultHostnameVerifier());
	        CloseableHttpClient httpclient = HttpClients.custom()
	                .setSSLSocketFactory(sslsf)
	                .build();
	        try {
	        	
	        	
	            HttpGet httpget = new HttpGet(url );
	            System.out.println("Executing request " + httpget.getRequestLine());
	            response = httpclient.execute(httpget);
	            try {
	                HttpEntity entity = response.getEntity();
	                forBack = EntityUtils.toString(response.getEntity());
	                EntityUtils.consume(entity);
	            } finally {
	                response.close();
	            }
	        } finally {
	            httpclient.close();
	        } 
	        return forBack;
		}
	
	//提交方式为https 的post方法
	/**
	 * 
	 * @param url 发起请求的地址
	 * @param list 的用法 : List<NameValuePair> nvps = new ArrayList<NameValuePair>();nvps.add(new BasicNameValuePair("grant_type", "password"));
	 * @return
	 * @throws Exception
	 */
	public static String httpsPost(String url, List<NameValuePair> list) throws Exception
	{
    	//创建SSLContext对象，并使用我们指定的信任管理器初始化
    	TrustManager[] tm = {new MyX509TrustManager()}; 
    	SSLContext sslContext = SSLContext.getInstance("SSL","SunJSSE"); 
    	sslContext.init(null, tm, new java.security.SecureRandom()); 
    	HttpPost httpsPost = null;
    	CloseableHttpResponse response = null;
    	String forBack = "";

        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
        		sslContext,
                new String[] { "TLSv1" },
                null,
                SSLConnectionSocketFactory.getDefaultHostnameVerifier());
        CloseableHttpClient httpclient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build();
        try {

            httpsPost = new HttpPost(url);
            httpsPost.setEntity(new UrlEncodedFormEntity(list)); 
            
            
            response = httpclient.execute(httpsPost);
            try {
                HttpEntity entity = response.getEntity();
                forBack = EntityUtils.toString(response.getEntity());
                EntityUtils.consume(entity);
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
        
        return forBack;
	}
	
	// http 的get方式提交
	public static String httpGet(String url) throws Exception
	{
		 CloseableHttpClient httpclient = HttpClients.createDefault();
		 String forBack = "";
	        try {
	            HttpGet httpGet = new HttpGet(url);
	            CloseableHttpResponse response1 = httpclient.execute(httpGet);
	            
	            try {
	                System.out.println(response1.getStatusLine());
	                HttpEntity entity1 = response1.getEntity();
	                // do something useful with the response body
	                // and ensure it is fully consumed
	                forBack = EntityUtils.toString(entity1);
	                EntityUtils.consume(entity1);
	            } finally {
	                response1.close();
	            }
	        } finally {
	            httpclient.close();
	        }
	        return forBack;
	}
	
	// http的Post方式提交
	public static  String httpPost(String url, List<NameValuePair> list) throws Exception
	{
		 CloseableHttpClient httpclient = HttpClients.createDefault();
		 String forBack = "";
		 try {   
	            HttpPost httpPost = new HttpPost(url);
	            System.out.println("Executing request " + httpPost.getRequestLine());
	            httpPost.setEntity(new UrlEncodedFormEntity(list));
	            CloseableHttpResponse response2 = httpclient.execute(httpPost);

	            try {
	                System.out.println(response2.getStatusLine());
	                HttpEntity entity2 = response2.getEntity();
	                forBack = EntityUtils.toString(entity2);
	                // do something useful with the response body
	                // and ensure it is fully consumed
	                EntityUtils.consume(entity2);
	            } finally {
	                response2.close();
	            }
	        } finally {
	            httpclient.close();
	        }
		 return forBack;
	}
	
	// http的Post方式提交,utf-8编码
	public static  String httpPosttUtf8(String url, List<NameValuePair> list) throws Exception
	{
		 CloseableHttpClient httpclient = HttpClients.createDefault();
		 String forBack = "";
		 try {   
	            HttpPost httpPost = new HttpPost(url);
	            System.out.println("Executing request " + httpPost.getRequestLine());
	            httpPost.setEntity(new UrlEncodedFormEntity(list,HTTP.UTF_8));
	            CloseableHttpResponse response2 = httpclient.execute(httpPost);

	            try {
	                System.out.println(response2.getStatusLine());
	                HttpEntity entity2 = response2.getEntity();
	                forBack = EntityUtils.toString(entity2);
	                // do something useful with the response body
	                // and ensure it is fully consumed
	                EntityUtils.consume(entity2);
	            } finally {
	                response2.close();
	            }
	        } finally {
	            httpclient.close();
	        }
		 return forBack;
	}
	
	
	
	public static String httpFilePost(String httpUrl,String fileUrl, String commentt) throws Exception
	{
		String back = "";
		CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost httppost = new HttpPost(httpUrl);

            FileBody bin = new FileBody(new File(fileUrl));
            StringBody comment = new StringBody(commentt, ContentType.TEXT_PLAIN);
            

            HttpEntity reqEntity = MultipartEntityBuilder.create()
                    .addPart("file", bin)
                    .addPart("key", comment)
                    .build();


            httppost.setEntity(reqEntity);

            System.out.println("executing request " + httppost.getRequestLine());
            System.out.println(commentt);
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    System.out.println("Response content length: " + resEntity.getContentLength());
                    back = EntityUtils.toString(response.getEntity());
                    System.out.println(back);
                }
                EntityUtils.consume(resEntity);
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
        
        return back;
	}
	
	// http的Post方式提交,设置编码
	public static  String httpPostUtf8(String url, List<NameValuePair> list) throws Exception
	{
		 CloseableHttpClient httpclient = HttpClients.createDefault();
		 String forBack = "";
		 try {   
	            HttpPost httpPost = new HttpPost(url);
	            System.out.println("Executing request " + httpPost.getRequestLine());
	            
	            
	            for(NameValuePair nvp : list)
	            {
	            	StringEntity content=new StringEntity(nvp.getValue(), Charset.forName("utf-8"));// 第二个参数，设置后才会对，内容进行编码

		            content.setContentType("application/text; charset=UTF-8");
		            content.setContentEncoding("utf-8");
		            
	            }
	            
	            
	            httpPost.setEntity(new UrlEncodedFormEntity(list));
	            CloseableHttpResponse response2 = httpclient.execute(httpPost);

	            try {
	                System.out.println(response2.getStatusLine());
	                HttpEntity entity2 = response2.getEntity();
	                forBack = EntityUtils.toString(entity2);
	                // do something useful with the response body
	                // and ensure it is fully consumed
	                EntityUtils.consume(entity2);
	            } finally {
	                response2.close();
	            }
	        } finally {
	            httpclient.close();
	        }
		 return forBack;
	}
}
