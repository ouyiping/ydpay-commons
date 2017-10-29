package com.ydpay.security;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.ydpay.utils.StringUtil;

/**
 * rsa签名，验签(签名算法sha-1),加密，解密
 */
public class RSAUtil {
	/** 加密算法RSA */
	private static final String KEY_ALGORITHM = "RSA";
	/** 签名算法 */
	public static final String SIGNATURE_ALGORITHM = "SHA1withRSA";// sha-1签名算法
	/** 签名算法 */
	public static final String SIGNATURE_ALGORITHM_MD5 = "MD5withRSA";// md5-1签名算法

	/**
	 * 从私钥证书中获取私钥
	 * 
	 * @param strPrivatePath
	 *            私钥证书地址
	 * @param strPassword
	 *            私钥证书密码
	 */
	private static PrivateKey getPrivateKeyFromPfx(String strPrivatePath,
			String strPassword) throws KeyStoreException,
			NoSuchAlgorithmException, CertificateException, IOException,
			UnrecoverableKeyException {
		KeyStore ks = KeyStore.getInstance("PKCS12");
		FileInputStream fis = new FileInputStream(strPrivatePath);
		char[] nPassword = null;
		if (StringUtil.isNotEmpty(strPassword)) {
			nPassword = strPassword.toCharArray();
		}
		ks.load(fis, nPassword);
		fis.close();
		Enumeration<String> enumas = ks.aliases();
		String keyAlias = null;
		if (enumas.hasMoreElements()) {
			keyAlias = (String) enumas.nextElement();
		}
		PrivateKey prikey = (PrivateKey) ks.getKey(keyAlias, nPassword);
		return prikey;
	}

	/**
	 * 根据私钥证书地址和私钥密码对数据进行签名
	 * 
	 * @param data
	 *            待签名数据
	 * @param strPrivatePath
	 *            私钥证书地址
	 * @param strPassword
	 *            私钥证书密码
	 */
	public static byte[] sign(byte[] data, String strPrivatePath,
			String strPassword) throws Exception {
		return sign(data, getPrivateKeyFromPfx(strPrivatePath, strPassword));
	}

	/**
	 * 使用私钥对数据进行签名
	 * 
	 * @param data
	 *            待签名数据
	 * @param privateKey
	 *            私钥
	 */
	public static byte[] sign(byte[] data, PrivateKey privateKey)
			throws Exception {
		return sign(data, privateKey, SIGNATURE_ALGORITHM);
	}

	/**
	 * 使用私钥对数据进行签名
	 * 
	 * @param data
	 *            待签名数据
	 * @param privateKey
	 *            私钥
	 */
	public static byte[] sign(byte[] data, PrivateKey privateKey,
			String signature_algorithm) throws Exception {
		Signature signature = Signature.getInstance(signature_algorithm);
		signature.initSign(privateKey);
		signature.update(data);
		return signature.sign();
	}

	/**
	 * 根据公钥证书地址获取公钥
	 * 
	 * @param strPublicPath
	 */
	public static PublicKey getPublicKey(String strPublicPath)
			throws FileNotFoundException, CertificateException {
		CertificateFactory certificatefactory = CertificateFactory
				.getInstance("X.509");
		FileInputStream bais = new FileInputStream(strPublicPath);
		X509Certificate Cert = (X509Certificate) certificatefactory
				.generateCertificate(bais);
		PublicKey pk = Cert.getPublicKey();
		return pk;
	}

	/**
	 * 根据公钥证书地址获取公钥，并进行签名校验
	 * 
	 * @param bytes
	 * @param sign
	 * @param strPublicPath
	 */
	public static boolean verify(byte[] bytes, byte[] sign, String strPublicPath)
			throws SignatureException, InvalidKeyException,
			NoSuchAlgorithmException, FileNotFoundException,
			CertificateException {
		PublicKey publickey = getPublicKey(strPublicPath);
		return verify(bytes, sign, publickey);
	}

	/**
	 * 使用公钥对进行签名校验
	 * 
	 * @param bytes
	 * @param sign
	 * @param publickey
	 * 
	 */
	public static boolean verify(byte[] bytes, byte[] sign, PublicKey publickey)
			throws SignatureException, InvalidKeyException,
			NoSuchAlgorithmException {
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initVerify(publickey);
		signature.update(bytes);
		return signature.verify(sign);
	}

	/**
	 * 根据公钥证书地址获取公钥并对数据进行加密
	 * 
	 * @param data
	 *            待加密数据
	 * @param strPublicPath
	 *            公钥证书地址
	 */
	public static byte[] encrypt(byte[] data, String strPublicPath)
			throws FileNotFoundException, CertificateException,
			NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

		PublicKey publickey = getPublicKey(strPublicPath);
		return doFinal(data, publickey, Cipher.ENCRYPT_MODE);
	}

	/**
	 * 使用私钥对数据进行加密
	 * 
	 * @param data
	 *            待加密数据
	 * @param strPrivatePath
	 *            私钥证书地址
	 * @param strPassword
	 *            私钥密码
	 * 
	 * @throws IOException
	 * @throws CertificateException
	 * @throws KeyStoreException
	 * @throws UnrecoverableKeyException
	 */
	public static byte[] encrypt(byte[] data, String strPrivatePath,
			String strPassword) throws IllegalBlockSizeException,
			BadPaddingException, InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, UnrecoverableKeyException,
			KeyStoreException, CertificateException, IOException {
		PrivateKey privateKey = getPrivateKeyFromPfx(strPrivatePath,
				strPassword);
		return doFinal(data, privateKey, Cipher.ENCRYPT_MODE);
	}

	/**
	 * 使用私钥对数据进行解密
	 * 
	 * @param data
	 *            待解密数据
	 * @param strPrivatePath
	 *            私钥证书地址
	 * @param strPassword
	 *            私钥密码
	 * 
	 * @throws IOException
	 * @throws CertificateException
	 * @throws KeyStoreException
	 * @throws UnrecoverableKeyException
	 */
	public static byte[] decrypt(byte[] data, String strPrivatePath,
			String strPassword) throws IllegalBlockSizeException,
			BadPaddingException, InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, UnrecoverableKeyException,
			KeyStoreException, CertificateException, IOException {
		PrivateKey privateKey = getPrivateKeyFromPfx(strPrivatePath,
				strPassword);
		return doFinal(data, privateKey, Cipher.DECRYPT_MODE);
	}

	/**
	 * 使用私钥对数据进行解密
	 * 
	 * @param data
	 * @param privateKey
	 */
	public static byte[] decrypt(byte[] data, PrivateKey privateKey)
			throws InvalidKeyException, IllegalBlockSizeException,
			BadPaddingException, NoSuchAlgorithmException,
			NoSuchPaddingException {
		return doFinal(data, privateKey, Cipher.DECRYPT_MODE);
	}

	/**
	 * 使用公钥对数据进行解密
	 * 
	 * @param data
	 *            待解密数据
	 * @param strPublicPath
	 *            公钥钥证书地址
	 * 
	 * @throws IOException
	 * @throws CertificateException
	 * @throws KeyStoreException
	 * @throws UnrecoverableKeyException
	 */
	public static byte[] decrypt(byte[] data, String strPublicPath)
			throws IllegalBlockSizeException, BadPaddingException,
			InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, UnrecoverableKeyException,
			KeyStoreException, CertificateException, IOException {
		PublicKey publickey = getPublicKey(strPublicPath);
		return doFinal(data, publickey, Cipher.DECRYPT_MODE);
	}

	/**
	 * 使用公钥对数据进行解密
	 * 
	 * @param data
	 * @param publickey
	 * @return
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 */
	public static byte[] decrypt(byte[] data, PublicKey publickey)
			throws InvalidKeyException, IllegalBlockSizeException,
			BadPaddingException, NoSuchAlgorithmException,
			NoSuchPaddingException {
		return doFinal(data, publickey, Cipher.DECRYPT_MODE);
	}

	/**
	 * 使用私钥对数据进行解密
	 * 
	 * @param data
	 *            待加解密数据
	 * @param privateKey
	 *            私钥
	 * @param mode
	 *            操作模式 Cipher.DECRYPT_MODE, Cipher.ENCRYPT_MODE
	 */
	public static byte[] doFinal(byte[] data, PrivateKey privateKey, int mode)
			throws IllegalBlockSizeException, BadPaddingException,
			InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException {
		Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
		cipher.init(mode, privateKey);
		return cipher.doFinal(data);
	}

	/**
	 * 使用公钥对数据进行加解密
	 * 
	 * @param data
	 *            待加解密数据
	 * @param publicKey
	 *            私钥
	 * @param mode
	 *            操作模式 Cipher.DECRYPT_MODE, Cipher.ENCRYPT_MODE
	 */
	public static byte[] doFinal(byte[] data, PublicKey publicKey, int mode)
			throws IllegalBlockSizeException, BadPaddingException,
			InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException {
		Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
		cipher.init(mode, publicKey);
		return cipher.doFinal(data);
	}

}
