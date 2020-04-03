package com.imooc.mall.service.impl;

import com.imooc.mall.MallApplicationTests;
import com.imooc.mall.enums.RoleEnum;
import com.imooc.mall.pojo.User;
import com.imooc.mall.service.IUserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

//@Transactional注解起到回滚的作用，不会在数据库中进行存储
@Transactional
public class UserServiceImplTest extends MallApplicationTests {
    @Autowired
    private IUserService userService;

    @Test
    public void register() {
        User user = new User("jack","12345","jack@qq.com", RoleEnum.CUSTOMER.getCode());
        userService.register(user);
    }
}