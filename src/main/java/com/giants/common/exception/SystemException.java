/**
 *
 */
package com.giants.common.exception;

import com.giants.common.GiantsConstants;

/**
 * @author vencent.lu
 *
 */
public class SystemException extends GiantsException {

    private static final long serialVersionUID = -3559878294452464545L;

    public SystemException() {
        super();
    }

    public SystemException(String errorMessageKey, Object arg, Throwable e) {
        super(errorMessageKey, arg, e);
    }

    public SystemException(String errorMessageKey, Object[] argArray,
                           Throwable e) {
        super(errorMessageKey, argArray, e);
    }

    public SystemException(String errorMessageKey, Throwable e) {
        super(errorMessageKey, e);
    }

    public SystemException(String errorMessageKey) {
        super(errorMessageKey);
    }

    public SystemException(String errorMessageKey, Object arg) {
        super(errorMessageKey, arg);
    }

    public SystemException(String errorMessageKey, Object[] argArray) {
        super(errorMessageKey, argArray);
    }

    @Override
    public byte buildErrorCode() {
        return GiantsConstants.ERROR_CODE_SYSTEM_ERROR;
    }

    @Override
    public Object getErrorData() {
        return null;
    }

}
