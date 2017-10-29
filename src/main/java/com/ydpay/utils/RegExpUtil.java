package com.ydpay.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 各种正则表达式工具类
 */
public class RegExpUtil
{
	
	/**
	 * 邮箱格式验证
	 * @param email
	 * @return true是正确格式，false就是不正确格式
	 */
	public static boolean checkEmail(String email)
	{
		String check = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";    
		Pattern regex = Pattern.compile(check);
		Matcher matcher = regex.matcher(email);
		if(matcher.matches())
		{
			return true;
		}
		return false;
	}
	
	
	/**
	 * 手机号码验证
	 * @param mobilephone
	 * @return true是正确格式，false就是不正确格式
	 */
	public static boolean checkMobilephone(String mobilephone)
	{
		String check = "^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|17[0-9]|18[0-9])\\d{8}$";    
		Pattern regex = Pattern.compile(check);
		Matcher matcher = regex.matcher(mobilephone);
		if(matcher.matches())
		{
			return true;
		}
		return false;
	}
	
	
	
}
