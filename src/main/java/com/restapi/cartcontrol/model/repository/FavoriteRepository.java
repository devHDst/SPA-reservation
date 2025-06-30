package com.restapi.cartcontrol.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import com.restapi.cartcontrol.model.entity.Favorite;

@EnableJpaRepositories
public interface FavoriteRepository extends JpaRepository<Favorite,String>{
    @Query(value = "SELECT * FROM favorite WHERE userId = :userId", nativeQuery = true)
    Optional<Favorite> getUserFavorite(@Param("userId") String userId);
}
