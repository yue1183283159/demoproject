package com.common;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.time.DateUtils;

public class StringUtils extends org.apache.commons.lang3.StringUtils {
	// private static final String PASSWORD_CRYPT_KEY =
	// XMLUtils.getConfig().getPasswdKey().substring(0,8);
	public static final String PASSWORD_CRYPT_KEY = "8V6yX7FX";

	// private final static String DES = "DES";
	// private static final byte[] desKey;
	// 解密数据
	public static String decrypt(String message, String key) throws Exception {

		byte[] bytesrc = convertHexString(message);
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
		cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
		byte[] retByte = cipher.doFinal(bytesrc);
		return new String(retByte);
	}

	public static byte[] encrypt(String message, String key) throws Exception {
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
		return cipher.doFinal(message.getBytes("UTF-8"));
	}

	public static String encrypt(String value) {
		String result = "";
		try {
			value = java.net.URLEncoder.encode(value, "utf-8");
			result = toHexString(encrypt(value, PASSWORD_CRYPT_KEY)).toUpperCase();
		} catch (Exception ex) {
			ex.printStackTrace();
			return "";
		}
		return result;
	}

	public static byte[] convertHexString(String ss) {
		byte digest[] = new byte[ss.length() / 2];
		for (int i = 0; i < digest.length; i++) {
			String byteString = ss.substring(2 * i, 2 * i + 2);
			int byteValue = Integer.parseInt(byteString, 16);
			digest[i] = (byte) byteValue;
		}
		return digest;
	}

	public static String toHexString(byte b[]) {
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			String plainText = Integer.toHexString(0xff & b[i]);
			if (plainText.length() < 2)
				plainText = "0" + plainText;
			hexString.append(plainText);
		}
		return hexString.toString();
	}

	public static boolean isNullOrEmpty(String value) {
		if (value == null || value.length() == 0) {
			return true;
		}
		return false;
	}

	public static boolean isNullOrEmpty(Object value) {
		if (value == null) {
			return true;
		}
		if (value.toString().length() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 输入 以逗号分隔的字符串(abc,def,ghi)， 返回返回'abc','def','ghi'格式数据，可以直接用于sql的in语句
	 *
	 * @param stringList
	 * @return
	 */
	public static String listToStringWithQuote(String stringList) {
		return listToStringWithQuote(stringList, ",");
	}

	/**
	 * * 输入 以separator分隔的字符串(如abc@def@ghi)， 返回返回'abc','def','ghi'格式数据，可以直接用于sql的in语句
	 *
	 * @param stringList
	 * @param separator
	 * @return
	 */
	public static String listToStringWithQuote(String stringList, String separator) {
		if (StringUtils.isNullOrEmpty(stringList)) {
			return "''";
		}
		String[] stringArray = stringList.split(separator);
		if (stringArray == null) {
			return "''";
		}
		List<String> list = Arrays.asList(stringArray);

		String stringWithQuote = listToStringWithQuote(list);
		return stringWithQuote;
	}

	/**
	 * 返回'abc','def','ghi'格式数据，可以直接用于sql的in语句
	 *
	 * @param list
	 * @return
	 */
	public static String listToStringWithQuote(List<String> list) {
		if (list != null && list.size() > 0) {
			StringBuilder sb = new StringBuilder(list.size() * 40);
			for (String str : list) {
				if (!isNullOrEmpty(str)) {
					sb.append("'").append(str.replace("'", "''")).append("',");
				}
			}
			if (sb.length() > 0) {
				return sb.substring(0, sb.length() - 1);
			}
		}
		return "''";
	}

	public static String listToStringWithQuote(List<Integer> list, String sep) {
		if (list != null && list.size() > 0) {
			StringBuilder sb = new StringBuilder(list.size() * 40);
			for (Integer number : list) {
				if (!isNullOrEmpty(number)) {
					sb.append(sep).append(number).append(sep).append(",");
				}
			}
			if (sb.length() > 0) {
				return sb.substring(0, sb.length() - 1);
			}
		}
		return "''";
	}

	/**
	 * 字符串转list, 按英文逗号拆分
	 *
	 * @param sourceString
	 * @return
	 */
	public static List<String> stringsToList(String sourceString) {
		return stringsToList(sourceString, ",");
	}

	/**
	 * 字符串转list, 按separator拆分
	 *
	 * @param sourceString
	 * @param separator
	 * @return
	 */
	public static List<String> stringsToList(String sourceString, String separator) {
		if (StringUtils.isNullOrEmpty(sourceString)) {
			return new ArrayList<String>();
		}
		String[] stringArray = sourceString.split(separator);
		List<String> list = Arrays.asList(stringArray);
		// 注意，下面这行代码是特意加上的，原因是：Arrays.asList返回的list，不能执行add,remove方法，执行会报错。
		// 为了使这个方法返回的list能够执行add,remove方法，就要使用下面这行代码
		list = new ArrayList<String>(list);
		return list;
	}

	public static void main(String[] args) throws Exception {

		// String value =
		// DateUtils.getCurrentStrTime(DateUtils.LINKED_DATE_STYLE);
		// System.out.println("加密数据:" + value);
		// System.out.println("密码为:abcd");
		// String a = encrypt(value);
		//
		// System.out.println("加密后的数据为:" + a);
		// String s = decrypt(a, "8V6yX7FX");
		// System.out.println("解密后的数据为:" + s);

		System.out.println(getExcelName());

	}

	/**
	 * 获取指定长度的随机数字字符串
	 *
	 * @param length
	 * @return
	 */
	public static String nonceStr(int length) {
		String chars = "0123456789";
		StringBuilder builder = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			builder.append(chars.charAt(new Random().nextInt(9)));
		}
		return builder.toString();
	}

	public static String getCurrTime() {
		SimpleDateFormat outFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String s = outFormat.format(new Date());
		return s;
	}

	public static String getExcelName() {
		return getCurrTime() + nonceStr(4);
	}

	public static String filterNullToSpace(Object str) {
		if (isNullOrEmpty(str)) {
			return "";
		}
		return str.toString();
	}

	// Added By Fangxm 2016.11.23 ---------START
	public static String TrimEnd(String input, String charsToTrim) {
		if (input == null) {
			return null;
		}
		return input.replaceAll("[" + charsToTrim + "]+$", "");
	}

	public static String TrimStart(String input, String charsToTrim) {
		if (input == null) {
			return null;
		}
		return input.replaceAll("^[" + charsToTrim + "]+", "");
	}

	public static String Trim(String input, String charsToTrim) {
		if (input == null) {
			return null;
		}
		return input.replaceAll("^[" + charsToTrim + "]+|[" + charsToTrim + "]+$", "");
	}

	public static String Trim(String input, Character charsToTrim) {
		if (input == null) {
			return input;
		}
		if (charsToTrim == null) {
			charsToTrim = ' ';
		}
		StringBuffer strb = new StringBuffer();
		strb.append(input);
		while (strb.length() > 0 && strb.charAt(0) == charsToTrim) {
			strb.deleteCharAt(0);
		}
		while (strb.length() > 0 && strb.charAt(strb.length() - 1) == charsToTrim) {
			strb.deleteCharAt(strb.length() - 1);
		}
		return strb.toString();
	}

	public static String TrimStart(String input, Character charsToTrim) {
		if (input == null) {
			return input;
		}
		if (charsToTrim == null) {
			charsToTrim = ' ';
		}
		StringBuffer strb = new StringBuffer();
		strb.append(input);
		while (strb.length() > 0 && strb.charAt(0) == charsToTrim) {
			strb.deleteCharAt(0);
		}
		return strb.toString();
	}

	public static String TrimEnd(String input, Character charsToTrim) {
		if (input == null) {
			return input;
		}
		if (charsToTrim == null) {
			charsToTrim = ' ';
		}
		StringBuffer strb = new StringBuffer();
		strb.append(input);
		while (strb.length() > 0 && strb.charAt(strb.length() - 1) == charsToTrim) {
			strb.deleteCharAt(strb.length() - 1);
		}
		return strb.toString();
	}

	// Added By Fangxm 2016.11.23 ---------END

	// Added By Fangxm 2016.11.24 ---------START

	/**
	 * 比较String是否相同
	 *
	 * @param strs
	 * @return true:相同 false:不同
	 */
	public static boolean isEqualStrings(String... strs) {
		if (strs == null || strs.length <= 0) {
			return false;
		}
		if (strs.length == 1) {
			return true;
		}
		for (int i = 1; i < strs.length; i++) {
			if (!isEqualTwoStrings(strs[i], strs[i - 1])) {
				return false;
			}
		}
		return true;
	}

	/**
	 * str1:"" str2:null true str1:"" str2:"" true str1:null str2:"" true str1:""
	 * str2:"xxx" false
	 *
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean isEqualTwoStrings(String str1, String str2) {
		if (isNullOrEmpty(str1) && !isNullOrEmpty(str2)) {
			return false;
		}
		if (!isNullOrEmpty(str1) && isNullOrEmpty(str2)) {
			return false;
		}
		if (isNullOrEmpty(str1) && isNullOrEmpty(str2)) {
			return true;
		}
		return str1.equals(str2);
	}

	public static boolean isEqualTwoDouble(String str1, String str2) {
		if (isNullOrEmpty(str1) && !isNullOrEmpty(str2)) {
			return false;
		}
		if (!isNullOrEmpty(str1) && isNullOrEmpty(str2)) {
			return false;
		}
		Double dbl1 = castStringToDouble(str1);
		Double dbl2 = castStringToDouble(str2);
		return Double.compare(dbl1, dbl2) == 0;
	}

	public static Double castStringToDouble(String str) {
		try {
			Double dbl = Double.valueOf(String.valueOf(str));
			return dbl;
		} catch (NumberFormatException e) {
			e.printStackTrace();
			throw new RuntimeException("NumberFormatException when cast String to Double, the value is : " + str);
		}
	}

	public static boolean isEqualTwoArray(String str1, String str2) {
		return isEqualTwoArray(str1, str2, ",");
	}

	public static boolean isEqualTwoArray(String str1, String str2, String opertor) {
		if (isNullOrEmpty(str1) && !isNullOrEmpty(str2)) {
			return false;
		}
		if (!isNullOrEmpty(str1) && isNullOrEmpty(str2)) {
			return false;
		}
		if (str1 == null && str2 == null) {
			return true;
		}
		if ("".equals(str1) && "".equals(str2)) {
			return true;
		}
		if ("[]".equals(str1) && "[]".equals(str2)) {
			return true;
		}
		if (str1.equals(str2)) {
			return true;
		}
		String[] strs1 = split(str1, opertor);
		String[] strs2 = split(str2, opertor);
		if (strs1.length != strs2.length) {
			return false;
		}
		List<String> strl1 = Arrays.asList(strs1);
		for (String s : strs2) {
			if (!strl1.contains(s)) {
				return false;
			}
		}

		return true;
	}

	// Added By Fangxm 2016.11.24 ---------END

	// Added By Fangxm 2016.12.09 ---------START

	/**
	 * 将id的合并成带单引号的字符串,用于sql
	 *
	 * @param idList
	 * @return
	 */
	public static String joinIdList(List<String> idList) {
		StringBuffer idsb = new StringBuffer();
		if (idList == null || idList.isEmpty()) {
			return "";
		}
		for (String id : idList) {
			if (isBlank(id)) {
				continue;
			}
			if (idsb.length() > 0) {
				idsb.append(",");
			}
			idsb.append("'");
			idsb.append(id);
			idsb.append("'");
		}
		if (idsb.length() <= 0) {
			return "";
		}
		return idsb.toString();
	}

	// Added By Fangxm 2016.12.09 ---------END
	/**
	 * 作为String来判断是否为0,不使用类型转换
	 *
	 * @param num
	 * @return
	 */
	public static boolean isZero(String num) {
		return StringUtils.isBlank(StringUtils.Trim(num.replace(".", ""), '0'));
	}

	public static String substring(String source, int count) {
		if (source == null) {
			return "";
		}
		if (source.length() == 0) {
			return "";
		}
		if (source.length() <= count) {
			return source;
		}
		return source.substring(0, count);
	}

	/**
	 * 只用正则表达式去除空格,制表符,换行符,回车
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
	 * 给StringBuffer添加新行
	 *
	 * @param sb
	 * @param str
	 */
	public static void appendNewline(StringBuffer sb, String str) {
		if (sb == null) {
			return;
		}
		sb.append(str);
		sb.append("\n");
	}

	public static String castToString(Object obj) {
		return castToString(obj, null);
	}

	/**
	 * 将Object转换成String
	 *
	 * @param obj
	 * @return
	 */
	public static String castToString(Object obj, Integer maxLength) {
		if (obj == null) {
			return "";
		}
		String str = null;
		if (obj instanceof String) {
			str = (String) obj;
		} else if (obj instanceof Date) {
			// str = DateUtils.convterDataStyle(DateUtils.DEFAULT_DATE_STYLE, (Date) obj);
		}
		// Added By Fangxm 2017.08.03 返回堆栈信息
		else if (obj instanceof Throwable) {
			Throwable ex = (Throwable) obj;
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			try {
				ex.printStackTrace(pw);
				str = sw.toString();
			} finally {
				pw.close();
			}
		} else {
			str = obj.toString();
		}
		// Modified By Fangxm 2017.08.08 按照最大长度截取
		if (maxLength != null && maxLength > 0 && str.length() > maxLength) {
			str = str.substring(0, maxLength);
		}
		return str;
	}

	/**
	 * 将map中值按key的ascii排序
	 */
	public static String getSortStr(Map<String, String> map) {
		if (MapUtils.isEmpty(map)) {
			return "";
		}
		String[] paramKeys = new String[map.size()];
		int index = 0;
		for (String key : map.keySet()) {
			if (!"sign".equals(key)) {
				paramKeys[index++] = key;
			}
		}
		Arrays.sort(paramKeys);
		StringBuffer tempSign = new StringBuffer();
		for (String key : paramKeys) {
			String value = map.get(key).toString();
			tempSign.append("&").append(key).append("=").append(value);
		}
		tempSign.deleteCharAt(0);
		return tempSign.toString();
	}

	/**
	 * split成List而不是String[]
	 * 
	 * @param str
	 * @param separatorChars
	 * @return
	 */
	public static List<String> splitToList(String str, String separatorChars) {
		return splitWorker(str, separatorChars, -1, false);
	}

	/**
	 * 这个方法是StringUtils里面抄过来的, 因为String[]是用List转换的, 我之后要用到stream的时候还要转换回去,
	 * 所以这里直接返回List
	 * 
	 * @param str
	 * @param separatorChars
	 * @param max
	 * @param preserveAllTokens
	 * @return
	 */
	private static List<String> splitWorker(String str, String separatorChars, int max, boolean preserveAllTokens) {
		// Performance tuned for 2.0 (JDK1.4)
		// Direct code is quicker than StringTokenizer.
		// Also, StringTokenizer uses isSpace() not isWhitespace()

		if (str == null) {
			return Collections.emptyList();
		}
		int len = str.length();
		if (len == 0) {
			return Collections.emptyList();
		}
		// List<String> list = List.newArrayList();
		List<String> list = new ArrayList<>();
		int sizePlus1 = 1;
		int i = 0;
		int start = 0;
		boolean match = false;
		boolean lastMatch = false;
		if (separatorChars == null) {
			// Null separator means use whitespace
			while (i < len) {
				if (Character.isWhitespace(str.charAt(i))) {
					if (match || preserveAllTokens) {
						lastMatch = true;
						if (sizePlus1++ == max) {
							i = len;
							lastMatch = false;
						}
						list.add(str.substring(start, i));
						match = false;
					}
					start = ++i;
					continue;
				}
				lastMatch = false;
				match = true;
				i++;
			}
		} else if (separatorChars.length() == 1) {
			// Optimise 1 character case
			char sep = separatorChars.charAt(0);
			while (i < len) {
				if (str.charAt(i) == sep) {
					if (match || preserveAllTokens) {
						lastMatch = true;
						if (sizePlus1++ == max) {
							i = len;
							lastMatch = false;
						}
						list.add(str.substring(start, i));
						match = false;
					}
					start = ++i;
					continue;
				}
				lastMatch = false;
				match = true;
				i++;
			}
		} else {
			// standard case
			while (i < len) {
				if (separatorChars.indexOf(str.charAt(i)) >= 0) {
					if (match || preserveAllTokens) {
						lastMatch = true;
						if (sizePlus1++ == max) {
							i = len;
							lastMatch = false;
						}
						list.add(str.substring(start, i));
						match = false;
					}
					start = ++i;
					continue;
				}
				lastMatch = false;
				match = true;
				i++;
			}
		}
		if (match || (preserveAllTokens && lastMatch)) {
			list.add(str.substring(start, i));
		}
		return list;
	}

	/**
	 * 将字符串用短横杠拼接
	 *
	 * @param strArr
	 * @return
	 * @author xiangmin.fang
	 */
	public static String joinWithCrossBar(String... strArr) {
		return joinStr("-", strArr);
	}

	/**
	 * 将字符串用箭头拼接
	 *
	 * @param strArr
	 * @return
	 */
	public static String joinWithArrow(String... strArr) {
		return joinStr("->", strArr);
	}

	public static String joinStr(String sep, String... strArr) {
		if (strArr == null || strArr.length <= 0) {
			return StringUtils.EMPTY;
		}
		return Arrays.asList(strArr).stream().filter(StringUtils::isNotBlank).collect(Collectors.joining(sep));
	}

	/**
	 * [中文姓名] 只显示第一个汉字，其他隐藏为2个星号<例子：李**>
	 *
	 * @param fullName
	 * @return
	 */
	public static String chineseName(String fullName) {
		if (StringUtils.isBlank(fullName)) {
			return "";
		}
		String name = StringUtils.left(fullName, 1);
		return StringUtils.rightPad(name, StringUtils.length(fullName), "*");
	}

}
