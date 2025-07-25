package com.restapi.cartcontrol.controller;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restapi.cartcontrol.model.entity.Reservation;
import com.restapi.cartcontrol.model.entity.Shop;
import com.restapi.cartcontrol.model.repository.MenuRepository;
import com.restapi.cartcontrol.model.repository.ReservationRepository;
import com.restapi.cartcontrol.model.repository.ShopRepository;
import com.restapi.cartcontrol.requestbody.Reservation.CancelBody;
import com.restapi.cartcontrol.requestbody.Reservation.RegistBody;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/reservation")
public class ReservationController {
    
    private final static Logger log = LoggerFactory.getLogger(ReservationController.class);
    private final static String SUCCESS_MSG = "予約登録しました。当日店舗にお越しください";
    private final static String FAILURE_MSG = "予約に失敗しました。";
    private final static String ID_NOT_FOUND_MEG = "予約IDが見つかりません";
    private final static String BAD_PARAM_MSG = "日付が過去または空欄の場合、予約できません。";
    private final static String SUCCESS_CANCEL_MSG = "正常にキャンセルできました";
    
    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    ShopRepository shopRepository;
    @Autowired
    MenuRepository menuRepository;

    @PostMapping("/getReservationInfo")
    public Reservation getReservationInfo(@RequestBody String userId) {
        Reservation reservation;
        try {
            reservation = reservationRepository.getUserReservation(userId);
            return reservation;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }
    
    @PostMapping("/registReservation")
    public Map<String, String> registReservation(@RequestBody RegistBody reservatoinInfo) throws Exception{
        // 店舗管理機能未実装のため暫定対応
        Shop checkShop = shopRepository.getShopDetailInfo(1);
        int chargePrice = (reservatoinInfo.getChargePrice() > 0) ? reservatoinInfo.getChargePrice() : 0;

        Map<String, String> response = new HashMap<>(); 
        // 日付チェック
        LocalDateTime current = LocalDateTime.now();
        // 予約日時が過去かデフォルト値なら返却
        if(current.isAfter(reservatoinInfo.getExpDate()) || current.equals(reservatoinInfo.getExpDate())){
            response.put("message", BAD_PARAM_MSG);
            return response;
        }

        // レスポンス準備
        try {
            Reservation newReservation = new Reservation();
            newReservation.setUserId(reservatoinInfo.getUserId());
            newReservation.setWaitCode(reservatoinInfo.getWaitCode());
            newReservation.setRegisterDate(new Date());
            newReservation.setExpireDate(reservatoinInfo.getExpDate());
            newReservation.setStatus(Reservation.STATUS_RESERVED);
            newReservation.setChargePrice(chargePrice);
            // 店舗管理機能未実装のため保留
            newReservation.setShop(checkShop);

            reservationRepository.save(newReservation);
            log.info("Controller: reservation Saved");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            response.put("message", ReservationController.FAILURE_MSG);
            return response;
        }
        
        response.put("message", ReservationController.SUCCESS_MSG);
        return response;
    }

    @PostMapping("/updateReserve")
    public String updateReserve(@RequestBody Reservation reservation) {
        LocalDateTime reserveTime = reservation.getExpireDate();
        LocalDateTime current = LocalDateTime.now();

        String returnMsg = "";
        if(reserveTime.isAfter(current)){
            try {
                reservationRepository.updateExpiredReservation(reserveTime);
                returnMsg = "予約時間を過ぎてしまいました";
                return returnMsg;
            } catch (Exception e) {
                returnMsg = e.getMessage();
                return returnMsg;
            }   
        }
        return returnMsg;
    }

    @PostMapping("/cancelReservation")
    public Map<String,String> postMethodName(@RequestBody CancelBody cancelBody) throws Exception {
        System.out.println(cancelBody.getWaitCode());
        Reservation checkinfo = reservationRepository.getReservationByWaitCd(cancelBody.getWaitCode());
        Map<String, String> response = new HashMap<>();

        if(checkinfo == null){
            log.error(ID_NOT_FOUND_MEG);
            response.put("message", ID_NOT_FOUND_MEG);
            return response;
        }

        // キャンセル通知メールの確認処理をここで実装(メール失敗したら巻き戻し)

        try {
            int result = reservationRepository.updateStatus(cancelBody.getWaitCode(), Reservation.STATUS_CANCELED);            
            System.out.println(result);
        } catch (Exception e) {
            log.error(e.getMessage());
            response.put("message", e.getMessage());
            return response;
        }

        response.put("message", SUCCESS_CANCEL_MSG);
        return response;
    }
    
}
