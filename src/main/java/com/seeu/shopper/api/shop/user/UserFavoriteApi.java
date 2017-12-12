package com.seeu.shopper.api.shop.user;

import com.seeu.shopper.user.model.UserFavorite;
import com.seeu.shopper.user.model.UserLogin;
import com.seeu.shopper.user.repository.UserFavoriteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by neo on 26/11/2017.
 * <p>
 * 用户收藏夹
 * <p>
 * 1. 列出该用户下所有收藏商品【分页】
 * [GET] /user/favorite/{uid}/public
 * <p>
 * 2. 列出自己的所有收藏商品【分页】【本用户】
 * [GET] /user/favorite
 * <p>
 * 3. 添加一个收藏商品【本用户】
 * [POST] /user/favorite
 * <p>
 * 4. 删除一个收藏商品【本用户】
 * [DELETE] /user/favorite/{pid}
 */

@RestController
@RequestMapping("/api/shop/v2/user/favorite")
public class UserFavoriteApi {

    @Resource
    UserFavoriteRepository userFavoriteRepository;


    /**
     * 列出该用户下收藏商品【分页】
     * <p>
     * 以后可增添隐私设置，过滤掉部分信息
     *
     * @param uid
     * @param page 参数传递进来，默认为 0
     * @param size 参数传递进来，默认为 10
     * @return
     */
    @GetMapping("/{uid}/public")
    public ResponseEntity list(@PathVariable("uid") Long uid,
                               @RequestParam(defaultValue = "0") Integer page,
                               @RequestParam(defaultValue = "10") Integer size) {
        Page userFavoritePage = userFavoriteRepository.findAllByUid(new PageRequest(page, size), uid);
        return userFavoritePage.getTotalElements() == 0 ? ResponseEntity.noContent().build() : ResponseEntity.ok(userFavoritePage);
    }

    /**
     * 列出自己的收藏商品【分页】【本用户】
     *
     * @param authUser
     * @param page
     * @param size
     * @return
     */
    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity listMine(@AuthenticationPrincipal UserLogin authUser,
                                   @RequestParam(defaultValue = "0") Integer page,
                                   @RequestParam(defaultValue = "10") Integer size) {
        Page userFavoritePage = userFavoriteRepository.findAllByUid(new PageRequest(page, size), authUser.getUid());
        return userFavoritePage.getTotalElements() == 0 ? ResponseEntity.noContent().build() : ResponseEntity.ok(userFavoritePage);
    }

    /**
     * 添加收藏商品信息，一次只能添加一条记录【本用户】
     *
     * @param authUser
     * @param pid   按参数传递进来
     * @return
     */
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity add(@AuthenticationPrincipal UserLogin authUser,
                              @RequestParam("pid") Long pid) {
        UserFavorite favorite = new UserFavorite();
        favorite.setUid(authUser.getUid());
        favorite.setPid(pid);
        favorite.setCreateDate(new Date());
        userFavoriteRepository.save(favorite);
        return ResponseEntity.ok().body("added success!");
    }

    /**
     * 删除自己的某一条收藏信息【本用户】
     *
     * @param authUser
     * @param pid
     * @return
     */
    @DeleteMapping("/{pid}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity delete(@AuthenticationPrincipal UserLogin authUser,
                                 @PathVariable("pid") Long pid) {
        userFavoriteRepository.deleteByUidAndPid(authUser.getUid(), pid);
        return ResponseEntity.ok().body("delete success!");
    }
}
