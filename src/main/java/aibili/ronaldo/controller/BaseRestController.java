package aibili.ronaldo.controller;

import aibili.ronaldo.dao.RestDao;
import aibili.ronaldo.domain.User;
import aibili.ronaldo.utils.MD5Util;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rtf on  2018/1/30.
 */

@RestController
@RequestMapping(value = "/api/ronaldo")
public class BaseRestController {
    @Autowired
    private RestDao restDao;

    @RequestMapping(value = "/*/{id}", method = RequestMethod.GET)
    public Object details(HttpServletRequest request, @PathVariable("id") Integer id) {
        String url = request.getRequestURI();
        String[] params = urlProcess(url);
        restDao.findObjectById(params[0], id);
        return "details";
    }

    @RequestMapping(value = "/*", method = RequestMethod.GET)
    public Object list(HttpServletRequest request) {
        String url = request.getRequestURI();
        String[] params = urlProcess(url);
        Map<String, Object> map = new HashMap<>();

        restDao.findObject(params[0], map);
        return "list";
    }

    @RequestMapping(value = "/*", method = RequestMethod.POST)
    public Object create(@RequestBody Object object) {
        return "create";
    }

    @RequestMapping(value = "/*/{id}", method = RequestMethod.PUT)
    public Object modify(@PathVariable("id") Integer id, @RequestBody User user) {
        user.setPassword(MD5Util.encode(user.getPassword()));
        return "modify";
    }

    @RequestMapping(value = "/*/{id}", method = RequestMethod.DELETE)
    public Object delete(@PathVariable("id") Integer id) {
        return "delete";
    }

    private String[] urlProcess(String url) {
        String prefix = "api/ronaldo/";
        return  url.substring(url.indexOf(prefix) + prefix.length()).split("/");
    }
}
