package com.supervise.common;

import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 */
public class CellUtil {
	/**
	 * 获取单元数值
	 * @param value
	 * @return value
	 */
	public static String trimValue(String value){
		if(!StringUtils.isEmpty(value)
				&&value.indexOf(".")>0){
			String[] item = value.split("[.]");
			if(1<item.length&&"0".equals(item[1])){
				value=item[0];
			}
		}
		return value;
	}

	/**
	 * 转换为数值
	 * @param value
	 * @return value
	 */
	public static Double transfValuetoDouble(String value){
		if(!StringUtils.isEmpty(value)){
			String reg = "(?i)^\\d+(\\.\\d+)?$";
		    Pattern pattern = Pattern.compile(reg);
	        Matcher matcher = pattern.matcher(value);
			if(matcher.find()){
				return Double.parseDouble(value);
			}
		}
		return null;
	}

	/**
	 * 格式化批次
	 * @param batchDate
	 * @return value
	 */
	public static String formateBatchdate(String batchDate){
		String formatStr = "";
		if(batchDate.length()<10&&batchDate.length()==8){
			//YYYY-M-D
			String date = batchDate.substring(7);
			String moth = batchDate.substring(5,6);
			String year = batchDate.substring(0,4);
			date = "0"+date;
			moth = "0"+moth;
			formatStr = year+"-"+moth+"-"+date;
		}else if (batchDate.length()>=10){
			//YYYY-MM-DD HH:MM:SS
			formatStr  = batchDate.substring(0,10);
		}
		return formatStr;
	}


}
