package com.ydpay.enums.base;


/**
 * @author Administrator
 * 模板专用！
 */
public enum ColumnType {

	STRING(1, "其它及字符"), 
	INTEGER(2, "数字"), 
	LONG(3, "long"), 
	TIME(4, "时间"), 
	DOUBLE(5, "小数"), 
	DATE(6, "日期");

	private final int code;
	private final String name;

	private ColumnType(int code, String name) {
		this.code = code;
		this.name = name;
	}

	public int getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public static ColumnType valueOfCode(int code) {
		for (ColumnType type : ColumnType.values()) {
			if (type.getCode() == code) {
				return type;
			}
		}
		throw new IllegalStateException("enums.type.invalidcode#" + code + "#"
				+ ColumnType.class.getName());
	}
}