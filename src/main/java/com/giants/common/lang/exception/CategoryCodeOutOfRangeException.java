package com.giants.common.lang.exception;

/**
 * @author vencent.lu
 */
public class CategoryCodeOutOfRangeException extends Exception {

    private static final long serialVersionUID = -5471026582527519627L;

    private String categoryCode = null;

    public CategoryCodeOutOfRangeException(String categoryCode) {
        super(new StringBuffer(
                "Category Code Out Of Range! For input String:\"")
                .append(categoryCode).append("\"").toString());
        this.categoryCode = categoryCode;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

}
