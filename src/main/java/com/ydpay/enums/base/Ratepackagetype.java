package com.ydpay.enums.base;

public enum Ratepackagetype {

	MERCHANT(1, "商户费率套餐"), AGENT(2, "代理费率套餐");

	private final int code;
	private final String name;

	private Ratepackagetype(int code, String name) {
		this.code = code;
		this.name = name;
	}

	public int getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public static Ratepackagetype valueOfCode(int code) {
		for (Ratepackagetype type : Ratepackagetype.values()) {
			if (type.getCode() == code) {
				return type;
			}
		}
		throw new IllegalStateException("enums.type.invalidcode#" + code + "#"
				+ Ratepackagetype.class.getName());
	}
}