package org.example.components.kind;

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
public class MyKindRepository {
    @PersistenceContext
    private EntityManager entityManager;
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public MyKindRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    public Iterable<Kind> filter(String name, String eatingWay, String climateZone, String order){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Kind> criteriaQuery = criteriaBuilder.createQuery(Kind.class);
        Root<Kind> kindRoot = criteriaQuery.from(Kind.class);
        Predicate predicate = criteriaBuilder.conjunction();
        if (name != null && !name.isEmpty()){
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(kindRoot.get("name"), name));
        }
        if (eatingWay != null && !eatingWay.isEmpty()){
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(kindRoot.get("eatingWay"), eatingWay));
        }
        if (climateZone != null && !climateZone.isEmpty()){
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(kindRoot.get("climateZone"), climateZone));
        }
        if (order != null && !order.isEmpty()){
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(kindRoot.get("order"), order));
        }



        criteriaQuery.where(predicate);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
