package aibili.ronaldo.controller;

import aibili.ronaldo.dao.UserDao;
import aibili.ronaldo.domain.User;
import aibili.ronaldo.utils.MD5Util;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rtf on  2018/1/25.
 */

@Api(tags="用户", description="提供用户操作API")
@RestController
@RequestMapping(value="/api/user")
public class UserController {
    @Autowired
    private UserDao userDao;

    @ApiOperation(value="根据id获取用户", notes="根据id获取用户")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Object user(@PathVariable("id") Integer id) {
        System.out.println(userDao.findObjectById("user", id));
        return userDao.findObjectById("users", id);
    }

    @ApiOperation(value="获取所有用户", notes="无条件时获取所有用户，根据条件获取用户")
    @RequestMapping(method = RequestMethod.GET)
    public List<User> user(@RequestParam(value="name", required = false) String name,
                            @RequestParam(value="gender", required = false) String gender,
                            @RequestParam(value="page", required = false) Integer page) {
        Map<String, Object> params = new HashMap<>();
        if(null != name){
            params.put("name", name);
        }
        if(null != gender) {
            params.put("gender", gender);
        }
        if(null != page){
            PageHelper.startPage(page,10);
        }
        List<User> list = userDao.findObject("users", params);
        return list;
    }

    @ApiOperation(value="创建用户", notes="创建用户")
    @RequestMapping(method = RequestMethod.POST)
    public Integer createUser(@RequestBody User user) {
        user.setPassword(MD5Util.encode(user.getPassword()));
        ObjectMapper oMapper = new ObjectMapper();
        Map<String, Object> map = oMapper.convertValue(user, Map.class);
        Integer result = userDao.insert("user", map);
        System.out.println(result);
        return result;
    }

    @ApiOperation(value="修改用户", notes="修改用户")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void modifyUser(@PathVariable("id") Integer id, @RequestBody User user) {
        user.setPassword(MD5Util.encode(user.getPassword()));
        ObjectMapper oMapper = new ObjectMapper();
        Map<String, Object> map = oMapper.convertValue(user, Map.class);
        userDao.update("users", id, map);
    }

    @ApiOperation(value="删除用户", notes="根据id来指定删除用户")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable("id") Integer id) {
        userDao.delete("users", id);
    }
}
