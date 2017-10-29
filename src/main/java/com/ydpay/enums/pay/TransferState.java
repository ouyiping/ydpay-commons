package com.ydpay.enums.pay;


/**
 * @author Administrator
 * 划帐专用！
 */
public enum TransferState {

	NOT_TRANSFER(1, "未划账"), 
	SUCCESS_TRANSFER(4, "成功"),
	FAIL_TRANSFER(5, "失败"), 
	NONE(99, "状态未知");
	
	private final int code;
	private final String name;

	private TransferState(int code, String name) {
		this.code = code;
		this.name = name;
	}

	public int getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public static TransferState valueOfCode(int code) {
		for (TransferState type : TransferState.values()) {
			if (type.getCode() == code) {
				return type;
			}
		}
		throw new IllegalStateException("enums.type.invalidcode#" + code + "#"
				+ TransferState.class.getName());
	}
}
