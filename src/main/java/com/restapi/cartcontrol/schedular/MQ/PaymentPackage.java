package com.restapi.cartcontrol.schedular.MQ;

import lombok.Data;

@Data
public class PaymentPackage {
    private String waitCode;
    private int servicePrice;
}
