package com.giants.common.exception;

import com.giants.common.GiantsConstants;

/**
 * @author vencent.lu
 *
 */
public class BusinessException extends GiantsException {
	
	private static final long serialVersionUID = -6147981068757417812L;
	
	private String errorKey;
	
	public BusinessException() {
		super();
	}

	public BusinessException(String errorKey, String errorMessageKey) {
		super(errorMessageKey);
		this.errorKey = errorKey;
	}
	
	public BusinessException(String errorKey, String errorMessageKey,
			Throwable e) {
		super(errorMessageKey, e);
		this.errorKey = errorKey;
	}

	public BusinessException(String errorKey, String errorMessageKey, Object arg) {
		super(errorMessageKey, arg);
		this.errorKey = errorKey;
	}
	
	public BusinessException(String errorKey, String errorMessageKey,
			Object arg, Throwable e) {
		super(errorMessageKey, arg, e);
		this.errorKey = errorKey;
	}

	public BusinessException(String errorKey, String errorMessageKey,
			Object[] argArray) {
		super(errorMessageKey, argArray);
		this.errorKey = errorKey;
	}
	
	public BusinessException(String errorKey, String errorMessageKey,
			Object[] argArray, Throwable e) {
		super(errorMessageKey, argArray, e);
		this.errorKey = errorKey;
	}
	
	@Override
	public byte buildErrorCode() {
		return GiantsConstants.ERROR_CODE_BUSINESS_EXCEPTION;
	}

	/**
	 * @return the errorKey
	 */
	public String getErrorKey() {
		return errorKey;
	}

	/**
	 * @param errorKey the errorKey to set
	 */
	public void setErrorKey(String errorKey) {
		this.errorKey = errorKey;
	}

}
