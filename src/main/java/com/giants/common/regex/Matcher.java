/**
 * 
 */
package com.giants.common.regex;

/**
 * @author vencent.lu
 *
 */
public interface Matcher {
	
	boolean find();

	String group();

	String group(int group);
}
