package org.example.components.shop;

import org.example.components.pet.Pet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.sql.DataSource;

@Repository
public class MyShopRepository {
    @PersistenceContext
    private EntityManager entityManager;
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public MyShopRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    public Iterable<Shop> filter(String address, String phone){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Shop> criteriaQuery = criteriaBuilder.createQuery(Shop.class);
        Root<Shop> shopRoot = criteriaQuery.from(Shop.class);
        Predicate predicate = criteriaBuilder.conjunction();
        if (address!= null && !address.isEmpty()){
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(shopRoot.get("address"), address));
        }
        if (phone != null && !phone.isEmpty()){
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(shopRoot.get("phone"), phone));
        }



        criteriaQuery.where(predicate);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
