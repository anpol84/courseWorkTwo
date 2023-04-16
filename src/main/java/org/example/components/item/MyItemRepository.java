package org.example.components.item;

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
public class MyItemRepository {
    @PersistenceContext
    private EntityManager entityManager;
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public MyItemRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    public Iterable<Item> filter(String category, String pet, Double purchase_price, Double selling_price){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Item> criteriaQuery = criteriaBuilder.createQuery(Item.class);
        Root<Item> itemRoot = criteriaQuery.from(Item.class);
        Predicate predicate = criteriaBuilder.conjunction();
        if (category!= null && !category.isEmpty()){
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(itemRoot.get("category"), category));
        }
        if (pet != null && !pet.isEmpty()){
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(itemRoot.get("pet"), pet));
        }
        if (purchase_price != null){
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(itemRoot.get("purchasePrice"), purchase_price));
        }
        if (selling_price != null){
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(itemRoot.get("sellingPrice"), selling_price));
        }


        criteriaQuery.where(predicate);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
