package aibili.ronaldo.controller;

import aibili.ronaldo.dao.RestDao;
import aibili.ronaldo.utils.ReturnValueUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
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

//    @RequestMapping(method = RequestMethod.GET)
//    public Object user(@RequestParam(value="name", required = false) String name,
//                       @RequestParam(value="gender", required = false) String gender,
//                       @RequestParam(value="page", required = false) Integer page,
//                       @RequestParam(value="pagesize", required = false) Integer pagesize) {
//        Map<String, Object> params = new HashMap<>();
//        if(null != name){
//            params.put("name", name);
//        }
//        if(null != gender) {
//            params.put("gender", gender);
//        }
//        if(null != page){
//            Integer pageSize = (null != pagesize)? pagesize:10;
//            PageHelper.startPage(page, pageSize);
//            List<Map<String, Object >> list = restDao.findObject("users", params);
//            PageInfo<Map<String, Object >> pageInfo = new PageInfo(list);
//            return ReturnValueUtil.ok(pageInfo);
//        }
//        List<Map<String, Object >> list = restDao.findObject("users", params);
//        return ReturnValueUtil.ok(list);
//    }
}
