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

@RestController
@RequestMapping(value="/api/user")
public class UserController {
    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Object user(@PathVariable("id") Integer id) {
        return userDao.findObjectById("users", id);
    }

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
}
