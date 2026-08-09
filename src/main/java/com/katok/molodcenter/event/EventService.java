package com.katok.molodcenter.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    public Event addEvent(Event event) {
        return eventRepository.save(event);
    }

    public Event getEventById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Event with id " + id + " undefined"));
    }

    public List<Event> getEventsByYouthCenterAndCategory(Long youthCenterId, Long categoryId) {
        return eventRepository.findByYouthCenterIdAndCategoryId(youthCenterId, categoryId);
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
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
