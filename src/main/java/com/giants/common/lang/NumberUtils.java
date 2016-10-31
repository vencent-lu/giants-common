package com.giants.common.lang;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author vencent.lu
 */

public class NumberUtils {
	private static Logger logger=LoggerFactory.getLogger(NumberUtils.class);
	/**
	 * 传入一个数字类型,返回一个保留两位小数的字符串
	 * @param obj
	 * @return
	 */
	public static String formatMoney(Double obj){
		if(obj==null){
			return "0.00";
		}
		DecimalFormat formatter=new DecimalFormat("##0.00");
		String money=null;
		try {
			money = formatter.format(obj);
		} catch (Exception e) {
			logger.error("the parameter ["+obj+"] is not legal..,it must be number",e);
		}		
		return money;
	}
	
	/**
	 * 传入一个数字类型,返回一个保留两位小数的字符串
	 * @param obj
	 * @return
	 */
	public static String formatMoney(Float obj){
		if(obj==null){
			return "0.00";
		}
		DecimalFormat formatter=new DecimalFormat("##0.00");
		String money=null;
		try {
			money = formatter.format(obj);
		} catch (Exception e) {
			logger.error("the parameter ["+obj+"] is not legal..,it must be number",e);
		}		
		return money;
	}
	
	/**
	 * 传入一个数字类型,返回一个保留两位小数的字符串
	 * @param obj
	 * @return
	 */
	public static String formatMoney(BigDecimal obj){
		if(obj==null){
			return "0.00";
		}
		DecimalFormat formatter=new DecimalFormat("##0.00");
		String money=null;
		try {
			money = formatter.format(obj);
		} catch (Exception e) {
			logger.error("the parameter ["+obj+"] is not legal..,it must be number",e);
		}		
		return money;
	}
	
}
