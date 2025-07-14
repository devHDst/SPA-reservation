package com.restapi.cartcontrol.model.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.restapi.cartcontrol.model.entity.Reservation;


@EnableJpaRepositories
public interface ReservationRepository extends JpaRepository<Reservation,String>{
    @Query(value = "SELECT * FROM reservation WHERE userId = :userId and status = 0",nativeQuery = true)
    Reservation getUserReservation(@Param("userId") String userId);

    @Query(value = "SELECT * FROM reservation WHERE wait_code = :waitCode and status = 0",nativeQuery = true)
    Reservation getReservationByWaitCd(@Param("waitCode") String waitCode);

    @Query(value = "SELECT * FROM reservation Where status = 0",nativeQuery = true)
    List<Reservation> getPaymentInfo();
    
    @Query(value = "UPDATE reservation SET status = 2 WHERE wait_code = :waitCode",nativeQuery = true)
    @Modifying
    @Transactional
    int setCheckin(@Param("waitCode") int waitCode);

    @Query(value = "UPDATE reservation SET status = :status WHERE wait_code = :waitCode",nativeQuery = true)
    @Transactional
    @Modifying
    int updateStatus(@Param("waitCode") String waitCode, @Param("status") int status);

    @Query(value = "UPDATE reservation SET status = 3 WHERE expire_date < :expireDate and status = 0",nativeQuery = true)
    @Modifying
    @Transactional
    int updateExpiredReservation(@Param("expireDate") LocalDateTime expireDate);
}