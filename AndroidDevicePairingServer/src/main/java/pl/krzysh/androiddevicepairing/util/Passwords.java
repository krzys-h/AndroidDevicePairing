package pl.krzysh.androiddevicepairing.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class Passwords {
	protected static Random random = new Random();
	
	public static String hashPassword(String password) {
		try {
			MessageDigest md;
			md = MessageDigest.getInstance("SHA-256");
			md.update(password.getBytes());
			byte[] result = md.digest();
			StringBuffer resultString = new StringBuffer();
			for(byte b : result) resultString.append(Integer.toString((b & 0xFF) + 0x100, 16).substring(1));
			return resultString.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String generatePassword() {
		return hashPassword(String.valueOf(random.nextInt()));
	}
}
