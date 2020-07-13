package com.genesis.apps.comm.util.crypt.ecies;


import com.genesis.apps.comm.util.crypt.ByteUtils;

import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.DerivationParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.DigestDerivationFunction;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.crypto.params.ISO18033KDFParameters;
import org.spongycastle.crypto.params.KDFParameters;
import org.spongycastle.util.Pack;


public class MyKDFBytesGenerator implements DigestDerivationFunction {
	private int counterStart;
	private Digest digest;
	private byte[] shared;
	private byte[] iv;

	/**
	 * Construct a KDF Parameters generator.
	 * <p>
	 * 
	 * @param counterStart
	 *            value of counter.
	 * @param digest
	 *            the digest to be used as the source of derived keys.
	 */
	public MyKDFBytesGenerator(int counterStart, Digest digest) {
		this.counterStart = counterStart;
		this.digest = digest;
	}

	public void init(DerivationParameters param) {
		if (param instanceof KDFParameters) {
			KDFParameters p = (KDFParameters) param;

			shared = p.getSharedSecret();
			iv = p.getIV();
		} else if (param instanceof ISO18033KDFParameters) {
			ISO18033KDFParameters p = (ISO18033KDFParameters) param;

			shared = p.getSeed();
			iv = null;
		} else {
			throw new IllegalArgumentException("KDF parameters required for generator");
		}
	}

	/**
	 * return the underlying digest.
	 */
	public Digest getDigest() {
		return digest;
	}

	/**
	 * fill len bytes of the output buffer with bytes generated from the
	 * derivation function.
	 * 
	 * @throws IllegalArgumentException
	 *             if the size of the request will cause an overflow.
	 * @throws DataLengthException
	 *             if the out buffer is too small.
	 */
	public int generateBytes(byte[] out, int outOff, int len) throws DataLengthException, IllegalArgumentException {
		if ((out.length - len) < outOff) {
			throw new OutputLengthException("output buffer too small");
		}

		long oBytes = len;
		int outLen = digest.getDigestSize();

		//
		// this is at odds with the standard implementation, the
		// maximum value should be hBits * (2^32 - 1) where hBits
		// is the digest output size in bits. We can't have an
		// array with a long index at the moment...
		//
		if (oBytes > ((2L << 32) - 1)) {
			throw new IllegalArgumentException("Output length too large");
		}

		int cThreshold = (int) ((oBytes + outLen - 1) / outLen);

		byte[] dig = new byte[digest.getDigestSize()];

		byte[] C = new byte[4];
		Pack.intToBigEndian(counterStart, C, 0);

		int counterBase = counterStart & ~0xFF;
		
		byte[] mySecret = ByteUtils.copyByteArray(shared, shared.length - 32, 32);
//		System.out.println("KDF mySecret : " + HexStringConverter.hexToString(mySecret));
//		System.out.println("KDF shared : " + HexStringConverter.hexToString(shared) + " iv : "+ HexStringConverter.hexToString(iv));
		for (int i = 0; i < cThreshold; i++) {
//			System.out.println("KDF round " + (i+counterStart) + " : "+ HexStringConverter.hexToString(ByteUtils.append(mySecret, C, iv)));
			
//			digest.update(ByteUtils.append(mySecret, C, iv), 0, mySecret.length + C.length + iv.length);
			
			digest.update(mySecret, 0, mySecret.length);
			digest.update(C, 0, C.length);

			if (iv != null) {
				digest.update(iv, 0, iv.length);
			}

			digest.doFinal(dig, 0);
//			System.out.println("KDF round result " + i + " : "+ HexStringConverter.hexToString(dig));

			if (len > outLen) {
				System.arraycopy(dig, 0, out, outOff, outLen);
				outOff += outLen;
				len -= outLen;
			} else {
				System.arraycopy(dig, 0, out, outOff, len);
			}

			if (++C[3] == 0) {
				counterBase += 0x100;
				Pack.intToBigEndian(counterBase, C, 0);
			}
		}
//		System.out.println("KDF output : "+ HexStringConverter.hexToString(out));

		digest.reset();

		return (int) oBytes;
	}
}
