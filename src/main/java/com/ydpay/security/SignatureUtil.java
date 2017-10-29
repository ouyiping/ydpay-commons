package com.ydpay.security;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.ydpay.utils.Constants;

public class SignatureUtil {

	/**
	 * 统一签名方法 md5和hmac
	 * 
	 * @description:
	 * @param signMethod
	 *            com.ydpay.utils.Constants.SIGN_METHOD_MD5,
	 *            com.ydpay.utils.Constants.SIGN_METHOD_HMAC
	 * @param data
	 * @param secretKey
	 * @return
	 * @throws InvalidKeyException
	 *             ,NoSuchAlgorithmException ,UnsupportedEncodingException
	 * @return String(返回值说明)
	 * @author Zhanghaifeng 2016年3月25日
	 * @throws IOException 
	 */
	public static String sign(String signMethod, String data,
			String secretKey) throws IOException {
		String sign = "";
		if (Constants.SIGN_METHOD_HMAC.equals(signMethod)) {
				sign = encryptHMAC(data, secretKey);
		} else {
			sign = MD5Util.string2MD5(data);
		}
		return sign;
	}
	
	public static String encryptHMAC(String data, String secret)
			throws IOException {
		byte[] bytes = null;
		try {
			SecretKey secretKey = new SecretKeySpec(
					secret.getBytes(Constants.CHARSET_UTF8), "HmacMD5");
			Mac mac = Mac.getInstance(secretKey.getAlgorithm());
			mac.init(secretKey);
			bytes = mac.doFinal(data.getBytes(Constants.CHARSET_UTF8));
		} catch (GeneralSecurityException gse) {
			throw new IOException(gse.toString());
		}
		return byte2hex(bytes);
	}
	
	private static String byte2hex(byte[] bytes) {
		StringBuilder sign = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(bytes[i] & 0xFF);
			if (hex.length() == 1) {
				sign.append("0");
			}
			sign.append(hex.toLowerCase());
		}
		return sign.toString();
	}
}
