package aibili.ronaldo.controller;

import aibili.ronaldo.dao.UserDao;
import aibili.ronaldo.domain.User;
import aibili.ronaldo.utils.MD5Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by rtf on  2018/1/28.
 */

@RestController
@RequestMapping(value="/api")
public class LoginController {
    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
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

    @Autowired
    protected AuthenticationManager authenticationManager;
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public Object signup(@ModelAttribute("user") User user, BindingResult result, HttpServletRequest request, HttpServletResponse response) {
        authenticateUserAndSetSession(user, request);
        return "OK";
    }
    private void authenticateUserAndSetSession(User user, HttpServletRequest request) {
        String username = user.getName();
        String password = MD5Util.encode(user.getPassword());
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        request.getSession();
        token.setDetails(new WebAuthenticationDetails(request));
        Authentication authenticatedUser = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
    }
}
