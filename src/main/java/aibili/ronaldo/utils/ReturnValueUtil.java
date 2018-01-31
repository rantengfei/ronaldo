package aibili.ronaldo.utils;

import com.github.pagehelper.PageInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rtf on  2018/1/30.
 */
public class ReturnValueUtil {

    public static Object ok(PageInfo pageInfo){
        Map<String, Object> list = new HashMap<>();
        list.put("list", pageInfo.getList());
        list.put("total", pageInfo.getTotal());
        list.put("pages", pageInfo.getPages());
        list.put("currentPage", pageInfo.getPageNum());
        list.put("firstPage", pageInfo.getFirstPage());
        list.put("prePage", pageInfo.getPrePage());
        list.put("nextPage", pageInfo.getNextPage());
        list.put("lastPage", pageInfo.getLastPage());
        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("data", list);
        return result;
    }

    public static Object ok(List<Map<String, Object>> map){
        Map<String, Object> list = new HashMap<>();
        list.put("list", map);
        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("data", list);
        return result;
    }

    public static Object ok(Map<String, Object> map){
        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("data",map);
        return result;
    }

    public static Object ok(){
        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        return result;
    }
}
