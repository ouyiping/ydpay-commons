package com.ydpay.enums.base;

import java.util.HashMap;
import java.util.Map;

public enum PaymentType {

	NONE(0, "未知"), CASH_PAY(1, "现金"), POS_PAY(2, "POS刷卡"), ONLINE_PAY(4,
			"银联在线(B2C)"), COLLECT(7, "对私代扣"), ALIPAY(12, "支付宝"), WEIXIN_PAY(13,
			"微信"), MPOS_PAY(14, "智能POS刷卡"), ONLINE_PAY_B2B(15, "银联在线(B2B)"), ALIPAY_BARCODE_PAY(
			16, "支付宝条码"), WEIXIN_BARCODE_PAY(17, "微信条码"), COMPANY_WALLET_PAY(
			18, "企业账户"), MASGET_WALLET_PAY(19, "我的钱包"), ALIPAY_WINDOW_PAY(20,
			"支付宝服务窗支付"), WEIXIN_WINDOW_PAY(21, "微信公众号支付"), MASGET_QRCODE(22,
			"银联二维码"), LOANPAY_WALLET_PAY(23, "贷款支付"), COLLECT_ACCOUNTTYPE_0(24,
			"对公代扣"), QUICK_PAY(25, "快捷支付"), WEIXIN_ALIPAY_PAY(26, "微信支付宝统一扫码"), WEIXIN_APP_PAY(
			27, "微信APP支付"), WEIXIN_ALIPAY_BARCODEPAY(28, "微信支付宝统一条码"), GATEWAY_PAY(
			29, "网关支付"), UNION_PAY(30, "银联二维码支付"), QQ_PAY(31, "qq钱包扫码支付"), QQ_BARCODE_PAY(
			32, "qq钱包条码支付"), QQ_WINDOW_PAY(33, "qq钱包窗口支付"), UNION_BARCODE_PAY(
			34, "银联二维码条码支付"), PHONE_PAY(35, "手机控件支付");

	private final int code;
	private final String name;

	private PaymentType(int code, String name) {
		this.code = code;
		this.name = name;
	}

	public int getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public static PaymentType valueOfCode(int code) {
		for (PaymentType type : PaymentType.values()) {
			if (type.getCode() == code) {
				return type;
			}
		}
		return NONE;
	}

	public static String paymentOfCode(int code) {
		for (PaymentType type : PaymentType.values()) {
			if (type.getCode() == code) {
				return type.getName();
			}
		}
		return NONE.getName();
	}

	public static Map<Integer, Object> getMap() {
		Map<Integer, Object> map = new HashMap<Integer, Object>();
		for (PaymentType type : PaymentType.values()) {
			map.put(type.getCode(), type.getName());
		}
		return map;
	}
}
