package org.example.components.shop;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Integer> {
    Shop getById(Long id);
    void deleteById(Long id);
    Optional<Shop> findById(Long id);

}
