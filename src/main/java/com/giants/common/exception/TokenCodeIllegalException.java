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
	 * @param errorKey
	 * @param errorMessageKey
	 */
	public TokenCodeIllegalException(String errorKey, String errorMessageKey) {
		super(errorKey, errorMessageKey);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param errorKey
	 * @param errorMessageKey
	 * @param arg
	 */
	public TokenCodeIllegalException(String errorKey, String errorMessageKey,
			Object arg) {
		super(errorKey, errorMessageKey, arg);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param errorKey
	 * @param errorMessageKey
	 * @param argArray
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
