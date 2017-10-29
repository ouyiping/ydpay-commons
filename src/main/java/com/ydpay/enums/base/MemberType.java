package com.ydpay.enums.base;

public enum MemberType {

	PLATFORM(1, "平台"), AGENT(2, "代理"), MERCHANT(3, "商户"),SALESMAN(4,"业务员");

	private final int code;
	private final String name;

	private MemberType(int code, String name) {
		this.code = code;
		this.name = name;
	}

	public int getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public static MemberType valueOfCode(int code) {
		for (MemberType type : MemberType.values()) {
			if (type.getCode() == code) {
				return type;
			}
		}
		throw new IllegalStateException("enums.type.invalidcode#" + code + "#"
				+ MemberType.class.getName());
	}
}