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
	 * @param errorKey
	 * @param errorMessageKey
	 */
	public NotLoggedInException(String errorKey, String errorMessageKey) {
		super(errorKey, errorMessageKey);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param errorKey
	 * @param errorMessageKey
	 * @param arg
	 */
	public NotLoggedInException(String errorKey, String errorMessageKey,
			Object arg) {
		super(errorKey, errorMessageKey, arg);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param errorKey
	 * @param errorMessageKey
	 * @param argArray
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
