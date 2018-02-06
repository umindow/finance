package com.supervise.controller;

import com.supervise.controller.vo.DataVo;
import com.supervise.dao.mysql.mapper.BankCreditMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xishui.hb on 2018/1/30 下午5:30.
 * 数据操作controller，查询/编辑/trigger推送
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
@RestController
@RequestMapping("/data")
public class DataController {
    @Autowired
    private BankCreditMapper bankCreditMapper;
    @RequestMapping(value = "bankCreditList", method = RequestMethod.GET)
    public ModelAndView list(int dataType) {
        ModelAndView view = new ModelAndView();
        List<DataVo> dataVos = new ArrayList<DataVo>();
        view.setViewName("pages/data/bankCreditList");
        view.addObject("dataVos", dataVos);
        return view;
    }
}
