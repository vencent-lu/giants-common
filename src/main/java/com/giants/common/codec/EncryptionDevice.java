/**
 * 
 */
package com.giants.common.codec;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.giants.common.codec.rsa.PrivateKey;
import com.giants.common.codec.rsa.PublicKey;
import com.giants.common.codec.rsa.RSAKeyPair;

/**
 * @author vencent.lu
 *
 */
public class EncryptionDevice {	
	protected static final Logger logger = LoggerFactory.getLogger(EncryptionDevice.class);
	
	public static String md5ByBase64Code(String str)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		return new String(Base64.encode(md5.digest(str.getBytes("utf-8"))));
	}
	
	public static String md5ByHexCode(String str)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		return new String(Hex.encode(md5.digest(str.getBytes("utf-8"))));
	}
	
	public static byte[] encryptDES(byte[] data, byte[] key)
			throws InvalidKeyException, NoSuchAlgorithmException,
			InvalidKeySpecException, NoSuchPaddingException,
			IllegalBlockSizeException, BadPaddingException {
		SecureRandom sr = new SecureRandom();
		DESKeySpec dks = new DESKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey securekey = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
		return cipher.doFinal(data);
	}
	
	public static String encryptDesByBase64Code(String data, String key)
			throws InvalidKeyException, NoSuchAlgorithmException,
			InvalidKeySpecException, NoSuchPaddingException,
			IllegalBlockSizeException, BadPaddingException {
		return new String(Base64.encode(encryptDES(data.getBytes(),
				key.getBytes())));
	}
	
	public static String encryptDesByHexCode(String data, String key)
			throws InvalidKeyException, NoSuchAlgorithmException,
			InvalidKeySpecException, NoSuchPaddingException,
			IllegalBlockSizeException, BadPaddingException {
		return new String(
				Hex.encode(encryptDES(data.getBytes(), key.getBytes())));
	}
	
	public static String encryptDesByBase64CodeForUrl(String data, String key)
			throws InvalidKeyException, NoSuchAlgorithmException,
			InvalidKeySpecException, NoSuchPaddingException,
			IllegalBlockSizeException, BadPaddingException {
		return Base64.encodeForUrl(encryptDES(data.getBytes(), key.getBytes()));
	}
	
	public static byte[] decryptDES(byte[] data, byte[] key)
			throws InvalidKeyException, NoSuchAlgorithmException,
			InvalidKeySpecException, NoSuchPaddingException,
			IllegalBlockSizeException, BadPaddingException {
		SecureRandom sr = new SecureRandom();
		DESKeySpec dks = new DESKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey securekey = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
		return cipher.doFinal(data);
	}
	
	public static String decryptDesByBase64Code(String data, String key) {
		if (data == null) return null;
		try {
			return new String(decryptDES(Base64.decode(data.getBytes()), key.getBytes()));
		} catch (Exception e) {
			logger.error("decrypt failure!", e);
			return null;
		}
	}
	
	public static String decryptDesByBase64CodeForUrl(String data, String key) {
		if (data == null) return null;
		data = Base64.urlRestoreStanderdBase64(data);
		try {
			return new String(decryptDES(Base64.decode(data.getBytes()), key.getBytes()));
		} catch (Exception e) {
			logger.error("decrypt failure!", e);
			return null;
		}
	}
	
	/**
	 * 创建密钥对
	 * @param keysize 密钥长度
	 * @return 密钥对
	 */
	public static RSAKeyPair generateRSAKeyPair(int keysize) {
		return new RSAKeyPair(keysize);
	}
	
	/**
	 * RSA加密后进行16进制编码
	 * @param plainText 明文
	 * @param publicKey 公钥
	 * @return 16进制编码密文
	 */
	public static String encryptRsaByHexCode(String plainText, PublicKey publicKey) {
		return new String(Hex.encode(encryptRsa(plainText.getBytes(), publicKey)));
	}
	
	/**
	 * RSA加密后用Base64编码
	 * @param plainText 明文
	 * @param publicKey 公钥
	 * @return Base64编码密文
	 */
	public static String encryptRsaByBase64Code(String plainText, PublicKey publicKey) {
		return new String(Base64.encode(encryptRsa(plainText.getBytes(), publicKey)));
	}
	
	/**
	 * RSA加密
	 * @param plainBytes 明文字节数组
	 * @param publicKey 公钥
	 * @return 密文字节数组
	 */
	public static byte[] encryptRsa(byte[] plainBytes, PublicKey publicKey) {
		if (ArrayUtils.isEmpty(plainBytes)) {
			return null;
		}
		try {
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, publicKey.getRsaPublicKey());
			int allowMaxLength = (int)Math.ceil((double)publicKey.getKeysize()/8)-11;
			if (plainBytes.length <= allowMaxLength) {
				return cipher.doFinal(plainBytes);
			} else {
				byte[] result = null;
				while (plainBytes.length != 0) {
					result = ArrayUtils.addAll(result, cipher.doFinal(ArrayUtils.subarray(plainBytes, 0, allowMaxLength)));
					plainBytes = ArrayUtils.subarray(plainBytes, allowMaxLength, plainBytes.length);
				}
				return result;
			}			
		} catch (NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidKeyException | IllegalBlockSizeException
				| BadPaddingException e) {
			logger.error("RSA encrypt error!", e);
		}
		return null;
	}
	
	/**
	 * RSA解密16进制编码的密文
	 * @param cipherText 密文
	 * @param privateKey 私钥
	 * @return 明文
	 */
	public static String decryptRsaByHexCode(String cipherText, PrivateKey privateKey) {
		return new String(decryptRsa(Hex.decodeByString(cipherText), privateKey));
	}
	
	/**
	 * RSA解密Base64编码的密文
	 * @param cipherText 密文
	 * @param privateKey 私钥
	 * @return 明文
	 */
	public static String decryptRsaByBase64Code(String cipherText, PrivateKey privateKey) {
		return new String(decryptRsa(Base64.decode(cipherText.getBytes()), privateKey));
	}
	
	/**
	 * RSA解密
	 * @param cipherBytes 密文字节数组
	 * @param privateKey 私钥
	 * @return 明文字节数组
	 */
	public static byte[] decryptRsa(byte[] cipherBytes, PrivateKey privateKey) {
		if (ArrayUtils.isEmpty(cipherBytes)) {
			return null;
		}
		try {
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, privateKey.getRsaPrivateKey());
			int unitCipherLength = (int)Math.ceil((double)privateKey.getKeysize()/8);
			if ((cipherBytes.length % unitCipherLength) != 0) {
				logger.error("Ciphertext bytes length must be {} integer times", unitCipherLength);
				return null;
			}
			if (cipherBytes.length == unitCipherLength) {
				return cipher.doFinal(cipherBytes);
			} else {
				byte[] result = null;
				while (cipherBytes.length != 0) {
					result = ArrayUtils.addAll(result, cipher.doFinal(ArrayUtils.subarray(cipherBytes, 0, unitCipherLength)));
					cipherBytes = ArrayUtils.subarray(cipherBytes, unitCipherLength, cipherBytes.length);
				}
				return result;
			}
		} catch (NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidKeyException | IllegalBlockSizeException
				| BadPaddingException e) {
			logger.error("RSA decrypt error!", e);
		}
		return null;
	}

}
