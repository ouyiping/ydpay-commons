package com.ydpay.enums.base;

/**
 * 用户授权标志
 * 0-需要授权
 * 1-不需要授权
 */
public enum EncryptFlag 
{
	/**0-需要授权*/
	NEED_ENCRYPT {public int getid() {return 0;} }, 
	/**1-不需要授权**/
	DONT_NEED_ENCRYPT {public int getid(){return 1;}};	
	
	public abstract int getid();
}
