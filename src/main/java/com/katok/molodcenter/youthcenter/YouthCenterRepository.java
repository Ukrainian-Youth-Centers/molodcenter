package com.katok.molodcenter.youthcenter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface YouthCenterRepository extends JpaRepository<YouthCenter, Long> {
    @Query(value = """
        SELECT * FROM youth_centers c 
        WHERE (6371 * acos(cos(radians(:latitude)) * cos(radians(c.latitude)) 
        * cos(radians(c.longitude) - radians(:longitude)) + sin(radians(:latitude)) 
        * sin(radians(c.latitude)))) <= :radius
        """, nativeQuery = true)
    Page<YouthCenter> findNearby(
            @Param("latitude") Float latitude,
            @Param("longitude") Float longitude,
            @Param("radius") Float radius,
            Pageable pageable);
}
