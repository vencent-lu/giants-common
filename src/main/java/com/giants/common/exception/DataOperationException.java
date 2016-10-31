package com.giants.common.exception;

import com.giants.common.GiantsConstants;

/**
 * @author vencent.lu
 *
 */
public class DataOperationException extends GiantsException {

	private static final long serialVersionUID = 5808479866183653754L;	
	
	public DataOperationException() {
		super();
	}

	public DataOperationException(String errorMessageKey, Object arg,
			Throwable e) {
		super(errorMessageKey, arg, e);
	}

	public DataOperationException(String errorMessageKey, Object[] argArray,
			Throwable e) {
		super(errorMessageKey, argArray, e);
	}

	public DataOperationException(String errorMessageKey, Throwable e) {
		super(errorMessageKey, e);
	}

	public DataOperationException(String errorMessageKey) {
		super(errorMessageKey);
	}

	public DataOperationException(String errorMessageKey, Object arg) {
		super(errorMessageKey, arg);
	}

	public DataOperationException(String errorMessageKey, Object[] argArray) {
		super(errorMessageKey, argArray);
	}

	@Override
	public byte buildErrorCode() {
		return GiantsConstants.ERROR_CODE_DATA_OPERATION_EXCEPTION;
	}

}
