/**
 * 
 */
package com.giants.common.exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.giants.common.GiantsConstants;

/**
 * @author vencent.lu
 *
 */
public class DataValidationException extends GiantsException {

	private static final long serialVersionUID = -3309819741742896496L;
	
	private List<FieldError> fieldErrors;
		
	public DataValidationException() {
		super();
	}

	public DataValidationException(String errorMessageKey) {
		super(errorMessageKey);
	}
	
	public void addFieldError(FieldError fieldError) {
		if (this.fieldErrors == null) {
			this.fieldErrors = new ArrayList<FieldError>();
		}
		this.fieldErrors.add(fieldError);
	}

	/* (non-Javadoc)
	 * @see com.giants.common.exception.GiantsException#buildErrorCode()
	 */
	@Override
	public byte buildErrorCode() {
		return GiantsConstants.ERROR_CODE_DATA_CHECK_FAILURE;
	}
	
	public List<FieldError> getFieldErrors() {
		return fieldErrors;
	}
	
	public FieldError createFieldError(String fieldName, String errorMsgKey, Object[] args) {
		return new FieldError(fieldName, errorMsgKey, args);
	}

	

	@Override
    public Object getErrorData() {
        return null;
    }

    @Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DataValidationException [errorMessageKey=");
		builder.append(getErrorMessageKey());
		builder.append(", messageArgs=");
		builder.append(getMessageArgs());
		builder.append(", errorCode=");
		builder.append(buildErrorCode());
		builder.append(", fieldErrors=");
		builder.append(fieldErrors);
		builder.append("]");
		return builder.toString();
	}



	public class FieldError {
		private String fieldName;
		private String errorMsgKey;
		private Object[] args;
				
		public FieldError() {
			super();
		}

		public FieldError(String fieldName, String errorMsgKey, Object[] args) {
			super();
			this.fieldName = fieldName;
			this.errorMsgKey = errorMsgKey;
			this.args = args;
		}

		public String getFieldName() {
			return fieldName;
		}

		public void setFieldName(String fieldName) {
			this.fieldName = fieldName;
		}

		public String getErrorMsgKey() {
			return errorMsgKey;
		}

		public void setErrorMsgKey(String errorMsgKey) {
			this.errorMsgKey = errorMsgKey;
		}

		public Object[] getArgs() {
			return args;
		}

		public void setArgs(Object[] args) {
			this.args = args;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("FieldError [fieldName=");
			builder.append(fieldName);
			builder.append(", errorMsgKey=");
			builder.append(errorMsgKey);
			builder.append(", args=");
			builder.append(Arrays.toString(args));
			builder.append("]");
			return builder.toString();
		}		
		
	}

}
