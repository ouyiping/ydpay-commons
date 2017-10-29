package com.ydpay.security;

import java.security.MessageDigest;

public class SHAUtil {
	/***
	 * SHA加密 生成40位SHA码
	 * 
	 * @param 待加密字符串
	 * @return 返回40位SHA码
	 */
	public static byte[] shaEncode(String inStr) throws Exception {
		MessageDigest sha = null;
		try {
			sha = MessageDigest.getInstance("SHA-1");
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return null;
		}

		byte[] byteArray = inStr.getBytes("UTF-8");
		sha.update(byteArray);
		byte[] md5Bytes = sha.digest();
		return  md5Bytes;
	}

	public static String bytes2Hex(byte[] bts) {
		String des = "";
		String tmp = null;
		for (int i = 0; i < bts.length; i++) {
			tmp = (Integer.toHexString(bts[i] & 0xFF));
			if (tmp.length() == 1) {
				des += "0";
			}
			des += tmp;
		}
		return des;
	}

}