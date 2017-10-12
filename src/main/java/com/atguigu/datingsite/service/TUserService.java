package com.atguigu.datingsite.service;

import com.atguigu.datingsite.bean.TUser;

import java.util.List;

public interface TUserService {

    public List<TUser> listAllUsers();

    boolean saveUser(TUser tUser);

    List<TUser> getUserByUserName(String userName);

    TUser getUserByUserId(Integer userId);

    boolean updateUser(TUser tUser);

    TUser getUserByUserNameAndPwd(TUser tUser);
}
