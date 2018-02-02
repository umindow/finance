package com.supervise.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.supervise.common.Constants;
import com.supervise.dao.mysql.entity.UserEntity;
import com.supervise.support.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by xishui.hb on 2018/1/30 下午5:29.
 * 用户管理的Controller
 *  //新增/修改/删除用户,用户授权等
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ModelAndView list(@RequestParam(value = "p", required = false) Integer pageNum){
        Page<UserEntity> pager = PageHelper.startPage(pageNum == null ? 1 : pageNum, Constants.PAGE_SIZE);
        ModelAndView view = new ModelAndView("pages/user/list","list",pager);
        return view;
    }
}
