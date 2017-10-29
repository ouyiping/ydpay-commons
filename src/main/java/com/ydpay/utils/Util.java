package com.ydpay.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jboss.netty.handler.codec.http.QueryStringDecoder;

import com.alibaba.fastjson.JSONObject;

public class Util {

	public static String rounded(String str) {
		DecimalFormat df = new DecimalFormat("0.00");
		String result = df.format(Double.parseDouble(str));
		return result;
	}
	
	/**
	 * 四舍五入
	 * 
	 * @param d
	 * @param scale
	 * @return
	 */
	private static double round(double d, int scale) {
		return Double.parseDouble(String.format("%."+scale+"f",d));
	}

	/**
	 * 四舍五入（保留2位小数）
	 * 
	 * @param d
	 * @return
	 */
	public static double round2(double d) {
		return round(d, 2);
	}

	public static String rounded(double str) {
		DecimalFormat df = new DecimalFormat("0.00");
		String result = df.format(str);
		return result;
	}

	public static double roundedToDouble(String str) {
		DecimalFormat df = new DecimalFormat("0.00");
		String result = df.format(Double.parseDouble(str));
		return ObjectParser.toDouble(result);
	}

	public static double roundedToDouble(double str) {
		DecimalFormat df = new DecimalFormat("0.00");
		String result = df.format(str);
		return ObjectParser.toDouble(result);
	}
	/**
	 * 四舍五入（保留2位小数）
	 * 
	 * @param d
	 *            ,multiple放大倍数100,
	 * @return
	 */
	public static long double2long(double d, int multiple) {
		String sLong = Double.toString(d);
		String ssLong = scientificNumChange(sLong);
		String[] SplLong = ssLong.split("\\.");
		if (SplLong.length == 1)
			return Long.parseLong(ssLong)*multiple;
		else if (SplLong.length == 2) {
			int mulNum = String.valueOf(multiple).length() - 1;
			String tmp = SplLong[1];
			long subLong1 = 0;
			long subLong2 = 0;
			if (tmp.length() < mulNum)
				subLong2 = Long.parseLong(tmp) * multiple / 10;
			else {

				String tmp1 = SplLong[1].substring(0, mulNum);
				subLong2 = Long.parseLong(tmp1);
			}
			String tmp2 = SplLong[0];
			subLong1 = Long.parseLong(tmp2) * multiple;
			long retLong = subLong1 + subLong2;
			return retLong;
		} else
			return -1;
	}

	public static Map<String, String> getQueryParams(String params) {
		QueryStringDecoder queryStringDecoder = new QueryStringDecoder(params);
		Map<String, String> paramsMap = new HashMap<String, String>();
		for (Entry<String, List<String>> p : queryStringDecoder.getParameters()
				.entrySet()) {
			String key = p.getKey().trim();
			List<String> vals = p.getValue();
			if (vals.size() > 0) {
				String value = vals.get(0);
				paramsMap.put(key, value);
			}
		}
		return paramsMap;
	}

	/**
	 * 构造HTTP POST交易表单的方法示例
	 * 
	 * @param action
	 *            表单提交地址
	 * @param paramObj
	 *            以JSONObject形式存储的表单键值
	 * @return 构造好的HTTP POST交易表单
	 */
	public static String createHtml(String action, Map<String, Object> paramObj) {
		StringBuffer sf = new StringBuffer();
		sf.append("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/></head><body>");
		sf.append("<form id = \"pay_form\"  action=\"" + action
				+ "\" method=\"post\">");
		// for (Map.Entry<String, Object> entry : paramObj.entrySet()) {
		// String key = entry.getKey();
		// String value = entry.getValue().toString();
		// sf.append("<input type=\"hidden\" name=\"" + key + "\" id=\"" + key
		// + "\" value=\"" + value + "\"/>");
		// }
		for (String key : paramObj.keySet()) {
			sf.append("<input type=\"hidden\" name=\"" + key + "\" id=\"" + key
					+ "\" value=\"" + paramObj.get(key) + "\"/>");
		}
		sf.append("</form>");
		sf.append("</body>");
		sf.append("<script type=\"text/javascript\">");
		sf.append("document.all.pay_form.submit();");
		sf.append("</script>");
		sf.append("</html>");
		return sf.toString();
	}

	public static String buildQuery(JSONObject paramObj) {
		StringBuffer query = new StringBuffer();
		boolean hasParam = false;
		for (Map.Entry<String, Object> entry : paramObj.entrySet()) {
			String key = entry.getKey();
			String value = (String) entry.getValue();
			if (StringUtil.isEmpty(key) || StringUtil.isEmpty(value))
				continue;
			if (hasParam) {
				query.append("&");
			} else {
				hasParam = true;
			}

			try {
				query.append(key).append("=")
						.append(URLEncoder.encode(value, "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return query.toString();
	}

	public static Map<String, String> converJsonToMap(JSONObject paramObj) {
		Map<String, String> stringMap = new HashMap<String, String>();
		for (Map.Entry<String, Object> entry : paramObj.entrySet()) {
			String key = entry.getKey();
			String value = (String) entry.getValue();
			if (StringUtil.isNotEmpty(key) && StringUtil.isNotEmpty(value))
				stringMap.put(key, value);
		}
		return stringMap;
	}

	public static List<String> converStringToList(String sources, String split) {
		if (StringUtil.isEmpty(sources))
			return null;

		List<String> list = new ArrayList<String>();
		String[] array = sources.split(split);
		for (String source : array) {
			list.add(source);
		}
		return list;
	}

	/**
	 * 隐藏手机号中间4位
	 * 
	 * @param mobilephone
	 */
	public static String hideMobilephone(String mobilephone) {
		return mobilephone.substring(0, 3) + "****"
				+ mobilephone.substring(7, mobilephone.length());
	}

	/**
	 * 隐藏银行卡号
	 * 
	 * @param mobilephone
	 */
	public static String hideBankaccount(String bankaccount) {
		if (bankaccount != null && bankaccount.length() >= 6) {
			return bankaccount.substring(0, 6) + "******"
					+ bankaccount.substring(bankaccount.length() - 4);
		} else {
			return bankaccount;
		}
	}

	/**
	 * 正则表达式：验证用户名
	 */
	public static final String REGEX_USERNAME = "^[a-zA-Z]\\w{5,17}$";

	/**
	 * 正则表达式：验证密码
	 */
	public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,16}$";

	/**
	 * 正则表达式：验证手机号
	 */
	public static final String REGEX_MOBILE = "^[1][3,4,5,7,8][0-9]{9}$";

	/**
	 * 正则表达式：验证邮箱
	 */
	public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

	/**
	 * 正则表达式：验证汉字
	 */
	public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5],{0,}$";

	/**
	 * 正则表达式：验证URL
	 */
	public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";

	/**
	 * 正则表达式：验证IP地址
	 */
	public static final String REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";

	/**
	 * 校验用户名
	 * 
	 * @param username
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isUsername(String username) {
		return Pattern.matches(REGEX_USERNAME, username);
	}

	/**
	 * 校验密码
	 * 
	 * @param password
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isPassword(String password) {
		return Pattern.matches(REGEX_PASSWORD, password);
	}

	/**
	 * 校验手机号
	 * 
	 * @param mobile
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isMobile(String mobile) {
		return Pattern.matches(REGEX_MOBILE, mobile);
	}

	/**
	 * 校验邮箱
	 * 
	 * @param email
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isEmail(String email) {
		return Pattern.matches(REGEX_EMAIL, email);
	}

	/**
	 * 校验汉字
	 * 
	 * @param chinese
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isChinese(String chinese) {
		return Pattern.matches(REGEX_CHINESE, chinese);
	}

	/**
	 * 校验URL
	 * 
	 * @param url
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isUrl(String url) {
		return Pattern.matches(REGEX_URL, url);
	}

	/**
	 * 校验IP地址
	 * 
	 * @param ipAddr
	 * @return
	 */
	public static boolean isIPAddr(String ipAddr) {
		return Pattern.matches(REGEX_IP_ADDR, ipAddr);
	}

	/*
	 * 校验过程： 1、从卡号最后一位数字开始，逆向将奇数位(1、3、5等等)相加。
	 * 2、从卡号最后一位数字开始，逆向将偶数位数字，先乘以2（如果乘积为两位数，将个位十位数字相加，即将其减去9），再求和。
	 * 3、将奇数位总和加上偶数位总和，结果应该可以被10整除。
	 */
	/**
	 * 校验银行卡卡号
	 */
	public static boolean checkBankCard(String bankCard) {
		if (bankCard.length() < 15 || bankCard.length() > 19) {
			return false;
		}
		char bit = getBankCardCheckCode(bankCard.substring(0,
				bankCard.length() - 1));
		if (bit == 'N') {
			return false;
		}
		return bankCard.charAt(bankCard.length() - 1) == bit;
	}

	/**
	 * 验证是否存在特殊字符
	 * 
	 * @param bankaccount
	 * @return
	 */
	public static boolean regEx(String bankaccount) {
		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(bankaccount);
		return m.find();
	}

	/**
	 * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
	 * 
	 * @param nonCheckCodeBankCard
	 * @return
	 */
	public static char getBankCardCheckCode(String nonCheckCodeBankCard) {
		if (nonCheckCodeBankCard == null
				|| nonCheckCodeBankCard.trim().length() == 0
				|| !nonCheckCodeBankCard.matches("\\d+")) {
			// 如果传的不是数据返回N
			return 'N';
		}
		char[] chs = nonCheckCodeBankCard.trim().toCharArray();
		int luhmSum = 0;
		for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
			int k = chs[i] - '0';
			if (j % 2 == 0) {
				k *= 2;
				k = k / 10 + k % 10;
			}
			luhmSum += k;
		}
		return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
	}

	public static String subString(String source, int maxLength) {
		if (StringUtil.isEmpty(source) || source.length() <= maxLength) {
			return source;
		}
		return source.substring(0, maxLength);
	}

	/**
	 * 将 该类字符串转成json对象 </br>
	 * respDesc=成功&signature=63BE84908C6A8FB2BA88E75BEB42014B
	 * 
	 * @param queryStr
	 * @return
	 */
	public static JSONObject makeHttpQueryParamToMap(String queryStr) {
		String[] resultArray = queryStr.split("&");
		JSONObject resultMap = new JSONObject();
		for (String str : resultArray) {
			int index = str.indexOf("=");
			resultMap.put(str.substring(0, index), str.substring(index + 1));
		}
		return resultMap;
	}

	/**
	 * 过滤参数
	 * 
	 * @author
	 * @param sArray
	 * @return
	 */
	public static Map<String, String> paraFilter(Map<String, String> sArray) {
		Map<String, String> result = new HashMap<String, String>(sArray.size());
		if (sArray == null || sArray.size() <= 0) {
			return result;
		}
		for (String key : sArray.keySet()) {
			String value = sArray.get(key);
			if (value == null || value.equals("")
					|| key.equalsIgnoreCase("sign")) {
				continue;
			}
			result.put(key, value);
		}
		return result;
	}

	/**
	 * @author
	 * @param payParams
	 * @return A=2&b=4&c=4
	 */
	public static String buildPayParams(Map<String, String> payParams,
			boolean encoding) {
		try {
			StringBuilder sb = new StringBuilder((payParams.size() + 1) * 10);
			List<String> keys = new ArrayList<String>(payParams.keySet());
			Collections.sort(keys);
			for (String key : keys) {
				sb.append(key).append("=");
				if (encoding) {
					sb.append(URLEncoder.encode(payParams.get(key), "UTF-8"));
				} else {
					sb.append(payParams.get(key));
				}
				sb.append("&");
			}
			sb.setLength(sb.length() - 1);
			return sb.toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * Map -->XML
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String parseXML(Map<String, String> map) {
		StringBuffer sb = new StringBuffer();
		sb.append("<xml encoding=\"UTF-8\">");
		Set es = map.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (null != v && !"".equals(v) && !"appkey".equals(k)) {
				sb.append("<" + k + ">" + map.get(k) + "</" + k + ">\n");
			}
		}
		sb.append("</xml>");
		return sb.toString();
	}

	public static String toXml(Map<String, String> params) {
		StringBuilder buf = new StringBuilder();
		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);
		buf.append("<xml>");
		for (String key : keys) {
			buf.append("<").append(key).append(">");
			buf.append("<![CDATA[").append(params.get(key)).append("]]>");
			buf.append("</").append(key).append(">\n");
		}
		buf.append("</xml>");
		return buf.toString();
	}

	/**
	 * 计算两个时间相差月数
	 * 
	 * @param date1
	 * @param date2
	 * @return 当月返回1
	 */
	public static int getMonthSpace(String satartdata, String enddata) {
		try {
			int result = 0;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c1 = Calendar.getInstance();
			Calendar c2 = Calendar.getInstance();
			c1.setTime(sdf.parse(satartdata));
			c2.setTime(sdf.parse(enddata));
			result = c2.get(Calendar.MONDAY) - c1.get(Calendar.MONTH);
			return result == 0 ? 1 : Math.abs(result);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * <功能简述> 获取数字随机数
	 * 
	 * @param length
	 *            位数
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public static int getRandomNumber(int length) {
		if (length > 0) {
			String len = "1";
			for (int i = 1; i < length; i++) {
				len = len + "0";
			}
			int digit = Integer.parseInt(len);
			return (int) ((Math.random() * 9 + 1) * digit);
		}
		return 888888;
	}

	public static void main(String[] args) throws Exception {
		double d = 1.302895E+7;
		System.out.println(double2long(d, 100));
		double dd = 0.01;
		System.out.println(double2long(dd, 100));
//		System.out
//		.println(AesEncryption
//				.Desencrypt(
//						"y6nsEa5Sy3gjYv9Ewmj8nO7vnzygWOSY3BZUJu-9disRPAS3UNw6zPqQQkOdoSpU4j36VKFl6Prxl2MNHC-H3q5MUw-TY8xh_vTsvhjPmSA=",
//						"n4u6bcqxfy7z5jb8", "n4u6bcqxfy7z5jb8"));
		
//		final String FORMAT = "json";
//		final String VERSION = "2.0";
//		final long APPID = 200289542;
//		String SECRETKEY = "b8ralk0ltenqi5cy";
//		String SESSION = "4bf3cbcb70526a5f6f7f565f55cea6d0";
//		final String URL = "https://gw.masget.com:7373/openapi/rest";
//		final String method = "masget.webapi.com.settlement.batch.transfer";// 后台
//		final tf_session SESSION_OBJ = new tf_session();
//
//		SESSION_OBJ.setCompanyid(APPID);
//		SESSION_OBJ.setSession(SESSION);
//		SESSION_OBJ.setSecretkey(SECRETKEY);
//		com.alibaba.fastjson.JSONArray jsonArray = new com.alibaba.fastjson.JSONArray();
//
//		final JSONObject josn = new JSONObject();
//		josn.put("accountname", "张海锋");
//		josn.put("bankaccount", "6225882020884927");
//		josn.put("accounttype", 1);
//		josn.put("bank", "");
//		josn.put("bankcode", "");
//		josn.put("ordernumber",
//				OrdernumUtil.get(OrderType.INCOME_APPLY_ORDER.getCode()));
//		josn.put("paymenttype", 4);
//		josn.put("exparams", "");
//		josn.put("remark", "备注");
//		jsonArray.add(josn);
//		final JSONObject jsonObject = new JSONObject();
//		jsonObject.put("total", 1);
//		jsonObject.put("totalamount", 10);
//		jsonObject.put("rows", jsonArray);
//		for (int i = 0; i < 1000; i++) {
//			josn.put("orderamount", i + 1);
//			josn.put("amount", i + 1);
//			jsonObject.put("totalamount", i + 1);
//			jsonObject
//					.put("batchnumber", System.currentTimeMillis() + "" + i++);
//			jsonObject.put("rows", jsonArray);
//			Map<String, String> params = ProviderUtil.makeProtocalParams(
//					method, jsonObject.toJSONString(), SESSION_OBJ,
//					com.masget.api.Constants.NEED_TO_REDIRECT);
//			transfer(method, params, URL);
//		}

	}

	public static void transfer(String mehtod,
			final Map<String, String> params, final String url) {
		new Thread(new Runnable() {
			public void run() {
				try {
					String result = HttpsUtil.sendPost(url,
							JSONObject.toJSONString(params));
					System.out.println(result);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	public static String getXMLValue(String srcXML, String element) {
		String ret = "";
		try {
			String begElement = "<" + element + ">";
			String endElement = "</" + element + ">";
			int begPos = srcXML.indexOf(begElement);
			int endPos = srcXML.indexOf(endElement);
			if (begPos != -1 && endPos != -1 && begPos <= endPos) {
				begPos += begElement.length();
				ret = srcXML.substring(begPos, endPos);
			} else {
				ret = "";
			}
		} catch (Exception e) {
			ret = "";
		}
		return ret;
	}

	/**
	 * 处理导入导出的字符含".0"
	 * 
	 * @author tch
	 * @param str
	 * @return
	 */
	public static String dealImportAndExportStr(String str) {
		 if (StringUtil.isNotEmpty(str) && str.trim().endsWith(".0")) {
			 String[] split = str.split(".0");
			 if (split.length>0) {
				 str = split[0];
			 }
		 }
		return str;
	}

	/**
     * 获取一定长度的随机字符串
     * @param length 指定字符串长度
     * @return 一定长度的字符串
     */
    public static String getRandomStringByLength(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
    
    
    public static String getSign(Map<String,Object> map,String key){
    	
        ArrayList<String> list = new ArrayList<String>();
        for(Map.Entry<String,Object> entry:map.entrySet()){
            if(entry.getValue()!=""){
                list.add(entry.getKey() + "=" + entry.getValue() + "&");
            }
        }
        int size = list.size();
        String [] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < size; i ++) {
            sb.append(arrayToSort[i]);
        }
        String result = sb.toString();
        result += "key=" + key;
        return result;
    }
    

	/**
	 * 科学计数法转成数字
	 * @param scientificNum
	 * @return
	 * @createTime 2016-6-30
	 * @author shenbin
	 */
	public static String scientificNumChange(String scientificNum){
		if(isBlank(scientificNum)){
			return null;
		}
		BigDecimal bd = new BigDecimal(scientificNum);
		return bd.toPlainString();//科学计数法转换
	}
	
	/**
	 * 两科学计数法相加后转成数字
	 * @param scientificNum
	 * @param augend
	 * @return
	 * @createTime 2016-8-22
	 * @author shenbin
	 */
	public static String addScientificNum(String scientificNum, String augend){
		if(isBlank(scientificNum)){
			return scientificNumChange(augend);
		}
		if(isBlank(augend)){
			return scientificNumChange(scientificNum);
		}
		BigDecimal bd = new BigDecimal(scientificNum);
		BigDecimal aug = new BigDecimal(augend);
		return bd.add(aug).toPlainString();
	}
	
	
	public static boolean isBlank(String str){
		if(null!=str && !str.trim().equals("")){
			return false;
		}
		return true;
	}
	
	public static BigDecimal addScientificNum(BigDecimal scientificNum, String augend){
		BigDecimal aug = new BigDecimal(augend);
		return scientificNum.add(aug);
	}    
	
	/* 
     * 计算两点之间距离 
     *  
     * @param start 
     *  
     * @param end 
     *  
     * @return 米 
     */  
    public static double getDistanceGaode(double startLongitude, double startLatitude, double endLongitude, double endLatitude)  
    {  
        double lon1 = (Math.PI / 180) * startLongitude;  
        double lon2 = (Math.PI / 180) * endLongitude;  
        double lat1 = (Math.PI / 180) *startLatitude;  
        double lat2 = (Math.PI / 180) * endLatitude;  
        // 地球半径  
        double R = 6371;  
        // 两点间距离 km，如果想要米的话，结果*1000就可以了  
        double d = Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon2 - lon1)) * R;  
        return d * 1000;  
    } 
    
    /**
	 * 计算两个日期之间相差的天数
	 * @param smdate
	 * @param bdate
	 * @return
	 */
	 public static int daysBetween(String old_date,String now_bdate){  
	        try {
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
				Calendar cal = Calendar.getInstance();    
				cal.setTime(sdf.parse(old_date));    
				long time1 = cal.getTimeInMillis();                 
				cal.setTime(sdf.parse(now_bdate));    
				long time2 = cal.getTimeInMillis();         
				long between_days=(time2-time1)/(1000*3600*24);  
				return Integer.parseInt(String.valueOf(between_days));
			} catch (Exception e) {
				e.printStackTrace();
			}
			return 0;     
	    }
	 
	 /**
	  * 获取当前时间前一天
	  * @param date
	  * @param format
	  * @return
	  */
	public static String getNextDay(Date date,String format) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		date = calendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
}
