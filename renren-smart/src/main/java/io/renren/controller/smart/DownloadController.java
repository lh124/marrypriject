package io.renren.controller.smart;

import io.renren.entity.smart.ClassEntity;
import io.renren.entity.smart.StudentEntity;
import io.renren.service.smart.ClassService;
import io.renren.service.smart.StudentService;
import io.renren.utils.R;
import io.renren.utils.dataSource.DBTypeEnum;
import io.renren.utils.dataSource.DbContextHolder;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("downloadheadimg")
public class DownloadController {
	
	@Autowired
	private ClassService classService;
	
	@Autowired
	private StudentService studentService;
	
	/**
	 * 列表
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/main")
	public R main(HttpServletRequest request,HttpServletResponse response){
		//查询列表数据
        DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("limit", 200);
        map.put("begin", 0);
        map.put("schoolId", request.getParameter("schoolId"));
        map.put("sidx", null);
        map.put("order", null);
        StringBuffer sb = new StringBuffer();
		List<ClassEntity> classList = classService.queryList(map);
		for (Iterator iterator = classList.iterator(); iterator.hasNext();) {
			ClassEntity classEntity = (ClassEntity) iterator.next();
			map.put("classId", classEntity.getId());
			List<StudentEntity> studentList = studentService.queryList(map);
			for (Iterator iterator2 = studentList.iterator(); iterator2.hasNext();) {
				StudentEntity studentEntity = (StudentEntity) iterator2.next();
				if(studentEntity.getPic() != null && !"".equals(studentEntity.getPic())){
					sb.append(studentEntity.getPic()+",");
				}
			}
		}
		try {
			if(sb != null && !sb.toString().equals("")){
				downloadMedia(response, request,writeZip(sb.toString()));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		DbContextHolder.setDbType(DBTypeEnum.MYSQL);
		return R.ok("下载成功");
	}
	
	private static String writeZip(String strs) throws IOException {
        String[] files = strs.split(",");
        String filename = new SimpleDateFormat("yyyyHHddhhmmsss").format(new Date())+".zip";
        String path = "http://wrs.gykjewm.com/statics/zip/"+filename;
        OutputStream os = new BufferedOutputStream( new FileOutputStream( "E:/web/webroot/wrs/statics/zip/"+filename) );
        ZipOutputStream zos = new ZipOutputStream( os );
        byte[] buf = new byte[8192];
        int len;
        for (int i=0;i<files.length;i++) {  
        	URL url = new URL(files[i]);
        	HttpURLConnection httpUrl = (HttpURLConnection) url.openConnection();
            File file = new File( files[i] );
            ZipEntry ze = new ZipEntry( file.getName());
            zos.putNextEntry( ze );
            BufferedInputStream bis = new BufferedInputStream(httpUrl.getInputStream() );
            while ( ( len = bis.read( buf ) ) > 0 ) {
                zos.write( buf, 0, len );
            }
            zos.closeEntry();
        }
        zos.setEncoding("GBK");
        zos.closeEntry();
        zos.close();
        
        for(int i=0;i<files.length;i++){
         File file= new File(files[i] );
         file.delete();
        }
        return path;
    }

	/**
	   * 从服务器中下载图片
	   *
	   * @param fileName 图片地址
	   * @param response
	   * @return
	   */
	  @SuppressWarnings("rawtypes")
	public void downloadMedia(HttpServletResponse response, HttpServletRequest request,String path) {
	    InputStream inputStream = null;
	    OutputStream outputStream = null;
	    try {
	      //处理中文乱码
	      request.setCharacterEncoding("UTF-8");
	      String fileName =path;
	      fileName = new String(fileName.getBytes("iso8859-1"),"UTF-8");
	      //处理浏览器兼容
	      response.setContentType("application/msexcel;charset=utf-8");//定义输出类型
	      Enumeration enumeration = request.getHeaders("User-Agent");
	      String browserName = (String) enumeration.nextElement();
	      boolean isMSIE = browserName.contains("MSIE");
	      if (isMSIE) {
	        response.addHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "UTF8"));
	      } else {
	        response.addHeader("Content-Disposition", "attachment;fileName=" + new String(fileName.getBytes("gb2312"), "ISO8859-1"));
	      }
	      //url地址如果存在空格，会导致报错！  解决方法为：用+或者%20代替url参数中的空格。
	      fileName = fileName.replace(" ", "%20");
	      //图片下载
	      URL url = new URL(fileName);
	      URLConnection conn = url.openConnection();
	      outputStream = response.getOutputStream();
	      inputStream = conn.getInputStream();
	      IOUtils.copy(inputStream, outputStream);
	    } catch (IOException e) {
	      System.err.println(e);
	    }finally { 
	      IOUtils.closeQuietly(inputStream); 
	      IOUtils.closeQuietly(outputStream); 
	   } 
	  }
	
    public static void saveInputStream(InputStream inputStream, String saveToPath){
        byte[] data = new byte[1024];
        int len = 0;
        FileOutputStream fileOutputStream = null;
        try{
            fileOutputStream = new FileOutputStream(saveToPath);
            while (-1 != (len = inputStream.read(data))){
                fileOutputStream.write(data, 0, len);

            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally{
            if (null != inputStream){
                try{
                    inputStream.close();
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
            if (null != fileOutputStream){
                try{
                    fileOutputStream.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
	
    public static void download(String urlString, String filename,String savePath) throws Exception {    
        // 构造URL    
        URL url = new URL(urlString);    
        // 打开连接    
        URLConnection con = url.openConnection();    
        //设置请求超时为5s    
        con.setConnectTimeout(5*1000);    
        // 输入流    
        InputStream is = con.getInputStream();    
        
        // 1K的数据缓冲    
        byte[] bs = new byte[1024];    
        // 读取到的数据长度    
        int len;    
        // 输出的文件流    
       File sf=new File(savePath);    
       if(!sf.exists()){    
           sf.mkdirs();    
       }    
       OutputStream os = new FileOutputStream(sf.getPath()+"\\"+filename);    
        // 开始读取    
        while ((len = is.read(bs)) != -1) {    
          os.write(bs, 0, len);    
        }    
        // 完毕，关闭所有链接    
        os.close();    
        is.close();    
    }  

}
