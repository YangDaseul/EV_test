package com.genesis.apps.comm.util.crypt;

import org.spongycastle.pqc.math.linearalgebra.ByteUtils;

import java.security.MessageDigest;

public class HashUtil {

	public static byte[] sha256(byte[] data) throws Exception {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] hash = digest.digest(data);

		return hash;
	}
	public static byte[] sha256_128(byte[] data) throws Exception {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] hash = digest.digest(data);

		return ByteUtils.subArray(hash, 0, 16);
	}
	public static byte[] sha256_64(byte[] data) throws Exception {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] hash = digest.digest(data);

		return ByteUtils.subArray(hash, 0, 8);
	}
}
