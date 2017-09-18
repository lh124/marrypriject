package io.renren.util;

import io.renren.utils.PIOExcelUtil;
import io.renren.utils.POIMvcUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.junit.Test;

public class CommonTest {

	@Test
	public void poiTest() throws Exception{
		File file = new File("G://test1.xlsx");
		FileInputStream input = new FileInputStream(file);
		POIMvcUtil poi = new POIMvcUtil();
		
		List<List<Object>> list = poi.getBankListByExcel(input, "test1.xlsx");
		
		for(List<Object> objList : list){
			for(Object obj : objList){
				System.out.println(obj.toString());
			}
		}
		/*List<Row> list = poi.readExcel(input, "test1.xlsx");
		
		for(Row row : list){
			for(int i=0; i<row.getLastCellNum(); i++){
				
				int type = row.getCell(i).getCellType();
				DecimalFormat de = new DecimalFormat("##0");
				if ( type == Cell.CELL_TYPE_STRING) 
					System.out.println(row.getCell(i).getStringCellValue());
				
				if ( type == Cell.CELL_TYPE_NUMERIC)
					
					//DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
					System.out.println(de.format(row.getCell(i).getNumericCellValue()));
			}
			System.out.println("-----------------------");
		}*/
		/*PIOExcelUtil eu = new PIOExcelUtil();  
        eu.setExcelPath("G:\\test1.xlsx");  
          
        System.out.println("=======测试Excel 默认 读取========");  
        eu.readExcel(); */
		
	}
}

