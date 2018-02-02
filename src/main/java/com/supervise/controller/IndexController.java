package com.supervise.controller;

import com.supervise.config.role.DataRole;
import com.supervise.config.role.DataType;
import com.supervise.config.role.Role;
import com.supervise.config.role.RoleType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by xishui on 2018/1/30 上午9:47.
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
@RestController
public class IndexController {
    /**
     * 首页
     */
    @RequestMapping({"/index",""})
    public ModelAndView index(Model model) {
        ModelAndView mv = new ModelAndView("/index");
        mv.addObject("msg","");
        return mv;
    }
}
