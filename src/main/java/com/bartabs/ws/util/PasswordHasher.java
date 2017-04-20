/*
 * Copyright (c) 2017, Ron Gerschel, Jon Goldberg and Victor Lora. All rights reserved.
 * Ron Gerschel, Jon Goldberg and Victor Lora PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package com.bartabs.ws.util;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * The {@code PasswordHasher} class implements a password hasher using the
 * java.security, java.math and javax.crypto packages
 *
 * @author Victor A. Lora
 * @version 1.0
 * @since 2017-04-16
 */
public class PasswordHasher
{
	/**
	 * Produces a hashed password using PBKDF2WithHmacSHA1
	 * 
	 * @param password
	 *            a plain text password {@code String}
	 * @param salt
	 *            a byte array to serve as the salt for the password. Use
	 *            {@code getSalt()} to generate a new salt array.
	 * @return a hashed password
	 * @throws NoSuchAlgorithmException
	 *             thrown when the algorithm specified by the JWT token does not
	 *             exist
	 * @throws InvalidKeySpecException
	 *             thrown when an invalid JWT token is provided
	 */
	public static String generateStrongPasswordHash(String password, byte[] salt)
			throws NoSuchAlgorithmException, InvalidKeySpecException
	{
		int iterations = 1000;
		char[] chars = password.toCharArray();

		if (salt == null) {
			salt = getSalt();
		}

		PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
		SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		byte[] hash = skf.generateSecret(spec).getEncoded();
		return iterations + ":" + toHex(salt) + ":" + toHex(hash);
	}

	/**
	 * Produces a byte array to be used as the salt for {@code
	 * generateStrongPasswordHash()}
	 * 
	 * @return a byte array used for the salt
	 * @throws NoSuchAlgorithmException
	 *             thrown when the algorithm specified by the JWT token does not
	 *             exist
	 */
	public static byte[] getSalt() throws NoSuchAlgorithmException
	{
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		byte[] salt = new byte[16];
		sr.nextBytes(salt);
		return salt;
	}

	/**
	 * Converts a byte array into hex
	 * 
	 * @param array
	 *            a byte array
	 * @return a hex representation of the given byte array
	 * @throws NoSuchAlgorithmException
	 *             thrown when the algorithm specified by the JWT token does not
	 *             exist
	 */
	private static String toHex(byte[] array) throws NoSuchAlgorithmException
	{
		BigInteger bi = new BigInteger(1, array);
		String hex = bi.toString(16);
		int paddingLength = (array.length * 2) - hex.length();
		if (paddingLength > 0) {
			return String.format("%0" + paddingLength + "d", 0) + hex;
		} else {
			return hex;
		}
	}
}
