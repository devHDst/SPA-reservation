package com.restapi.cartcontrol.model.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table(name = "favorite")
@Entity
public class Favorite {
    @Id
    @Column(name = "userId", nullable = false, unique = true)
    private String userId;
    
    @OneToMany(mappedBy = "favorite",cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Shop> shopList;

    public Favorite(){}
    public Favorite(String userId, List<Shop> shopList){
        this.userId = userId;
        this.shopList = shopList;
    }
}
