package com.restapi.cartcontrol.requestbody.Favorite;

import lombok.Data;

@Data
public class RegistBody {
    private String userId;
    private long selectedShopId;
}
