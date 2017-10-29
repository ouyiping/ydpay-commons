package com.ydpay.security;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AesEncryption {

	public static final String ECB_PKCS5Padding = "AES/ECB/PKCS5Padding";
	public static final String CBC_NoPadding = "AES/CBC/NoPadding";

	public static String Encrypt(String data, String key, String iv,
			String padding) {
		try {

			Cipher cipher = Cipher.getInstance(padding);
			int blockSize = cipher.getBlockSize();

			byte[] dataBytes = data.getBytes("UTF-8");
			int plaintextLength = dataBytes.length;
			if (plaintextLength % blockSize != 0) {
				plaintextLength = plaintextLength
						+ (blockSize - (plaintextLength % blockSize));
			}

			byte[] plaintext = new byte[plaintextLength];
			System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);

			SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
			IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

			cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
			byte[] encrypted = cipher.doFinal(plaintext);
			return Base64Method.EncryptBase64(encrypted);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String Encrypt(String data, String key, String iv)
			throws Exception {
		return Encrypt(data, key, iv, CBC_NoPadding);
	}

	public static String Desencrypt(String data, String key, String iv,
			String padding) throws Exception {
		try {

			byte[] encrypted1 = Base64Method.DecryptBase64ForByte(data);
			Cipher cipher = Cipher.getInstance(padding);
			SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
			IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
			byte[] original = cipher.doFinal(encrypted1);
			String originalString = new String(original, "UTF-8");
			return originalString;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String Desencrypt(String data, String key, String iv)
			throws Exception {
		return Desencrypt(data, key, iv, CBC_NoPadding);
	}

	/**
	 * 对应base的加密为3
	 * 
	 * @param data
	 * @param key
	 * @param iv
	 * @return
	 * @throws Exception
	 */
	public static String Encrypt_base64_3(String data, String key, String iv)
			throws Exception {
		try {

			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			int blockSize = cipher.getBlockSize();

			byte[] dataBytes = data.getBytes("UTF-8");
			int plaintextLength = dataBytes.length;
			if (plaintextLength % blockSize != 0) {
				plaintextLength = plaintextLength
						+ (blockSize - (plaintextLength % blockSize));
			}

			byte[] plaintext = new byte[plaintextLength];
			System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);

			SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
			IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

			cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
			byte[] encrypted = cipher.doFinal(plaintext);
			return Base64Method.EncryptBase64_3(encrypted);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 对应base64加密为3
	 * 
	 * @param data
	 * @param key
	 * @param iv
	 * @return
	 * @throws Exception
	 */
	public static String Desencrypt_base64_3(String data, String key, String iv)
			throws Exception {
		try {

			byte[] encrypted1 = Base64Method.DecryptBase64ForByte_3(data);
			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
			IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
			byte[] original = cipher.doFinal(encrypted1);
			String originalString = new String(original, "UTF-8");
			return originalString;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 对应base的加密为3
	 * 
	 * @param data
	 * @param key
	 * @param iv
	 * @return
	 * @throws Exception
	 */
	public static String Encrypt_base64_default(String data, String key,
			String iv) throws Exception {
		return Encrypt_base64_default(data, key, iv, CBC_NoPadding);
	}

	public static String Encrypt_base64_default(String data, String key,
			String iv, String padding) throws Exception {
		try {

			Cipher cipher = Cipher.getInstance(padding);
			int blockSize = cipher.getBlockSize();

			byte[] dataBytes = data.getBytes("UTF-8");
			int plaintextLength = dataBytes.length;
			if (plaintextLength % blockSize != 0) {
				plaintextLength = plaintextLength
						+ (blockSize - (plaintextLength % blockSize));
			}

			byte[] plaintext = new byte[plaintextLength];
			System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);

			SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
			IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

			cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
			byte[] encrypted = cipher.doFinal(plaintext);
			return Base64Method.EncryptBase64_default(encrypted);

		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}
	}

	/**
	 * 对应base64加密为3
	 * 
	 * @param data
	 * @param key
	 * @param iv
	 * @return
	 * @throws Exception
	 */
	public static String Desencrypt_base64_default(String data, String key,
			String iv) throws Exception {
		return Desencrypt_base64_default(data, key, iv, CBC_NoPadding);
	}

	public static String Desencrypt_base64_default(String data, String key,
			String iv, String padding) throws Exception {
		try {
			byte[] encrypted1 = Base64Method.DecryptBase64ForByte_default(data);
			Cipher cipher = Cipher.getInstance(padding);
			SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
			IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
			byte[] original = cipher.doFinal(encrypted1);
			String originalString = new String(original, "UTF-8");
			return originalString;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
