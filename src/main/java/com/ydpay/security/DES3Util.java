package com.ydpay.security;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class DES3Util {
	// 算法名称
	public static final String KEY_ALGORITHM = "desede";
	// 算法名称/加密模式/填充方式
	public static final String CIPHER_ALGORITHM = "desede/CBC/PKCS7Padding";

	/**
	 * CBC加密
	 * 
	 * @param key
	 *            密钥
	 * @param keyiv
	 *            IV
	 * @param data
	 *            明文
	 */
	public static byte[] des3EncodeCBC(byte[] key, byte[] keyiv, byte[] data)
			throws Exception {
		return des3EncodeCBC(key, keyiv, data, null);
	}

	/**
	 * 
	 * CBC加密
	 * 
	 * @param key
	 *            密钥
	 * @param keyiv
	 *            IV
	 * @param data
	 *            明文
	 * @param provider
	 *            指定的处理程序
	 */
	public static byte[] des3EncodeCBC(byte[] key, byte[] keyiv, byte[] data,
			Provider provider) throws Exception {
		if (provider != null)
			Security.addProvider(provider);

		Key deskey = keyGenerator(key);
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		IvParameterSpec ips = new IvParameterSpec(keyiv);
		cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
		byte[] bOut = cipher.doFinal(data);
		return bOut;
	}

	/**
	 * 
	 * 生成密钥key对象
	 * 
	 * @param KeyStr
	 *            密钥字符串
	 * @return 密钥对象
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws Exception
	 */
	private static Key keyGenerator(byte[] keyStr) throws Exception {
		DESedeKeySpec KeySpec = new DESedeKeySpec(keyStr);
		SecretKeyFactory KeyFactory = SecretKeyFactory
				.getInstance(KEY_ALGORITHM);
		return ((Key) (KeyFactory
				.generateSecret(((java.security.spec.KeySpec) (KeySpec)))));
	}

	/**
	 * CBC解密
	 * 
	 * @param key
	 *            密钥
	 * @param keyiv
	 *            IV
	 * @param data
	 *            Base64编码的密文
	 * @return 明文
	 * @throws Exception
	 */
	public static byte[] des3DecodeCBC(byte[] key, byte[] keyiv, byte[] data)
			throws Exception {
		return des3DecodeCBC(key, keyiv, data, null);
	}

	/**
	 * CBC解密
	 * 
	 * @param key
	 *            密钥
	 * @param keyiv
	 *            IV
	 * @param data
	 *            Base64编码的密文
	 * @param provider
	 *            指定的处理程序
	 * @return 明文
	 * @throws Exception
	 */
	public static byte[] des3DecodeCBC(byte[] key, byte[] keyiv, byte[] data,
			Provider provider) throws Exception {
		if (provider != null)
			Security.addProvider(provider);
		Key deskey = keyGenerator(key);
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		IvParameterSpec ips = new IvParameterSpec(keyiv);
		cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
		byte[] bOut = cipher.doFinal(data);
		return bOut;
	}

	/**
	 * 使用base64编码的key进行3des加密
	 * 
	 * @param key
	 * @param keyiv
	 * @param data
	 */
	public static byte[] des3EncodeCBCBase64(String key, String keyiv,
			byte[] data) throws Exception {
		return des3EncodeCBCBase64(key, keyiv, data, null);
	}

	/**
	 * 使用base64编码的key进行3des加密
	 * 
	 * @param key
	 * @param keyiv
	 * @param data
	 */
	public static byte[] des3EncodeCBCBase64(String key, String keyiv,
			byte[] data, Provider provider) throws Exception {
			byte[] des3 = des3EncodeCBC(
					Base64Method.DecryptBase64ForByte_default(key),
					Base64Method.DecryptBase64ForByte_default(keyiv), data,
					provider);
			return des3;
	}

	/**
	 * 使用base64编码的key进行3des解密
	 * 
	 * @param key
	 * @param keyiv
	 * @param data
	 * 
	 */
	public static byte[] des3DecodeCBCBase64(String key, String keyiv,
			byte[] data) throws Exception {
		return des3DecodeCBCBase64(key, keyiv, data, null);
	}

	/**
	 * 使用base64编码的key进行3des解密
	 * 
	 * @param key
	 * @param keyiv
	 * @param data
	 * 
	 */
	public static byte[] des3DecodeCBCBase64(String key, String keyiv,
			byte[] data, Provider provider) throws Exception {
		byte[] des3 = des3DecodeCBC(
				Base64Method.DecryptBase64ForByte_default(key),
				Base64Method.DecryptBase64ForByte_default(keyiv), data,
				provider);
		return des3;
	}

}