package com.seeu.shopper.api.shop.user;

import com.seeu.shopper.user.model.UserLogin;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by neo on 25/11/2017.
 * <p>
 * 用户登录直接使用 spring security，在配置类中已经实现
 * <p>
 * api: /signin?email=xx&password=xx
 * api: /signout
 */
@RestController
public class UserSignInApi {

    @RequestMapping("/signin")
    public String signIn(String email, String password) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal != null && !principal.equals("anonymousUser")) {
            UserLogin authUser = (UserLogin) principal;
            // TODO ...
        }
        return null;
    }

}
