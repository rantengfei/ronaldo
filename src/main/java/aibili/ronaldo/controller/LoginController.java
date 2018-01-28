package aibili.ronaldo.controller;

import aibili.ronaldo.dao.UserDao;
import aibili.ronaldo.domain.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * Created by rtf on  2018/1/28.
 */

@Api(tags="登录/退出", description="用户登录/退出")
@RestController
public class LoginController {
    @Autowired
    private UserDao userDao;

    @ApiOperation(value="登录/退出", notes="登录/退出")
    @RequestMapping(value = "/api/login", method = RequestMethod.POST)
    @ResponseBody
    public Object login(@AuthenticationPrincipal User loginedUser, @RequestParam(name = "logout", required = false) String logout) {
        if (logout != null) {
            return null;
        }
        if (loginedUser != null) {
            return userDao.findObjectById("user", loginedUser.getId());
        }
        return null;
    }
}
