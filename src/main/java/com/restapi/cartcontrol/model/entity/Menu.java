package com.restapi.cartcontrol.model.entity;

import jakarta.persistence.Id;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table(name = "Menu")
@Entity
public class Menu {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name",nullable = false)
    private String name;
    @Column(name = "price",nullable = false)
    private int price;
    @Column(name = "imgUrl")
    private String imgUrl;

    @ManyToOne
    @JoinColumn(name = "shop_id", nullable = false)
    @JsonBackReference
    private Shop shop;

    public Menu(){
    }

    public Menu(long id, int price, String imgUrl, String name,Shop shop){
        this.id = id;
        this.price = price;
        this.imgUrl = imgUrl;
        this.name = name;
        this.shop = shop;
    }
    public Menu(long id, int price, String imgUrl){
        this.id = id;
        this.price = price;
        this.imgUrl = imgUrl;
    }
}
