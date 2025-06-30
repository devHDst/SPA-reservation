package com.restapi.cartcontrol.requestbody.Shop;

import java.util.Date;

import lombok.Data;

@Data
public class RegistBody {
    private String address;
    private boolean status;
    private Date openhour;
    private String shopComment;
}
