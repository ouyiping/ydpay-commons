package com.ydpay.enums.base;

public enum Certflag {

	NOT_CERTFLAG(1, "未认证"), SUCCESS_CERTFLAG(2, "认证成功"), FAILS_CERTFLAG(3,
			"认证失败");

	private final int code;
	private final String name;

	private Certflag(int code, String name) {
		this.code = code;
		this.name = name;
	}

	public int getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public static Certflag valueOfCode(int code) {
		for (Certflag type : Certflag.values()) {
			if (type.getCode() == code) {
				return type;
			}
		}
		throw new IllegalStateException("enums.type.invalidcode#" + code + "#"
				+ Certflag.class.getName());
	}
}