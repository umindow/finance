package com.supervise.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.supervise.common.SessionUser;
import com.supervise.config.role.DataType;
import com.supervise.config.role.DepType;
import com.supervise.config.role.RoleType;
import com.supervise.dao.mysql.entity.UserEntity;
import com.supervise.dao.mysql.mapper.UserMapper;
import com.supervise.support.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by xishui.hb on 2018/1/30 下午5:29.
 * 用户管理的Controller
 * //新增/修改/删除用户,用户授权等
 *
 * @author xishui
 *         Description:
 *         Modify Record
 *         ----------------------------------------
 *         User    |    Time    |    Note
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserMapper userMapper;
    @ResponseBody
    @RequestMapping(value = "/init")
    private String initUser(){
        UserEntity userEntity = new UserEntity();
        userEntity.setLevel(RoleType.MANAGER.getRoleLevel());
        userEntity.setUserCnName("管理员");
        userEntity.setUserName("admin");
        userEntity.setUserStatus(UserEntity.UserStatus.ALIVE.getStatus());
        List<Integer> dataLevels = Lists.newArrayList();
        dataLevels.add(DataType.SUPERVISE_BANK_DATA.getDataLevel());
        dataLevels.add(DataType.SUPERVISE_BIZ_DATA.getDataLevel());
        dataLevels.add(DataType.SUPERVISE_FEE_DATA.getDataLevel());
        dataLevels.add(DataType.SUPERVISE_REPLACE_DATA.getDataLevel());
        dataLevels.add(DataType.SUPERVISE_TRACE_DATA.getDataLevel());
        userEntity.setDataLevels(JSON.toJSONString(dataLevels));
        userEntity.setDepId(String.valueOf(DepType.COMPREHENSIVE_DEP.getDepId()));
        userEntity.setPassword("123456");
        userMapper.insert(userEntity);
        return "success";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(@RequestParam(value = "p", required = false) Integer pageNum) {
        ModelAndView view = new ModelAndView("pages/user/list");
        UserEntity userEntity = SessionUser.INSTANCE.getCurrentUser();
        List<UserEntity> aliveUsers = new ArrayList<UserEntity>();
        boolean addUsers = false;
        boolean deleteUser = false;
        boolean updateUser = true;
        if(20==userEntity.getLevel()){
            addUsers=true;
            deleteUser= true;
            List<UserEntity> userEntityList = userMapper.selectAll();
            for (final UserEntity user : userEntityList) {
                if (user.getUserStatus() == UserEntity.UserStatus.ALIVE.getStatus()) {
                    aliveUsers.add(user);
                }
            }
        }else if(10==userEntity.getLevel()){
            UserEntity eUserEntity  =userMapper.selectByPrimaryKey(userEntity.getId());
            if (userEntity.getUserStatus() == UserEntity.UserStatus.ALIVE.getStatus()) {
                aliveUsers.add(eUserEntity);
            }
        }
        view.addObject("users", aliveUsers);
        view.addObject("addUsers", addUsers);
        view.addObject("deleteUser", deleteUser);
        view.addObject("updateUser", updateUser);
        view.addObject("roles", Arrays.asList(RoleType.values()));
        view.addObject("dataRoles", DataType.listHtmlDataType());
        view.addObject("depRoles",Arrays.asList(DepType.values()));
        return view;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView addView() {
        ModelAndView view = new ModelAndView("pages/user/add");
        view.addObject("roles", Arrays.asList(RoleType.values()));
        view.addObject("dataRoles", DataType.listHtmlDataType());
        view.addObject("depRoles",Arrays.asList(DepType.values()));
        return view;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Result saveUser(UserEntity userEntity) {
        if (null == userEntity || null == userEntity.getUserName() || null == userEntity.getPassword()
                || null == userEntity.getUserCnName() || null == userEntity.getEmail() || null == userEntity.getPhone()) {
            return Result.fail("请求参数不合法,请检查必填项.");
        }
        Example example = new Example(UserEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userName", userEntity.getUserName());
        List<UserEntity> users = userMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(users)) {
            return Result.fail("用户名已存在:" + userEntity.getUserName());
        }
        if (null != userEntity.getDataLevels()) {
            List<String> dataLevelStrs = Arrays.asList(userEntity.getDataLevels().split(","));
            List<Integer> dataLeves = new ArrayList<Integer>();
            for (final String dataLevel : dataLevelStrs) {
                dataLeves.add(Integer.valueOf(dataLevel));
            }
            userEntity.setDataLevels(JSON.toJSONString(dataLeves));
        }
//        if(null != userEntity.getDepId()){
//            List<String> depIdStrs = Arrays.asList(userEntity.getDepId().split(","));
//            List<Integer> depIds = new ArrayList<Integer>();
//            for(final String depId : depIdStrs){
//                depIds.add(Integer.valueOf(depId));
//            }
//            userEntity.setDepId(JSON.toJSONString(depIds));
//        }
        userEntity.setUserStatus(UserEntity.UserStatus.ALIVE.getStatus());
        userMapper.insert(userEntity);
        return Result.success();
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Result delete(long id) {
        if(SessionUser.INSTANCE.getCurrentUser().getId().longValue() == id){
            return Result.fail("不能删除自己.");
        }
        UserEntity user = userMapper.selectByPrimaryKey(id);
        if (null == user) {
            return Result.fail("用户不存在.");
        }
        user.setUserStatus(UserEntity.UserStatus.NON_LIVE.getStatus());
        userMapper.deleteByPrimaryKey(id);
        return Result.success();
    }

    @ResponseBody
    @RequestMapping(value = "/modifyUser", method = RequestMethod.POST)
    public Result modifyUser(UserEntity userEntity) {
        long id = userEntity.getId();
        UserEntity euserEntity = userMapper.selectByPrimaryKey(id);
        euserEntity.setUserCnName(userEntity.getUserCnName());
        euserEntity.setPassword(userEntity.getPassword());
        euserEntity.setEmail(userEntity.getEmail());
        euserEntity.setPhone(userEntity.getPhone());
        userMapper.updateByPrimaryKeySelective(euserEntity);
        return Result.success();
    }
}
