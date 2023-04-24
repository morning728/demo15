package com.example.demo15.repository;
import com.example.demo15.Model.Item;
import com.example.demo15.Model.Order;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

//@Service
//@Data
public class Repository {

    private ArrayList<Order> orderList = new ArrayList<Order>(Arrays.asList(
            new Order("test4"),
            new Order("test5"),
            new Order("test6")
    ));
    private List<Item> itemList = new ArrayList<Item>(Arrays.asList(
//            new Item("test1", "test11"),
//            new Item("test2", "test22"),
//            new Item("test3", "test33")
    ));

    public void addOrderByCreationDate(String date){
        orderList.add(new Order(date));
    }

    public void removeOrderByCreationDate(String date){
        orderList.remove(orderList.stream().filter(u -> Objects.equals(u.getCreationDate(), date)).findAny().orElse(null));
    }
    public void addItemByCreationDate(String date){
//        itemList.add(new Item(date, "40"));
    }

    public void removeItemByCreationDate(String date){
        if(itemList.stream().anyMatch(u -> Objects.equals(u.getCreateDate(), date))){
//            itemList.remove(new Item(date, "40"));
        }
    }




}

