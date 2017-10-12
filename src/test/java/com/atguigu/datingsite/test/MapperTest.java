package com.atguigu.datingsite.test;

import com.atguigu.datingsite.bean.TUser;
import com.atguigu.datingsite.dao.TUserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
@RunWith(value=SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
        "classpath:spring-beans.xml",
        "classpath:spring-mybatis.xml",
        "classpath:spring-tx.xml"})
public class MapperTest {

    @Autowired
    TUserMapper tUserMapper;

    @Test
    public void test1(){
        System.out.println(tUserMapper);
        List<TUser> list = tUserMapper.select(new TUser());
        for (TUser user:list) {
            System.out.println(user.getUserId()+"\t"+user.getUserName());
        }
    }

}
