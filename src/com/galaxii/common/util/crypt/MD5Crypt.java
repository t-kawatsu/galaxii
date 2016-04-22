package com.galaxii.common.util.crypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author http://www.syboos.jp
 */
public class MD5Crypt {

	public static String crypt(String str) throws NoSuchAlgorithmException {
		if (str == null || str.length() == 0) {
			throw new IllegalArgumentException("String to encript cannot be null or zero length");
		}

		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(str.getBytes());
		byte[] hash = md.digest();

		return hashByte2MD5(hash);
	}

	public static String decrypt(String str) throws Exception {
		throw new Exception();
	}

	public static String crypt(byte [] bytes) throws NoSuchAlgorithmException {
		if (bytes == null || bytes.length == 0) {
			throw new IllegalArgumentException("bytes to encript cannot be null or zero length");
		}
		MessageDigest digest = MessageDigest.getInstance("MD5");
		byte[] hash = digest.digest(bytes);

		return hashByte2MD5(hash);
	}

	private static String hashByte2MD5(byte []hash) {
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < hash.length; i++) {
			if ((0xff & hash[i]) < 0x10) {
				hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
			} else {
				hexString.append(Integer.toHexString(0xFF & hash[i]));
			}
		}

		return hexString.toString();
	}
}
