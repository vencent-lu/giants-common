/**
 *
 */
package com.giants.common.tools;

import org.apache.commons.lang.StringUtils;

import com.giants.common.lang.exception.CategoryCodeFormatException;
import com.giants.common.lang.exception.CategoryCodeOutOfRangeException;

/**
 * @author vencent.lu
 *
 */
public class CategoryCodeTools {

    private int defaultLevelLen = 2;
    private String[] levelLenArr = null;

    public CategoryCodeTools() {
        super();
    }

    public CategoryCodeTools(String codeFormat) {
        String[] format = codeFormat.split(":");
        this.defaultLevelLen = Integer.parseInt(format[0]);
        if (format.length == 2) {
            this.levelLenArr = format[1].split(",");
        }
    }

    public int getLevelCodeLen(int level) {
        if (this.levelLenArr == null || this.levelLenArr.length < level) {
            return this.defaultLevelLen;
        } else {
            if (StringUtils.isEmpty(this.levelLenArr[level - 1])) {
                return this.defaultLevelLen;
            } else {
                return Integer.parseInt(this.levelLenArr[level - 1]);
            }
        }
    }

    public int getLevelCodeFullLen(int leve) {
        int length = 0;
        for (int i = 0; i < leve; i++) {
            length = length + this.getLevelCodeLen(i + 1);
        }
        return length;
    }

    public int getLevel(String categoryCode) throws CategoryCodeFormatException {
        return this.getLevelCodeInfo(categoryCode)[0];
    }

    public int getNextLevelFullLen(String categoryCode) throws CategoryCodeFormatException {
        int[] leveInfo = this.getLevelCodeInfo(categoryCode);
        return leveInfo[1] + this.getLevelCodeLen(++leveInfo[0]);
    }

    public String getNextLevelFirstCode(String categoryCode) throws CategoryCodeFormatException {
        if (categoryCode == null) {
            categoryCode = "";
        }
        int nextLevelLen = this.getLevelCodeLen(this.getLevel(categoryCode) + 1);
        StringBuffer sb = new StringBuffer(categoryCode);
        for (int i = 0; i < nextLevelLen; i++) {
            sb.append('0');
        }
        return sb.toString();
    }

    public String getLevelCode(String categoryCode, int level) throws CategoryCodeFormatException {
        if (level > 0 && level <= this.getLevel(categoryCode)) {
            return categoryCode.substring(0, this.getLevelCodeFullLen(level));
        }
        return null;
    }

    public String[] getAllLevelCodes(String categoryCode) throws CategoryCodeFormatException {
        int level = this.getLevel(categoryCode);
        String[] levelCodes = new String[level];
        for (int i = 0; i < level - 1; i++) {
            levelCodes[i] = this.getLevelCode(categoryCode, i + 1);
        }
        levelCodes[levelCodes.length - 1] = categoryCode;
        return levelCodes;
    }

    public String getPlusOneCode(String categoryCode) throws CategoryCodeFormatException, CategoryCodeOutOfRangeException {
        int[] levelInfo = this.getLevelCodeInfo(categoryCode);
        int levelCodeLen = this.getLevelCodeLen(levelInfo[0]);
        String parentCode = categoryCode.substring(0, levelInfo[1] - levelCodeLen);
        String levelCode = categoryCode.substring(levelInfo[1] - levelCodeLen);
        String plusOneLevelCode = String.valueOf(Integer.parseInt(levelCode) + 1);
        int plusOneLevelLen = plusOneLevelCode.length();
        if (plusOneLevelLen > levelCodeLen) {
            throw new CategoryCodeOutOfRangeException(categoryCode);
        }
        StringBuffer sb = new StringBuffer(parentCode);
        for (int i = plusOneLevelLen; i < levelCodeLen; i++) {
            sb.append('0');
        }
        sb.append(plusOneLevelCode);
        return sb.toString();
    }

    private int[] getLevelCodeInfo(String categoryCode) throws CategoryCodeFormatException {
        int[] levelInfo = new int[2];
        if (StringUtils.isEmpty(categoryCode)) {
            levelInfo[0] = 0;
            levelInfo[1] = 0;
            return levelInfo;
        }
        int level = 0;
        int codeLen = categoryCode.length();
        int levelLen = 0;
        while (levelLen < codeLen) {
            levelLen = levelLen + this.getLevelCodeLen(++level);
        }
        if (levelLen != codeLen) {
            throw new CategoryCodeFormatException(categoryCode);
        }
        levelInfo[0] = level;
        levelInfo[1] = levelLen;
        return levelInfo;
    }

}
