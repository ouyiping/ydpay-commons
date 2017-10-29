package com.ydpay.enums.base;

public enum StaffType {

	OWNER(1, "店长"), MANAGER(2, "主管"), STAFF(3, "业务员/收银员");

	private final int code;
	private final String name;

	private StaffType(int code, String name) {
		this.code = code;
		this.name = name;
	}

	public int getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public static StaffType valueOfCode(int code) {
		for (StaffType type : StaffType.values()) {
			if (type.getCode() == code) {
				return type;
			}
		}
		throw new IllegalStateException("enums.type.invalidcode#" + code + "#"
				+ StaffType.class.getName());
	}
}
