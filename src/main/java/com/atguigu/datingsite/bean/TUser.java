package com.atguigu.datingsite.bean;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="t_user")
public class TUser {

    @Id //声明主键是userId
    @GeneratedValue(strategy= GenerationType.IDENTITY) //主键自增策略
    private Integer userId;
    private String userName;
    private String userPwd;
    private String nickName;
    private String gender;
    private String job;
    private String hometown;
    private String userDescribe;
    private String pictureGroupName;
    private String pictureRemoteName;


    public TUser() {
    }

    public TUser(String userName, String userPwd, String nickName, String gender, String job, String hometown, String userDescribe, String pictureGroupName, String pictureRemoteName) {
        this.userName = userName;
        this.userPwd = userPwd;
        this.nickName = nickName;
        this.gender = gender;
        this.job = job;
        this.hometown = hometown;
        this.userDescribe = userDescribe;
        this.pictureGroupName = pictureGroupName;
        this.pictureRemoteName = pictureRemoteName;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public String getNickName() {
        return nickName;
    }

    public String getGender() {
        return gender;
    }

    public String getJob() {
        return job;
    }

    public String getHometown() {
        return hometown;
    }

    public String getUserDescribe() {
        return userDescribe;
    }

    public String getPictureGroupName() {
        return pictureGroupName;
    }

    public String getPictureRemoteName() {
        return pictureRemoteName;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public void setUserDescribe(String userDescribe) {
        this.userDescribe = userDescribe;
    }

    public void setPictureGroupName(String pictureGroupName) {
        this.pictureGroupName = pictureGroupName;
    }

    public void setPictureRemoteName(String pictureRemoteName) {
        this.pictureRemoteName = pictureRemoteName;
    }

    @Override
    public String toString() {
        return "TUser{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userPwd='" + userPwd + '\'' +
                ", nickName='" + nickName + '\'' +
                ", gender='" + gender + '\'' +
                ", job='" + job + '\'' +
                ", hometown='" + hometown + '\'' +
                ", userDescribe='" + userDescribe + '\'' +
                ", pictureGroupName='" + pictureGroupName + '\'' +
                ", pictureRemoteName='" + pictureRemoteName + '\'' +
                '}';
    }
}
