package com.dc.util.pass;

import java.util.Random;

public class Pass {
	
	public static String getDefaultPassWord(){
		int[] array = {0,1,2,3,4,5,6,7,8,9};
        Random rand = new Random();
        for (int i = 10; i > 1; i--) {
            int index = rand.nextInt(i);
            int tmp = array[index];
            array[index] = array[i - 1];
            array[i - 1] = tmp;
        }
        StringBuffer result = new StringBuffer();
        for(int i = 0; i < 6; i++){
        	result.append(array[i]);
        }
        return result.toString();
	}
	
}
