package com.ydpay.enums.base;


public enum DeviceType
{
	
	PC(1, "PC"),
	PHONE_ANDROID(2, "Phone_android"),
	PHONE_IOS(3, "phone_ios");

	private final int code;
	private final String name;

	private DeviceType(int code, String name)
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
	
	public static DeviceType valueOfCode(int code)
	{
		for (DeviceType type : DeviceType.values())
		{
			if (type.getCode() == code)
			{
				return type;
			}
		}
		throw new IllegalStateException("enums.type.invalidcode#"+code+"#"+DeviceType.class.getName());
	}
}