package com.seeu.shopper.api.admin;

import com.seeu.shopper.user.model.UserAuthRole;
import com.seeu.shopper.user.repository.UserAuthRoleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by neoxiaoyi.
 * User: neo
 * Date: 27/11/2017
 * Time: 4:19 PM
 * Describe:
 * <p>
 * 用户权限角色信息的增添、删除、查询
 * <p>
 * /////// 其实，提供这类操作给管理员并没有什么用，权限角色是在代码中控制的，新增／删除是毫无意义的。
 * /////// 故，只列出一个查询方法，提供给有可能的必要查询。
 * <p>
 * 1. 查询所有的角色信息【管理员】【分页】
 * [GET] /roles
 */

@RestController
@RequestMapping("/api/admin/v2/roles")
public class AuthApi {

    @Resource
    UserAuthRoleRepository userAuthRoleRepository;

    /**
     * 查询角色信息【管理员】【分页】
     *
     * @param page
     * @param size
     * @return
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity listAll(@RequestParam(defaultValue = "0") Integer page,
                                  @RequestParam(defaultValue = "10") Integer size) {
        Page rolesPage = userAuthRoleRepository.findAll(new PageRequest(page, size));
        return rolesPage.getTotalElements() == 0 ? ResponseEntity.noContent().build() : ResponseEntity.ok(rolesPage);
    }

//    @PostMapping
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity add(UserAuthRole role){
//        role.setId(null);
//    }

}
