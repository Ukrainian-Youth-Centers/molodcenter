package com.katok.molodcenter.event;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    Page<Event> findByYouthCenterIdAndCategoryId(Long youthCenterId, Long categoryId, Pageable pageable);
    @Query(value = """
        SELECT * FROM events e 
        WHERE (:startTimeFrom IS NULL OR e.start_date_time >= :startTimeFrom)
          AND (:startTimeTo IS NULL OR e.start_date_time <= :startTimeTo)
          AND (:endTimeFrom IS NULL OR e.end_date_time >= :endTimeFrom)
          AND (:endTimeTo IS NULL OR e.end_date_time <= :endTimeTo)
    """, nativeQuery = true)
    Page<Event> findEventsByTimeRange(
            @Param("startTimeFrom") OffsetDateTime startTimeFrom,
            @Param("startTimeTo") OffsetDateTime startTimeTo,
            @Param("endTimeFrom") OffsetDateTime endTimeFrom,
            @Param("endTimeTo") OffsetDateTime endTimeTo,
            Pageable pageable
    );

    Page<Event> findByYouthCenterId(Long youthCenterId, Pageable pageable);
}
