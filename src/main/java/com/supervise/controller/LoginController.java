package com.supervise.controller;

import com.supervise.common.Constants;
import com.supervise.common.SessionUser;
import com.supervise.config.role.RoleType;
import com.supervise.controller.vo.Login;
import com.supervise.dao.mysql.entity.UserEntity;
import com.supervise.dao.mysql.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by xishui.hb on 2018/1/30 下午4:46.
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
@RestController
public class LoginController {
    private final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private UserMapper userMapper;
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView login(@ModelAttribute Login login, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        Example example = new Example(UserEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userName",login.getUserName());
        criteria.andEqualTo("password",login.getPassword());
        List<UserEntity> userEntities = userMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(userEntities)){
            modelAndView.setViewName("/index");
            modelAndView.addObject("msg","用户不存在");
            return  modelAndView;
        }
        request.getSession().setAttribute(Constants.SessionContant.USER_SESSION_KEY, userEntities.get(0));
        SessionUser.INSTANCE.setCurrentUser(userEntities.get(0));
        modelAndView.setViewName("/main");
        return modelAndView;
    }

    @RequestMapping(value = "/logout")
    public ModelAndView logout(HttpServletRequest request) {
        request.getSession().removeAttribute(Constants.SessionContant.USER_SESSION_KEY);
        SessionUser.INSTANCE.removeSession();
        return new ModelAndView("/index");
    }
}
