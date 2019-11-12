package com.dc.util.encryption;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;



@Repository("md5EncryptionService")
public class MDEncryptionServiceImpl implements EncryptionService {

	private static final Logger logger = Logger.getLogger(MDEncryptionServiceImpl.class);
	
	private static String ENCRY_TYPE = "MD5";
	
	@Value("${char_set}")
	private String charSet;
	
	@Override
	public String decode(String value) {

		return null;
	}

	@Override
	public String encode(String value) {

		MessageDigest messageDigest = null;

		try {
			messageDigest = MessageDigest.getInstance(ENCRY_TYPE);

			messageDigest.reset();

			messageDigest.update(value.getBytes(this.charSet));
		} catch (NoSuchAlgorithmException e) {
			
			logger.error(e.getMessage(),e);
			return null;
		} catch (UnsupportedEncodingException e) {
			
			logger.error(e.getMessage(),e);
			return null;
		}

		byte[] byteArray = messageDigest.digest();

		StringBuffer md5StrBuff = new StringBuffer();

		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(
						Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}

		return md5StrBuff.toString();
	}

}
