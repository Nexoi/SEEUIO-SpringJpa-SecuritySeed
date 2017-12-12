package com.seeu.shopper.user.repository;

import com.seeu.shopper.user.model.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Iterator;
import java.util.List;

public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {

    List<UserAddress> findByUid(@Param("uid") Long uid);

    UserAddress findByIdAndUid(@Param("id") Long id, @Param("uid") Long uid);

    void deleteInBatchByIdAndUid(@Param("id") Iterator<Long> id, @Param("uid") Long uid);

}