/**
 *
 */
package com.giants.common.tools;

/**
 * @author vencent.lu
 *
 */
public class FilterClassNotFindNoArgumentsConstructorException extends RuntimeException {
    private static final long serialVersionUID = -6507469962545809162L;

    private Class<?> filterClass;

    protected FilterClassNotFindNoArgumentsConstructorException(Class<?> filterClass, Throwable e) {
        super(new StringBuilder(filterClass.getName()).append(" without no arguments constructor!").toString(), e);
        this.filterClass = filterClass;
    }

    public Class<?> getFilterClass() {
        return filterClass;
    }

}
