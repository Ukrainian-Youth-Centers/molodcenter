package com.katok.molodcenter.category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Page<Category> findAllByYouthCenterIsNull(Pageable pageable);
    Page<Category> findAllByYouthCenterId(Long youthCenterId, Pageable pageable);
    Optional<Category> findByExternalId(String externalId);
}