package com.giants.common;

/**
 * @author vencent.lu
 * 
 */
public class GiantsConstants {
	
	//Exception 错误编码
	public final static byte ERROR_CODE_SUCCESS = 0;//成功，没有错误
	public final static byte ERROR_CODE_NOT_LOGGED_IN = 1;//没有登录，或没有进行认证
	public final static byte ERROR_CODE_DATA_CHECK_FAILURE = 2;//数据验证失败
	public final static byte ERROR_CODE_BUSINESS_EXCEPTION = 3;//业务操作异常
	public final static byte ERROR_CODE_NOT_AUTHORITY = 4;//没有操作权限
	public final static byte ERROR_CODE_DATA_OPERATION_EXCEPTION = 5;//数据操作异常
	public final static byte ERROR_CODE_VIEW_EXCEPTION = 6;//显示层异常
	public final static byte ERROR_CODE_TOKEN_EXCEPTION = 7;//授权码非法
	public final static byte ERROR_CODE_SYSTEM_ERROR = 127;//系统错误	

}
