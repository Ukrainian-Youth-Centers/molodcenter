package com.katok.molodcenter.event;

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

    @GetMapping("/{id}")
    private EventDto getEventById(Long id) {
        return EventDto.toEventDto(eventService.getEventById(id));
    }

    @PostMapping
    private EventDto addEvent(@RequestBody EventCreateDto eventCreateDtoDetails) {
        YouthCenter youthCenter = youthCenterService.getYouthCenterById(eventCreateDtoDetails.getYouthCenterId());

        Event eventDetails = Event.builder()
                .name(eventCreateDtoDetails.getName())
                .description(eventCreateDtoDetails.getDescription())
                .youthCenter(youthCenter)
                .startDateTime(eventCreateDtoDetails.getStartDateTime())
                .endDateTime(eventCreateDtoDetails.getEndDateTime())
                .build();

        Event event = eventService.addEvent(eventDetails);

        return EventDto.toEventDto(event);
    }

    @PatchMapping("/{id}")
    private EventDto updateEvent(@PathVariable Long id, @RequestBody EventCreateDto eventCreateDtoDetails) {
        YouthCenter youthCenter = youthCenterService.getYouthCenterById(eventCreateDtoDetails.getYouthCenterId());

        Event eventDetails = Event.builder()
                .youthCenter(youthCenter)
                .description(eventCreateDtoDetails.getDescription())
                .name(eventCreateDtoDetails.getName())
                .endDateTime(eventCreateDtoDetails.getEndDateTime())
                .startDateTime(eventCreateDtoDetails.getStartDateTime())
                .build();

        Event event = eventService.updateEvent(id, eventDetails);

        return EventDto.toEventDto(event);
    }

    @DeleteMapping("/{id}")
    private void deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
    }
}
