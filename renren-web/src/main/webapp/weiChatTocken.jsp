<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="java.io.*"%>
<%
	 request.setCharacterEncoding("UTF-8");  
     response.setCharacterEncoding("UTF-8");  

     /** 读取接收到的xml消息 */  
     StringBuffer sb = new StringBuffer();  
     InputStream is = request.getInputStream();  
     InputStreamReader isr = new InputStreamReader(is, "UTF-8");  
     BufferedReader br = new BufferedReader(isr);  
     String s = "";  
     while ((s = br.readLine()) != null) {  
         sb.append(s);  
     }  
     String xml = sb.toString(); //次即为接收到微信端发送过来的xml数据  

     String result = "";  
     /** 判断是否是微信接入激活验证，只有首次接入验证时才会收到echostr参数，此时需要把它直接返回 */  
     String echostr = request.getParameter("echostr");  

     try {  
         OutputStream os = response.getOutputStream(); 
         System.out.println("-----echoStr--->" + echostr); 
         os.write(echostr.getBytes("UTF-8"));  
         os.flush();  
         os.close();  
     } catch (Exception e) {  
         e.printStackTrace();  
     }  

	
%>
