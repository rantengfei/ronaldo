package aibili.ronaldo.dao;

import aibili.ronaldo.dao.impl.DynamicSql;
import aibili.ronaldo.domain.User;
import aibili.ronaldo.domain.UserView;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

/**
 * Created by rtf on  2018/1/25.
 */
public interface UserDao extends BaseDao<User>{
    @SelectProvider(type=DynamicSql.class, method="findAllSql")
    List<UserView> getByUserName(@Param("table_name") final String table_name, @Param("map") final Map<String, Object> map);
}
