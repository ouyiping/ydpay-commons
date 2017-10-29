package com.ydpay.enums.base;


public enum Logintype
{
	PASSWORD(1, "密码"),
	VERIFYCODE(2, "验证码");

	private final int code;
	private final String name;

	private Logintype(int code, String name)
	{
		this.code = code;
		this.name = name;
	}

	public int getCode()
	{
		return code;
	}

	public String getName()
	{
		return name;
	}
	
	public static Logintype valueOfCode(int code)
	{
		for (Logintype type : Logintype.values())
		{
			if (type.getCode() == code)
			{
				return type;
			}
		}
		throw new IllegalStateException("enums.type.invalidcode#"+code+"#"+Logintype.class.getName());
	}
}