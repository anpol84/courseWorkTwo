package org.example.components.address;

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
public class MyAddressRepository {
    @PersistenceContext
    private EntityManager entityManager;
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public MyAddressRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    public Iterable<Address> filter(String region, String city, String street, Integer house, Integer flat){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Address> criteriaQuery = criteriaBuilder.createQuery(Address.class);
        Root<Address> addressRoot = criteriaQuery.from(Address.class);
        Predicate predicate = criteriaBuilder.conjunction();
        if (region!= null && !region.isEmpty()){
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(addressRoot.get("region"), region));
        }
        if (city != null && !city.isEmpty()){
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(addressRoot.get("city"), city));
        }
        if (street != null && !street.isEmpty()){
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(addressRoot.get("street"), street));
        }
        if (house != null){
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(addressRoot.get("house"), house));
        }
        if (flat != null){
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(addressRoot.get("flat"), flat));
        }

        criteriaQuery.where(predicate);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
