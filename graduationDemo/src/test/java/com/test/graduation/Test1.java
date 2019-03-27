package com.test.graduation;

public class Test1 {

	private static String StrToBinstr(String str) {
		char[] strChar = str.toCharArray();
		String result = "";
		for (int i = 0; i < strChar.length; i++) {
			result += Integer.toBinaryString(strChar[i]);
		}
		return result;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(StrToBinstr("你好"));
	}

}
