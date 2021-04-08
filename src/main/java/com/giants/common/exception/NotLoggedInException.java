/**
 *
 */
package com.giants.common.exception;

import com.giants.common.GiantsConstants;

/**
 * @author vencent.lu
 *
 * Create Date:2013年12月20日
 */
public class NotLoggedInException extends BusinessException {

    private static final long serialVersionUID = -1092738358651157116L;

    public NotLoggedInException() {
        super();
    }

    /**
     * @param errorKey 错误KEY
     * @param errorMessageKey 错误信息资源Key
     */
    public NotLoggedInException(String errorKey, String errorMessageKey) {
        super(errorKey, errorMessageKey);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param errorKey 错误KEY
     * @param errorMessageKey 错误信息资源Key
     * @param arg 资源参数
     */
    public NotLoggedInException(String errorKey, String errorMessageKey,
                                Object arg) {
        super(errorKey, errorMessageKey, arg);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param errorKey 错误KEY
     * @param errorMessageKey 错误信息资源Key
     * @param argArray 资源参数数组
     */
    public NotLoggedInException(String errorKey, String errorMessageKey,
                                Object[] argArray) {
        super(errorKey, errorMessageKey, argArray);
        // TODO Auto-generated constructor stub
    }

    /* (non-Javadoc)
     * @see com.giants.common.exception.BusinessException#buildErrorCode()
     */
    @Override
    public byte buildErrorCode() {
        return GiantsConstants.ERROR_CODE_NOT_LOGGED_IN;
    }

}
