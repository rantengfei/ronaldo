package aibili.ronaldo.dao.impl;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

/**
 * Created by programmer on  2018/1/26.
 */
public class DynamicSql {
    public String findByIdSql(@Param("table_name") final String table_name, @Param("id") final Integer id) {
        return new SQL() {
            {
                SELECT("*");
                FROM(table_name);
                WHERE("id = #{id}");
            }
        }.toString();
    }
    public String findAllSql(@Param("table_name") final String table_name, @Param("map") final Map<String, Object> map) {
        StringBuffer condition = new StringBuffer();
        int index = 0;
        for (Map.Entry<String, Object> entry : map.entrySet()){
            if(index > 0 && index < map.size()){
                condition.append(" AND ");
            }
            condition.append(entry.getKey());
            condition.append("=");
            condition.append("'");
            condition.append(entry.getValue());
            condition.append("'");

            index++;
        }
        return new SQL() {
            {
                SELECT("*");
                FROM(table_name);
                if(null != condition && condition.length() > 0){
                    WHERE(condition.toString());
                }
            }
        }.toString();
    }
    public String createObject(@Param("table_name") final String table_name,  @Param("map") final Map<String, Object> map) {
        return new SQL() {
            {
                INSERT_INTO(table_name);
                for (Map.Entry<String, Object> entry : map.entrySet()){
                    VALUES(entry.getKey(), entry.getValue().toString());
                }
            }
        }.toString();
    }
    public String modifyObject(@Param("table_name") final String table_name,  @Param("id") final Integer id, @Param("map") final Map<String, Object> map) {
        return new SQL() {
            {
                UPDATE(table_name);
                for (Map.Entry<String, Object> entry : map.entrySet()){
                    StringBuffer sb = new StringBuffer();
                    sb.append(entry.getKey());
                    sb.append("=");
                    sb.append("'");
                    sb.append(entry.getValue());
                    sb.append("'");

                    SET(sb.toString());
                }
                WHERE("id = #{id}");
            }
        }.toString();
    }
    public String deleteObject(@Param("table_name") final String table_name, @Param("id") final Integer id) {
        return new SQL() {
            {
                DELETE_FROM(table_name);
                WHERE("id = #{id}");
            }
        }.toString();
    }
}
