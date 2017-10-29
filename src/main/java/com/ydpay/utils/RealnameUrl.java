package com.ydpay.utils;

import java.util.UUID;

import com.alibaba.fastjson.JSONObject;

public class RealnameUrl {

//	{"accountname":"朱翠平","bankcardtype":1,"mobilephone":"13706505157","bankaccount":"6222031202000648237",
	//"accounttype":1,"ordernumber":"1503026277713QCVM","certificateno":"342222198707010540"}

	public static JSONObject realnameAuthentication(String accountname, String mobilephone, String bankaccount, String idcardno){
		JSONObject obj = new JSONObject();
		obj.put("accountname", accountname);
		obj.put("mobilephone", mobilephone);
		obj.put("bankaccount", bankaccount);
		obj.put("certificateno", idcardno);
		obj.put("bankcardtype", 1);
		obj.put("accounttype", 1);
		obj.put("ordernumber", UUID.randomUUID().toString());
		String result = MasgetClientManager.gateway(
				"masget.trustfunds.com.authentication.realname", obj.toJSONString());
		JSONObject resultObj = JSONObject.parseObject(result);
		return resultObj;
	}
}
