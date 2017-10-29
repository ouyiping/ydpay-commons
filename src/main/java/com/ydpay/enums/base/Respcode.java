package com.ydpay.enums.base;

public enum Respcode {

	NOT_PAY(1, "未支付"), SUCCESS_PAY(2, "支付成功");

	private final int code;
	private final String name;

	private Respcode(int code, String name) {
		this.code = code;
		this.name = name;
	}

	public int getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public static Respcode valueOfCode(int code) {
		for (Respcode type : Respcode.values()) {
			if (type.getCode() == code) {
				return type;
			}
		}
		throw new IllegalStateException("enums.type.invalidcode#" + code + "#"
				+ Respcode.class.getName());
	}
}