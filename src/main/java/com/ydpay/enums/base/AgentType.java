package com.ydpay.enums.base;

public enum AgentType {

	COMPANY(3, "商户"), AGENT(2, "代理");

	private final int code;
	private final String name;

	private AgentType(int code, String name) {
		this.code = code;
		this.name = name;
	}

	public int getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public static AgentType valueOfCode(int code) {
		for (AgentType type : AgentType.values()) {
			if (type.getCode() == code) {
				return type;
			}
		}
		throw new IllegalStateException("enums.type.invalidcode#" + code + "#"
				+ AgentType.class.getName());
	}
}