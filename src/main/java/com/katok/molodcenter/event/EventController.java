package com.katok.molodcenter.event;

import com.katok.molodcenter.category.Category;
import com.katok.molodcenter.category.CategoryService;
import com.katok.molodcenter.youthcenter.YouthCenter;
import com.katok.molodcenter.youthcenter.YouthCenterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/events")
public class EventController {
    private final EventService eventService;
    private final YouthCenterService youthCenterService;
    private final CategoryService categoryService;

    @GetMapping
    public Page<EventDto> getEvents(
            @RequestParam(required = false) OffsetDateTime startTimeFrom,
            @RequestParam(required = false) OffsetDateTime startTimeTo,
            @RequestParam(required = false) OffsetDateTime endTimeFrom,
            @RequestParam(required = false) OffsetDateTime endTimeTo,
            @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 10);

        return eventService.getEventsByTimeRange(startTimeFrom, startTimeTo, endTimeFrom, endTimeTo, pageable).map(EventDto::toEventDto);
    }

    @GetMapping("/{id}")
    public EventDto getEventById(@PathVariable Long id) {
        return EventDto.toEventDto(eventService.getEventById(id));
    }

    @PostMapping
    public EventDto addEvent(@Valid @RequestBody EventCreateDto eventCreateDtoDetails) {
        YouthCenter youthCenter = youthCenterService.getYouthCenterById(eventCreateDtoDetails.getYouthCenterId());
        Category category = categoryService.getCategoryById(eventCreateDtoDetails.getCategoryId());

        Event eventDetails = Event.builder()
                .name(eventCreateDtoDetails.getName())
                .description(eventCreateDtoDetails.getDescription())
                .youthCenter(youthCenter)
                .category(category)
                .startDateTime(eventCreateDtoDetails.getStartDateTime())
                .endDateTime(eventCreateDtoDetails.getEndDateTime())
                .build();

        Event event = eventService.addEvent(eventDetails);

        return EventDto.toEventDto(event);
    }

    @PatchMapping("/{id}")
    public EventDto updateEvent(@PathVariable Long id, @Valid @RequestBody EventCreateDto eventCreateDtoDetails) {
        Event eventDetails = Event.builder()
                .description(eventCreateDtoDetails.getDescription())
                .name(eventCreateDtoDetails.getName())
                .endDateTime(eventCreateDtoDetails.getEndDateTime())
                .startDateTime(eventCreateDtoDetails.getStartDateTime())
                .build();

        if (eventCreateDtoDetails.getYouthCenterId() != null) {
            YouthCenter youthCenter = youthCenterService.getYouthCenterById(eventCreateDtoDetails.getYouthCenterId());

            eventDetails.setYouthCenter(youthCenter);
        }
        if (eventCreateDtoDetails.getCategoryId() != null) {
            Category category = categoryService.getCategoryById(eventCreateDtoDetails.getCategoryId());

            eventDetails.setCategory(category);
        }

        Event event = eventService.updateEvent(id, eventDetails);

        return EventDto.toEventDto(event);
    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
    }
}
