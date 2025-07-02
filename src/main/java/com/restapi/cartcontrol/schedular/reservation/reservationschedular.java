package com.restapi.cartcontrol.schedular.reservation;

import java.time.LocalDateTime;
import java.util.List;


import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.restapi.cartcontrol.model.entity.Reservation;
import com.restapi.cartcontrol.model.repository.MenuRepository;
import com.restapi.cartcontrol.model.repository.ReservationRepository;
import com.restapi.cartcontrol.schedular.MQ.PaymentSender;

import org.slf4j.Logger;

@Configuration
@EnableScheduling
public class reservationschedular {
    
    private final static Logger log = LoggerFactory.getLogger(reservationschedular.class);

    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    MenuRepository menuRepository;
    @Autowired
    PaymentSender paymentSender;

    // @Scheduled(cron = "1 */15 * * * *", zone = "Asia/Tokyo")
    public void expireReservation(){
        LocalDateTime current = LocalDateTime.now();
        try {
            reservationRepository.updateExpiredReservation(current);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @Scheduled(cron = "0 */15 * * * *", zone = "Asia/Tokyo")
    public void updateExecutedPayment(){
        log.info("start send queue");
        List<Reservation> checkedInfo;
        checkedInfo = reservationRepository.getPaymentInfo();
        if(checkedInfo.isEmpty()){  
            log.error("schedular error: paymentInfo not found");
            return;
        }else{
            log.info("sending queue");
            for (Reservation reservation : checkedInfo) {
                LocalDateTime current = LocalDateTime.now();
                if(current.isAfter(reservation.getExpireDate())){
                    // 支払い料金を設定しているならStripeを待ってから完了に更新
                    if(reservation.getChargePrice() > 0){
                        try {
                            paymentSender.sendQueue(reservation.getWaitCode(),reservation.getChargePrice());
                            reservationRepository.updateStatus(reservation.getWaitCode(), Reservation.STATUS_COMPLETED);    
                        }catch (Exception e) {
                            System.err.println("schedular error:"+e.getMessage());
                            log.error("schedular error:" + e.getMessage());
                        }
                    }else{
                        //それ以外の ステータスキャンセル(2)して予約情報は完了に更新
                        reservationRepository.updateStatus(reservation.getWaitCode(), Reservation.STATUS_COMPLETED);
                    }
                }
            }
            log.info("complete queue");
        }
        return;
    }   
}
