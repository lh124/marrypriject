package io.renren.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Pattern;

import io.renren.service.PhotoClassService;
import io.renren.service.PhotoFunctionModulesService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

public class StringTest {

	//private PhotoClassService photoClassService;
	
	@Test
	public void TestMapResult(){
		
	}
	
	@Test
	public void StringSubTest(){
		
		String str = "15038125900";
		System.out.println(str.substring(5));
	}
	
	@Test
	public void StringIndexTest(){
		
		String url = "/wrs/front/sdfsa.html";
		System.out.println(url.lastIndexOf("/"));
	}
	
	@Test
	public void patterTest(){
		String pat = ".*front/home/ss/sdfsd.html";
		
		String pp = "sdee/front/home/ss/sdfsd.html";
		
		boolean isMatch = Pattern.matches(pat, pp);
		System.out.println(isMatch);
	}
}
