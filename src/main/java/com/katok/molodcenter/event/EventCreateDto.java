package com.katok.molodcenter.event;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventCreateDto {
    public static EventCreateDto toEventDto(Event event) {
        return new EventCreateDto (
                event.getYouthCenter().getId(),
                event.getCategory().getId(),
                event.getName(),
                event.getDescription(),
                event.getStartDateTime(),
                event.getEndDateTime()
        );
    }

    @NotNull
    private Long youthCenterId;
    @NotNull
    private Long categoryId;
    @NotBlank
    private String name;
    private String description;
    @NotNull
    private OffsetDateTime startDateTime;
    @NotNull
    private OffsetDateTime endDateTime;
}
