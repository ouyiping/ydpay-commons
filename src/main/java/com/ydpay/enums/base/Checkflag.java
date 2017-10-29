package com.ydpay.enums.base;

/**
 * @author Administrator
 * 对帐专用！
 */
public enum Checkflag {

	NOT_CHECK(1, "未对账"), 
	HAVE_CHECK(2, "已对账");
	
	private final int code;
	private final String name;

	private Checkflag(int code, String name) {
		this.code = code;
		this.name = name;
	}

	public int getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public static Checkflag valueOfCode(int code) {
		for (Checkflag type : Checkflag.values()) {
			if (type.getCode() == code) {
				return type;
			}
		}
		return NOT_CHECK;
	}
}
