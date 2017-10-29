package com.ydpay.utils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.stream.JsonReader;
import com.ydpay.utils.ObjectParser;

//singleton class

/*
 * validate json example
 * {
 *  get: {							//第一层 key:接口名称，value: 接口需要传入的json参数格式对象
 * 	 companyid: "Long",				//key:字段名，value:类型名称
 *   consignnotenum(option): "String",	//(option)表示此字段为可选字段
 *   amount: "Double",
 *   state: "Short",
 *   flag: "Integer",
 *   createdtime: "Date",
 *   array: "String[]",				//在类型后再加上"[]"表示传进的"array"为数组
 *   consignnotedetail:{
 *    ...
 *   },
 *   charge:[
 *   	{...},
 *   	{...}
 *   ]
 *  } 				
 * }
 */
public class DataValidator {
	private Map<String, JsonObject> validateMap;
	private Gson gson;

	public DataValidator() {
		validateMap = new HashMap<String, JsonObject>();
		gson = new GsonBuilder().serializeNulls()
				.setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	}

	public String getValidData(Object validateObject, String methodName,
			String data) throws Exception {
		// 找校验文件
		JsonObject classJson = getValidateMap(validateObject);
		if (classJson == null || !classJson.has(methodName)) {
			// 没有校验文件，不需要校验
			return data;
		}
		JsonElement methodJson = classJson.get(methodName);

		if (data.isEmpty()
				&& (methodJson.isJsonNull()
						|| methodJson.toString().equals("[]") || methodJson
						.toString().equals("{}"))) {
			return data;
		}

		if (!methodJson.isJsonArray() && !methodJson.isJsonObject()) {
			String value = methodJson.getAsString().toLowerCase();
			String valueName = value.replace("[]", "");

			if (!value.equals(valueName)) {
				JsonElement dataElement = gson
						.fromJson(data, JsonElement.class);
				if (!dataElement.isJsonArray()) {
					throw new Exception(gson.toJson(dataElement) + "不是数组");
				}
				JsonArray validArray = new JsonArray();
				JsonArray dataArray = dataElement.getAsJsonArray();

				for (JsonElement e : dataArray) {
					validArray.add(validateElementType(e, valueName));
				}

				return gson.toJson(validArray);
			} else {
				return gson.toJson(validateElementType(new JsonPrimitive(data),
						valueName));
			}
		}

		JsonElement dataJson = gson.fromJson(data, JsonElement.class);

		JsonElement result = getValidJson(methodJson, dataJson);

		return gson.toJson(result);
	}

	private JsonElement getValidJson(JsonElement validateJson,
			JsonElement dataJson) throws Exception {
		if (dataJson.isJsonArray() && validateJson.isJsonArray()) {
			JsonArray result = new JsonArray();

			JsonArray dataArray = dataJson.getAsJsonArray();
			JsonElement validateElement = validateJson.getAsJsonArray().get(0);

			for (int i = 0; i < dataArray.size(); ++i) {
				JsonElement e = dataArray.get(i);
				JsonElement r = getValidJson(validateElement, e);
				result.add(r);
			}

			return result;
		} else if (dataJson.isJsonObject() && validateJson.isJsonObject()) {
			JsonObject result = new JsonObject();

			JsonObject dataObject = dataJson.getAsJsonObject();
			JsonObject validateObject = validateJson.getAsJsonObject();

			for (Entry<String, JsonElement> entry : validateObject.entrySet()) {
				String key = entry.getKey();

				String keyName = key.replace("(option)", "");

				if (!dataObject.has(keyName)) {
					if (keyName.equals(key)) {
						throw new Exception("找不到参数" + keyName);
					} else {
						// 可选字段
						continue;
					}
				}

				if (entry.getValue().isJsonArray()
						|| entry.getValue().isJsonObject()) {
					JsonElement r = getValidJson(entry.getValue(),
							dataObject.get(keyName));
					result.add(keyName, r);
					continue;
				}

				String value = entry.getValue().getAsString().toLowerCase();
				String valueName = value.replace("[]", "");

				JsonElement field = dataObject.get(keyName);

				if (!value.equals(valueName)) {
					JsonArray validArray = new JsonArray();

					JsonArray array = field.getAsJsonArray();
					for (JsonElement e : array) {
						validArray.add(validateElementType(e, valueName));
					}

					result.add(keyName, validArray);
				} else {
					result.add(keyName, validateElementType(field, valueName));
				}
			}

			return result;
		} else {
			throw new Exception("数据格式有误" + gson.toJson(dataJson));
		}
	}

	private JsonElement validateElementType(JsonElement param, String type)
			throws Exception {
		if (type.equals("long")) {
			if (param.isJsonNull()) {
				return param;
			}
			return new JsonPrimitive(param.getAsLong());
		} else if (type.equals("int") || type.equals("integer")) {
			if (param.isJsonNull()) {
				return param;
			}
			return new JsonPrimitive(param.getAsInt());
		} else if (type.equals("double")) {
			if (param.isJsonNull()) {
				return param;
			}
			return new JsonPrimitive(param.getAsDouble());
		} else if (type.equals("date")) {
			if (param.isJsonNull()) {
				return param;
			}
			if (ObjectParser.toDate(param.getAsString()) != null) {
				return new JsonPrimitive(param.getAsString());
			} else {
				throw new Exception("日期类型错误");
			}
		} else if (type.equals("short")) {
			if (param.isJsonNull()) {
				return param;
			}
			return new JsonPrimitive(param.getAsShort());
		} else {
			if (param.isJsonNull()) {
				return param;
			}
			return new JsonPrimitive(param.getAsString());
		}
	}

	public JsonObject getValidateMap(Object validateObject) throws Exception {
		// validateObject:bean class
		String className = null;
		if (validateObject.getClass().getSuperclass() == Object.class) {
			className = validateObject.getClass().getSimpleName();
		} else {
			className = validateObject.getClass().getSuperclass()
					.getSimpleName();
		}

		if (validateMap.containsKey(className)) {
			return validateMap.get(className);
		}

		JsonObject result = null;
		InputStream filestream = null;
		filestream = validateObject.getClass().getResourceAsStream(
				className + ".json");
		if (filestream == null) {
			// 找不到校验文件, 不需要校验
			validateMap.put(className, result);
			return result;
		}
		InputStreamReader reader = new InputStreamReader(filestream);
		result = ((JsonElement) gson.fromJson(new JsonReader(reader),
				JsonElement.class)).getAsJsonObject();
		filestream.close();
		reader.close();

		validateMap.put(className, result);

		return result;
	}
}
