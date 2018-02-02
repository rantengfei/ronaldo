package aibili.ronaldo.controller;

import aibili.ronaldo.dao.RestDao;
import aibili.ronaldo.dao.impl.RestDaoImpl;
import aibili.ronaldo.utils.PagingUtil;
import aibili.ronaldo.utils.ReturnValueUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    private RestDaoImpl restDao;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Object user(@PathVariable("id") Integer id) {
        Map<String, Object> map = restDao.findObjectById("users", id);
        return ReturnValueUtil.ok(map);
    }

//    @RequestMapping(method = RequestMethod.GET)
//    public Object list(HttpServletRequest request) {
//        StringBuffer sql = new StringBuffer();
//        sql.append("SELECT users.*, roles.en_name, roles.cn_name FROM users ");
//        sql.append(" LEFT JOIN user_role ON users.id = user_role.user_id ");
//        sql.append(" LEFT JOIN roles ON roles.id = user_role.role_id");
//        return PagingUtil.getObject(request, restDao, sql.toString());
//    }

//    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
//    public Object fetch_val(HttpServletRequest request, @PathVariable("id") Integer id) {
//        StringBuffer sql = new StringBuffer();
//        sql.append("SELECT users.*, roles.en_name, roles.cn_name FROM users ");
//        sql.append(" LEFT JOIN user_role ON users.id = user_role.user_id ");
//        sql.append(" LEFT JOIN roles ON roles.id = user_role.role_id");
//        sql.append(" WHERE users.id = ");
//        sql.append(id);
//        return PagingUtil.getObjectById(restDao, sql.toString());
//    }


}
