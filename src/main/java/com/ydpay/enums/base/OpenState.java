package com.ydpay.enums.base;


/**
 * @author Administrator
 * 支付方式专用！
 */
public enum OpenState {

	HAVE_OPEN(1, "已开通"), 
	NOT_OPEN(2, "未开通"), 
	HAVE_APPLY(3, "已申请,待审核");
	
	private final int code;
	private final String name;

	private OpenState(int code, String name) {
		this.code = code;
		this.name = name;
	}

	public int getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public static OpenState valueOfCode(int code) {
		for (OpenState type : OpenState.values()) {
			if (type.getCode() == code) {
				return type;
			}
		}
		throw new IllegalStateException("enums.type.invalidcode#" + code + "#"
				+ OpenState.class.getName());
	}
}
