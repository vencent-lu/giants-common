/**
 * 
 */
package com.giants.common.codec;

/**
 * @author vencent.lu
 *
 */
public final class Hex {
	
	private static final char[] HEX = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
    };

	/**
	 * 将字节数组用16进制编码
	 * @param bytes 字节数组
	 * @return 字符数组
	 */
    public static char[] encode(byte[] bytes) {
        final int nBytes = bytes.length;
        char[] result = new char[2*nBytes];

        int j = 0;
        for (int i=0; i < nBytes; i++) {
            // Char for top 4 bits
            result[j++] = HEX[(0xF0 & bytes[i]) >>> 4 ];
            // Bottom 4
            result[j++] = HEX[(0x0F & bytes[i])];
        }

        return result;
    }
    
    /**
     * 将字节数组用16进制编码
     * @param bytes 将字节数
     * @return 16进制编码字符串
     */
    public static String encodeByString(byte[] bytes) {
    	return new String(encode(bytes));
    }
    
    /**
     * 解码16进制字符数组
     * @param chars 16进制字符数组
     * @return 字节数组
     */
    public static byte[] decode(char[] chars) {
    	byte[] res = new byte[chars.length/2]; 
        int[] b = new int[2];  
  
        for(int i=0,c=0; i<chars.length; i+=2,c++){              
            for(int j=0; j<2; j++){  
                if(chars[i+j]>='0' && chars[i+j]<='9'){  
                    b[j] = (chars[i+j]-'0');  
                }else if(chars[i+j]>='A' && chars[i+j]<='F'){  
                    b[j] = (chars[i+j]-'A'+10);  
                }else if(chars[i+j]>='a' && chars[i+j]<='f'){  
                    b[j] = (chars[i+j]-'a'+10);  
                }  
            }   
              
            b[0] = (b[0]&0x0f)<<4;  
            b[1] = (b[1]&0x0f);  
            res[c] = (byte) (b[0] | b[1]);  
        }  
          
        return res;
    }
    
    /**
     * 解码16进制字符串
     * @param hex 16进制字符串
     * @return 字节数组
     */
    public static byte[] decodeByString(String hex) {
    	return decode(hex.toCharArray());
    }

}
