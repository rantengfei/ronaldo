package aibili.ronaldo.controller;

import aibili.ronaldo.dao.RestDao;
import aibili.ronaldo.utils.ReturnValueUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by rtf on  2018/1/25.
 */

@RestController
@RequestMapping(value="/api/ronaldo/users")
public class UserController {
    @Autowired
    private RestDao restDao;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Object user(@PathVariable("id") Integer id) {
        Map<String, Object> map = restDao.findObjectById("users", id);
        return ReturnValueUtil.ok(map);
    }
}
