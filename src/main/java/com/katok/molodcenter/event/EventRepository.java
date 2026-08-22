package com.katok.molodcenter.event;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    Page<Event> findByYouthCenterIdAndCategoryId(Long youthCenterId, Long categoryId, Pageable pageable);
    @Query(value = """
        SELECT e FROM Event e
        WHERE (CAST(:startTimeFrom AS timestamp) IS NULL OR e.startDateTime >= :startTimeFrom)
          AND (CAST(:startTimeTo AS timestamp) IS NULL OR e.startDateTime <= :startTimeTo)
          AND (CAST(:endTimeFrom AS timestamp) IS NULL OR e.endDateTime >= :endTimeFrom)
          AND (CAST(:endTimeTo AS timestamp) IS NULL OR e.endDateTime <= :endTimeTo)
        """)
    Page<Event> findEventsByTimeRange(
            @Param("startTimeFrom") OffsetDateTime startTimeFrom,
            @Param("startTimeTo") OffsetDateTime startTimeTo,
            @Param("endTimeFrom") OffsetDateTime endTimeFrom,
            @Param("endTimeTo") OffsetDateTime endTimeTo,
            Pageable pageable
    );

    Page<Event> findByYouthCenterId(Long youthCenterId, Pageable pageable);

    @Modifying
    @Query(value = """
        DELETE FROM events WHERE id IN (
                                 SELECT id FROM events
                                 WHERE (:startTimeFrom IS NULL OR start_date_time >= :startTimeFrom)
                                   AND (:startTimeTo IS NULL OR start_date_time <= :startTimeTo)
                                   AND (:endTimeFrom IS NULL OR end_date_time >= :endTimeFrom)
                                   AND (:endTimeTo IS NULL OR end_date_time <= :endTimeTo)
                                 LIMIT :limit)
    """, nativeQuery = true)
    int deleteEventsByTimeRange(
            @Param("startTimeFrom") OffsetDateTime startTimeFrom,
            @Param("startTimeTo") OffsetDateTime startTimeTo,
            @Param("endTimeFrom") OffsetDateTime endTimeFrom,
            @Param("endTimeTo") OffsetDateTime endTimeTo,
            @Param("limit") int limit
    );
}
