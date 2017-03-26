package com.hu.myxposeddemo.old;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 保留的公共方法，以便以后使用
 * 
 * @author user
 */
public class StringUtil {

	public static int reversedOrder(int data) {
		byte[] array = new byte[4];
		array[0] = (byte) ((data >> 24) & 0xff);
		array[1] = (byte) ((data >> 16) & 0xff);
		array[2] = (byte) ((data >> 8) & 0xff);
		array[3] = (byte) ((data) & 0xff);
		return array[0] | (array[1] << 8) | (array[2] << 16) | (array[3] << 24);
	}
	
	public static long toUnsignedLong(int x) {
		return ((long) x) & 0xffffffffL;
	}

	private static final char[] HEX_CHAR = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	/**
	 * byteArrayToHexString作用�? 字节数据转十六进制字符串
	 */
	public static String byteArrayToHexString(byte[] data) {
		StringBuilder strBuilder = new StringBuilder();
		for (int i = 0; i < data.length; i++) {
			// 取出字节的高四位，作为索引得到相应的十六进制标识符，注意 >>> 无符号右�?
			strBuilder.append(HEX_CHAR[(data[i] & 0xF0) >>> 4]);
			// 取出字节的低四位，作为索引得到相应的十六进制标识�?
			strBuilder.append(HEX_CHAR[(data[i] & 0x0F)]);
			if (i < data.length - 1) {
				//strBuilder.append(' ');
			}
		}
		return strBuilder.toString();
	}

	/**
	 * getBytesFromCharArray作用�? 把char数组转成byte
	 */
	private static byte[] getBytesFromCharArray(char[] chars) {
		Charset cs = Charset.forName("UTF-8");
		CharBuffer cb = CharBuffer.allocate(chars.length);
		cb.put(chars);
		cb.flip();
		ByteBuffer bb = cs.encode(cb);
		return bb.array();
	}

	/**
	 * getCharsFromByteArray作用�? 把一个byte数组转成�?个char数组
	 */
	private static char[] getCharsFromByteArray(byte[] bytes) {
		Charset cs = Charset.forName("UTF-8");
		ByteBuffer bb = ByteBuffer.allocate(bytes.length);
		bb.put(bytes);
		bb.flip();
		CharBuffer cb = cs.decode(bb);
		return cb.array();
	}

	/**
	 * 方法1�? hexStringtoByteArray1作用�? 将一个指定的hex值的字符串转成一个byte的数�?
	 */
	public static byte[] hexStringtoByteArray1(String hexString) {
		if (hexString == null)
			System.out.println("this hexString must not be empty");
		hexString = hexString.toLowerCase();
		final byte[] byteArray = new byte[hexString.length() / 2];
		int k = 0;
		for (int i = 0; i < byteArray.length; i++) {// 因为�?16进制，最多只会占�?4位，转换成字节需要两�?16进制的字符，高位在先
			byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff); // Character.digit
																					// 在指定的基数返回指定字符的数�?
			byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
			byteArray[i] = (byte) (high << 4 | low);
			k += 2;
		}
		return byteArray;
	}

	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	/**
	 * 方法2�? hexStringtoByteArray2作用�? 将一个指定的hex值的字符串转成一个byte的数�?
	 */
	public static byte[] hexStringtoByteArray2(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	/**
	 * printByteArray作用�? 输出�?个字节数组当中的每一个字�?
	 */
	public static int printByteArray(byte[] needOutputData, int length) {

		if(needOutputData==null){
			System.out.println("err: input data is null");
			return -1;
		}
		
		for (int i = 0; i < length; i++) {
			if (i % 16 == 0 && i != 0)
				System.out.println("");
			System.err.format("%02x ", new Object[] { new Integer(
					needOutputData[i] & 0xFF) });
		}
		System.out.println("");
		return 1;
	}

	/**
	 * intChangeToByteArray int�? byte数组
	 */
	private static byte[] intChangeToByteArray(int value) {
		byte[] a = new byte[4];
		a[3] = (byte) ((value >> 24) & 0xff);
		a[2] = (byte) ((value >> 16) & 0xff);
		a[1] = (byte) ((value >> 8) & 0xff);
		a[0] = (byte) ((value) & 0xff);
		return a;
	}

	
	
	public static byte[] intChangeToByteArray(int value, byte[] data, int index) {
		data[3+index] = (byte) ((value >> 24) & 0xff);
		data[2+index] = (byte) ((value >> 16) & 0xff);
		data[1+index] = (byte) ((value >> 8) & 0xff);
		data[index] = (byte) ((value) & 0xff);
		return data;
	}
	
	
	public static int byteArrayChangeToInt(byte[] array, int beginIndex){
		return (array[beginIndex] &0xFF) | ((array[beginIndex+1] << 8)&0xFF00) | 
				((array[beginIndex+2] << 16) &0xFF0000) | ((array[beginIndex+3] << 24) &0xFF000000);
	}
	
	/**
	 * 合并两个字节数组
	 */
	private static byte[] byteMerger(byte[] byte_1, byte[] byte_2) {
		byte[] byte_3 = new byte[byte_1.length + byte_2.length];
		System.arraycopy(byte_1, 0, byte_3, 0, byte_1.length);
		System.arraycopy(byte_2, 0, byte_3, byte_1.length, byte_2.length);
		return byte_3;
	}

	/**
	 * 循环右移
	 */
	private static int lrotr(int signed, int shift) {
		long unsigned =toUnsignedLong(signed);
		long right = unsigned >> shift;
		long left = unsigned << (32 - shift);
		long temp = left | right;
		return (int) temp;
	}

	/**
	 * 模式匹配去掉串中的非可见字符
	 * 
	 * @param str
	 * @return
	 */
	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

	/**
	 * 从数组中查找模式数组
	 * 
	 * @param text
	 * @param text_len
	 * @param bytePattern
	 * @param startIndex
	 * @return
	 */
	public static int arrayIndexOf(byte[] text, int text_len,
			byte[] bytePattern, int startIndex) {
		int matchPoint = -1;
		int j = 0;
		if (text_len == 0 || startIndex > text_len)
			return -1;
		for (int i = startIndex; i < text_len; i++) {
			if (j > 0 && bytePattern[j] != text[i]) {
				j = 0;
			}
			if (bytePattern[j] == text[i]) {
				j++;
			}
			if (j == bytePattern.length) {
				matchPoint = i - bytePattern.length + 1;
				return matchPoint;
			}
		}
		return -1;
	}

	public static int byteCopy(byte[] des, byte[] src, int len, int desBeginIndex) {
		for (int i = 0; i < len; i++) {
			des[i + desBeginIndex] = src[i];
		}
		return 0;
	}
	
	
	public static int byteCopy(byte[] des, byte[] src, int len) {
		for (int i = 0; i < len; i++) {
			des[i] = src[i];
		}
		return 0;
	}

	public static int byteCopy(byte[] des, int len, int srcBeginIndex,
			byte[] src) {
		for (int i = 0; i < len; i++) {
			des[i] = src[i + srcBeginIndex];
		}
		return 0;
	}


	
	
	public static String myUrlDecodedata(String srcData) {

			try {
				String urlDecodeData = URLDecoder.decode(srcData,"utf-8");
				return urlDecodeData;
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		return null;
	}
	
	public static String myUrlEncodedata(String srcData) {
		try {
			String urlCodeData = URLEncoder.encode(srcData, "utf-8");
			return urlCodeData;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
