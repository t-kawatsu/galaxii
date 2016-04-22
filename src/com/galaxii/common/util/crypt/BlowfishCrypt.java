package com.galaxii.common.util.crypt;

import javax.crypto.spec.SecretKeySpec;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * @see http://pentan.info/java/sample/cipher.html
 * @Author http://tech.chitgoks.com/2009/07/15/encrypt-and-decrypt-using-blowfish-in-java/
 */
public class BlowfishCrypt {

	private static final String CRYPT_TYPE = "Blowfish";
	private static final String ALGORITHM  = "Blowfish/CBC/PKCS5Padding";
	private static final String SECRET_KEY = "adlknaaRlnSElknacdf34345";
	private static String iv = "jogojcom"; // 8

	public static String encrypt(String str) throws Exception {
		try {
			// 秘密鍵を構築します
			SecretKeySpec key = new SecretKeySpec(SECRET_KEY.getBytes(), CRYPT_TYPE);
			// IV(初期化ベクトル)を構築します
			IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes());
			// 暗号化を行うアルゴリズム、モード、パディング方式を指定します
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			// 初期化します
			cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
			// 暗号化します
			return (new BASE64Encoder()).encodeBuffer( cipher.doFinal(str.getBytes()) ).trim();
		} catch (Exception e) {
			throw e;
		}
	}

	public static String decrypt(String str) throws Exception {
		try {
			// 秘密鍵を構築します
			SecretKeySpec key = new SecretKeySpec(SECRET_KEY.getBytes(), CRYPT_TYPE);
			// IV(初期化ベクトル)を構築します
			IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes());
			// 暗号化を行うアルゴリズム、モード、パディング方式を指定します
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			// 初期化します
			cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
			// 複合化します
			byte[] bytes = (new BASE64Decoder()).decodeBuffer(str);
			return new String(cipher.doFinal(bytes));
		} catch (Exception e) {
			throw e; 
		}
	}
}
