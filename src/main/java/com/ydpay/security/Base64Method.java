package com.ydpay.security;

import org.apache.commons.codec.binary.Base64;

public class Base64Method {

	public static String EncryptBase64(String a_strString) {
		try {
			Base64 base64 = new Base64();
			String base64str = new String(base64.encode(a_strString
					.getBytes("utf-8")), "utf-8");
			base64str = base64str.replace("\n", "").replace("\r", "")
					.replace('+', '-').replace('/', '_');
			return base64str;
		} catch (Exception e) {
			return "";
		}
	}

	public static String EncryptBase64(byte[] bytes) throws Exception {
		Base64 base64 = new Base64();
		String base64str = new String(base64.encode(bytes), "utf-8");
		base64str = base64str.replace("\n", "").replace("\r", "")
				.replace('+', '-').replace('/', '_');
		return base64str;
	}

	public static String DecryptBase64(String a_strString) throws Exception {
		Base64 base64 = new Base64();
		byte[] bytes = base64.decode(a_strString.replace('-', '+')
				.replace('_', '/').getBytes("utf-8"));
		String str = new String(bytes, "utf-8");
		return str;
	}

	public static byte[] DecryptBase64ForByte(String a_strString)
			throws Exception {
		Base64 base64 = new Base64();
		byte[] bytes = base64.decode(a_strString.replace('-', '+')
				.replace('_', '/').getBytes("utf-8"));
		return bytes;
	}

	// base64
	public static String EncryptBase64_1(String a_strString) throws Exception {
		Base64 base64 = new Base64();
		String base64str = new String(base64.encode(a_strString
				.getBytes("utf-8")), "utf-8");
		base64str = base64str.replace("\n", "").replace("\r", "")
				.replace('+', '@');
		return base64str;
	}

	public static String DecryptBase64_1(String a_strString) throws Exception {
		Base64 base64 = new Base64();
		byte[] bytes = base64.decode(a_strString.replace('@', '+').getBytes(
				"utf-8"));
		String str = new String(bytes, "utf-8");
		return str;
	}

	public static String EncryptBase64_2(String a_strString) throws Exception {
		Base64 base64 = new Base64();
		String base64str = new String(base64.encode(a_strString
				.getBytes("utf-8")), "utf-8");
		base64str = base64str.replace("\n", "").replace("\r", "")
				.replace('+', '-').replace('/', '_');
		return base64str;
	}

	public static String EncryptBase64_2(byte[] bytes) throws Exception {
		Base64 base64 = new Base64();
		String base64str = new String(base64.encode(bytes), "utf-8");
		base64str = base64str.replace("\n", "").replace("\r", "")
				.replace('+', '-').replace('/', '_');
		return base64str;
	}

	public static String DecryptBase64_2(String a_strString) throws Exception {
		Base64 base64 = new Base64();
		byte[] bytes = base64.decode(a_strString.replace('-', '+')
				.replace('_', '/').getBytes("utf-8"));
		String str = new String(bytes, "utf-8");
		return str;
	}

	public static byte[] DecryptBase64ForByte_2(String a_strString)
			throws Exception {
		Base64 base64 = new Base64();
		byte[] bytes = base64.decode(a_strString.replace('-', '+')
				.replace('_', '/').getBytes("utf-8"));
		return bytes;
	}

	public static String EncryptBase64_3(String a_strString) throws Exception {
		Base64 base64 = new Base64();
		String base64str = new String(base64.encode(a_strString
				.getBytes("utf-8")), "utf-8");
		base64str = base64str.replace("\n", "").replace("\r", "")
				.replace('+', '-').replace('/', '_').replace("=", ".");
		return base64str;
	}

	public static String EncryptBase64_3(byte[] bytes) throws Exception {
		Base64 base64 = new Base64();
		String base64str = new String(base64.encode(bytes), "utf-8");
		base64str = base64str.replace("\n", "").replace("\r", "")
				.replace('+', '-').replace('/', '_').replace("=", ".");
		return base64str;
	}

	public static String DecryptBase64_3(String a_strString) throws Exception {
		Base64 base64 = new Base64();
		byte[] bytes = base64.decode(a_strString.replace('-', '+')
				.replace('_', '/').replace(".", "=").getBytes("utf-8"));
		String str = new String(bytes, "utf-8");
		return str;
	}

	public static byte[] DecryptBase64ForByte_3(String a_strString)
			throws Exception {
		Base64 base64 = new Base64();
		byte[] bytes = base64.decode(a_strString.replace('-', '+')
				.replace('_', '/').replace(".", "=").getBytes("utf-8"));
		return bytes;
	}
	
	public static String EncryptBase64_default(String a_strString) throws Exception {
		Base64 base64 = new Base64();
		String base64str = new String(base64.encode(a_strString
				.getBytes("utf-8")), "utf-8");
		return base64str;
	}

	public static String EncryptBase64_default(byte[] bytes) throws Exception {
		Base64 base64 = new Base64();
		String base64str = new String(base64.encode(bytes), "utf-8");
		return base64str;
	}

	public static String DecryptBase64_default(String a_strString) throws Exception {
		Base64 base64 = new Base64();
		byte[] bytes = base64.decode(a_strString.getBytes("utf-8"));
		String str = new String(bytes, "utf-8");
		return str;
	} 
	public static byte[] DecryptBase64ForByte_default(String a_strString)
			throws Exception {
		Base64 base64 = new Base64();
		byte[] bytes = base64.decode(a_strString.getBytes("utf-8"));
		return bytes;
	}
	
}
