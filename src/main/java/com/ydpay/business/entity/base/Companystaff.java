package com.ydpay.business.entity.base;

/**
*
* @Author auto_gen_by_tool
* @CreatedTime 2017-08-06 03:41:59
*/
public class Companystaff {
	private long staffid;
	public void setStaffid(long staffid) {
        this.staffid = staffid;
   }
   public long getStaffid() {
        return staffid;
   }

   private String staffname;
   public void setStaffname(String staffname) {
        this.staffname = staffname;
   }
   public String getStaffname() {
        return staffname;
   }

   private long companyid;
   public void setCompanyid(long companyid) {
        this.companyid = companyid;
   }
   public long getCompanyid() {
        return companyid;
   }

   private String companyname;
   public void setCompanyname(String companyname) {
        this.companyname = companyname;
   }
   public String getCompanyname() {
        return companyname;
   }

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

   private int stafftype;
   public void setStafftype(int stafftype) {
        this.stafftype = stafftype;
   }
   public int getStafftype() {
        return stafftype;
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

   private long roleresourceid;
   public void setRoleresourceid(long roleresourceid) {
        this.roleresourceid = roleresourceid;
   }
   public long getRoleresourceid() {
        return roleresourceid;
   }

   private long agentstaffid;
   public void setAgentstaffid(long agentstaffid) {
        this.agentstaffid = agentstaffid;
   }
   public long getAgentstaffid() {
        return agentstaffid;
   }

   private String agentstaffname;
   public void setAgentstaffname(String agentstaffname) {
        this.agentstaffname = agentstaffname;
   }
   public String getAgentstaffname() {
        return agentstaffname;
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
  public long getOwnmemberid() {
	return ownmemberid;
}
public void setOwnmemberid(long ownmemberid) {
	this.ownmemberid = ownmemberid;
}
public String getOwnmembername() {
	return ownmembername;
}
public void setOwnmembername(String ownmembername) {
	this.ownmembername = ownmembername;
}

private long ownmemberid;
  private String ownmembername;


}