package aibili.ronaldo.dao;

import aibili.ronaldo.dao.impl.DynamicSql;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

/**
 * Created by rtf on  2018/1/30.
 */
public interface RestDao {
    @SelectProvider(type=DynamicSql.class, method="findAllSql")
    List<Map<String, Object>> findObject(@Param("table_name") final String table_name, @Param("map") final Map<String, Object> map);

    @SelectProvider(type=DynamicSql.class, method="findByIdsSql")
    Map<String, Object> findObjectByIds(@Param("table_name") final String table_name, @Param("ids") final List<Integer> ids);

    @SelectProvider(type=DynamicSql.class, method="findByIdSql")
    Object findObjectById(@Param("table_name") final String table_name, @Param("id") final Integer id);

    @SelectProvider(type=DynamicSql.class, method="createObject")
    void insert(@Param("table_name") final String table_name, @Param("map") final Map<String, Object> map);

    @SelectProvider(type=DynamicSql.class, method="modifyObject")
    void update(@Param("table_name") final String table_name,  @Param("id") final Integer id, @Param("map") final Map<String, Object> map);

    @SelectProvider(type=DynamicSql.class, method="deleteObject")
    void delete(@Param("table_name") final String table_name, @Param("id") final Integer id);
}
