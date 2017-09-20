package com.warchm.modules.sys.entity;

import java.io.Serializable;
import java.util.Date;

public class UserInformation implements Serializable {
    private Integer userId;

    private String userName;

    private String mobileNumber;

    private String email;

    private String password;

    private String realName;

    private String company;

    private String title;

    private Integer birthYear;

    private String major1;

    private String major2;

    private String major3;

    private Date registerDate;

    private String activationCode;

    private Integer status;

    private Integer roleId;

    private Integer publicInformation;

    private String welcomeMessage;

    private static final long serialVersionUID = 1L;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber == null ? null : mobileNumber.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company == null ? null : company.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public String getMajor1() {
        return major1;
    }

    public void setMajor1(String major1) {
        this.major1 = major1 == null ? null : major1.trim();
    }

    public String getMajor2() {
        return major2;
    }

    public void setMajor2(String major2) {
        this.major2 = major2 == null ? null : major2.trim();
    }

    public String getMajor3() {
        return major3;
    }

    public void setMajor3(String major3) {
        this.major3 = major3 == null ? null : major3.trim();
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode == null ? null : activationCode.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getPublicInformation() {
        return publicInformation;
    }

    public void setPublicInformation(Integer publicInformation) {
        this.publicInformation = publicInformation;
    }

    public String getWelcomeMessage() {
        return welcomeMessage;
    }

    public void setWelcomeMessage(String welcomeMessage) {
        this.welcomeMessage = welcomeMessage == null ? null : welcomeMessage.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", userId=").append(userId);
        sb.append(", userName=").append(userName);
        sb.append(", mobileNumber=").append(mobileNumber);
        sb.append(", email=").append(email);
        sb.append(", password=").append(password);
        sb.append(", realName=").append(realName);
        sb.append(", company=").append(company);
        sb.append(", title=").append(title);
        sb.append(", birthYear=").append(birthYear);
        sb.append(", major1=").append(major1);
        sb.append(", major2=").append(major2);
        sb.append(", major3=").append(major3);
        sb.append(", registerDate=").append(registerDate);
        sb.append(", activationCode=").append(activationCode);
        sb.append(", status=").append(status);
        sb.append(", roleId=").append(roleId);
        sb.append(", publicInformation=").append(publicInformation);
        sb.append(", welcomeMessage=").append(welcomeMessage);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}