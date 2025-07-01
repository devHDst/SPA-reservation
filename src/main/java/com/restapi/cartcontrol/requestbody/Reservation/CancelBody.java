package com.restapi.cartcontrol.requestbody.Reservation;

import lombok.Data;

@Data
public class CancelBody {
    private String waitCode;
    private String userId;
}
