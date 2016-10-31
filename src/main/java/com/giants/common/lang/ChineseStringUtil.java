package com.giants.common.lang;

import java.util.ArrayList;
import java.util.List;

/**
 * @author vencent.lu
 */
public class ChineseStringUtil {
	
	public static int length(String str){
		int len = 0;
		for(int i=0;i<str.length();i++){
			char c = str.charAt(i);
			Integer asc = Integer.valueOf(c);
			if(asc<0) asc = asc + 65536;
			if(asc>255) len++;
			len++;
		}
		return len;
	}
	
	public static String subString(String str,int index,int dis){
		String subStr = "";
		for(int i=0,j=0,k=1;i<str.length();i++){
			char c = str.charAt(i);
			Integer asc = Integer.valueOf(c);
			if(asc<0) asc = asc + 65536;
			if((j>=index && k<dis) || (k==dis && asc<=255)) subStr = subStr + c;
			if(k>=dis) break;
			if(asc>255){
				j++;
				k++;
			}
			j++;
			k++;
		}
		return subStr;
	}
	
	public static String subString(String str,int index){
		return subString(str,index,length(str));
	}
	
	public static List<String> splitToList(String str,int length){
		List<String> arrayStr = new ArrayList<String>();
		while(length(str)>length){
			String it = subString(str,0,length);
			arrayStr.add(it);
			str = str.substring(it.length(),str.length());
		}
		arrayStr.add(str);
		return arrayStr;
	}
	
	public static String[] splitToArray(String str,int length){
		List<String> list = splitToList(str,length);
		String[] array = new String[list.size()];
		for(int i=0;i<list.size();i++){
			array[i]=list.get(i);
		}
		return array;
	}

}
