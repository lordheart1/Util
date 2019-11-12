package com.dc.util.encryption;

/**
 * 加解密接口(部分实现的解密返回空，如该算法为不可逆)
 * @author 小俊俊
 *
 */
public interface EncryptionService {

	/**
	 * 解密
	 * @param 密文
	 * @return 原文
	 */
	public String decode(String value);
	
	/**
	 * 加密
	 * @param 原文
	 * @return 密文
	 */
	public String encode(String value);
}