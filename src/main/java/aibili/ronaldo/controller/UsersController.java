package aibili.ronaldo.controller;

import aibili.ronaldo.dao.UserDao;
import aibili.ronaldo.domain.Users;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by programmer on  2018/1/25.
 */

@Api(tags="用户", description="提供用户操作API")
@RestController
@RequestMapping(value="/api")
public class UsersController {
    @Autowired
    private UserDao userDao;

    @ApiOperation(value="根据id获取用户", notes="根据id获取用户")
    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public Object users(@PathVariable("id") Integer id) {
        System.out.println(userDao.findObjectById("users", id));
        return userDao.findObjectById("users", id);
    }

    @ApiOperation(value="获取所有用户", notes="无条件时获取所有用户，根据条件获取用户")
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<Users> users(@RequestParam(value="name", required = false) String name,
                      @RequestParam(value="gender", required = false) String gender) {
        Map<String, Object> params = new HashMap<>();
        if(null != name){
            params.put("name", name);
        }
        if(null != gender) {
            params.put("gender", gender);
        }
        return userDao.findObject("users", params);
    }

    @ApiOperation(value="创建用户", notes="创建用户")
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public void createUser(@RequestBody Map map) {
        userDao.insert("users", map);
    }

    @ApiOperation(value="修改用户", notes="修改用户")
    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    public void modifyUser(@PathVariable("id") Integer id, @RequestBody Map map) {
        userDao.update("users", id, map);
    }

    @ApiOperation(value="删除用户", notes="根据id来指定删除用户")
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable("id") Integer id) {
        userDao.delete("users", id);
    }
}
