package aibili.ronaldo.security;

import aibili.ronaldo.dao.PermissionDao;
import aibili.ronaldo.dao.UserDao;
import aibili.ronaldo.domain.Permission;
import aibili.ronaldo.domain.User;
import aibili.ronaldo.domain.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rtf on  2018/1/28.
 */

@Service
public class UrlUserService implements UserDetailsService{
    @Autowired
    private UserDao userDao;
    @Autowired
    private PermissionDao permissionDao;
    @Override
    public UserDetails loadUserByUsername(String userName) { //重写loadUserByUsername 方法获得 userdetails 类型用户
        Map<String, Object> map = new HashMap<>();
        map.put("name", userName);
        List<Integer> ids = new ArrayList<>();
        List<UserView> users = userDao.getByUserName("users_view", map);
        Integer user_id = 0;
        if(null != users || users.size() == 0){
            throw new UsernameNotFoundException("UserName "+userName+" not found");
        }else{
            for(UserView user : users) {
                ids.add(user.getPermission_id());
                user_id = user.getId();
            }
        }
        List<Permission> permissions = permissionDao.findObjectByIds("permission",ids);
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Permission permission : permissions) {
            if (permission != null && permission.getName()!=null) {
                GrantedAuthority grantedAuthority = new UrlGrantedAuthority(permission.getUrl(),permission.getMethod());
                grantedAuthorities.add(grantedAuthority);
            }
        }
        User user = userDao.findObjectById("users", user_id);
        user.setAuthorities(grantedAuthorities);
        return user;
    }
}
