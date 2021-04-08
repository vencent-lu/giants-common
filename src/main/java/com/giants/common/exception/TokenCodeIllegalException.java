/**
 *
 */
package com.giants.common.exception;

import com.giants.common.GiantsConstants;

/**
 * @author vencent.lu
 *
 */
public class TokenCodeIllegalException extends BusinessException {

    private static final long serialVersionUID = 2670636443254016142L;

    public TokenCodeIllegalException() {
        super();
    }

    /**
     * @param errorKey 错误KEY
     * @param errorMessageKey 错误信息资源Key
     */
    public TokenCodeIllegalException(String errorKey, String errorMessageKey) {
        super(errorKey, errorMessageKey);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param errorKey 错误KEY
     * @param errorMessageKey 错误信息资源Key
     * @param arg 资源参数
     */
    public TokenCodeIllegalException(String errorKey, String errorMessageKey,
                                     Object arg) {
        super(errorKey, errorMessageKey, arg);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param errorKey 错误KEY
     * @param errorMessageKey 错误信息资源Key
     * @param argArray 资源参数数据
     */
    public TokenCodeIllegalException(String errorKey, String errorMessageKey,
                                     Object[] argArray) {
        super(errorKey, errorMessageKey, argArray);
        // TODO Auto-generated constructor stub
    }

    @Override
    public byte buildErrorCode() {
        return GiantsConstants.ERROR_CODE_TOKEN_EXCEPTION;
    }

}
