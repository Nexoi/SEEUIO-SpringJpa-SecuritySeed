package com.seeu.shopper.api.shop.user;

import com.seeu.shopper.user.model.UserLogin;
import com.seeu.shopper.user.service.UserSignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by neo on 25/11/2017.
 * <p>
 * 用户注册
 * <p>
 * 1. 注册用户，邮箱
 * [POST] /signup
 * <p>
 * 2. 激活用户，邮箱内 URL 点击触发
 * [Any] /active?token=xx
 * <p>
 * 3. 注销用户，将用户状态改为 -1【本用户】
 * [DELETE] /written-off
 */
@RestController
@RequestMapping("/api/shop/v2")
public class UserSignUpApi {
    @Autowired
    UserSignUpService userSignUpService;

    /**
     * 注册
     *
     * @param username
     * @param email
     * @param password
     * @return
     */
    @PostMapping("/signup")
    public ResponseEntity signUp(String username, String email, String password) {
        UserSignUpService.SIGN_STATUS status = userSignUpService.signUp(username, email, password, false);

        switch (status) {
            case signup_success:
                return ResponseEntity.ok().body("sign up success");
            case signup_active_waiting:
                return ResponseEntity.ok().body("The active email was send successfully! Please check your email box.");
            case signup_failure:
                return ResponseEntity.badRequest().body("sign up failure.");
            case signup_error_email:
                return ResponseEntity.badRequest().body("Email address was not available, please check your email first.");
            case signup_error_email_send:
                return ResponseEntity.badRequest().body("Email send failure, please check your email address and ensure it is available.");
            case signup_error_name:
                return ResponseEntity.badRequest().body("Name was empty. Please input your nickname first");
            case signup_error_password:
                return ResponseEntity.badRequest().body("Password Length should be larger than 6");
            case sign_exception:
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Exception occur! Please contact with our admin to help you finish this signup.");
            default:
                return ResponseEntity.ok().body(""); // 默认情况不返回任何信息
        }
    }

    /**
     * 激活账户
     *
     * @param token
     * @return
     */
    @RequestMapping("/active")
    public ResponseEntity activeAccount(String token) {
        UserSignUpService.SIGN_STATUS status = userSignUpService.activeAccount(token);
        switch (status) {
            case signup_active_failure:
                return ResponseEntity.badRequest().body("sign up failure.");
            case signup_active_success:
//                return ResponseEntity.status(HttpStatus.FOUND).location("/");
                return ResponseEntity.ok().body("active success!");
            case sign_exception:
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Exception occur!");
            default:
                return ResponseEntity.ok().body(""); // 默认情况不返回任何信息
        }
    }

    @DeleteMapping("/written-off")
    public ResponseEntity disableAccount(@AuthenticationPrincipal UserLogin authUser) {
        if (authUser == null)
            return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body("not authoritative");
        UserSignUpService.SIGN_STATUS status = userSignUpService.writtenOff(authUser.getUid());
        switch (status) {
            case written_off_success:
                return ResponseEntity.ok().body("written off success");
            case written_off_failure:
                return ResponseEntity.badRequest().body("written off failure");
            default:
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Exception occur");
        }
    }

}
