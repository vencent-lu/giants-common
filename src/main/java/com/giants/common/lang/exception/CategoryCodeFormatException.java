package com.giants.common.lang.exception;

/**
 * @author vencent.lu
 */
public class CategoryCodeFormatException extends Exception {

    private static final long serialVersionUID = 8383276235642098437L;

    private String categoryCode = null;

    public CategoryCodeFormatException(String categoryCode) {
        super(new StringBuffer(
                "Category Code Format Error! For input String:\"")
                .append(categoryCode).append("\"").toString());
        this.categoryCode = categoryCode;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

}
