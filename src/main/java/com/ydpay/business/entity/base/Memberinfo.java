package com.ydpay.business.entity.base;

/**
*
* @Author auto_gen_by_tool
* @CreatedTime 2017-08-06 03:41:59
*/
public class Memberinfo {
     private long memberid;
     public void setMemberid(long memberid) {
          this.memberid = memberid;
     }
     public long getMemberid() {
          return memberid;
     }

     private String membername;
     public void setMembername(String membername) {
          this.membername = membername;
     }
     public String getMembername() {
          return membername;
     }

     private int memberlevel;
     public void setMemberlevel(int memberlevel) {
          this.memberlevel = memberlevel;
     }
     public int getMemberlevel() {
          return memberlevel;
     }

     private int membertype;
     public void setMembertype(int membertype) {
          this.membertype = membertype;
     }
     public int getMembertype() {
          return membertype;
     }

     private String accountname;
     public void setAccountname(String accountname) {
          this.accountname = accountname;
     }
     public String getAccountname() {
          return accountname;
     }

     private String mobilephone;
     public void setMobilephone(String mobilephone) {
          this.mobilephone = mobilephone;
     }
     public String getMobilephone() {
          return mobilephone;
     }

     private String idcardno;
     public void setIdcardno(String idcardno) {
          this.idcardno = idcardno;
     }
     public String getIdcardno() {
          return idcardno;
     }

     private String bankaccount;
     public void setBankaccount(String bankaccount) {
          this.bankaccount = bankaccount;
     }
     public String getBankaccount() {
          return bankaccount;
     }

     private String bankname;
     public void setBankname(String bankname) {
          this.bankname = bankname;
     }
     public String getBankname() {
          return bankname;
     }

     private String bankcode;
     public void setBankcode(String bankcode) {
          this.bankcode = bankcode;
     }
     public String getBankcode() {
          return bankcode;
     }

     private int accounttype;
     public void setAccounttype(int accounttype) {
          this.accounttype = accounttype;
     }
     public int getAccounttype() {
          return accounttype;
     }

     private int bankcardtype;
     public void setBankcardtype(int bankcardtype) {
          this.bankcardtype = bankcardtype;
     }
     public int getBankcardtype() {
          return bankcardtype;
     }

     private int certflag;
     public void setCertflag(int certflag) {
          this.certflag = certflag;
     }
     public int getCertflag() {
          return certflag;
     }

     private String loginpwd;
     public void setLoginpwd(String loginpwd) {
          this.loginpwd = loginpwd;
     }
     public String getLoginpwd() {
          return loginpwd;
     }

     private String pwdkey;
     public void setPwdkey(String pwdkey) {
          this.pwdkey = pwdkey;
     }
     public String getPwdkey() {
          return pwdkey;
     }

     private String remark;
     public void setRemark(String remark) {
          this.remark = remark;
     }
     public String getRemark() {
          return remark;
     }

     private int enableflag;
     public void setEnableflag(int enableflag) {
          this.enableflag = enableflag;
     }
     public int getEnableflag() {
          return enableflag;
     }

     private long createdby;
     public void setCreatedby(long createdby) {
          this.createdby = createdby;
     }
     public long getCreatedby() {
          return createdby;
     }

     private String createdbyname;
     public void setCreatedbyname(String createdbyname) {
          this.createdbyname = createdbyname;
     }
     public String getCreatedbyname() {
          return createdbyname;
     }

     private String createdtime;
     public void setCreatedtime(String createdtime) {
          this.createdtime = createdtime;
     }
     public String getCreatedtime() {
          return createdtime;
     }

     private String begincreatedtime;
     public void setBegincreatedtime(String begincreatedtime) {
          this.begincreatedtime = begincreatedtime;
     }
     public String getBegincreatedtime() {
          return begincreatedtime;
     }

     private String endcreatedtime;
     public void setEndcreatedtime(String endcreatedtime) {
          this.endcreatedtime = endcreatedtime;
     }
     public String getEndcreatedtime() {
          return endcreatedtime;
     }

     private long updateby;
     public void setUpdateby(long updateby) {
          this.updateby = updateby;
     }
     public long getUpdateby() {
          return updateby;
     }

     private String updatebyname;
     public void setUpdatebyname(String updatebyname) {
          this.updatebyname = updatebyname;
     }
     public String getUpdatebyname() {
          return updatebyname;
     }

     private String updatetime;
     public void setUpdatetime(String updatetime) {
          this.updatetime = updatetime;
     }
     public String getUpdatetime() {
          return updatetime;
     }

     private String beginupdatetime;
     public void setBeginupdatetime(String beginupdatetime) {
          this.beginupdatetime = beginupdatetime;
     }
     public String getBeginupdatetime() {
          return beginupdatetime;
     }

     private String endupdatetime;
     public void setEndupdatetime(String endupdatetime) {
          this.endupdatetime = endupdatetime;
     }
     public String getEndupdatetime() {
          return endupdatetime;
     }

     private int pagesize;
     public void setPagesize(int pagesize) {
          this.pagesize = pagesize;
     }
     public int getPagesize() {
          return pagesize;
     }

     private int pagenum;
     public void setPagenum(int pagenum) {
          this.pagenum = pagenum;
     }
     public int getPagenum() {
          return pagenum;
     }

     private long pmemberid;
	public long getPmemberid() {
		return pmemberid;
	}
	public void setPmemberid(long pmemberid) {
		this.pmemberid = pmemberid;
	}
	
	private String pmembername;
	public String getPmembername() {
		return pmembername;
	}
	public void setPmembername(String pmembername) {
		this.pmembername = pmembername;
	}
	
	private int auditflag;
	public int getAuditflag() {
		return auditflag;
	}
	public void setAuditflag(int auditflag) {
		this.auditflag = auditflag;
	}
	  private long ratepackageconfigid;
	  private String ratepackagecode;
	public long getRatepackageconfigid() {
		return ratepackageconfigid;
	}
	public void setRatepackageconfigid(long ratepackageconfigid) {
		this.ratepackageconfigid = ratepackageconfigid;
	}
	public String getRatepackagecode() {
		return ratepackagecode;
	}
	public void setRatepackagecode(String ratepackagecode) {
		this.ratepackagecode = ratepackagecode;
	}
	 private long agentstaffid;
	public long getAgentstaffid() {
		return agentstaffid;
	}
	public void setAgentstaffid(long agentstaffid) {
		this.agentstaffid = agentstaffid;
	}
	private String agentstaffname;
	public String getAgentstaffname() {
		return agentstaffname;
	}
	public void setAgentstaffname(String agentstaffname) {
		this.agentstaffname = agentstaffname;
	}
    private String pmobilephone;
	public String getPmobilephone() {
		return pmobilephone;
	}
	public void setPmobilephone(String pmobilephone) {
		this.pmobilephone = pmobilephone;
	}
	 
	  
}