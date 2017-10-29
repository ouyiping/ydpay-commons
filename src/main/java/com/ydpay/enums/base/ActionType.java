package com.ydpay.enums.base;

public enum ActionType {

	DEFAULT(1, "其他"), REG(2, "注册"), LOGIN(3, "登录");

	private final int code;
	private final String name;

	private ActionType(int code, String name) {
		this.code = code;
		this.name = name;
	}

	public int getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public static ActionType valueOfCode(int code) {
		for (ActionType type : ActionType.values()) {
			if (type.getCode() == code) {
				return type;
			}
		}
		throw new IllegalStateException("enums.type.invalidcode#" + code + "#"
				+ ActionType.class.getName());
	}
}