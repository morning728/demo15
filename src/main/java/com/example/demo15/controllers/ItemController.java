package com.example.demo15.controllers;

import com.example.demo15.Model.Item;
import com.example.demo15.Model.Order;
import com.example.demo15.repository.repositoryPostgres;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ItemController {

    private final repositoryPostgres repository;
    private final EntityManager em;
    private final SessionFactory sessionFactory;

    private Session session;

    @PostConstruct
    public void sessionCreation() {
        session = sessionFactory.openSession();
    }

    @PreDestroy
    public void sessionDestroying() {
        session.close();
    }


    @GetMapping("/items")
    public String showItems(@RequestParam(value = "sort", required=false) String sort,Model model) {
        List<Item> items;
        if(sort == null) {
            Transaction transaction = session.beginTransaction();
            items = session.createQuery("SELECT i FROM Item i", Item.class).getResultList();
            transaction.commit();


            //List<Order> orders = repository.findAll();
        }
        else{
                CriteriaBuilder cb = em.getCriteriaBuilder();
                CriteriaQuery<Item> cq = cb.createQuery(Item.class);

                Root<Item> manufacture = cq.from(Item.class);
                Predicate addressPredicate = cb.equal(manufacture.get("price"), sort);
                cq.where(addressPredicate);
                TypedQuery<Item> query = em.createQuery(cq);
                items = query.getResultList();
        }

        model.addAttribute("items", items);
        return "items";
    }

    @PostMapping("/items")
    public String addItem(@RequestParam("id") Long id,
                            @ModelAttribute("item") Item item,
                            Model model) {
        //Transaction transaction = session.beginTransaction();


        //item.setId(session.createQuery("SELECT MAX(id) from Item", Long.class).getSingleResult() + 1);
        //System.out.println(item);
        //session.persist(item);
        //System.out.println(item.toString());
        //transaction.commit();
        Session s = sessionFactory.openSession();
        Transaction transaction = s.beginTransaction();
//НАВДО ОБНОВЛЯТЬ
        Item item1 = new Item();
        item1.setPrice(item.getPrice());
        item1.setCreateDate(item.getCreateDate());
        item1.setOrder(session.createQuery("SELECT o FROM Order o WHERE o.id = :id", Order.class)
                        .setParameter("id", id)
                        .getSingleResult());
        s.persist(item1);
        transaction.commit();
        s.close();
        return  "redirect:/home";
    }
}

//    CriteriaBuilder cb = em.getCriteriaBuilder();
//    CriteriaQuery<Manufacture> cq = cb.createQuery(Manufacture.class);
//
//    Root<Manufacture> manufacture = cq.from(Manufacture.class);
//    Predicate addressPredicate = cb.equal(manufacture.get("address"), address);
//       cq.where(addressPredicate);
//               TypedQuery<Manufacture> query = em.createQuery(cq);
//        return query.getResultList();
