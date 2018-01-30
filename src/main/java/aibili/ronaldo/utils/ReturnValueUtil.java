package aibili.ronaldo.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rtf on  2018/1/30.
 */
public class ReturnValueUtil {

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
