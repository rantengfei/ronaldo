package aibili.ronaldo.dao;

import aibili.ronaldo.dao.impl.DynamicSql;
import aibili.ronaldo.domain.RolePermission;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

/**
 * Created by rtf on  2018/1/28.
 */
public interface RolePermissionDao extends BaseDao<RolePermissionDao>{
    @SelectProvider(type=DynamicSql.class, method="findAllSql")
    List<RolePermission> getRolePermission(@Param("table_name") final String table_name, @Param("map") final Map<String, Object> map);
}
