package com.katok.molodcenter.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {
    public static EventDto toEventDto(Event event) {
        return new EventDto(
                event.getId(),
                event.getYouthCenter().getId(),
                event.getCategory().getId(),
                event.getName(),
                event.getDescription(),
                event.getStartDateTime(),
                event.getEndDateTime()
        );
    }

    private Long id;
    private Long youthCenterId;
    private Long categoryId;
    private String name;
    private String description;
    private OffsetDateTime startDateTime;
    private OffsetDateTime endDateTime;
}
