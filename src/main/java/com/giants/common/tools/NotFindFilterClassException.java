/**
 *
 */
package com.giants.common.tools;

/**
 * @author vencent.lu
 *
 */
public class NotFindFilterClassException extends RuntimeException {
    private static final long serialVersionUID = -4566595278369428630L;

    protected NotFindFilterClassException() {
        super(
                "PageQueryCondition initialization failed, please set filters or implementation PageCondition class extends PageQueryCondition class");
    }

}
