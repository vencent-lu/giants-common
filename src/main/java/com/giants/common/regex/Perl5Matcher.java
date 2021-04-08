/**
 *
 */
package com.giants.common.regex;

import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.PatternMatcherInput;

/**
 * @author vencent.lu
 *
 */
public class Perl5Matcher implements Matcher {

    private PatternMatcher matcher;
    private PatternMatcherInput input;
    private Pattern pattern;

    public Perl5Matcher(PatternMatcher matcher, PatternMatcherInput input,
                        Pattern pattern) {
        super();
        this.matcher = matcher;
        this.input = input;
        this.pattern = pattern;
    }

    /* (non-Javadoc)
     * @see com.giants.common.regex.Matcher#find()
     */
    @Override
    public boolean find() {
        return this.matcher.contains(input, (org.apache.oro.text.regex.Pattern) pattern.getPatternObj());
    }

    /* (non-Javadoc)
     * @see com.giants.common.regex.Matcher#group()
     */
    @Override
    public String group() {
        return this.matcher.getMatch().group(0);
    }

    /* (non-Javadoc)
     * @see com.giants.common.regex.Matcher#group(int)
     */
    @Override
    public String group(int group) {
        return this.matcher.getMatch().group(group);
    }

}
