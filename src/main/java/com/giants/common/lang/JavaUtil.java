package com.giants.common.lang;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

@SuppressWarnings({"rawtypes", "unchecked"})
public class JavaUtil {
private JavaUtil(){}
    
    public static final int INDENT = 4;

    public static String getArrayAsString(Object obj, int indent){
        if ( obj == null ){
            return null;
        }
        if ( indent < 0 ){
            throw new IllegalArgumentException();
        }
        
        StringBuffer sb = new StringBuffer();
        int i = 0;
        for (i=0; i < Array.getLength(obj); i++){
            sb.append(getObjectAsString(Array.get(obj, i), indent));
            sb.append(", ");
        }
        
        if ( sb.length() == 0 ){
            return "";
        }
        else{
            return sb.toString().substring(0, sb.length()-2);
        }
    }

    public static String getArrayAsString(Object obj, String separator){
        return getArrayAsString(obj, separator, "", 0);
    }

    public static String getArrayAsString(Object obj, String separator, int fromIndex){
        return getArrayAsString(obj, separator, "", fromIndex);
    }
    
    public static String getArrayAsString(Object obj, String separator, String quoteString, int fromIndex){
        if ( obj == null ){
            return null;
        }
        if ( fromIndex < 0 ){
            throw new IllegalArgumentException();
        }
        if ( separator == null ){
            separator = ", ";  // default value
        }
        if ( quoteString == null ){
            quoteString = "";
        }

        StringBuffer sb = new StringBuffer(128);
        for (int i=fromIndex; i < Array.getLength(obj); i++){
            sb.append(quoteString).append(getObjectAsString(Array.get(obj, i))).append(quoteString).append(separator);
        }
        
        // remove the last separator
        if ( sb.length() == 0 ){
            return "";
        }
        else{
            return sb.substring(0, sb.length() - separator.length());
        }
    }
    
    public static String getObjectAsString(Object obj){
        return getObjectAsString(obj, 0);
    }

    /**
     * Get the content of an object.
     * @param obj
     * @return
     */
    public static String getObjectAsString(Object obj, int indent){
        if ( obj == null ) return null;

        if ( obj instanceof Map ){
            return getObjectAsString((Map)obj, indent);
        }
        else if ( obj instanceof Collection ){
            return getObjectAsString((Collection)obj, indent);
        }
        else if ( obj.getClass().isArray() ){
            return getArrayAsString(obj, indent);
        }
        else{
            return obj.toString().trim();
        }
    }

    /**
     * Return a string separated by separator. Each item in the string should be enclosed by quoteString.
     * For example, for a list of 1 2 3 4, separator ",", return 1,2,3,4
     * @param obj
     * @param separator
     * @param quoteString
     * @return
     */
    public static String getObjectAsString(Object obj, String separator, String quoteString){
        if ( obj == null ){
            return null;
        }
        if ( separator == null ){
            separator = ",";
        }
        if ( quoteString == null ){
            quoteString = "";
        }
        
        StringBuffer sb = new StringBuffer(128);
        if ( obj instanceof Collection ){
            Collection collection = (Collection) obj;
            for (Iterator i = collection.iterator(); i.hasNext(); ){
                sb.append(quoteString).append(i.next()).append(quoteString).append(separator);
            }
            sb.delete(sb.length() - separator.length(), sb.length());
        }
        else if ( obj.getClass().isArray() ){
            for (int i=0; i < Array.getLength(obj); i++){
                sb.append(quoteString).append(Array.get(obj, i)).append(quoteString).append(separator);
            }
            sb.delete(sb.length() - separator.length(), sb.length());
        }
        else{
            sb.append(quoteString).append(obj).append(quoteString);
        }
        return sb.toString();
    }

    public static String getObjectAsString(Map obj, int indent){
        if ( obj == null ) return null;
        if ( indent < 0 ){
            throw new IllegalArgumentException();
        }

        StringBuffer sb = new StringBuffer("");
        sb.append(obj.getClass().getName());
        sb.append(" {\r\n");

        for ( Iterator keys = obj.keySet().iterator(); keys.hasNext(); ){
            Object key = keys.next();
            Object value = obj.get(key);

            sb.append(StringUtil.space(indent + INDENT));
            sb.append(key + " = ");
            sb.append((value==null) ? "null" : getObjectAsString(value, indent + INDENT));
            sb.append("\r\n");
        }
        return sb.append(StringUtil.space(indent) + "}").toString();
    }

    public static String getObjectAsString(Collection obj, int indent){
        if ( obj == null ) return null;
        if ( indent < 0 ){
            throw new IllegalArgumentException();
        }

        StringBuffer sb = new StringBuffer("");
        sb.append(obj.getClass().getName());
        sb.append(" {\r\n");

        for ( Iterator values = obj.iterator(); values.hasNext(); ){
            Object value = values.next();
            sb.append(StringUtil.space(indent + INDENT));
            sb.append((value==null) ? "null" : getObjectAsString(value, indent + INDENT));
            sb.append("\r\n");
        }
        return sb.append(StringUtil.space(indent) + "}").toString();
    }

    /**
     * Get the simple class name for a class, etc, java.lang.String --> String
     * If it is a primitive type, just return it
     * @param javaType
     * @return
     */
    public static String getClassNameWithoutPackage(String fullClassName){
        int pos = fullClassName.lastIndexOf(".");
        if ( pos == -1 )
            return fullClassName;
        else
            return fullClassName.substring(pos+1);
    }
    public static String javaTrunc(String num,int bit){
    	//BigDecimal bigNum=null;
    	int moveBit=0;
    	int dot=-1;
    	int length=num.length();
    	try{
    		 //bigNum=new BigDecimal(num);
    		dot=num.indexOf('.');
    	}catch(NumberFormatException e){
    		return StringUtils.EMPTY;
    	}
    	if(bit<0){
    		if(dot==-1){
    			moveBit=length+bit;
    		}else{
    			moveBit=dot+bit;
    		}
    		
    	}
    	else if(bit>0){
    		if(dot==-1){
    			moveBit=length;
    		}else{
    			moveBit=dot+bit+1;
    			if(moveBit>length){
    			return StringUtils.EMPTY;
    			}else{
    			return num.substring(0,moveBit);
    			}
    		}
    	}
    	num=num.substring(0,moveBit);
    	for(int i=0;i<Math.abs(bit);i++){
    		num=num+"0";
    	}
    	return num;
    }
    
	/**
	 * 判断stringArray中的字符串或者其开头部分是否在stringList中存在
	 * @param stringList
	 * @param stringArray
	 * @return boolean
	 */
	public static boolean compareArrayWithList(List stringList, String[] stringArray) {
		if (stringList!=null && stringList.size()>0) {
			for (int i=0; i<stringArray.length; i++) {
				if(!compareStringInList(stringList, stringArray[i])){
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	/**
	 * 判断compareString或者其开头部分是否在stringList中存在
	 * @param currentUserOrgIdList
	 * @param orgId
	 * @return boolean
	 */
	public static boolean compareStringInList(List stringList, String compareString) {
		for (int j=0; j<stringList.size(); j++) {
			String str = stringList.get(j).toString();
			if (compareString.indexOf(str)==0){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 将addList中的值加到list中，并去除重复数据
	 * @param currentUserOrgIdList
	 * @param orgId
	 * @return boolean
	 */
	public static List addList(List list, List addList) {
		if(addList!=null && addList.size()>0){
			if(list==null){
				return addList;
			}else{
				for(int i=0; i<addList.size(); i++){
					if (!list.contains(addList.get(i))) {
						list.add(addList.get(i));
					}
				}
			}
		}
		return list;
	}

}
