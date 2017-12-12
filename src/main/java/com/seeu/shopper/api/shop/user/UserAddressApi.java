package com.seeu.shopper.api.shop.user;

import com.seeu.shopper.user.model.UserAddress;
import com.seeu.shopper.user.model.UserLogin;
import com.seeu.shopper.user.repository.UserAddressRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by neo on 26/11/2017.
 * <p>
 * 用户地址信息
 * <p>
 * 1. 查询某用户所有地址
 * [GET] /user/address/{uid}/public
 * <p>
 * 2. 查询某用户某一条地址
 * [GET] /user/address/{uid}/{id}/public
 * <p>
 * 3. 查询自己的地址信息【本用户】
 * [GET] /user/address
 * <p>
 * 4. 查询自己的某一条地址信息【本用户】
 * [GET] /user/address/{id}
 * <p>
 * 5. 添加一条地址信息【本用户】
 * [POST] /user/address
 * <p>
 * 6. 删除一条地址信息【本用户】
 * [DELETE] /user/address/{id}
 * <p>
 * 7. 更新一条地址信息【本用户】
 * [PUT] /user/address/{id}
 */
@RestController
@RequestMapping("/api/shop/v2/user/address")
public class UserAddressApi {

    @Resource
    private UserAddressRepository userAddressRepository;

    /**
     * 获取该用户所有地址条目信息
     *
     * @param uid
     * @return
     */
    @ApiOperation(notes = "获取该用户所有地址条目信息", value = "获取地址组")
    @GetMapping("/{uid}/public")
    public ResponseEntity list(@PathVariable("uid") Long uid) {
        List<UserAddress> address = userAddressRepository.findByUid(uid);
        return address.size() == 0 ? ResponseEntity.noContent().build() : ResponseEntity.ok(address);
    }

    /**
     * 获取该用户某一条地址信息（ uid 必须与 地址Id 匹配）
     *
     * @param id
     * @param uid
     * @return
     */
    @ApiOperation(notes = "获取该用户某一条地址信息（ uid 必须与 地址Id 匹配）", value = "获取地址")
    @GetMapping("/{uid}/{id}/public")
    public ResponseEntity get(@PathVariable("id") Long id, @PathVariable("uid") Long uid) {
        UserAddress addresses = userAddressRepository.findByIdAndUid(id, uid);
        return addresses == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(addresses);
    }

    /**
     * 获取该用户所有地址条目信息【本用户】
     *
     * @return
     */
    @ApiOperation(notes = "获取该用户所有地址条目信息", value = "获取地址组")
    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity listMine(@AuthenticationPrincipal UserLogin authUser) {
        List<UserAddress> address = userAddressRepository.findByUid(authUser.getUid());
        return address.size() == 0 ? ResponseEntity.noContent().build() : ResponseEntity.ok(address);
    }

    /**
     * 获取该用户某一条地址信息【本用户】
     *
     * @param authUser
     * @param id
     * @return
     */
    @ApiOperation(notes = "获取该用户某一条地址信息（ uid 必须与 地址Id 匹配）", value = "获取地址")
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity getMine(@AuthenticationPrincipal UserLogin authUser, @PathVariable("id") Long id) {
        UserAddress addresses = userAddressRepository.findByIdAndUid(id, authUser.getUid());
        return addresses == null ? ResponseEntity.noContent().build() : ResponseEntity.ok(addresses);
    }


    /**
     * 添加一条地址信息，且用户必须为已登录用户
     *
     * @param userAddress
     * @param authUser
     * @return
     */
    @ApiOperation(notes = "添加一条地址信息，且用户必须为已登录用户", value = "添加地址")
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity add(UserAddress userAddress,
                              @AuthenticationPrincipal UserLogin authUser) {
        // 直接 save，后端不需要对此做任何判断处理
        userAddress.setUid(authUser.getUid());
        userAddressRepository.save(userAddress);
        return ResponseEntity.ok().body("address save success");
    }

    /**
     * 删除该用户的某一组地址信息（ uid 必须与 地址Id 匹配）
     *
     * @param ids
     * @param authUser
     * @return 永远返回 success，所以删除后需要用户刷新页面进行确认
     */
    @ApiOperation(notes = "删除该用户的某一组地址信息（ uid 必须与 地址Id 匹配）", value = "删除地址")
    @DeleteMapping("/{ids}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity delete(@AuthenticationPrincipal UserLogin authUser,
                                 @ApiParam(example = "1,2,3,4")
                                 @PathVariable("ids") String ids) {
        // 规整化数据 ids
        String[] idsStrings = ids.split(",");
        List<Long> idsLongs = new ArrayList<>();
        for (String id : idsStrings) {
            idsLongs.add(Long.parseLong(id));
        }
        if (idsLongs.size() == 0)
            return ResponseEntity.badRequest().body("Please input a correct id list like : 1,2,3,4 ( use ',' to split this string)");
        Iterator<Long> iteratorIds = idsLongs.iterator();
        userAddressRepository.deleteInBatchByIdAndUid(iteratorIds, authUser.getUid());
        return ResponseEntity.ok().body("delete success");
    }


    /**
     * 更新该用户下某一条地址信息（ uid 必须与 地址Id 匹配）
     *
     * @param id
     * @param authUser
     * @param userAddress
     * @return
     */
    @ApiOperation(notes = "更新该用户下某一条地址信息（ uid 必须与 地址Id 匹配）", value = "更新地址")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity update(@PathVariable("id") Long id,
                                 @AuthenticationPrincipal UserLogin authUser,
                                 UserAddress userAddress) {
        userAddress.setUid(authUser.getUid());
        userAddress.setId(id);
        userAddress.setCreateTime(null); // 该项不可修改
        userAddress.setUpdateTime(new Date());
        userAddressRepository.save(userAddress); // 主键 id 必须存在，否则会被视作新增
        return ResponseEntity.ok().body("update success!");
    }

}
