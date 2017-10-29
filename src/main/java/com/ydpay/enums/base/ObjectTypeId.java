package com.ydpay.enums.base;

public enum ObjectTypeId {

	MEMBER(1, "会员"), COMPANY(2, "商户"), COMPANY_STAFF(3, "员工");

	private final int code;
	private final String name;

	private ObjectTypeId(int code, String name) {
		this.code = code;
		this.name = name;
	}

	public int getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public static ObjectTypeId valueOfCode(int code) {
		for (ObjectTypeId type : ObjectTypeId.values()) {
			if (type.getCode() == code) {
				return type;
			}
		}
		throw new IllegalStateException("enums.type.invalidcode#" + code + "#"
				+ ObjectTypeId.class.getName());
	}
}
