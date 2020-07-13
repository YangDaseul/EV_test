package com.genesis.apps.comm.util.crypt.ecies;

import org.spongycastle.crypto.digests.SHA256Digest;
import org.spongycastle.crypto.macs.HMac;

/**
 * HMAC_SHA256_128
 */
public class MyHmac extends HMac {
	public MyHmac() {
		super(new SHA256Digest());
	}

	@Override
	public int getMacSize() {
		return super.getMacSize() / 2;
	}

	@Override
	public int doFinal(byte[] out, int outOff) {
		byte[] temp = new byte[32];
		int len = super.doFinal(temp, outOff);

		for (int i = 0; i < out.length; i++) {
			out[i] = temp[i];
		}
		return len / 2;
	}

}
