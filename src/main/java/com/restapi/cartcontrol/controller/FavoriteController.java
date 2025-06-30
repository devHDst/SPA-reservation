package com.restapi.cartcontrol.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restapi.cartcontrol.model.entity.Favorite;
import com.restapi.cartcontrol.model.entity.Shop;
import com.restapi.cartcontrol.model.repository.FavoriteRepository;
import com.restapi.cartcontrol.model.repository.ShopRepository;
import com.restapi.cartcontrol.requestbody.Favorite.RegistBody;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/favorite")
public class FavoriteController {
    
    @Autowired
    FavoriteRepository favoriteRepository;
    @Autowired
    ShopRepository shopRepository;

    @PostMapping("/getfavshop")
    public Optional<Favorite> getFavoriteList(@RequestBody String  userId) {
        try {
            Optional<Favorite> favoriteList = favoriteRepository.getUserFavorite(userId);
            return favoriteList;
        } catch (Exception e) {
            System.err.println("通信エラーが発生しました");
            return null;
        }
    }
    
    @PostMapping("/add")
    public String registerFavorite(@RequestBody RegistBody registBody) {
        Optional<Favorite> targetRecord = favoriteRepository.getUserFavorite(registBody.getUserId()); 
        Shop targetShop = shopRepository.getShopDetailInfo(registBody.getSelectedShopId());
        if(!targetRecord.isPresent() && targetShop != null){
            Favorite newInfo = new Favorite();
            newInfo.setUserId(registBody.getUserId());
            List<Shop> favoriteList = new ArrayList<Shop>();
            
            if(targetShop != null){
                favoriteList.add(targetShop);
                newInfo.setShopList(favoriteList);
            }
            favoriteRepository.save(newInfo);
        }else{
            Favorite updateInfo =  targetRecord.get();
            List<Shop> shopListForUsr =  targetRecord.get().getShopList();
            shopListForUsr.add(targetShop);
            updateInfo.setShopList(shopListForUsr);
            favoriteRepository.save(updateInfo);
        }
        return "更新いたしました";
    }
    
}