package com.giants.common.exception;

import com.giants.common.GiantsConstants;

/**
 * @author vencent.lu
 *
 */
public class ViewException extends GiantsException {

	private static final long serialVersionUID = -7781279526943303234L;
	
	public ViewException() {
		super();
	}

	public ViewException(String errorMessageKey, Object arg, Throwable e) {
		super(errorMessageKey, arg, e);
	}

	public ViewException(String errorMessageKey, Object[] argArray, Throwable e) {
		super(errorMessageKey, argArray, e);
	}

	public ViewException(String errorMessageKey, Throwable e) {
		super(errorMessageKey, e);
	}

	public ViewException(String errorMessageKey) {
		super(errorMessageKey);
	}

	public ViewException(String errorMessageKey, Object arg) {
		super(errorMessageKey, arg);
	}

	public ViewException(String errorMessageKey, Object[] argArray) {
		super(errorMessageKey, argArray);
	}

	@Override
	public byte buildErrorCode() {
		return GiantsConstants.ERROR_CODE_VIEW_EXCEPTION;
	}

    @Override
    public Object getErrorData() {
        // TODO Auto-generated method stub
        return null;
    }

}
