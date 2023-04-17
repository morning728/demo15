package com.example.demo15.controllers;


import com.example.demo15.Model.Item;
import com.example.demo15.Model.Order;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Controller
public class OrderController {

    //private final repositoryPostgres repository;

    private final SessionFactory sessionFactory;

    private Session session;

    @PostConstruct
    public void sessionCreation(){
        session = sessionFactory.openSession();
    }

    @PreDestroy
    public void sessionDestroying(){
        session.close();
    }

    @GetMapping("/test")
    public String temproraty() {
        return "test";
    }

    @GetMapping("/h")
    public String webhook() throws IOException, InterruptedException {
        //String str = getWebHook("asdf");
//        log.info(str);
//        if (str.contains("public_url\":\"https")) {
//            log.info(str.substring(str.indexOf("public_url\":\"https") + 13,
//                    str.indexOf("\",", str.indexOf("public_url\":\"https") + 13)));
//        }
        //log.info(getWebHook("asd"));
        return "qwe";
    }

    @GetMapping("/home")
    public String showOrders(Model model){
        Transaction transaction = session.beginTransaction();
        List<Order> orders = session.createQuery("SELECT o FROM Order o", Order.class).getResultList();
        transaction.commit();
//        List<Order> orders = repository.findAll();
        model.addAttribute("orders", orders);
        model.addAttribute("order", new Order());
        return "home";
    }
    @PostMapping("/home")
    public String addOrder(@ModelAttribute("order") Order order, Model model){
        Session s = sessionFactory.openSession();
        Transaction transaction = s.beginTransaction();
        s.persist(order);
        transaction.commit();
        s.close();
//        System.out.println(order);
//        repository.saveAndFlush(order);
        return "redirect:/home";
    }
    @DeleteMapping("/home")
    public String deleteStation(@RequestParam("id") Long id){
        Transaction transaction = session.beginTransaction();
        List<Order> order = session.createQuery("SELECT o FROM Order o WHERE o.id = :id", Order.class)
                .setParameter("id", id)
                .getResultList();
        session.remove(order.get(0));
        session.flush();
        transaction.commit();
//        if(repository.findById(Long.valueOf(id)).isPresent()){
//            repository.delete(repository.findById(Long.valueOf(id)).get());
//        }
        //repository.delete(repository.findById(Long.valueOf(id)));
        return "redirect:/home";
    }

    @GetMapping("/home/order")
    public String showOrders(@RequestParam("id") Long id, Model model){
        Transaction transaction = session.beginTransaction();
        Order order = session.createQuery("SELECT o FROM Order o WHERE o.id = :id", Order.class)
                .setParameter("id", id)
                .getSingleResult();
        transaction.commit();
//        List<Order> orders = repository.findAll();
        model.addAttribute("order_items", order.getItems());
        model.addAttribute("order", order);
        model.addAttribute("item", new Item());
        return "order";
    }

}
