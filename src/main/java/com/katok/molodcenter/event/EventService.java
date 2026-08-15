package com.katok.molodcenter.event;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@RequiredArgsConstructor
@Service
public class EventService {
    private final EventRepository eventRepository;

    public Event addEvent(Event event) {
        return eventRepository.save(event);
    }

    public Event getEventById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Event with id " + id + " undefined"));
    }

    public Page<Event> getEvents(Pageable pageable) {
        return eventRepository.findAll(pageable);
    }

    public Page<Event> getEventsByYouthCenterAndCategory(Long youthCenterId, Long categoryId, Pageable pageable) {
        return eventRepository.findByYouthCenterIdAndCategoryId(youthCenterId, categoryId, pageable);
    }

    public Page<Event> getEventsByYouthCenterId(Long youthCenterId, Pageable pageable) {
        return eventRepository.findByYouthCenterId(youthCenterId, pageable);
    }

    public Page<Event> getEventsByTimeRange(
            OffsetDateTime startTimeFrom,
            OffsetDateTime startTimeTo,
            OffsetDateTime endTimeFrom,
            OffsetDateTime endTimeTo,
            Pageable pageable
    ) {
        return eventRepository.findEventsByTimeRange(startTimeFrom, startTimeTo, endTimeFrom, endTimeTo, pageable);
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

    public int deleteEvents(OffsetDateTime startTimeFrom,
                            OffsetDateTime startTimeTo,
                            OffsetDateTime endTimeFrom,
                            OffsetDateTime endTimeTo,
                            int limit) {
        return eventRepository.deleteEventsByTimeRange(startTimeFrom, startTimeTo, endTimeFrom, endTimeTo, limit);
    }

    public Event updateEvent(Long id, Event eventDetails) {
        Event event = getEventById(id);

        if (eventDetails.getDescription() != null) {
            event.setDescription(eventDetails.getDescription());
        }

        if (eventDetails.getEndDateTime() != null) {
            event.setEndDateTime(eventDetails.getEndDateTime());
        }

        if (eventDetails.getStartDateTime() != null) {
            event.setStartDateTime(eventDetails.getStartDateTime());
        }

        if (eventDetails.getName() != null) {
            event.setName(eventDetails.getName());
        }

        eventRepository.save(event);

        return event;
    }
}
