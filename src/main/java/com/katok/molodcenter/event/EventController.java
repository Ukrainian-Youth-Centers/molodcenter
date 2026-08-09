package com.katok.molodcenter.event;

import com.katok.molodcenter.category.Category;
import com.katok.molodcenter.category.CategoryService;
import com.katok.molodcenter.youthcenter.YouthCenter;
import com.katok.molodcenter.youthcenter.YouthCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/events")
public class EventController {
    @Autowired
    private EventService eventService;
    @Autowired
    private YouthCenterService youthCenterService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/{id}")
    public EventDto getEventById(Long id) {
        return EventDto.toEventDto(eventService.getEventById(id));
    }

    @PostMapping
    public EventDto addEvent(@RequestBody EventCreateDto eventCreateDtoDetails) {
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
    public EventDto updateEvent(@PathVariable Long id, @RequestBody EventCreateDto eventCreateDtoDetails) {
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
