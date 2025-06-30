package com.restapi.cartcontrol.model.DTO;

public class MenuDto {
    private long id;
    private int price;
    private String imgUrl;

    public MenuDto(long id, int price, String imgUrl) {
        this.id = id;
        this.price = price;
        this.imgUrl = imgUrl;
    }
}
