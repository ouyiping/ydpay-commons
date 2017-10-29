package com.ydpay.enums.base;


/**
 * @author Administrator
 * 广告专用！
 */
public enum AdvState {
	HAVE_APPLY(1, "待审核"),
	ON_ADV(2, "已审核(上架)"), 
	NOT_OPEN(3, "不能使用"), 
	OFF_ADV(4, "下架");
	
	private final int code;
	private final String name;

	private AdvState(int code, String name) {
		this.code = code;
		this.name = name;
	}

	public int getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public static AdvState valueOfCode(int code) {
		for (AdvState type : AdvState.values()) {
			if (type.getCode() == code) {
				return type;
			}
		}
		throw new IllegalStateException("enums.type.invalidcode#" + code + "#"
				+ AdvState.class.getName());
	}
}
