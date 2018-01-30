package aibili.ronaldo.security;

import aibili.ronaldo.dao.UserDao;
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
    @Override
    public UserDetails loadUserByUsername(String name) { //重写loadUserByUsername 方法获得 userdetails 类型用户
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        List<Integer> ids = new ArrayList<>();
        List<UserView> users = userDao.getByUserName("users_view", map);
        Integer user_id = 0;
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        if(null == users || users.size() == 0){
            throw new UsernameNotFoundException("UserName "+name+" not found");
        }else{

            for(UserView user : users) {
                user_id = user.getId();
                if(user.getMethod().equals("GET") || user.getMethod().equals("ALL")){
                    GrantedAuthority grantedAuthority = new UrlGrantedAuthority(user.getUrl(), user.getMethod());
                    grantedAuthorities.add(grantedAuthority);
                    GrantedAuthority grantedAuthority1 = new UrlGrantedAuthority(user.getUrl() + "/**", user.getMethod());
                    grantedAuthorities.add(grantedAuthority1);
                }else{
                    GrantedAuthority grantedAuthority = new UrlGrantedAuthority(user.getUrl(), user.getMethod());
                    grantedAuthorities.add(grantedAuthority);
                }
            }
        }
        User user = userDao.findObjectById("users", user_id);
        user.setAuthorities(grantedAuthorities);
        return user;
    }
}
