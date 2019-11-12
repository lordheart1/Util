package com.dc.util.encryption;

import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.dc.util.encryption.rsa.RSAEncrypt;

@Repository("mobileEncryptionService")
public class MobileRSAEncryptionServiceImpl implements EncryptionService {

	private static final Logger logger = Logger
			.getLogger(MobileRSAEncryptionServiceImpl.class);

	private static final int MAX = 256;
	private static final int HALF = 127;

	private static final int RADIX = 16;
	private static final int STEP = 2;

	@Value("${char_set}")
	private String charSet;

	@Resource(name = "RSAEncrypt")
	private RSAEncrypt rsaEncrypt;

	@Override
	public String decode(String value) {
	
		int length = value.length();
		
		int b = length / MAX;
		
		byte[] bs = new byte[0];
		
		for(int i = 0 ; i < b ; i++) {
			
			String subValue = value.substring(i * MAX,(i + 1) * MAX);
			
			byte[] bytes = this.decoder(subValue);
			
			bs = contact(bs,bytes);
		}
		
		try {
			return new String(bs,this.charSet);
		} catch (UnsupportedEncodingException e) {
			logger.warn(e.getMessage(), e);
		}
		
		return null;
	}

	public static byte[] contact(byte a[], byte b[]) {
		byte[] f = new byte[a.length + b.length];
		for (int i = 0; i < f.length; i++)
			if (i < a.length)
				f[i] = a[i];
			else
				f[i] = b[i - a.length];
		return f;
	}

	public byte[] decoder(String value) {

		int length = value.length();

		if (length % STEP != 0) {

			return null;
		}

		int bLength = length / STEP;

		byte[] bs = new byte[bLength];

		StringBuffer sb = new StringBuffer(value);

		for (int index = 0; index < bLength; index++) {

			int start = index * STEP;
			int end = start + STEP;

			String temp = sb.substring(start, end);

			int iTemp = Integer.parseInt(temp, RADIX);

			if (iTemp > MAX) {

				return null;
			}

			if (iTemp > HALF) {

				iTemp = iTemp - MAX;
			}

			bs[index] = (byte) iTemp;
		}

		try {
			bs = rsaEncrypt.decrypt(rsaEncrypt.getPrivateKey(), bs);

			return bs;

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return null;
		}
	}

	@Override
	public String encode(String value) {

		byte[] cipher = null;

		try {
			cipher = rsaEncrypt.encrypt(rsaEncrypt.getPublicKey(),
					value.getBytes());

			StringBuffer sb = new StringBuffer();

			for (byte ciph : cipher) {

				int c = (int) ciph;

				if (c < 0) {
					c = c + 256;
				}

				String v = Integer.toString(c, 16);

				if (v.length() == 1) {
					sb.append("0");
				}

				sb.append(v);
			}

			return sb.toString();
		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			return null;
		}

	}

}
