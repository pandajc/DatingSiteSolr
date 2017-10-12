package com.atguigu.datingsite.service.impl;

import com.atguigu.datingsite.bean.TUser;
import com.atguigu.datingsite.dao.TUserMapper;
import com.atguigu.datingsite.service.TUserService;
import com.atguigu.datingsite.util.SolrUtil;
import com.github.abel533.entity.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TUserServiceImpl implements TUserService{

    @Autowired
    TUserMapper tUserMapper;

    @Override
    public List<TUser> listAllUsers(){
        return tUserMapper.select(new TUser());
    }

    @Override
    public boolean saveUser(TUser tUser) {
        boolean result = tUserMapper.insertSelective(tUser) == 1;
        if (result){
            SolrUtil.addUserDocument(tUser);
        }
        return result;
    }

    @Override
    public List<TUser> getUserByUserName(String userName) {
        Example example = new Example(TUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userName", userName);
        return tUserMapper.selectByExample(example);
    }

    @Override
    public TUser getUserByUserId(Integer userId) {
        return tUserMapper.selectByPrimaryKey(userId);
    }

    @Override
    public boolean updateUser(TUser tUser) {
        boolean result = tUserMapper.updateByPrimaryKeySelective(tUser) == 1;
        if (result){
            SolrUtil.updateUserDocument(tUser);
        }
        return result;
    }

    @Override
    public TUser getUserByUserNameAndPwd(TUser tUser) {
        Example example = new Example(TUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userName", tUser.getUserName())
                .andEqualTo("userPwd", tUser.getUserPwd());
        List<TUser> tUsers = tUserMapper.selectByExample(example);
        if (tUsers.size() == 1){
            return tUsers.get(0);
        }
        return null;
    }

}
