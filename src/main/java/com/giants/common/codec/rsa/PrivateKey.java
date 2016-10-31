/**
 * 
 */
package com.giants.common.codec.rsa;

import java.security.interfaces.RSAPrivateKey;

/**
 * @author vencent.lu
 *
 */
public class PrivateKey extends Key {
	private static final long serialVersionUID = -458412182354413206L;
	
	private RSAPrivateKey rsaPrivateKey;

	public PrivateKey(int keysize, RSAPrivateKey rsaPrivateKey) {
		super(keysize);
		this.rsaPrivateKey = rsaPrivateKey;
	}

	public RSAPrivateKey getRsaPrivateKey() {
		return rsaPrivateKey;
	}
	
	

}
