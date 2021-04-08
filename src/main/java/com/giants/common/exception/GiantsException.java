package com.giants.common.exception;

import java.util.ArrayList;
import java.util.List;

import com.giants.common.SpringContextHelper;

/**
 * @author vencent.lu
 */
public abstract class GiantsException extends RuntimeException {

    private static final long serialVersionUID = -516478451762223581L;

    private String errorMessageKey;
    private List<Object> messageArgs;

    private String errorMessage;

    public abstract byte buildErrorCode();

    public abstract Object getErrorData();

    public GiantsException() {
        super();
    }

    public GiantsException(String errorMessageKey, Throwable e) {
        super(errorMessageKey, e);
        this.errorMessage = SpringContextHelper.getMessage(errorMessageKey);
    }

    public GiantsException(String errorMessageKey, Object arg, Throwable e) {
        super(errorMessageKey, e);
        this.errorMessageKey = errorMessageKey;
        this.messageArgs = new ArrayList<Object>();
        this.messageArgs.add(arg);
        this.errorMessage = SpringContextHelper.getMessage(errorMessageKey, arg);
    }

    public GiantsException(String errorMessageKey, Object[] argArray, Throwable e) {
        super(errorMessageKey, e);
        this.errorMessageKey = errorMessageKey;
        this.messageArgs = new ArrayList<Object>();
        for (Object arg : argArray) {
            this.messageArgs.add(arg);
        }
        this.errorMessage = SpringContextHelper.getMessage(errorMessageKey, argArray);
    }

    public GiantsException(String errorMessageKey) {
        super(errorMessageKey);
        this.errorMessageKey = errorMessageKey;
        this.errorMessage = SpringContextHelper.getMessage(errorMessageKey);
    }

    public GiantsException(String errorMessageKey, Object arg) {
        super(errorMessageKey);
        this.errorMessageKey = errorMessageKey;
        this.messageArgs = new ArrayList<Object>();
        this.messageArgs.add(arg);
        this.errorMessage = SpringContextHelper.getMessage(errorMessageKey, arg);
    }

    public GiantsException(String errorMessageKey, Object[] argArray) {
        super(errorMessageKey);
        this.errorMessageKey = errorMessageKey;
        this.messageArgs = new ArrayList<Object>();
        for (Object arg : argArray) {
            this.messageArgs.add(arg);
        }
        this.errorMessage = SpringContextHelper.getMessage(errorMessageKey, argArray);
    }

    public String getErrorMessageKey() {
        return errorMessageKey;
    }

    public List<Object> getMessageArgs() {
        return messageArgs;
    }

    public void setErrorMessageKey(String errorMessageKey) {
        this.errorMessageKey = errorMessageKey;
    }

    public void setMessageArgs(List<Object> messageArgs) {
        this.messageArgs = messageArgs;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.getClass().getSimpleName());
        builder.append(" [errorMessageKey=");
        builder.append(errorMessageKey);
        builder.append(", messageArgs=");
        builder.append(messageArgs);
        builder.append(", errorMessage=");
        builder.append(errorMessage);
        builder.append(", errorCode=");
        builder.append(buildErrorCode());
        builder.append("]");
        return builder.toString();
    }

}
