package com.restapi.cartcontrol.model.entity;

import java.util.List;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table(name = "Shop")
@Entity
public class Shop{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "address",nullable = false)
    private String address;
    @Column(name = "status",nullable = false)
    private boolean status;
    @Column(name = "openhour",nullable = false)
    @DateTimeFormat
    private Date openhour;
    @Column(name = "shopComment")
    private String shopComment;
    // jsonアノテーションで循環参照回避
    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Reservation> reservation;
    @OneToMany(mappedBy = "shop",cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Menu> menu;

    @ManyToOne
    @JoinColumn(name = "favorite_id",nullable = true)
    @JsonBackReference
    private Favorite favorite;

    public Shop(){
    }
    public Shop(long id, String address, boolean status, List<Reservation> reservation,List<Menu> menu, Date openhour, String shopComment){
        this.id = id;
        this.address = address;
        this.status = status;
        this.reservation = reservation;
        this.menu = menu;
        this.openhour = openhour;
        this.shopComment = shopComment;
    }
}
