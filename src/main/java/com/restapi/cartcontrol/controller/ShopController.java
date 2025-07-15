package com.restapi.cartcontrol.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.restapi.cartcontrol.model.entity.Shop;
import com.restapi.cartcontrol.model.repository.ShopRepository;
import com.restapi.cartcontrol.requestbody.Shop.RegistBody;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/shop")
@CrossOrigin(origins = "*", methods = {RequestMethod.POST})
public class ShopController {
    
    @Autowired
    ShopRepository shopRepository;

    @PostMapping("/searchShop")
    public List<Shop> getSearchShop(@RequestBody String addressInfo) {
        List<Shop> searchList = shopRepository.searchShopByAddress(addressInfo);
        return searchList;
    }
    
    @PostMapping("/registShop")
    public String registShop(@RequestBody RegistBody registBody) {
        Shop newShop = new Shop();

        newShop.setAddress(registBody.getAddress());
        newShop.setStatus(registBody.isStatus());
        newShop.setOpenhour(registBody.getOpenhour());
        newShop.setShopComment(registBody.getShopComment());
        
        shopRepository.save(newShop);

        return null;
    }
    
    @PostMapping("/detail")
    public Shop getShopDetail(long id){
        Shop shop;
        try {
            shop = shopRepository.getShopDetailInfo(id);
            return shop;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }

    }
}
