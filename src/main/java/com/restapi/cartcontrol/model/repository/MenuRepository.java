package com.restapi.cartcontrol.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import com.restapi.cartcontrol.model.entity.Menu;

@EnableJpaRepositories
public interface MenuRepository extends JpaRepository<Menu,Long>{


    @Query(value = "SELECT * FROM menu m WHERE m.shop_id = ?1", nativeQuery = true)
    List<Menu> getMenuList(@Param("shopId") long shopId);

    @Query(value = "SELECT * FROM menu WHERE shop_id = :shopId Limit 1",nativeQuery = true)
    Menu getChooseMenu(@Param("shopId") long shopId);
}
