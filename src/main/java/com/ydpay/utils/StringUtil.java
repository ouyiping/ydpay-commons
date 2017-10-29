package com.ydpay.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	public static final String NUMBER = "0123456789";
	public static final String ALL_LETTER_AND_NUMBER = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	public static final String LOW_LETTER_AND_NUMBER = "abcdefghijklmnopqrstuvwxyz0123456789";
	public static final String UPPER_LETTER_AND_NUMBER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	/**
	 * 构造HTTP POST交易表单的方法示例
	 * 
	 * @param action
	 *            表单提交地址
	 * @param hiddens
	 *            以MAP形式存储的表单键值
	 * @return 构造好的HTTP POST交易表单
	 */
	public static String createHtml(String action, Map<String, String> hiddens) {
		StringBuffer sf = new StringBuffer();
		sf.append("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/></head><body>");
		sf.append("<form id = \"pay_form\" action=\"" + action
				+ "\" method=\"post\">");
		if (null != hiddens && 0 != hiddens.size()) {
			Set<Entry<String, String>> set = hiddens.entrySet();
			Iterator<Entry<String, String>> it = set.iterator();
			while (it.hasNext()) {
				Entry<String, String> ey = it.next();
				String key = ey.getKey();
				String value = ey.getValue();
				sf.append("<input type=\"hidden\" name=\"" + key + "\" id=\""
						+ key + "\" value=\"" + value + "\"/>");
			}
		}
		sf.append("</form>");
		sf.append("</body>");
		sf.append("<script type=\"text/javascript\">");
		sf.append("document.all.pay_form.submit();");
		sf.append("</script>");
		sf.append("</html>");
		return sf.toString();
	}

	/**
	 * 获取jar包的运行路径
	 * 
	 * @return
	 */
	public static String getProjectPath() {

		java.net.URL url = StringUtil.class.getProtectionDomain()
				.getCodeSource().getLocation();
		String filePath = null;
		try {
			filePath = java.net.URLDecoder.decode(url.getPath(), "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (filePath.endsWith(".jar"))
			filePath = filePath.substring(0, filePath.lastIndexOf("/") + 1);
		java.io.File file = new java.io.File(filePath);
		filePath = file.getAbsolutePath();
		return filePath;
	}

	// 右补
	public static String PadRight(String str, int strLength, String pad) {
		int strLen = str.length();
		if (strLen < strLength) {
			while (strLen < strLength) {
				StringBuffer sb = new StringBuffer();
				sb.append(str).append(pad);// 右补
				str = sb.toString();
				strLen = str.length();
			}
		}
		return str;
	}

	// 左补
	public static String PadLeft(String str, int strLength, String pad) {
		int strLen = str.length();
		if (strLen < strLength) {
			while (strLen < strLength) {
				StringBuffer sb = new StringBuffer();
				sb.append(pad).append(str);// 左补0
				str = sb.toString();
				strLen = str.length();
			}
		}
		return str;
	}

	public static int getChineseLength(String name, String endcoding)
			throws Exception {
		int len = 0; // 定义返回的字符串长度
		int j = 0;
		// 按照指定编码得到byte[]
		byte[] b_name = name.getBytes(endcoding);
		while (true) {
			short tmpst = (short) (b_name[j] & 0xF0);
			if (tmpst >= 0xB0) {
				if (tmpst < 0xC0) {
					j += 2;
					len += 2;
				} else if ((tmpst == 0xC0) || (tmpst == 0xD0)) {
					j += 2;
					len += 2;
				} else if (tmpst == 0xE0) {
					j += 3;
					len += 2;
				} else if (tmpst == 0xF0) {
					short tmpst0 = (short) (((short) b_name[j]) & 0x0F);
					if (tmpst0 == 0) {
						j += 4;
						len += 2;
					} else if ((tmpst0 > 0) && (tmpst0 < 12)) {
						j += 5;
						len += 2;
					} else if (tmpst0 > 11) {
						j += 6;
						len += 2;
					}
				}
			} else {
				j += 1;
				len += 1;
			}
			if (j > b_name.length - 1) {
				break;
			}
		}
		return len;
	}

	public static String getKey(int count, int seed) {
		return getKey(LOW_LETTER_AND_NUMBER, count, seed);
	}

	public static String getKey(String base, int count) {
		return getKey(base, count, 0);
	}

	public static String getKey(String base, int count, int seed) {
		Random random = new Random(System.currentTimeMillis() + seed);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < count; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	/**
	 * 检查指定的字符串是否为空。
	 * <ul>
	 * <li>SysUtils.isEmpty(null) = true</li>
	 * <li>SysUtils.isEmpty("") = true</li>
	 * <li>SysUtils.isEmpty("   ") = true</li>
	 * <li>SysUtils.isEmpty("abc") = false</li>
	 * </ul>
	 * 
	 * @param value
	 *            待检查的字符串
	 * @return true/false
	 */
	public static boolean isEmpty(String value) {
		int strLen;
		if (value == null || (strLen = value.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if ((Character.isWhitespace(value.charAt(i)) == false)) {
				return false;
			}
		}
		return true;
	}

	public static boolean isNotEmpty(String values) {
		return !isEmpty(values);
	}

	/**
	 * 检查对象是否为数字型字符串,包含负数开头的。
	 */
	public static boolean isNumeric(Object obj) {
		if (obj == null) {
			return false;
		}
		char[] chars = obj.toString().toCharArray();
		int length = chars.length;
		if (length < 1)
			return false;

		int i = 0;
		if (length > 1 && chars[0] == '-')
			i = 1;

		for (; i < length; i++) {
			if (!Character.isDigit(chars[i])) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 检查指定的字符串列表是否不为空。
	 */
	public static boolean areNotEmpty(String... values) {
		boolean result = true;
		if (values == null || values.length == 0) {
			result = false;
		} else {
			for (String value : values) {
				result &= !isEmpty(value);
			}
		}
		return result;
	}

	/**
	 * 把通用字符编码的字符串转化为汉字编码。
	 */
	public static String unicodeToChinese(String unicode) {
		StringBuilder out = new StringBuilder();
		if (!isEmpty(unicode)) {
			for (int i = 0; i < unicode.length(); i++) {
				out.append(unicode.charAt(i));
			}
		}
		return out.toString();
	}

	public static String toUnderlineStyle(String name) {
		StringBuilder newName = new StringBuilder();
		for (int i = 0; i < name.length(); i++) {
			char c = name.charAt(i);
			if (Character.isUpperCase(c)) {
				if (i > 0) {
					newName.append("_");
				}
				newName.append(Character.toLowerCase(c));
			} else {
				newName.append(c);
			}
		}
		return newName.toString();
	}

	public static String convertString(byte[] data, int offset, int length) {
		if (data == null) {
			return null;
		} else {
			try {
				return new String(data, offset, length, Constants.CHARSET_UTF8);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	public static byte[] convertBytes(String data) {
		if (data == null) {
			return null;
		} else {
			try {
				return data.getBytes(Constants.CHARSET_UTF8);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * 判断字符串是不是数字类型 (整型 长整型)
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isMath(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher matcher = pattern.matcher(str);
		if (matcher.matches()) {
			return true;
		}
		return false;
	}

	/**
	 * 首字母小写<br>
	 * 
	 * @param str
	 * @return
	 */
	public static String toLowerCaseFistChar(String str) {
		char[] array = str.toCharArray();
		array[0] += 32;
		return String.valueOf(array);
	}

	/**
	 * 过滤请求报文中的空字符串或者空字符串
	 * 
	 * @param contentData
	 * @return
	 */
	public static Map<String, String> filterBlank(
			Map<String, String> contentData) {
		Map<String, String> submitFromData = new HashMap<String, String>();
		Set<String> keyset = contentData.keySet();

		for (String key : keyset) {
			String value = contentData.get(key);
			if (isNotEmpty(value)) {
				// 对value值进行去除前后空处理
				submitFromData.put(key, value.trim());
			}
		}
		return submitFromData;
	}

	/**
	 * list集合转成String工具类
	 * @author tch
	 * @param <T>
	 */
	public static <T> String listToString(List<T> list) {
		String str = "";
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (list.size() == i + 1) {
					str += list.get(i);
				} else {
					str += list.get(i) + ",";
				}
			}
		}
		return str;
	}
	
	public static String toUpperCaseFirstOne(String s)
    {
        if(Character.isUpperCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
    }
	
}
