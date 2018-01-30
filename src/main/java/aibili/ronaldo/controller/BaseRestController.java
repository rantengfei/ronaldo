package aibili.ronaldo.controller;

import aibili.ronaldo.dao.RestDao;
import aibili.ronaldo.domain.User;
import aibili.ronaldo.utils.MD5Util;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
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
        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("data", restDao.findObjectById(params[0], id));
        return result;
    }

    @RequestMapping(value = "/*", method = RequestMethod.GET)
    public Map<String, Object> list(HttpServletRequest request) {
        String url = request.getRequestURI();
        String[] preUrl = urlProcess(url);
        Map<String, Object> map = new HashMap<>();
        for (Map.Entry<String, String[]> entry :  request.getParameterMap().entrySet()){
            map.put(entry.getKey(), entry.getValue()[0]);
        }

        Map<String, Object> result = new HashMap<>();
        Map<String, Object> list = new HashMap<>();
        list.put("list", restDao.findObject(preUrl[0], map));
        result.put("status", 0);
        result.put("data", list);
        return result;
    }

    @RequestMapping(value = "/*", method = RequestMethod.POST)
    public Object create(HttpServletRequest request, @RequestBody Object object) {
        String url = request.getRequestURI();
        String[] params = urlProcess(url);
        ObjectMapper oMapper = new ObjectMapper();
        Map<String, Object> map = oMapper.convertValue(object, Map.class);
        restDao.insert(params[0], map);
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
