package aibili.ronaldo.utils;


import aibili.ronaldo.dao.impl.RestDaoImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rtf on  2018/2/2.
 */
public class PagingUtil {
    public static Object getObject(HttpServletRequest request, RestDaoImpl restDao, String sql) {
        Map<String, Object> params = new HashMap<>();
        for (Map.Entry<String, String[]> entry :  request.getParameterMap().entrySet()){
            params.put(entry.getKey(), entry.getValue()[0]);
        }
        if(null !=  params.get("page")){
            Integer pageSize = (null != params.get("pagesize"))?
                    Integer.parseInt(params.get("pagesize").toString()):10;

            PageHelper.startPage(Integer.parseInt(params.get("page").toString()), pageSize);
            params.remove("page");
            params.remove("pagesize");
            List<Map<String, Object >> list = restDao.findAllBySql(sql.toString());
            PageInfo<Map<String, Object >> pageInfo = new PageInfo(list);
            return ReturnValueUtil.ok(pageInfo);
        }
        List<Map<String, Object >> list =  restDao.findAllBySql(sql.toString());
        return ReturnValueUtil.ok(list);
    }

    public static Object getObjectById(RestDaoImpl restDao, String sql) {
        return ReturnValueUtil.ok(restDao.findOneBySql(sql.toString()));
    }

}



