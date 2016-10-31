/**
 * 
 */
package com.giants.common.regex;

import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.PatternCompiler;
import org.apache.oro.text.regex.PatternMatcherInput;
import org.apache.oro.text.regex.Perl5Compiler;

/**
 * @author vencent.lu
 *
 */
public class Pattern {
	
	private final static PatternCompiler pattern = new Perl5Compiler();
	
	private Object patternObj;
	
	/**
	 * @param patternObj
	 */
	public Pattern(Object patternObj) {
		super();
		this.patternObj = patternObj;
	}

	public synchronized final static Pattern compile(String regex) {
		try {
			return new Pattern(pattern.compile(regex));
		} catch (MalformedPatternException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean matches(String input) {
		return new org.apache.oro.text.regex.Perl5Matcher().matches(input,
				(org.apache.oro.text.regex.Pattern) this.patternObj);
	}
	
	public Matcher matcher(String input) {
		return new Perl5Matcher(new org.apache.oro.text.regex.Perl5Matcher(),
				new PatternMatcherInput(input), this);
	}
	
	/**
	 * @return the patternObj
	 */
	public Object getPatternObj() {
		return patternObj;
	}

}
