package com.ydpay.provider.webinterface;

import java.util.Map;

public interface rsmd extends mginterface
{
	String doPost(Map<String,String> postdata);
	String doPostForOtherSystem(String method,String args);
}
