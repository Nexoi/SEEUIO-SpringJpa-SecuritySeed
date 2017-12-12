package com.seeu.shopper.api.shop.user;

import com.seeu.shopper.user.model.UserInfo;
import com.seeu.shopper.user.model.UserLogin;
import com.seeu.shopper.user.repository.UserInfoRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by neo on 26/11/2017.
 * <p>
 * 用户信息
 * <p>
 * 1. 查询某用户信息
 * [GET] /user/info/{uid}
 * <p>
 * 2. 查询自己信息
 * [GET] /user/info
 * <p>
 * 3. 更新／增添自己信息
 * [POST/PUT] /user/info
 */
@RestController
@RequestMapping("/api/shop/v2/user/info")
public class UserInfoApi {

    @Resource
    private UserInfoRepository userInfoRepository;


    /**
     * 获取该用户信息，将来可考虑隐私设置，屏蔽部分内容
     *
     * @param uid
     * @return
     */
    @ApiOperation(notes = "获取该用户信息", value = "获取用户信息")
    @GetMapping("/{uid}")
    public ResponseEntity get(@PathVariable("uid") Long uid) {
        UserInfo info = userInfoRepository.findOne(uid);
        return info == null ? ResponseEntity.noContent().build() : ResponseEntity.ok(info);
    }

    /**
     * 获取自己的信息
     *
     * @param authUser
     * @return
     */
    @ApiOperation(notes = "获取自己的用户信息", value = "获取自己的用户信息")
    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity getMine(@AuthenticationPrincipal UserLogin authUser) {
        UserInfo info = userInfoRepository.findOne(authUser.getUid());
        return info == null ? ResponseEntity.noContent().build() : ResponseEntity.ok(info);
    }

    /**
     * 添加／更新一条地址信息，且用户必须为已登录用户，必须为自己的信息
     *
     * @param userInfo
     * @param authUser
     * @return
     */
    @ApiOperation(notes = "添加／更新一条用户信息，必须为自己的信息", value = "添加／更新用户信息")
    @PostMapping
    @PutMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity add(UserInfo userInfo,
                              @AuthenticationPrincipal UserLogin authUser) {
        // 直接 save，后端不需要对此做任何判断处理
        userInfo.setUid(authUser.getUid());
        userInfo.setCreateDate(null);
        userInfoRepository.save(userInfo); // 如果不存在则进行添加，存在则进行更新
        return ResponseEntity.ok().body("info save/update success");
    }

}
