package com.katok.molodcenter.event;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
@RequiredArgsConstructor
public class EventScheduler {
    private final EventService eventService;

    @Value("${scheduler.event.limit}")
    private int limit;
    @Value("${scheduler.event.cooldown}")
    private int cooldown;
    @Value("${event.delete-after-days}")
    private int deleteAfterDays;

    @Async
    @Scheduled(cron = "0 0 3 * * *")
    public void executeDeleteEventsTask() {
        OffsetDateTime deleteTime = OffsetDateTime.now().minusDays(deleteAfterDays);

        int deletedIds;

        do {
            deletedIds = eventService.deleteEvents(null, null, null, deleteTime, limit);

            try {
                Thread.sleep(cooldown);
            } catch (InterruptedException ignored) {
                Thread.currentThread().interrupt();
            }
        } while (deletedIds == limit);
    }
}
