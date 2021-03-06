package com.seeu.configurer.auth;

import com.seeu.shopper.user.model.USER_STATUS;
import com.seeu.shopper.user.model.UserLogin;
import com.seeu.shopper.user.repository.UserLoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


/**
 * Created by neo on 08/10/2017.
 */
@Component("seeuAuthenticationProvider")
public class SEEUAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserLoginRepository userLoginRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        // 普通 账号／密码 验证
        // 用户必须为【正常状态】用户
        UserLogin user = userLoginRepository.findByEmail(email);
        if (user != null
                && user.getPassword().equals(password)
                && user.getMemberStatus() != null
                && user.getMemberStatus() != USER_STATUS.UNACTIVED
                && user.getMemberStatus() != USER_STATUS.DISTORY) {
            return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
