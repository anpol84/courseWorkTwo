package org.example.components.pet;

import org.example.components.empolyee.Employee;
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
public class MyPetRepository {
    @PersistenceContext
    private EntityManager entityManager;
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public MyPetRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    public Iterable<Pet> filter(Double weight, String alias, String gender, String color, Double price){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pet> criteriaQuery = criteriaBuilder.createQuery(Pet.class);
        Root<Pet> petRoot = criteriaQuery.from(Pet.class);
        Predicate predicate = criteriaBuilder.conjunction();

        if (weight != null){
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(petRoot.get("weight"), weight));
        }
        if (alias != null && !alias.isEmpty()){
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(petRoot.get("alias"), alias));
        }
        if (gender != null && !gender.isEmpty()){
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(petRoot.get("gender"), gender));
        }
        if (color != null && !color.isEmpty()){
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(petRoot.get("color"), color));
        }
        if (price != null){
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(petRoot.get("price"), price));
        }


        criteriaQuery.where(predicate);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
