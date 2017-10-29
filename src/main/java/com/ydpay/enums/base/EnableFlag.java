package com.ydpay.enums.base;

import java.util.HashMap;
import java.util.Map;


/**
 * 
* @ClassName:EnableFlag.java
* @author:  Zhanghaifeng
* @date:    2016年1月21日
 */
public enum EnableFlag {
	
	ENABLE(1,"可用"),
	DISABLE(2,"不可用"),
	WAIT_FOR_AUDIT(3,"待审核");
	
	private final int id;
	private final String name;
	
	private EnableFlag(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getid() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public static String getEnableFlagName(int id){
		String name = "未知";
		for(EnableFlag enableFlag : EnableFlag.values()){
			if(enableFlag.getid() == id){
				name = enableFlag.getName();
				break;
			}
		}
		return name;
	}

	public static Map<Integer, Object> getMap() {
		Map<Integer, Object> map = new HashMap<Integer, Object>();
		for (EnableFlag type : EnableFlag.values()) {
			map.put(type.getid(), type.getName());
		}
		return map;
	}
}
