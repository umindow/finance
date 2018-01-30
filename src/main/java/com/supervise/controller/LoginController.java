package com.supervise.controller;

import com.supervise.common.Constants;
import com.supervise.common.SessionUser;
import com.supervise.config.role.RoleType;
import com.supervise.controller.vo.Login;
import com.supervise.dao.mysql.entity.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

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

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView login(@ModelAttribute Login login, HttpServletRequest request) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(login.getUserName());
        userEntity.setUserCnName("假设账户");
        userEntity.setLevel(RoleType.SUPER_MANAGER.getRoleLevel());
        request.getSession().setAttribute(Constants.SessionContant.USER_SESSION_KEY, userEntity);
        SessionUser.INSTANCE.setCurrentUser(userEntity);
        return new ModelAndView("/main");
    }

    @RequestMapping(value = "/logout")
    public ModelAndView logout(HttpServletRequest request) {
        request.getSession().removeAttribute(Constants.SessionContant.USER_SESSION_KEY);
        SessionUser.INSTANCE.removeSession();
        return new ModelAndView("/index");
    }
}
