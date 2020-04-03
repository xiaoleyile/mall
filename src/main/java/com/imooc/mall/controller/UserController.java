package com.imooc.mall.controller;


import com.imooc.mall.consts.MallConst;
import com.imooc.mall.form.UserLoginForm;
import com.imooc.mall.form.UserRegisterForm;
import com.imooc.mall.pojo.User;
import com.imooc.mall.service.IUserService;
import com.imooc.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static com.imooc.mall.enums.ResponseEnum.NEED_LOGIN;
import static com.imooc.mall.enums.ResponseEnum.PARAM_ERROR;

@RestController

@Slf4j
public class UserController {
//前段发过来的数据

    //调用服务层
    @Autowired
    private IUserService userService;

    @PostMapping("/user/register")
    public ResponseVo register(@Valid @RequestBody UserRegisterForm userForm,
                               BindingResult bindingResult) {
        //添加@Slf4j进行日志添加
       if(bindingResult.hasErrors()){
           log.info("注册信息有误，{}{}",bindingResult.getFieldError(),bindingResult.getFieldError().getDefaultMessage());
           return ResponseVo.error(PARAM_ERROR,bindingResult);
       }
       //如果成功就返回服务层返回的数据
        User user = new User();  //此时user对象里面的数据为空，需要把表单里面的数据提交到User
        BeanUtils.copyProperties(userForm,user);
        return userService.register(user);
    }

    @PostMapping("/user/login")
    public ResponseVo login(@Valid @RequestBody UserLoginForm userLoginForm,
                            BindingResult bindingResult,
                            HttpSession session){
        if(bindingResult.hasErrors()){
            log.info("注册信息有误，{}{}",bindingResult.getFieldError(),bindingResult.getFieldError().getDefaultMessage());
            return ResponseVo.error(PARAM_ERROR,bindingResult);
        }
        //登陆成功后才能进行session
        ResponseVo<User> responseVo = userService.login(userLoginForm.getUsername(), userLoginForm.getPassword());

        //设置Session  里面有两个参数，第一个常量定义
        session.setAttribute(MallConst.CURRENT_USER,responseVo.getData());
      //  HttpSession session = httpServletRequest.getSession();

        return responseVo;
    }

    @GetMapping("/user")
    public ResponseVo userInfo(HttpSession session){
        User user= (User) session.getAttribute(MallConst.CURRENT_USER);
        if(user==null){
            return ResponseVo.error(NEED_LOGIN);
        }
        return ResponseVo.success(user);
    }

}
