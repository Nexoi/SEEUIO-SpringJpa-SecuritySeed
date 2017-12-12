package com.seeu.shopper.user.repository;

import com.seeu.shopper.user.model.UserFavorite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface UserFavoriteRepository extends JpaRepository<UserFavorite, Long> {

    Page findAllByUid(Pageable pageable, @Param("uid") Long uid);

    void deleteByUidAndPid(@Param("uid") Long uid, @Param("pid") Long pid);
}