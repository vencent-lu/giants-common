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
public class NotAuthorityException extends BusinessException {

	private static final long serialVersionUID = -6723136045799246142L;

	
	public NotAuthorityException() {
		super();
	}

	/**
	 * @param errorKey
	 * @param errorMessageKey
	 */
	public NotAuthorityException(String errorKey, String errorMessageKey) {
		super(errorKey, errorMessageKey);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param errorKey
	 * @param errorMessageKey
	 * @param arg
	 */
	public NotAuthorityException(String errorKey, String errorMessageKey,
			Object arg) {
		super(errorKey, errorMessageKey, arg);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param errorKey
	 * @param errorMessageKey
	 * @param argArray
	 */
	public NotAuthorityException(String errorKey, String errorMessageKey,
			Object[] argArray) {
		super(errorKey, errorMessageKey, argArray);
	}
	/* (non-Javadoc)
	 * @see com.giants.common.exception.BusinessException#buildErrorCode()
	 */
	@Override
	public byte buildErrorCode() {
		return GiantsConstants.ERROR_CODE_NOT_AUTHORITY;
	}

}
