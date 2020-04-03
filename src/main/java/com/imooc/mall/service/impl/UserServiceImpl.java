package com.imooc.mall.service.impl;

import com.imooc.mall.dao.UserMapper;
import com.imooc.mall.enums.RoleEnum;
import com.imooc.mall.pojo.User;
import com.imooc.mall.service.IUserService;
import com.imooc.mall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

import static com.imooc.mall.enums.ResponseEnum.*;

@Service
public class UserServiceImpl implements IUserService {
    //注入dao层的UserMapper
    @Autowired
    private UserMapper userMapper;

    @Override
    public ResponseVo<User> register(User user) {
        //1\校验数
        //1)用户名不能重复
        int countByUsername = userMapper.countByUsername(user.getUsername());
        if (countByUsername>0){
            return ResponseVo.error(USERNAME_EXIST);
        }
        //校验email
        int countByEmail = userMapper.countByEmail(user.getEmail());
        if (countByEmail>0){
            return  ResponseVo.error(EMAIL_EXIST);
        }
        user.setRole(RoleEnum.CUSTOMER.getCode());
        //如果校验成功进行密码MD5加密
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes(StandardCharsets.UTF_8)));
        //传入数据库
        int resoultCount = userMapper.insertSelective(user);
        if (resoultCount==0){
            return  ResponseVo.error(ERROR);
        }
        return ResponseVo.successByMsg();

    }

    @Override
    public ResponseVo<User> login(String username, String password) {
        User user = userMapper.selectByUsername(username); //拿到数据库里面的数据
        //判断数据是否为空
        if(user==null){
            return ResponseVo.error(USERNAME_OR_PASSWORD_ERROR);
        }
        if(user.getPassword().equalsIgnoreCase(DigestUtils.md5DigestAsHex(user.getPassword().getBytes(StandardCharsets.UTF_8)))){
            return ResponseVo.error(USERNAME_OR_PASSWORD_ERROR);
        }
        user.setPassword("");

        return ResponseVo.success(user);
    }




}
