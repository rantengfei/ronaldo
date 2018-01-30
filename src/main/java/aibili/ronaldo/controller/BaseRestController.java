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
        String[] params = urlProcess(request);
        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("data", restDao.findObjectById(params[0], id));
        return result;
    }

    @RequestMapping(value = "/*", method = RequestMethod.GET)
    public Object list(HttpServletRequest request) {
        String[] preUrl = urlProcess(request);
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
        String[] params = urlProcess(request);
        ObjectMapper oMapper = new ObjectMapper();
        Map<String, Object> map = oMapper.convertValue(object, Map.class);
        map.put("password", MD5Util.encode(map.get("password").toString()));
        restDao.insert(params[0], map);
        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        return result;
    }

    @RequestMapping(value = "/*/{id}", method = RequestMethod.PUT)
    public Object modify(HttpServletRequest request, @PathVariable("id") Integer id, @RequestBody Object object) {
        String[] params = urlProcess(request);
        ObjectMapper oMapper = new ObjectMapper();
        Map<String, Object> map = oMapper.convertValue(object, Map.class);
        map.put("password", MD5Util.encode(map.get("password").toString()));
        restDao.update(params[0], id, map);
        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        return result;
    }

    @RequestMapping(value = "/*/{id}", method = RequestMethod.DELETE)
    public Object delete(@PathVariable("id") Integer id) {

        return "delete";
    }

    private String[] urlProcess(HttpServletRequest request) {
        String prefix = "api/ronaldo/";
        String url = request.getRequestURI();
        return url.substring(url.indexOf(prefix) + prefix.length()).split("/");
    }
}
