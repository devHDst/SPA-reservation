package com.restapi.cartcontrol.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restapi.cartcontrol.model.entity.Menu;
import com.restapi.cartcontrol.model.entity.Shop;
import com.restapi.cartcontrol.model.repository.MenuRepository;
import com.restapi.cartcontrol.model.repository.ShopRepository;
import com.restapi.cartcontrol.requestbody.Menu.RegistBody;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    MenuRepository menuRepository;
    
    @Autowired
    ShopRepository shopRepository;

    @PostMapping("/getMenuList")
    public List<Menu> getShopMenu(@RequestBody  Map<String, Object> data) throws Exception{
        int id = (int) data.get("id");
        System.out.println("id"+id);
        List<Menu> menuList;
        Long shopId = Long.valueOf(id);
        try{
            menuList = menuRepository.getMenuList(shopId);
            if(menuList == null){
                throw new Exception("メニュー情報が存在しません");
            }
            return menuList;
        }catch(Exception e){
            System.err.println(e.getMessage());
            return null;
        }
    }

    @PostMapping("/addMenu")
    public void postMethodName(@RequestBody RegistBody registBody) throws Exception{
        
        Shop targetShop = shopRepository.getShopDetailInfo(registBody.getShopId());
        // 店舗情報が存在する場合
        if(targetShop != null){
            Menu registMenu = new Menu();
            registMenu.setPrice(registBody.getPrice());
            registMenu.setImgUrl(registBody.getImgUrl());
            // registMenu.setShopId((int)registBody.getShopId());
            
            menuRepository.save(registMenu);
        }
    }
    
}
