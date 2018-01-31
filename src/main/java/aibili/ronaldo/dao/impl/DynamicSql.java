package aibili.ronaldo.dao.impl;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.Arrays;
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
//        System.out.println("-----------------------------------------------------------");
//        System.out.println(map);
        String sql =new SQL() {
            {
                SELECT("*");
                FROM(table_name);
                int index = 0;
                if(map.isEmpty()){
                    ORDER_BY("id DESC");
                }else{
                    for (Map.Entry<String, Object> entry : map.entrySet()) {
                        if (index > 0 && index < map.size()) {
                            if(entry.getKey().contains("sort")){
                                if(entry.getValue().toString().contains("-")){
                                    ORDER_BY(entry.getValue().toString().split("-")[1]+" DESC");
                                    continue;
                                }else{
                                    ORDER_BY(entry.getValue().toString());
                                    continue;
                                }
                            }else{
                                AND();
                            }
                        }
                        StringBuffer condition = new StringBuffer();
                        if (entry.getKey().contains("-in")) {
                            condition.append(Arrays.toString(entry.getKey().split("-")).replace("[", "").replace(",", " ").replace("]", ""));
                            condition.append("(");
                            if (entry.getKey().getClass().getTypeName() == "java.lang.Integer") {
                                condition.append(Arrays.toString(entry.getValue().toString().split(",")).replace(",", ",").replace("[", "").replace("]", "").replace(" ", ""));
                            } else {
                                condition.append(Arrays.toString(entry.getValue().toString().split(",")).replace(",", "','").replace("[", "'").replace("]", "'").replace(" ", ""));
                            }
                            condition.append(")");
                        } else if (entry.getKey().contains("-contains")) {
                            condition.append("'");
                            condition.append(entry.getValue());
                            condition.append("'");
                            condition.append("=");
                            condition.append("any(");
                            condition.append(entry.getKey().replace("-contains", ""));
                            condition.append(")");
                        } else if (entry.getKey().contains("-gt")) {
                            condition.append(entry.getKey().replace("-gt", ""));
                            condition.append(">");
                            condition.append(entry.getValue());
                        } else if (entry.getKey().contains("-lt")) {
                            condition.append(entry.getKey().replace("-lt", ""));
                            condition.append("<");
                            condition.append(entry.getValue());
                        } else if (entry.getKey().contains("-ne")) {
                            condition.append(entry.getKey().replace("-ne", ""));
                            condition.append("<>");
                            condition.append(entry.getValue());
                        } else if (entry.getKey().contains("-range")) {
                            condition.append(entry.getKey().replace("-range", ""));
                            condition.append(">=");
                            condition.append(entry.getValue().toString().split("-")[0]);
                            if (entry.getValue().toString().split("-").length != 1) {
                                condition.append(" ");
                                condition.append("and");
                                condition.append(" ");
                                condition.append(entry.getKey().replace("-range", ""));
                                condition.append("<=");
                                condition.append(entry.getValue().toString().split("-")[1]);
                            }
                        } else if (entry.getKey().contains("-like")) {
                            condition.append(entry.getKey().replace("-like", ""));
                            condition.append(" ");
                            condition.append("like");
                            condition.append(" '%");
                            condition.append(entry.getValue().toString().split("-")[0]);
                            condition.append("%'");
                        } else if (entry.getKey().contains("-overlap")) {
                            condition.append(entry.getKey().replace("-overlap", ""));
                            condition.append(" ");
                            condition.append("&&");
                            condition.append(" ARRAY[");
                            condition.append(entry.getValue());
                            condition.append("]");
                        } else {
                            if(entry.getKey().contains("sort")){
                                if(entry.getValue().toString().contains("-")){
                                    ORDER_BY(entry.getValue().toString().split("-")[1]+" DESC");
                                }else {
                                    ORDER_BY(entry.getValue().toString());
                                }
                                continue;
                            }else{
                                condition.append(entry.getKey());
                                condition.append("='");
                                condition.append(entry.getValue());
                                condition.append("'");
                            }
                        }

                        WHERE(condition.toString());


                        index++;
                    }
                }

            }
        }.toString();
//        System.out.println(sql);
            return sql;

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


    public String findtableSql(@Param("table_name") final String table_name) {
        String sql = new SQL() {
            {
                SELECT("table_name");
                FROM( "INFORMATION_SCHEMA.tables");
                WHERE("table_name='"+table_name+"_view'");
            }

        }.toString();
        System.out.println(sql);
        return sql;

    }

}
