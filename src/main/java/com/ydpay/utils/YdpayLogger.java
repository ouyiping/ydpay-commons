package com.ydpay.utils;

import org.apache.log4j.Logger;

public class YdpayLogger {
	public static Logger logger = Logger.getLogger(YdpayLogger.class);

	public static void info(String message){
		info(message, null);
	}
	
	public static void info(String message, Throwable e){
		logger.info(message, e);
	}

	public static void error(String message){
		error(message, null);
	}
	
	public static void error(String message, Throwable e){
		logger.error(message, e);
	}
	
	public static void debug(String message){
		debug(message, null);
	}
	
	public static void debug(String message, Throwable e){
		logger.debug(message, e);
	}
}
