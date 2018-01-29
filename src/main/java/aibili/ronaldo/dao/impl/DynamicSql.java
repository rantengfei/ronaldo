package aibili.ronaldo.dao.impl;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;
import java.util.Map;

/**
 * Created by rtf on  2018/1/26.
 */
public class DynamicSql {
    public String findByIdSql(@Param("table_name") final String table_name, @Param("id") final Integer id) {
        String sql = new SQL() {
            {
                SELECT("*");
                FROM(table_name);
                WHERE("id = #{id}");
            }
        }.toString();
        System.out.println(sql);
        return sql;
    }

    public String findByIdsSql(@Param("table_name") final String table_name, @Param("ids") final List<Integer> ids) {
        String sql = new SQL() {
            {
                SELECT("*");
                FROM(table_name);
                for(int i = 0; i < ids.size(); i++) {
                    if(i > 0 && i < ids.size()){
                        OR();
                    }
                    WHERE("id = " + i);
                }
            }
        }.toString();
        System.out.println(sql);
        return sql;
    }

    public String findAllSql(@Param("table_name") final String table_name, @Param("map") final Map<String, Object> map) {
        return new SQL() {
            {
                SELECT("*");
                FROM(table_name);
                int index = 0;
                for (Map.Entry<String, Object> entry : map.entrySet()){
                    if(index > 0 && index < map.size()){
                        AND();
                    }
                    StringBuffer condition = new StringBuffer();
                    condition.append(entry.getKey());
                    condition.append("='");
                    condition.append(entry.getValue());
                    condition.append("'");
                    WHERE(condition.toString());
                    index++;
                }
            }
        }.toString();
    }
    public String createObject(@Param("table_name") final String table_name,  @Param("map") final Map<String, Object> map) {
        return new SQL() {
            {
                INSERT_INTO(table_name);
                for (Map.Entry<String, Object> entry : map.entrySet()){
                    if(entry.getKey() == "id" || entry.getValue() == null){
                        continue;
                    }
                    if(entry.getValue().getClass().getTypeName() == "java.lang.Integer"){
                        VALUES(entry.getKey(), entry.getValue().toString());
                        continue;
                    }
                    StringBuffer sb = new StringBuffer();
                    sb.append("'");
                    sb.append(entry.getValue());
                    sb.append("'");
                    VALUES(entry.getKey(), sb.toString());
                }
            }
        }.toString();
    }
    public String modifyObject(@Param("table_name") final String table_name,  @Param("id") final Integer id, @Param("map") final Map<String, Object> map) {
        return new SQL() {
            {
                UPDATE(table_name);
                for (Map.Entry<String, Object> entry : map.entrySet()){
                    if(entry.getKey() == "id" || entry.getValue() == null){
                        continue;
                    }
                    StringBuffer sb = new StringBuffer();
                    sb.append(entry.getKey());
                    sb.append("=");
                    if(entry.getValue().getClass().getTypeName() == "java.lang.Integer"){
                        sb.append(entry.getValue());
                    }else {
                        sb.append("'");
                        sb.append(entry.getValue());
                        sb.append("'");
                    }

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
