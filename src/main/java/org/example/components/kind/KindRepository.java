package org.example.components.kind;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KindRepository extends JpaRepository<Kind, Long> {
}
