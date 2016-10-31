/**
 * 
 */
package com.giants.common.codec.rsa;

import java.io.Serializable;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author vencent.lu
 *
 */
public class RSAKeyPair implements Serializable {
	private static final long serialVersionUID = 3379782164293464743L;
	
	private static final Logger  logger = LoggerFactory.getLogger(RSAKeyPair.class);
	
	private static KeyPairGenerator keyPairGenerator;
	static {
		try {
			keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			logger.error("KeyPairGenerator initialize error!", e);
		}
	}
		
	private int keysize;
	private KeyPair keyPair;
	
	@SuppressWarnings("static-access")
	public RSAKeyPair(int keysize) {
		super();
		this.keysize = keysize;
		keyPairGenerator.initialize(keysize);
		this.keyPair = this.keyPairGenerator.generateKeyPair();		
	}
	
	public PublicKey getPublicKey() {
		if (this.keyPair == null) {
			return null;
		}
		return new PublicKey(this.keysize, (RSAPublicKey)this.keyPair.getPublic());
	}
	
	public PrivateKey getPrivateKey() {
		if (this.keyPair == null) {
			return null;
		}
		return new PrivateKey(this.keysize, (RSAPrivateKey)this.keyPair.getPrivate());
	}

}
