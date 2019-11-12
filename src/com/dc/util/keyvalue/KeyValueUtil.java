package com.dc.util.keyvalue;

import org.springframework.stereotype.Repository;

import com.dc.util.keyvalue.bean.UserBusiBean;


@Repository("keyValueUtil")
public class KeyValueUtil {

	private static String BITS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	private static int RU_LENGTH = 4;
	
	private static String ZERO = "0";
	
	private int MAX_RADIX = 36;
	
	private int PUBLIC_BIT = 7;
	
	private int DEFAULT_RADIX = BITS.length();
	
	public String encoding(Integer userId,Integer busiId) {
	
		String rU = this.toString(userId, DEFAULT_RADIX);
		
		String rB = this.toString(busiId,DEFAULT_RADIX);
		
		int publicInt = 0;
		
		if(rU.length() > RU_LENGTH || rB.length() > RU_LENGTH ) {
			
			int uF = (rU.length() > RU_LENGTH) ? Integer.parseInt(String.valueOf(rU.charAt(0))) : 0;
			int bF = (rB.length() > RU_LENGTH) ? Integer.parseInt(String.valueOf(rB.charAt(0))) : 0;
			
			publicInt = uF * PUBLIC_BIT + bF;
			rU = (rU.length() > RU_LENGTH) ? rU.substring(1,rU.length()) : rU;
			rB = (rB.length() > RU_LENGTH) ? rB.substring(1,rB.length()) : rB;
		}
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(this.toString(publicInt, DEFAULT_RADIX));
		
		if(rU.length() < RU_LENGTH) {
			
			for(int i = 0 ; i < (RU_LENGTH - rU.length()) ; i ++) {
				
				sb.append(ZERO);
			}
		}
		sb.append(rU);
		
		if(rB.length() < RU_LENGTH) {
			
			for(int i = 0 ; i < (RU_LENGTH - rB.length()) ; i ++) {
				
				sb.append(ZERO);
			}
		}
		sb.append(rB);
		
		return sb.toString();
	}
	
	public UserBusiBean decoding(String key) {
		
		String uF = ZERO;
		String bF = ZERO;
		
		String kF = key.substring(0,1);
		
		if(!kF.equals(ZERO)) {
			
			int f = (int)this.getLong(kF, DEFAULT_RADIX);
			
			uF = String.valueOf(f / PUBLIC_BIT);
			bF = String.valueOf(f % PUBLIC_BIT);
		}
		
		String uKey = uF + key.substring(1,RU_LENGTH + 1);
		String bKey = bF + key.substring(1 + RU_LENGTH,1 + RU_LENGTH + RU_LENGTH);
		
		long uId = this.getLong(uKey, this.DEFAULT_RADIX);
		long bId = this.getLong(bKey, DEFAULT_RADIX);
		
		UserBusiBean userBusi = new UserBusiBean();
		
		userBusi.setUserId((int)uId);
		userBusi.setBusiId((int)bId);
		
		return userBusi;
	}
	
	protected double log(double value, double base)  {
		
		return  Math.log(value) / Math.log(base);
	}
	
	protected String toString(long l,int radix) {
		
		if(l == 0) {
			
			return ZERO;
		}
		
		if(radix <= MAX_RADIX) {
			
			return Long.toString(l,radix);
		}
		
		StringBuilder result = new StringBuilder();
		
		int residue = 0;
		long mold = l;
		
		//mold = l / radix;
		while(mold != 0) {
			residue = (int)(mold % radix);
			result.append(BITS.charAt(residue));
			
			mold = mold / radix;
		}
		
		return result.reverse().toString();
		
	}
	
	protected long getLong(String value,int radix) {
		if(radix <= MAX_RADIX) {
			
			return Long.parseLong(value, radix);
		}
		
		int length = value.length();
		
		long result = 0;
		
		long r = (long)radix;
		
		for(int i = 0 ; i < length ; i ++) {
			
			result *= r;
			result += BITS.indexOf(value.charAt(i));
		}
		
		return result;
	}
}
