package com.restapi.cartcontrol.model.entity;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table(name = "Reservation")
@Entity
public class Reservation{

    public static final int STATUS_RESERVED = 0;
    public static final int STATUS_COMPLETED = 1;
    public static final int STATUS_STRIPE_ERROR = 2;
    public static final int STATUS_QUEUE_ERROR = 3;
    public static final int STATUS_EXPIRED = 99;
    

    @Id
    @Column(name = "waitCode",nullable = false,unique = true)
    private String waitCode;
    @Column(name = "userId",nullable = false,unique = true)
    private String userId;
    @Column(name = "registerDate",nullable = false,unique = true)
    @DateTimeFormat
    private Date registerDate;
    @Column(name = "expireDate")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime expireDate;
    @Column(name = "status", nullable = false)
    private int status;
    @Column(name = "chargePrice", nullable = false)
    private int chargePrice;

    @ManyToOne
    @JoinColumn(name = "shop_id", nullable = false)
    @JsonBackReference
    private Shop shop;

    public Reservation(){}

    public Reservation(String waitCode, String userId,Date registerDate, LocalDateTime expireDate, int status,Shop shop, int chargePrice){
        this.waitCode = waitCode;
        this.userId = userId;
        this.registerDate = registerDate;
        this.expireDate = expireDate;
        this.status = status;
        this.shop = shop;
        this.chargePrice = chargePrice;
    }

}
