package io.renren.controller;

import io.renren.utils.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;


/**
 * 
 * 
 * @author yuanbichang
 * @email 673290684@qq.com
 * @date 2017-11-07 14:55:16
 */
@RestController
@RequestMapping("databackup")
public class DataBackupsController {
	
	//log日志
		//阿里云API的内或外网域名
		private static String ENDPOINT;
		//阿里云API的密钥Access Key ID
		private static String ACCESS_KEY_ID;
		//阿里云API的密钥Access Key Secret
		private static String ACCESS_KEY_SECRET;
		//阿里云API的bucket名称
		private static String BACKET_NAME;
		//初始化属性
		static{
			ENDPOINT = "oss-cn-hangzhou.aliyuncs.com";
			ACCESS_KEY_ID = "LTAIyY1y6mvPjVip";
			ACCESS_KEY_SECRET = "zio4dKlkF6424JE3gUxa8vzPyBcAaF";
			BACKET_NAME = "guanyukeji-static";
		}
		
		/**
		 * 获取阿里云OSS客户端对象
		 * @return ossClient
		 */
		public static  OSSClient getOSSClient(){
			return new OSSClient(ENDPOINT,ACCESS_KEY_ID, ACCESS_KEY_SECRET);
		}

		/**
		 * 上传图片至OSS
		 * @param ossClient  oss连接
		 * @param file 上传文件（文件全路径如：D:\\image\\cake.jpg）
		 * @param bucketName  存储空间
		 * @param folder 模拟文件夹名 如"qj_nanjing/"
		 * @return String 返回的唯一MD5数字签名
		 * */
		public static  String uploadObject2OSS(OSSClient ossClient, File file, String bucketName, String folder) {
			String resultStr = null;
			try {
				//以输入流的形式上传文件
				InputStream is = new FileInputStream(file);
				//文件名
				String fileName = file.getName(); 
				//文件大小
	            Long fileSize = file.length(); 
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
	            metadata.setContentType(".sql");  
	            //指定该Object被下载时的名称（指示MINME用户代理如何显示附加的文件，打开或下载，及文件名称）
	            metadata.setContentDisposition("filename/filesize=" + fileName + "/" + fileSize + "Byte.");  
	            //上传文件   (上传文件流的形式)
	            PutObjectResult putResult = ossClient.putObject(bucketName, folder + fileName, is, metadata);  
				//解析结果
				resultStr = putResult.getETag();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return resultStr;
		}
	
	 /** 
     * Java代码实现MySQL数据库导出 
     *  
     * @author GaoHuanjie 
     * @param hostIP MySQL数据库所在服务器地址IP 
     * @param userName 进入数据库所需要的用户名 
     * @param password 进入数据库所需要的密码 
     * @param savePath 数据库导出文件保存路径 
     * @param fileName 数据库导出文件文件名 
     * @param databaseName 要导出的数据库名 
     * @return 返回true表示导出成功，否则返回false。 
     */  
	@ResponseBody
	@RequestMapping("/tablelist")
    public R exportDatabaseTool()  {  
		 try {
			return getdata();
		} catch (InterruptedException e) {
			e.printStackTrace();
			return R.error();
		}
    }
	
	public void deleteIo(){
		
		
	}
	
	public R getdata()throws InterruptedException{
		String hostIP ="localhost";
		String userName="wrsuser";
		String password="1990623qweabc";
		String savePath="E:\\web\\webroot\\wrs\\statics\\backupDatabase\\";
		String fileName=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".sql";
		String databaseName="wrs_db";
        File saveFile = new File(savePath);  
        if (!saveFile.exists()) {// 如果目录不存在  
            saveFile.mkdirs();// 创建文件夹  
        }  
        if(!savePath.endsWith(File.separator)){  
            savePath = savePath + File.separator;  
        }  
          
        PrintWriter printWriter = null;  
        BufferedReader bufferedReader = null;  
        try {  
            printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(savePath + fileName), "utf8"));  
            Process process = Runtime.getRuntime().exec(" mysqldump -h" + hostIP + " -u" + userName + " -p" + password + " --set-charset=UTF8 " + databaseName);  
            InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream(), "utf8");  
            bufferedReader = new BufferedReader(inputStreamReader);  
            String line;  
            while((line = bufferedReader.readLine())!= null){  
                printWriter.println(line);  
            }  
            printWriter.flush();  
            if(process.waitFor() == 0){//0 表示线程正常终止。
            	File file = new File(savePath+fileName);
            	uploadObject2OSS(new OSSClient(ENDPOINT,ACCESS_KEY_ID, ACCESS_KEY_SECRET), file, BACKET_NAME, "data_backup/");
                return R.ok("数据库成功备份！！！");  
            }  
        }catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                if (bufferedReader != null) {  
                    bufferedReader.close();  
                }  
                if (printWriter != null) {  
                    printWriter.close();  
                }  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        return R.error("数据库备份失败！！！"); 
	}
	
}
