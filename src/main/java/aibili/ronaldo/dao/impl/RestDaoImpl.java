package aibili.ronaldo.dao.impl;

import aibili.ronaldo.dao.RestDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by rtf on  2018/1/26.
 */

@Service
public class RestDaoImpl implements RestDao{
    @Autowired
    private RestDao restDao;
    @Override
    public List<Map<String, Object>> findObject(String table_name, Map<String, Object> map) {
        return restDao.findObject(tableName(table_name), map);
    }

    @Override
    public Map<String, Object> findObjectByIds(String table_name, List<Integer> ids) {
        return restDao.findObjectByIds(tableName(table_name), ids);
    }

    @Override
    public Map<String, Object> findObjectById(String table_name, Integer id) {
        return restDao.findObjectById(tableName(table_name), id);
    }

    @Override
    public void insert(String table_name, Map<String, Object> map) {
        restDao.insert(table_name, map);
    }

    @Override
    public void update(String table_name, Integer id, Map<String, Object> map) {
        restDao.update(table_name, id, map);
    }

    @Override
    public void delete(String table_name, Integer id) {
        restDao.delete(table_name, id);
    }

    @Override
    public Map<String, Object> findTableView(String table_name) {
        return restDao.findTableView(table_name);
    }

    private String tableName(String tableName){
        Map<String, Object> map = restDao.findTableView(tableName);
        if(null != map && map.size()>0){
            tableName = tableName + "_view";
        }
        return tableName;
    }
}
