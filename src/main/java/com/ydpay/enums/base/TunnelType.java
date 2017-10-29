package com.ydpay.enums.base;

public enum TunnelType {

	XINFU(1, "大额封顶"), SFT(2, "快捷无积分"), KJT(3, "快捷有积分");

	private final int code;
	private final String name;

	private TunnelType(int code, String name) {
		this.code = code;
		this.name = name;
	}

	public int getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public static TunnelType valueOfCode(int code) {
		for (TunnelType type : TunnelType.values()) {
			if (type.getCode() == code) {
				return type;
			}
		}
		throw new IllegalStateException("enums.type.invalidcode#" + code + "#"
				+ TunnelType.class.getName());
	}
}
