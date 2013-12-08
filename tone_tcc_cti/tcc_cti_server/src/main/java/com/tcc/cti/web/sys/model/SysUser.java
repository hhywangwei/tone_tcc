package com.tcc.cti.web.sys.model;

/**
 * 企业接入号信息
 * 
* <ul>
* <li>id:编号
* <li>companyid:企业ID
* <li>uname:用户登录名
* <li>name:用户真实姓名
* <li>upass:用户密码
* <li>userLimit:用户权限：1企业管理员2 企业坐席3 企业班长
* <li>field1:备用
* <li>field2:备用
* </ul>
* 
 * @author hantongshan
 */
public class SysUser {
	
    private Integer id;

    private Integer companyid;

    private String uname;

    private String name;

    private String upass;

    private Integer userLimit;

    private String field1;

    private String field2;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCompanyid() {
        return companyid;
    }

    public void setCompanyid(Integer companyid) {
        this.companyid = companyid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname == null ? null : uname.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getUpass() {
        return upass;
    }

    public void setUpass(String upass) {
        this.upass = upass == null ? null : upass.trim();
    }

    public Integer getUserLimit() {
        return userLimit;
    }

    public void setUserLimit(Integer userLimit) {
        this.userLimit = userLimit;
    }

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1 == null ? null : field1.trim();
    }

    public String getField2() {
        return field2;
    }

    public void setField2(String field2) {
        this.field2 = field2 == null ? null : field2.trim();
    }
}