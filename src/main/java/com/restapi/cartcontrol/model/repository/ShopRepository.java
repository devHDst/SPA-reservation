package com.restapi.cartcontrol.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import com.restapi.cartcontrol.model.entity.Reservation;
import com.restapi.cartcontrol.model.entity.Shop;


@EnableJpaRepositories
public interface ShopRepository extends JpaRepository<Shop,Long>{
    @Query(value = "SELECT reservation FROM Shop WHERE id = :id",nativeQuery = true)
    List<Reservation> getReservationList(@Param("id") long id);
    
    @Query(value = "SELECT * FROM Shop WHERE address LIKE CONCAT('%',:searchinfo,'%')",nativeQuery = true)
    List<Shop> searchShopByAddress(@Param("searchInfo") String searchInfo);

    @Query(value = "SELECT * FROM Shop WHERE id = :id", nativeQuery = true)
    Shop getShopDetailInfo(@Param("id") long id);
}
