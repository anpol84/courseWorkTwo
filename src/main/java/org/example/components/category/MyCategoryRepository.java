package org.example.components.category;

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
public class MyCategoryRepository {
    @PersistenceContext
    private EntityManager entityManager;
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public MyCategoryRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    public Iterable<Category> filter(String name, String purpose, String averageSize){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Category> criteriaQuery = criteriaBuilder.createQuery(Category.class);
        Root<Category> categoryRoot = criteriaQuery.from(Category.class);
        Predicate predicate = criteriaBuilder.conjunction();
        if (name!= null && !name.isEmpty()){
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(categoryRoot.get("name"), name));
        }
        if (purpose != null && !purpose.isEmpty()){
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(categoryRoot.get("purpose"), purpose));
        }
        if (averageSize != null && !averageSize.isEmpty()){
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(categoryRoot.get("averageSize"), averageSize));
        }



        criteriaQuery.where(predicate);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
