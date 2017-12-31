package com.lashes.dao;

import com.lashes.entities.Stock;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class InventoryDaoImpl implements InventoryDao {


    @PersistenceContext
    EntityManager em;
    private  final int pageSize = 10;

    public List<Stock> getAllItems(int firstResult){
       Query selectQuery = em.createQuery("select  s from RgProduct s");
       selectQuery.setFirstResult(firstResult*pageSize);
       selectQuery.setMaxResults(pageSize);
       List<Stock> stockList = selectQuery.getResultList();
       return stockList;
    }


    public  int getLastPageNumber(){
        String countQ = "Select count(s.id) from RgProduct s";
        Query countQuery = em.createQuery(countQ);
        Long countResult= (Long) countQuery.getSingleResult();
        int lastPageNumber = (int) (Math.ceil((countResult/pageSize)));
        System.out.println(lastPageNumber);
        return lastPageNumber;

    }




}
