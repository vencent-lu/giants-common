/*
 * ======================================================================================
 * Copyright Protection: This software program and any associated materials are
 * subject to Typical Technologies Inc. copyright. It is protected by copyright
 * laws of Canada, and the United States, and by International copyright convention,
 * and also is confidential proprietary work product of Typical Technologies Inc.
 * No part of this software program, its associated building blocks, or its underlying
 * design concepts and documentation may be copied, reproduced transmitted, transcribed,
 * translated into any other programming language (natural or binary),  in any form
 * or by any means, without express prior written consent of Typical Technologies Inc.
 * or as may be described in a software license agreement.
 * Copyright (c) Typical Technolgoies Inc. - All Rights Reserved
 * ======================================================================================
 * Program External Name:
 * Description:
 *
 *
 * Original Author: John Zhang
 * Original Creation Date: 2004-11-27
 * ======================================================================================
 * Revision History
 * ----------------
 * Version Date(yymmdd) By   Description                              Bug#/Log#/Ref#
 * ------- ------------ ---- ---------------------------------------- --------------
 *
 * ======================================================================================
 */

package com.giants.common.lang;

import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

@SuppressWarnings({"rawtypes", "unchecked"})
public final class StringUtil {
    private StringUtil() {
    }

    public static final String TOKEN_PREFIX = "${";
    public static final String TOKEN_SUFFIX = "}";

    /**
     * Pad the specified char to the left and right of the specific string.
     *
     * @param s      String
     * @param length int
     * @param ch     char
     * @return String
     */
    public static String padlr(String s, int length, char ch) {
        if (s.length() >= length) return s;

        StringBuffer sb = new StringBuffer(length);

        int strLen = 0;
        try {
            strLen = s.getBytes("UTF-8").length;
        } catch (Exception ex) {
            strLen = s.getBytes().length;
        }

        int len = (length - strLen) / 2;
        for (int i = 0; i < len; i++) {
            sb.append(ch);
        }

        sb.append(s);

        for (int i = len + strLen; i < length; i++) {
            sb.append(ch);
        }

        return sb.toString();
    }

    public static String padr(String s, int length, char ch) {
        return fillAtEnd(s, length, ch);
    }

    public static String fillAtEnd(String s, int length, char ch) {
        if (s.length() >= length) return s;

        StringBuffer sb = new StringBuffer(s);
        for (int i = 0; i < length - s.length(); i++) {
            sb.append(ch);
        }

        return sb.toString();
    }

    public static String padl(String s, int length, char ch) {
        return fillAtHead(s, length, ch);
    }

    /**
     * padding a StringBuffer to the fixed length with a character from head
     *
     * @param s  the existed StringBuffer
     * @param len the expected length to expanded
     * @param ch   character to pad with
     * @return String
     */
    public static String fillAtHead(String s, int len, char ch) {
        if (s.length() >= len) return s;

        StringBuffer sb = new StringBuffer(len);
        for (int i = 0; i < len - s.length(); i++) {
            sb.append(ch);
        }
        return sb.append(s).toString();
    }

    /**
     * Generate a string which is composite of a repeatedString for length times.
     *
     * @param length         the repeated times
     * @param repeatedString  repeated string
     * @return String
     */
    public static String repeat(int length, String repeatedString) {
        if (length <= 0) {
            throw new IllegalArgumentException("length must be greater than 0.");
        }

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            sb.append(repeatedString);
        }

        return sb.toString();
    }

    /**
     * Return an empty space string with the specified length
     *
     * @param length 长度
     * @return String
     */
    public static String space(int length) {
        return repeat(length, " ");
    }

    /**
     * Create a space string which looks visually the same length as the input string,
     * by replacing all non-white-space to ' '.
     *
     * @param s String
     * @return String
     */
    public static String space(String s) {
        if (isEmptyString(s)) return "";

        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            sb.append(Character.isWhitespace(ch) ? ch : ' ');
        }

        return sb.toString();
    }

    /**
     * Replace a sub string with a new string in a string.
     *
     * @param sourceString The string which will be changed.
     * @param oldSubString The sub-string which will be replaced
     * @param newSubString The new sub-string which will take the place of the old string
     * @return source string if no replacement happens or source string is null
     * a new replaced string otherwise
     */
    public static String replaceSubString(String sourceString,
                                          String oldSubString,
                                          String newSubString) {
        if (oldSubString == null || oldSubString.length() == 0) {
            throw new RuntimeException("Old sub string could not be null or empty");
        }

        if (newSubString == null) {
            throw new RuntimeException("New sub string could not be null");
        }

        if (sourceString == null) {
            return sourceString;
        }

        StringBuffer sb = new StringBuffer("");
        int prePosition = 0, nextPosition;
        int len = oldSubString.length();

        while ((nextPosition = sourceString.indexOf(oldSubString, prePosition)) !=
                -1) {
            sb.append(sourceString.substring(prePosition, nextPosition));
            sb.append(newSubString);
            prePosition = nextPosition + len;
        }

        if (prePosition == 0) {
            return sourceString;
        } else {
            sb.append(sourceString.substring(prePosition));
            return sb.toString();
        }
    }

    /**
     * Replace a sub string with a new string in a string. Put space in according count
     * before each new line.
     *
     * @param sourceString  The string which will be changed.
     * @param oldSubString  The sub-string which will be replaced
     * @param newSubString  The new sub-string which will take the place of the old string
     * @param positionAware indicate whether to put space character before each new line
     * @return String
     */
    public static String replaceSubString(String sourceString,
                                          String oldSubString,
                                          String newSubString,
                                          boolean positionAware) {
        if (oldSubString == null || oldSubString.length() == 0) {
            throw new RuntimeException("Old sub string could not be null or empty");
        }

        if (newSubString == null) {
            throw new RuntimeException("New sub string could not be null");
        }

        if (sourceString == null) {
            return sourceString;
        }

        StringBuffer sb = new StringBuffer("");
        int prePosition = 0, nextPosition, tempPosition;
        int len = oldSubString.length();
        boolean addSpace = positionAware &
                (newSubString.indexOf("\r\n") != -1);

        String spaceString = null;

        while ((nextPosition = sourceString.indexOf(oldSubString, prePosition)) !=
                -1) {
            sb.append(sourceString.substring(prePosition, nextPosition));

            if (addSpace) {
                tempPosition = lastIndexOf(sourceString, "\r\n", nextPosition);

                if (tempPosition == -1) {
                    spaceString = sourceString.substring(0, nextPosition);
                } else {
                    spaceString = sourceString.substring(tempPosition + 1, nextPosition);
                }

                if (spaceString.trim().length() != 0) {
                    spaceString = space(spaceString);
                }
                // todo: the following method exist potential bugs, fix it later
                // should be able to handle different kind of line separator
                sb.append(replaceSubString(newSubString,
                        "\r\n",
                        "\r\n" + spaceString));
            } else {
                sb.append(newSubString);
            }

            prePosition = nextPosition + len;
        }

        if (prePosition == 0) {
            return sourceString;
        } else {
            sb.append(sourceString.substring(prePosition));
            return sb.toString();
        }
    }

    public static int indexOf(String source, String searchStrings[]) {
        return indexOf(source, searchStrings, 0);
    }

    /**
     * Search for the first appearance of string in searchStrings within source string.
     *
     * @param source        String
     * @param searchStrings String[]
     * @param from          int
     * @return int
     */
    public static int indexOf(String source, String searchStrings[], int from) {
        int firstPos = -1;
        for (int i = 0; i < searchStrings.length; i++) {
            int pos = source.indexOf(searchStrings[i], from);
            if (pos >= 0) {
                if (firstPos == -1) {
                    firstPos = pos;
                } else if (pos < firstPos) {
                    firstPos = pos;
                }
            }
        }

        return firstPos;
    }

    /**
     * Get the last index of any char in searchStrings in the source string.
     *
     * @param source source
     * @param searchStrings search Strings
     * @param from from
     * @return int
     */
    public static int lastIndexOf(String source, String searchStrings, int from) {
        int pos = -1;

        for (int i = 0; i < searchStrings.length(); i++) {
            char ch = searchStrings.charAt(i);
            int lastIndex = source.lastIndexOf(ch, from);
            if (lastIndex > pos) {
                pos = lastIndex;
            }
        }

        return pos;
    }

    public static boolean isEmptyString(String s) {
        return (s == null || trim(s).length() == 0);
    }

    public static String collectionToString(Collection c) {
        return collectionToString(c, " ");
    }

    public static String collectionToString(Collection c, String delims) {
        if (c == null || c.isEmpty()) {
            return "";
        }

        if (delims == null) {
            delims = " ";
        }

        StringBuffer sb = new StringBuffer(128);
        int j = 0;
        for (Iterator i = c.iterator(); i.hasNext(); ) {
            if (j != 0) {
                sb.append(delims);
            }
            sb.append(i.next());
            j++;
        }

        return sb.toString();
    }

    /**
     * Return a comma delim string. Can be used to create in parameter in sql.
     * Given: s = abc def  gh, ijk  escapeChar = '
     * Return: 'abc','def','gh','ijk'
     * @param s String
     * @param escapeChar escape Char
     * @return String
     */
    public static String toCommaDelim(String s, String escapeChar) {
        if (escapeChar == null) {
            escapeChar = "";
        }

        StringBuffer sb = new StringBuffer();

        List list = toList(s);
        int length = list.size() - 1;
        for (int i = 0; i < length; i++) {
            sb.append(escapeChar).append(list.get(i)).append(escapeChar).append(",");
        }
        sb.append(escapeChar).append(list.get(list.size() - 1)).append(escapeChar);

        return sb.toString();
    }

    public static List toList(String s) {
        return toList(s, ", ");
    }

    /**
     * Transform a separated string into a list.
     *
     * @param s string
     * @param delims the separators
     * @return a List or null
     */
    public static List toList(String s, String delims) {
        return toList(s, delims, String.class);
    }

    /**
     * Transform a separated string into a list.  The class of the item in the
     * list is specified by the clazz parameter.
     *
     * @param s string
     * @param delims the separators
     * @param clazz Class
     * @return a List or null
     */
    public static List toList(String s, String delims, Class clazz) {
        if (isEmptyString(s)) {
            return new Vector(0);
        }

        Constructor constructor = null;
        try {
            if (clazz != null) {
                constructor = clazz.getConstructor(new Class[]{String.class});
            }
        } catch (Exception ex) {
            // ignore
        }

        StringTokenizer st = new StringTokenizer(s, delims);

        List list = new Vector();

        if (constructor != null) {
            try {
                while (st.hasMoreTokens()) {
                    list.add(constructor.newInstance(new Object[]{st.nextToken()}));
                }
            } catch (Throwable t) {
                //throw ExceptionHelper.getGenericException(t);
            }
        } else {
            while (st.hasMoreTokens()) {
                list.add(st.nextToken());
            }
        }

        if (list.isEmpty())
            return null;
        else
            return list;
    }

    public static String fromList(List list) {
        if (list == null) return null;
        if (list.isEmpty()) return "";

        StringBuffer sb = new StringBuffer();
        for (Iterator i = list.iterator(); i.hasNext(); ) {
            sb.append(i.next()).append(" ");
        }

        return sb.toString().trim();
    }

    public static boolean isUSAscii(String s) {
        try {
            return (s.length() == s.getBytes("UTF-8").length);
        } catch (Throwable t) {
            //throw ExceptionHelper.getGenericException(t);
            return false;
        }
    }

    /**
     * Convert a String to UTF-8 format regardless its encoding.
     *
     * @param s string
     * @return String
     */
    public static String toUTF8(String s) {
        StringBuffer sb = new StringBuffer();

        for (int j = 0; j < s.length(); j++) {
            char c = s.charAt(j);
            if (c >= '\001' && c <= '\177') {
                sb.append(c);
            } else if (c > '\u07FF') {
                sb.append(0xe0 | c >> 12 & 0xf);
                sb.append(0x80 | c >> 6 & 0x3f);
                sb.append(0x80 | c >> 0 & 0x3f);
            } else {
                sb.append(0xc0 | c >> 6 & 0x1f);
                sb.append(0x80 | c >> 0 & 0x3f);
            }
        }
        return sb.toString();
    }

    /**
     * Transform a ',' or ' ' separated string into a Map
     *
     * @param s string
     * @return a Map or null
     */
    public static Map toMap(String s) {
        return toMap(s, ", ");
    }

    /**
     * Transform a separated string into a Map.
     *
     * @param s string
     * @param delims the separators
     * @return a Map or null
     */
    public static Map toMap(String s, String delims) {
        if (s == null || s.trim().length() == 0) {
            return null;
        }

        StringTokenizer st = new StringTokenizer(s, delims);

        Map map = new HashMap();

        while (st.hasMoreTokens()) {
            String value = st.nextToken();
            map.put(value, value);
        }

        if (map.isEmpty())
            return null;
        else
            return map;
    }

    /**
     * Truncate the string if it ends with any string in stringList.
     * If it ends with multiple strings in stringList, then truncate it as
     * long as possible (with the longest match in stringList).
     * @param s String
     * @param stringList string List
     * @return String
     */
    public static String truncateStringInList(String s, List stringList) {

        int maxLength = -1;
        int pos = -1;

        for (int i = 0; i < stringList.size(); i++) {
            String stringInList = (String) stringList.get(i);
            if (s.endsWith(stringInList)) {
                if (stringInList.length() > maxLength) {
                    maxLength = stringInList.length();
                    pos = i;
                }
            }
        }

        if (pos != -1) {
            return s.substring(0, s.length() - maxLength);
        } else {
            return s;
        }
    }

    /**
     * Return the count of double byte characters in a string.
     *
     * @param s String
     * @return int
     */
    public static int countDoubleByteChar(String s) {
        return s.length() - countSingleByteChar(s);
    }

    /**
     * Return the count of single byte characters in a string.
     *
     * @param s String
     * @return int
     */
    public static int countSingleByteChar(String s) {
        int count = 0;

        for (int i = 0; i < s.length(); i++) {
            if (isSingleByteChar(s.charAt(i))) {
                count++;
            }
        }

        return count;
    }


    /**
     * Check if a character is single byte.
     *
     * @param c char
     * @return boolean
     */
    public static boolean isSingleByteChar(char c) {
        return (c & 0xff00) == 0;
    }

    /**
     * Split a string into a list.  String is delimmed by space.
     * Each sring in the result list is one or more complete words.
     * If the length of a word exceeds the maxLength, the whole word will
     * still be stored in the result list.  But the return value will be the
     * maximum length of that kind of words.
     *
     * @param result    the list to hold the result
     * @param s String
     * @param maxLength int
     * @return the actual display length of the items in result
     */
    public static int split(List result, String s, int maxLength) {
        return split(result, s, maxLength, " ");
    }

    /**
     * Split a string into a list.
     *
     * @param result    the list to hold the result
     * @param s String
     * @param maxLength int
     * @param delim String
     * @return the actual display length of the items in result
     */
    public static int split(List result, String s, int maxLength, String delim) {

        int displayLength = maxLength;

        StringTokenizer st = new StringTokenizer(s, delim);
        StringBuffer line = null;

        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            int tokenLength = countDoubleByteChar(token) + token.length();

            if (tokenLength >= displayLength) {
                displayLength = token.length();
                if (line != null) {
                    result.add(line);
                    line = null;
                }
                result.add(token);
            } else if (line == null) {
                line = new StringBuffer(token);
            } else if (line.length() + tokenLength > displayLength - 1) {
                result.add(line);
                line = null;
                result.add(token);
            } else {
                line.append(" ").append(token);
            }
        }

        return displayLength;
    }

    /**
     * Replace tokens in a string with the values of System properties.
     *
     * @param s String
     * @return String
     */
    public static String replaceWithSystemProperties(String s) {
        return replaceWithSystemProperties(s, TOKEN_PREFIX, TOKEN_SUFFIX);
    }

    /**
     * Replace tokens in a string with the values of System properties.
     *
     * @param s String
     * @param tokenPrefix token prefix
     * @param tokenSuffix token suffix
     * @return String
     */
    public static String replaceWithSystemProperties(String s, String tokenPrefix, String tokenSuffix) {
        Collection tokens = getTokens(s, tokenPrefix, tokenSuffix);
        for (Iterator i = tokens.iterator(); i.hasNext(); ) {
            String token = (String) i.next();
            s = StringUtil.replaceSubString(s, tokenPrefix + token + tokenSuffix, System.getProperty(token));
        }
        return s;
    }


    /**
     * Get all the tokens in a string.  Each token is inside a ${}
     *
     * @param s String
     * @return a collection of tokens inside the string. Each token is embraced
     * with ${}
     */
    public static Collection getTokens(String s) {
        return getTokens(s, TOKEN_PREFIX, TOKEN_SUFFIX);
    }


    /**
     * Get all the tokens in a string.  Each token is inside a ${}
     *
     * @param s String
     * @param tokenPrefix token Prefix
     * @param tokenSuffix token Suffix
     * @return a collection of tokens inside the string. Each token is embraced
     * with ${}
     * empty Collection if no token found
     */
    public static Collection getTokens(String s, String tokenPrefix, String tokenSuffix) {
        if (tokenPrefix == null || tokenPrefix.length() == 0) {
            throw new IllegalArgumentException("Token prefix can not be null or empty");
        }

        if (tokenSuffix == null || tokenSuffix.length() == 0) {
            throw new IllegalArgumentException("Token suffix can not be null or empty");
        }

        if (tokenPrefix.trim().equals(tokenSuffix.trim())) {
            throw new IllegalArgumentException("Token prefix can not be the same as suffix after trimming");
        }

        Vector tokens = new Vector();

        int start, end;
        int lengthOfTokenPrefix = tokenPrefix.length();
        int lengthOfTokenSuffix = tokenSuffix.length();

        for (start = 0; (start = s.indexOf(tokenPrefix, start)) != -1; ) {
            end = s.indexOf(tokenSuffix, start);
            if (end != -1) {
                start = s.lastIndexOf(tokenPrefix, end);
                tokens.add(s.substring(start + lengthOfTokenPrefix, end));
                start = end + lengthOfTokenSuffix;
            } else {
                break;
            }
        }

        return tokens;
    }


    /**
     * Replace the tokens in a string with the value of a bean's property.
     *
     * @param s String
     * @param bean Object
     * @return String
     */
    public static String replaceTokensByBeanProperties(String s, Object bean) {
        try {
            Collection tokens = getTokens(s, TOKEN_PREFIX, TOKEN_SUFFIX);
            for (Iterator i = tokens.iterator(); i.hasNext(); ) {
                String token = (String) i.next();
                s = StringUtils.replace(s, TOKEN_PREFIX + token + TOKEN_SUFFIX, BeanUtils.getProperty(bean, token));
            }
            return s;
        } catch (Throwable t) {
            //throw ExceptionHelper.getGenericException(t);
            return null;
        }
    }


    /**
     * Check if a string contains token, which starts with tokenPrefix and ends
     * with tokenSuffix.
     *
     * @param s String
     * @param tokenPrefix token prefix
     * @param tokenSuffix token suffix
     * @return boolean
     */
    public static boolean hasToken(String s, String tokenPrefix, String tokenSuffix) {
        if (tokenPrefix == null || tokenPrefix.length() == 0) {
            throw new IllegalArgumentException("Token prefix can not be null or empty");
        }

        if (tokenSuffix == null || tokenSuffix.length() == 0) {
            throw new IllegalArgumentException("Token suffix can not be null or empty");
        }

        int begin = s.indexOf(tokenPrefix);
        if (begin == -1) {
            return false;
        }

        int end = s.indexOf(tokenSuffix, begin + tokenPrefix.length());
        if (end == -1) {
            return false;
        }

        return true;
    }

    /**
     * Judge if a string is a number.
     *
     * @param str String
     * @return boolean
     */
    public static boolean isNumber(String str) {
        if (isEmptyString(str)) return false;

        String s = str.trim();
        if (s.startsWith("+") || s.startsWith("-")) {
            s = s.substring(1);
        }

        for (int i = 0; i < s.length(); i++) {
            if (!Character.isDigit(s.charAt(i)) && '.' != s.charAt(i)) {
                return false;
            }
        }

        return true;
    }

    /**
     * For a string "abc. - ac 1 ab", get "abcacab"
     *
     * @param name String
     * @return String
     */
    public static String keepLetterOnly(String name) {
        StringBuffer sb = new StringBuffer(128);
        for (int i = 0; i < name.length(); i++) {
            char ch = name.charAt(i);
            if (Character.isLetter(ch)) {
                sb.append(ch);
            }
        }
        return sb.toString();
    }

    /**
     * Beautify SQL by removing unnecessary white spaces.
     *
     * @param sql String
     * @return String
     */
    public static String beautifySQL(String sql) {
        if (sql == null || sql.trim().length() == 0) {
            return sql;
        }

        StringBuffer sb = new StringBuffer(sql.length());
        boolean append = false;
        boolean insideQuote = false;
        char[] chars = sql.toCharArray();
        char escapeChar = '\'';
        char lastChar = 0;

        for (int i = 0; i < chars.length; i++) {
            char ch = chars[i];
            if (ch == escapeChar) {
                insideQuote = !insideQuote;
                append = true;
                sb.append(ch);
                lastChar = ch;
            } else if (Character.isWhitespace(ch)) {
                if (insideQuote) {
                    sb.append(' ');
                    lastChar = ' ';
                } else if (append && !(lastChar == ',' || lastChar == '=')) {
                    sb.append(' ');
                    lastChar = ' ';
                    append = false; // only append one blank
                }
            } else {
                if (!insideQuote && lastChar == ' ' && (ch == ',' || ch == '=')) {
                    sb.setCharAt(sb.length() - 1, ch);
                } else {
                    sb.append(ch);
                }
                append = true;
                lastChar = ch;
            }
        }

        if (Character.isWhitespace(sb.charAt(sb.length() - 1))) {
            return sb.substring(0, sb.length() - 1);
        } else {
            return sb.toString();
        }
    }

    /**
     * Trim a string. The difference than the String.trim() is that this method supports blank
     * in GB2312, which is 12288.
     *
     * @param s String
     * @return String
     */
    public static String trim(String s) {
        if (s == null) {
            return null;
        }
        int len = s.length();
        int st = 0;
        char[] val = s.toCharArray();

        while ((st < len) && (val[st] <= ' ' || val[st] == 12288)) { // 12288 is Chinese blank
            st++;
        }
        while ((st < len) && (val[len - 1] <= ' ' || val[len - 1] == 12288)) {
            len--;
        }
        return ((st > 0) || (len < s.length())) ? s.substring(st, len) : s;
    }

    /**
     * 字符串首字母大写
     *
     * @param str String
     * @return String
     */
    public static String capitalize(String str) {
        StringBuffer strbuf = new StringBuffer();
        strbuf.append(String.valueOf(str.charAt(0)).toUpperCase());
        strbuf.append(str.substring(1, str.length()));
        return new String(strbuf);
    }

    /**
     * 字符串首字母小写
     *
     * @param str String
     * @return String
     * add by lutao
     */
    public static String capitalizeLower(String str) {
        StringBuffer strbuf = new StringBuffer();
        strbuf.append(String.valueOf(str.charAt(0)).toLowerCase());
        strbuf.append(str.substring(1, str.length()));
        return new String(strbuf);
    }

    /**
     * 判断字符串数组中是否存在重复数据
     *
     * @param sArray String Array
     * @return boolean
     */
    public static boolean hasDupValue(String[] sArray) {
        if (sArray != null && sArray.length > 1) {
            for (int i = 0; i < sArray.length; i++) {
                String s = StringUtil.trim(sArray[i]);
                for (int j = i + 1; j < sArray.length; j++) {
                    if (s.equals(StringUtil.trim(sArray[j]))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * @param list list
     * @return String
     */
    public static String collectionToStringWithQuote(List list) {
        String s = "";

        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                s = s + ",'" + list.get(i) + "'";
            }
        }
        return s.substring(1);
    }

    public static final char UNDERLINE = '_';

    public static String camelToUnderline(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append(UNDERLINE);
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static String underlineToCamel(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (c == UNDERLINE) {
                if (++i < len) {
                    sb.append(Character.toUpperCase(param.charAt(i)));
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
    }

}
