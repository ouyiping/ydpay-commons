package com.ydpay.utils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.alibaba.fastjson.JSONObject;

public class ObjectParser {
	static public Date toDate(Object date) throws ParseException {
		if (date == null) {
			return null;
		}
		Date result = null;
		String str = date.toString();
		String parse = str;
		parse = parse.replaceFirst("^[0-9]{4}([^0-9])", "yyyy$1");
		parse = parse.replaceFirst("^[0-9]{2}([^0-9])", "yy$1");
		parse = parse.replaceFirst("([^0-9])[0-9]{1,2}([^0-9])", "$1MM$2");
		parse = parse.replaceFirst("([^0-9])[0-9]{1,2}( ?)", "$1dd$2");
		parse = parse.replaceFirst("( )[0-9]{1,2}([^0-9])", "$1HH$2");
		parse = parse.replaceFirst("([^0-9])[0-9]{1,2}([^0-9])", "$1mm$2");
		parse = parse.replaceFirst("([^0-9])[0-9]{1,2}([^0-9]?)", "$1ss$2");

		SimpleDateFormat format = new SimpleDateFormat(parse);

		result = format.parse(str);

		return result;
	}

	static public Integer toInteger(Object data) {
		if (data == null) {
			return 0;
		}
		return JSONObject.parseObject(data.toString(), Integer.class);
	}

	static public Long toLong(Object data) {
		if (data == null) {
			return 0l;
		}
		return JSONObject.parseObject(data.toString(), Long.class);
	}

	static public Short toShort(Object data) {
		if (data == null) {
			return 0;
		}
		return JSONObject.parseObject(data.toString(), Short.class);
	}

	static public Double toDouble(Object data) {
		if (data == null) {
			return 0d;
		}
		return JSONObject.parseObject(data.toString(), Double.class);
	}

	public static String toString(Object data) {
		if (data == null) {
			return "";
		}
		return String.valueOf(data.toString());
	}

	static public BigDecimal toBigDecimal(Object data) {
		if (data == null) {
			return new BigDecimal(0);
		}
		return JSONObject.parseObject(data.toString(), BigDecimal.class);
	}

	public static Boolean toBoolean(Object data) {
		if (data == null) {
			return null;
		}
		return Boolean.valueOf(data.toString()).booleanValue();
	}
}
