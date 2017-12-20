package io.renren.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;



public class OssUploadUtil {
	
	//log日志
		//阿里云API的内或外网域名
		private static String ENDPOINT = "oss-cn-hangzhou.aliyuncs.com";;
		//阿里云API的密钥Access Key ID
		private static String ACCESS_KEY_ID = "LTAIyY1y6mvPjVip";
		//阿里云API的密钥Access Key Secret
		private static String ACCESS_KEY_SECRET = "zio4dKlkF6424JE3gUxa8vzPyBcAaF";
		//阿里云API的bucket名称
		private static String BACKET_NAME = "guanyukeji-static";
		
		public static void main(String[] args) {
			File file = new File("C:/Users/Administrator/Desktop/img/1.jpg");
			//以输入流的形式上传文件
			InputStream is;
			try {
				is = new FileInputStream(file);
				System.out.println(uploadObject2OSS(is,"test/"));
			} catch (FileNotFoundException e) {
			}
		}
		
		/**
		 * 上传图片至OSS
		 * @param ossClient  oss连接
		 * @param folder 模拟文件夹名 如"qj_nanjing/"
		 * @return String 返回的唯一MD5数字签名
		 * */
		public static  String uploadObject2OSS2(InputStream is, String folder) {
			OSSClient ossClient = new OSSClient(ENDPOINT, ACCESS_KEY_ID,ACCESS_KEY_SECRET);
			String bucketName = BACKET_NAME;
			String fileName = null;
			try {
				//文件名
				fileName = UUID.randomUUID().toString().replace("-", "") + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".mp3";
				//文件大小
//	            Long fileSize = file.length(); 
	            //创建上传Object的Metadata  
				ObjectMetadata metadata = new ObjectMetadata();
				//上传的文件的长度
	            metadata.setContentLength(is.available());  
	            //指定该Object被下载时的网页的缓存行为
	            metadata.setCacheControl("no-cache"); 
	            //指定该Object下设置Header
	            metadata.setHeader("Pragma", "no-cache");  
	            //指定该Object被下载时的内容编码格式
	            metadata.setContentEncoding("utf-8");  
	            //文件的MIME，定义文件的类型及网页编码，决定浏览器将以什么形式、什么编码读取文件。如果用户没有指定则根据Key或文件名的扩展名生成，
	            //如果没有扩展名则填默认值application/octet-stream
	            //指定该Object被下载时的名称（指示MINME用户代理如何显示附加的文件，打开或下载，及文件名称）
//	            metadata.setContentDisposition("filename/filesize=" + fileName + "/" + fileSize + "Byte.");  
	            //上传文件   (上传文件流的形式)
//	            PutObjectResult putResult = ossClient.putObject(bucketName, folder + fileName, is, metadata);  
	            ossClient.putObject(bucketName, folder + fileName, is, metadata);  
				//解析结果
//				resultStr = putResult.getETag();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return fileName;
		}

		/**
		 * 上传图片至OSS
		 * @param ossClient  oss连接
		 * @param folder 模拟文件夹名 如"qj_nanjing/"
		 * @return String 返回的唯一MD5数字签名
		 * */
		public static  String uploadObject2OSS(InputStream is, String folder) {
			OSSClient ossClient = new OSSClient(ENDPOINT, ACCESS_KEY_ID,ACCESS_KEY_SECRET);
			String bucketName = BACKET_NAME;
			String fileName = null;
			try {
				//文件名
				fileName = UUID.randomUUID().toString().replace("-", "") + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".jpg";
				//文件大小
//	            Long fileSize = file.length(); 
	            //创建上传Object的Metadata  
				ObjectMetadata metadata = new ObjectMetadata();
				//上传的文件的长度
	            metadata.setContentLength(is.available());  
	            //指定该Object被下载时的网页的缓存行为
	            metadata.setCacheControl("no-cache"); 
	            //指定该Object下设置Header
	            metadata.setHeader("Pragma", "no-cache");  
	            //指定该Object被下载时的内容编码格式
	            metadata.setContentEncoding("utf-8");  
	            //文件的MIME，定义文件的类型及网页编码，决定浏览器将以什么形式、什么编码读取文件。如果用户没有指定则根据Key或文件名的扩展名生成，
	            //如果没有扩展名则填默认值application/octet-stream
	            metadata.setContentType(".jpg"); 
	            //指定该Object被下载时的名称（指示MINME用户代理如何显示附加的文件，打开或下载，及文件名称）
//	            metadata.setContentDisposition("filename/filesize=" + fileName + "/" + fileSize + "Byte.");  
	            //上传文件   (上传文件流的形式)
//	            PutObjectResult putResult = ossClient.putObject(bucketName, folder + fileName, is, metadata);  
	            ossClient.putObject(bucketName, folder + fileName, is, metadata);  
				//解析结果
//				resultStr = putResult.getETag();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return fileName;
		}
	
}
