package com.ydpay.global;

import java.util.List;

import com.ydpay.business.entity.base.Companyinfo;
import com.ydpay.business.entity.base.Companystaff;
import com.ydpay.business.entity.base.Memberinfo;
import com.ydpay.business.entity.base.Membermerchant;

public class tf_session {
	private String appid;
	private String appname;
	private String mobilephone;
	private String logintime;
	private String lastusetime;
	private int devicetype;
	private String session;
	private String secretkey;
	private int persisted;

	private long memberid;
	private String membername;
	private int membertype;
	private long staffid;
	private String staffname;
	private int stafftype;
	private int memberlevel;
	private long agentstaffid;
	private String agentstaffname;

	private Memberinfo memberinfo;
	private Companyinfo companyinfo;
	private Membermerchant membermerchant;
	private List<Companystaff> companystaffs;
	private long roleresourceid;

	public long getRoleresourceid() {
		return roleresourceid;
	}

	public void setRoleresourceid(long roleresourceid) {
		this.roleresourceid = roleresourceid;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getAppname() {
		return appname;
	}

	public void setAppname(String appname) {
		this.appname = appname;
	}

	public String getMobilephone() {
		return mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	public String getLogintime() {
		return logintime;
	}

	public void setLogintime(String logintime) {
		this.logintime = logintime;
	}

	public String getLastusetime() {
		return lastusetime;
	}

	public void setLastusetime(String lastusetime) {
		this.lastusetime = lastusetime;
	}

	public int getDevicetype() {
		return devicetype;
	}

	public void setDevicetype(int devicetype) {
		this.devicetype = devicetype;
	}

	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
	}

	public String getSecretkey() {
		return secretkey;
	}

	public void setSecretkey(String secretkey) {
		this.secretkey = secretkey;
	}

	public int getPersisted() {
		return persisted;
	}

	public void setPersisted(int persisted) {
		this.persisted = persisted;
	}

	public long getMemberid() {
		return memberid;
	}

	public void setMemberid(long memberid) {
		this.memberid = memberid;
	}

	public String getMembername() {
		return membername;
	}

	public void setMembername(String membername) {
		this.membername = membername;
	}

	public long getStaffid() {
		return staffid;
	}

	public void setStaffid(long staffid) {
		this.staffid = staffid;
	}

	public String getStaffname() {
		return staffname;
	}

	public void setStaffname(String staffname) {
		this.staffname = staffname;
	}

	public Memberinfo getMemberinfo() {
		return memberinfo;
	}

	public void setMemberinfo(Memberinfo memberinfo) {
		this.memberinfo = memberinfo;
	}

	public Companyinfo getCompanyinfo() {
		return companyinfo;
	}

	public void setCompanyinfo(Companyinfo companyinfo) {
		this.companyinfo = companyinfo;
	}

	public Membermerchant getMembermerchant() {
		return membermerchant;
	}

	public void setMembermerchant(Membermerchant membermerchant) {
		this.membermerchant = membermerchant;
	}

	public List<Companystaff> getCompanystaffs() {
		return companystaffs;
	}

	public void setCompanystaffs(List<Companystaff> companystaffs) {
		this.companystaffs = companystaffs;
	}

	public int getStafftype() {
		return stafftype;
	}

	public void setStafftype(int stafftype) {
		this.stafftype = stafftype;
	}

	public int getMembertype() {
		return membertype;
	}

	public void setMembertype(int membertype) {
		this.membertype = membertype;
	}

	private int certflag;

	public int getCertflag() {
		return certflag;
	}

	public void setCertflag(int certflag) {
		this.certflag = certflag;
	}

	public long getAgentstaffid() {
		return agentstaffid;
	}

	public void setAgentstaffid(long agentstaffid) {
		this.agentstaffid = agentstaffid;
	}

	public String getAgentstaffname() {
		return agentstaffname;
	}

	public void setAgentstaffname(String agentstaffname) {
		this.agentstaffname = agentstaffname;
	}

	public int getMemberlevel() {
		return memberlevel;
	}

	public void setMemberlevel(int memberlevel) {
		this.memberlevel = memberlevel;
	}
	

}
