package com.ydpay.entity;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ResponseStruct {
	private Map<String, Object> responseMap;

	public ResponseStruct() {
		responseMap = new HashMap<String, Object>();
		responseMap.put("response", "00");
		responseMap.put("message", "");
	}

	public ResponseStruct(Map<String, Object> dataMap, String message) {
		responseMap = new HashMap<String, Object>();
		responseMap.put("response", "00");
		responseMap.put("message", message);
		responseMap.put("data", dataMap);
	}

	public ResponseStruct(String message) {
		responseMap = new HashMap<String, Object>();
		responseMap.put("response", "00");
		responseMap.put("message", message);
	}

	public ResponseStruct(String response, String message) {
		responseMap = new HashMap<String, Object>();
		responseMap.put("response", response);
		responseMap.put("message", message);
	}

	public ResponseStruct(String response, String message, Object value) {
		responseMap = new HashMap<String, Object>();
		responseMap.put("response", response);
		responseMap.put("message", message);
		responseMap.put("data", value);
	}


	public ResponseStruct(String message, Map<String, Object> map) {
		responseMap = map;
		responseMap.put("response", "00");
		responseMap.put("message", message);
	}

	public ResponseStruct setMessage(String message) {
		responseMap.put("message", message);
		return this;
	}

	public ResponseStruct setRet(String response) {
		responseMap.put("response", response);
		return this;
	}
	public ResponseStruct put(String key, Object value) {
		responseMap.put(key, value);
		return this;
	}

	public ResponseStruct put(Map<String, Object> map) {
		responseMap.putAll(map);
		return this;
	}

	public String toString(boolean isSerial) {
		Gson gson = null;
		if (isSerial)
			gson = new GsonBuilder().serializeNulls().disableHtmlEscaping()
					.setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		else
			gson = new GsonBuilder().disableHtmlEscaping()
					.setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		return gson.toJson(responseMap);
	}

	@Override
	public String toString() {
		Gson gson = new GsonBuilder().serializeNulls().disableHtmlEscaping()
				.setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		return gson.toJson(responseMap);
	}

}
