package com.wbc.graduation.util;

import org.bouncycastle.util.Arrays;
import org.springframework.stereotype.Component;

import com.wbc.graduation.exception.Md5EncodeException;
/*
 * md5算法工具类——可生成16、32位md5密文
 */
@Component
public class MD5diyUtils {
	
	private final static String SALT = "b50ff00ffed0d241";
	//链接变量初始值
	private long [] arr={0x67452301L,0xefcdab89L,0x98badcfeL,0x10325476L};
	private static String data_encoder = ""; 
    //算法中常数M[i][j],赋值规则为4294967296*abs(sin(i))的整数部分，i是当前轮换次数，单位是弧度
    //这里方便起见，将所有值列在数组M[][]下
    static final long t[][] = {
		{0xd76aa478, 0xe8c7b756, 0x242070db, 0xc1bdceee,
		0xf57c0faf, 0x4787c62a, 0xa8304613, 0xfd469501,
		0x698098d8, 0x8b44f7af, 0xffff5bb1, 0x895cd7be,
		0x6b901122, 0xfd987193, 0xa679438e, 0x49b40821},
		
		{0xf61e2562, 0xc040b340, 0x265e5a51, 0xe9b6c7aa,
		0xd62f105d, 0x02441453, 0xd8a1e681, 0xe7d3fbc8,
		0x21e1cde6, 0xc33707d6, 0xf4d50d87, 0x455a14ed,
		0xa9e3e905, 0xfcefa3f8, 0x676f02d9, 0x8d2a4c8a},
		
		{0xfffa3942, 0x8771f681, 0x6d9d6122, 0xfde5380c,
		0xa4beea44, 0x4bdecfa9, 0xf6bb4b60, 0xbebfbc70,
		0x289b7ec6, 0xeaa127fa, 0xd4ef3085, 0x04881d05,
		0xd9d4d039, 0xe6db99e5, 0x1fa27cf8, 0xc4ac5665},
		
		{0xf4292244, 0x432aff97, 0xab9423a7, 0xfc93a039,
		0x655b59c3, 0x8f0ccc92, 0xffeff47d, 0x85845dd1,
		0x6fa87e4f, 0xfe2ce6e0, 0xa3014314, 0x4e0811a1,
		0xf7537e82, 0xbd3af235, 0x2ad7d2bb, 0xeb86d391}};
    
    //循环计算中的偏移量
    static final int s[][] = {
    		{7,12,17,22},
    		{5,9,14,20},
    		{4,11,16,23},
    		{6,10,15,21}};
    
    //M[n][j]代表使用消息分组的第几个子分组
    static final int M[][] = {
    		{0, 1, 2, 3, 4, 5, 6, 7, 8, 9,10,11,12,13,14,15},
    		{1, 6,11, 0, 5,10,15, 4, 9,14, 3, 8,13, 2, 7,12},
    		{5, 8,11,14, 1, 4, 7,10,13, 0, 3, 6, 9,12,15, 2},
    		{0, 7,14, 5,12, 3,10, 1, 8,15, 6,13, 4,11, 2, 9}};
    
    
    //用来判定该使用循环算法
    private static long function(int i, long b, long c, long d) {
    	switch (i) {
		case 0:
			return (b & c) | ((~b) & d);
		case 1:
			return (b & d) | (c & (~d));
		case 2:
			return b ^ c ^ d;
		case 3:
			return c ^ (b | (~d));
		default:
			return 0;
		}
    }
    
    /*
     * 填充
     */
    public void padding(int byte_Length,byte[] data_Bytes,long K){
    	int last_len = byte_Length % 64;	//尾部数据长度
        byte [] paddingBytes=new byte[64];	//最后组数据
        for(int i=0;i<last_len;i++)
        	paddingBytes[i]=data_Bytes[byte_Length-last_len+i];		//提出尾部数据
        
        //小于448bit的情况，先填充100...0再填充K值的低64位，增1个分组
        if(last_len <= 56){
            if(last_len<56){
            	//填充10000000
            	paddingBytes[last_len]=(byte)(1<<7);
            	//填充00000000
                for(int i=1;i<56-last_len;i++)
                	paddingBytes[last_len+i]=0;
            }
            for(int i=0;i<8;i++){
            	paddingBytes[56+i]=(byte)(K&0xFFL);
                K=K>>8;
            }
            FGHI(divide(paddingBytes,0));
        
        }else{		//大于448bit的情况，增2个分组，先填充100...0再填充K值的低64位，只填充00...0至高448位，低64位填充长度
            //填充10000000,填充第一组
            paddingBytes[last_len]=(byte)(1<<7);
            //填充00000000
            for(int i=last_len+1;i<64;i++)
            	paddingBytes[i]=0;
            FGHI(divide(paddingBytes,0));
            
            //填充00000000，填充第二组
            for(int i=0;i<56;i++)
            	paddingBytes[i]=0;
            //填充低64位
            for(int i=0;i<8;i++){
            	//Byte数组先存储len的低位数据，然后右移len，避免与先前数据冲突
            	paddingBytes[56+i]=(byte)(K&0xFFL);
                K=K>>8;
            }
            FGHI(divide(paddingBytes,0));
        }
    }
   
    /*
     * 返回16位密文，实质是返回32位密文的第8~23位密文
     */
    public String encrypt16(String data) throws Md5EncodeException{
    	
    	return encrypt32(data).substring(8, 24);
    }
    
    /*
     * md5加密主方法，返回32位md5密文
     */
    public String encrypt32(String data) throws Md5EncodeException{
    	//TODO
    	if(data==null){
    		throw new Md5EncodeException("待加密数据为空");
    	}
    	
    	//md5加盐方法 这里将待处理数据data倒序进行md5处理
    	byte[] reversStr = Arrays.reverse((data+SALT).getBytes());
//    	System.out.println("待加密数据："+data);
//    	System.out.println("待加密数据倒叙："+ new String(reversStr));
//      byte [] data_Bytes=data.getBytes();	//原数据
    	
        int byte_Length = reversStr.length;		//字节数组长度
        long K = (long)(byte_Length<<3);		//二进制位数k
        int groupCount = byte_Length/64;		//分组个数

        for(int i = 0;i < groupCount;i++){		//分块
        	FGHI(divide(reversStr, i*64));		//处理分组
        }
        padding( byte_Length,reversStr,K);		//填充
        
        
        
        //这里的if语句修复了一个bug，
        //当将该16进制数arr[i]进行“&”操作时，当出现诸如“0e”&“ff”时，会导致“0”被忽略，最终导致密文因缺0而不足32位，也就不满足md5加密算法
        for(int i=0;i<4;i++){
        	if (Long.toHexString(arr[i] & 0xFF).length() == 1) {
        		data_encoder += "0"+
        				Long.toHexString(arr[i] & 0xFF) +
	            		Long.toHexString((arr[i] & 0xFF00) >> 8) +
	            		Long.toHexString((arr[i] & 0xFF0000) >> 16) +
	            		Long.toHexString((arr[i] & 0xFF000000) >> 24);;
        	}else if(Long.toHexString(arr[i] & 0xFF00).length() == 3){
        		data_encoder += Long.toHexString(arr[i] & 0xFF) +
	            		"0" +
        				Long.toHexString((arr[i] & 0xFF00) >> 8)+
	            		Long.toHexString((arr[i] & 0xFF0000) >> 16) +
	            		Long.toHexString((arr[i] & 0xFF000000) >> 24);
        	}else if(Long.toHexString(arr[i] & 0xFF0000).length() == 5){
        		data_encoder += Long.toHexString(arr[i] & 0xFF) +
	            		Long.toHexString((arr[i] & 0xFF00) >> 8) +
	            		"0" +
	            		Long.toHexString((arr[i] & 0xFF0000) >> 16) +
	            		Long.toHexString((arr[i] & 0xFF000000) >> 24);
        		
        	}else if(Long.toHexString(arr[i] & 0xFF000000).length() == 7){
        		data_encoder += Long.toHexString(arr[i] & 0xFF) +
	            		Long.toHexString((arr[i] & 0xFF00) >> 8) +
	            		Long.toHexString((arr[i] & 0xFF0000) >> 16) +
	            		"0"+
	            		Long.toHexString((arr[i] & 0xFF000000) >> 24);
        		
        	}else{
        		data_encoder += Long.toHexString(arr[i] & 0xFF) +
	            		Long.toHexString((arr[i] & 0xFF00) >> 8) +
	            		Long.toHexString((arr[i] & 0xFF0000) >> 16) +
	            		Long.toHexString((arr[i] & 0xFF000000) >> 24);
        	}
  
        }
        //数据归原
        arr[0]= 0x67452301L;
        arr[1]= 0xefcdab89L;
        arr[2]= 0x98badcfeL;
        arr[3]= 0x10325476L;
        String ret_data = data_encoder;
        data_encoder = "";
        return ret_data;
    }
    
    //从inputBytes的索引开始取512位，作为新的512bit的分组
    private static long[] divide(byte[] inputBytes,int start){
        long [] group=new long[16];
        for(int i=0;i<16;i++){
        	//byte->bit，且去掉符号位
            group[i]=byte2unsign(inputBytes[4*i+start])|
                (byte2unsign(inputBytes[4*i+1+start]))<<8|
                (byte2unsign(inputBytes[4*i+2+start]))<<16|
                (byte2unsign(inputBytes[4*i+3+start]))<<24;
        }
        return group;
    }
    
    //byte带符号位，去掉符号位
    public static long byte2unsign(byte b){
        return b < 0 ? b & 0x7F + 128 : b;
     }
    
    //处理分组，每个groups 64字节 512bit
    private void FGHI(long[] groups) {
        long a = arr[0], b = arr[1], c = arr[2], d = arr[3];
        //四轮循环
        for(int n = 0; n < 4; n++) {
        	//16轮迭代
        	for(int i = 0; i < 16; i++) {
            	arr[0] += (function(n, arr[1], arr[2], arr[3])&0xFFFFFFFFL) + groups[M[n][i]] + t[n][i];
                arr[0] = arr[1] + ((arr[0]&0xFFFFFFFFL)<< s[n][i % 4] | ((arr[0]&0xFFFFFFFFL) >>> (32 - s[n][i % 4])));
                
                //循环轮换
                long temp = arr[3];
                arr[3] = arr[2];
                arr[2] = arr[1];
                arr[1] = arr[0];
                arr[0] = temp;
            }
        }
        //加入之前计算的结果
        arr[0] += a;
        arr[1] += b;
        arr[2] += c;
        arr[3] += d;
        
        //防止溢出
        for(int n = 0; n < 4 ; n++) {
        	arr[n] &=0xFFFFFFFFL;
        }
    }
}

