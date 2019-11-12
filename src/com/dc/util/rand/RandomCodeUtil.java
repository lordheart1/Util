package com.dc.util.rand;

import java.util.Random;

import org.apache.log4j.Logger;

public class RandomCodeUtil {
    private static final Logger logger = Logger.getLogger(RandomCodeUtil.class);
    
    /**
     * 生成随机串
     * @param length     随机串长度
     * @param withChar   是否包含字母
     * @param withCase   是否包含大写（为true时withChar无效）
     * @return String
     */
    public static String getRandomCode(int length, boolean withChar, boolean withCase) {
        if(length <= 0) { 
            return "12345678";
        }
        StringBuffer buffer = null;
        if(withCase) {
            buffer = new StringBuffer("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
        }else if(withChar) {
            buffer = new StringBuffer("0123456789abcdefghijklmnopqrstuvwxyz");
        }else {
            new StringBuffer("0123456789");
        }
        StringBuffer sb = new StringBuffer();
        Random r = new Random();
        int range = buffer.length();
        for (int i = 0; i < length; i ++) {
            sb.append(buffer.charAt(r.nextInt(range)));
        }
        logger.debug("RandomCode:" + sb.toString());
        return sb.toString();
    }
}