package com.restapi.cartcontrol.requestbody.Reservation;

import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class RegistBody {
    private String waitCode;
    private String userId;
    private long shopId;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime expDate;
    private int chargePrice;
}
