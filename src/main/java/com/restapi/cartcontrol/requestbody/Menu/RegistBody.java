package com.restapi.cartcontrol.requestbody.Menu;


import lombok.Data;

@Data
public class RegistBody {
    private long shopId;
    private int price;
    private String imgUrl;
}
