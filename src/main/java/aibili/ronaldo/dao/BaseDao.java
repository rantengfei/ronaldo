package aibili.ronaldo.dao;

import aibili.ronaldo.dao.impl.DynamicSql;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

/**
 * Created by programmer on  2018/1/26.
 */
public interface BaseDao<T> {
    @SelectProvider(type=DynamicSql.class, method="findAllSql")
    List<T> findObject(@Param("table_name") final String table_name, @Param("map") final Map<String, Object> map);

    @SelectProvider(type=DynamicSql.class, method="findByIdSql")
    T findObjectById(@Param("table_name") final String table_name, @Param("id") final Integer id);

    @SelectProvider(type=DynamicSql.class, method="createObject")
    @Options(useGeneratedKeys=true, keyProperty="id")
    Integer insert(@Param("table_name") final String table_name, @Param("map") final Map<String, Object> map);

    @SelectProvider(type=DynamicSql.class, method="modifyObject")
    void update(@Param("table_name") final String table_name,  @Param("id") final Integer id, @Param("map") final Map<String, Object> map);

    @SelectProvider(type=DynamicSql.class, method="deleteObject")
    void delete(@Param("table_name") final String table_name, @Param("id") final Integer id);
}
