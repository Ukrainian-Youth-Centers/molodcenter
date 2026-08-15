package com.katok.molodcenter.youthcenter;

import com.katok.molodcenter.category.CategoryDto;
import com.katok.molodcenter.category.CategoryService;
import com.katok.molodcenter.event.EventDto;
import com.katok.molodcenter.event.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/youth-centers")
public class YouthCenterController {
    private final YouthCenterService youthCenterService;
    private final EventService eventService;
    private final CategoryService categoryService;

    @GetMapping("/{id}")
    public YouthCenterDto getYouthCenterById(@PathVariable Long id) {
        YouthCenter youthCenter = youthCenterService.getYouthCenterById(id);

        return YouthCenterDto.toYouthCenterDto(youthCenter);
    }

    @GetMapping
    public Page<YouthCenterDto> getYouthCentersByLocation(@RequestParam Double latitude,
                                                          @RequestParam Double longitude,
                                                          @RequestParam Double radius,
                                                          @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 10);

        Page<YouthCenter> youthCenters = youthCenterService.getYouthCentersByLocation(new GeoLocation(latitude, longitude), radius, pageable);

        return youthCenters.map(YouthCenterDto::toYouthCenterDto);
    }

    @GetMapping("/{id}/events")
    public Page<EventDto> getEventsByYouthCenter(@PathVariable Long id,
                                                 @RequestParam(required = false) Long categoryId,
                                                 @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 10);

        if (categoryId == null) {
            return eventService.getEventsByYouthCenterId(id, pageable).map(EventDto::toEventDto);
        } else {
            return eventService.getEventsByYouthCenterAndCategory(id, categoryId, pageable)
                    .map(EventDto::toEventDto);
        }
    }

    @GetMapping("/{id}/categories")
    public Page<CategoryDto> getCategoriesByYouthCenter(@PathVariable Long id,
                                                        @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 10);

        return categoryService.getCategoriesByYouthCenterId(id, pageable).map(CategoryDto::toCategoryDto);
    }

    @PostMapping
    public ResponseEntity<YouthCenterDto> createYouthCenter(@Valid @RequestBody YouthCenterCreateDto youthCenterCreateDto) {
        YouthCenter youthCenter = YouthCenter.builder()
                .geoLocation(youthCenterCreateDto.getGeoLocation())
                .name(youthCenterCreateDto.getName())
                .build();

        youthCenter = youthCenterService.addYouthCenter(youthCenter);

        return ResponseEntity.status(HttpStatus.CREATED).body(YouthCenterDto.toYouthCenterDto(youthCenter));
    }

    @PatchMapping("/{id}")
    public YouthCenterDto updateYouthCenter(@PathVariable Long id, @Valid @RequestBody YouthCenterCreateDto youthCenterCreateDto) {
        YouthCenter youthCenterDetails = YouthCenter.builder()
                .name(youthCenterCreateDto.getName())
                .geoLocation(youthCenterCreateDto.getGeoLocation())
                .build();

        YouthCenter youthCenter = youthCenterService.updateYouthCenter(id, youthCenterDetails);

        return YouthCenterDto.toYouthCenterDto(youthCenter);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteYouthCenter(@PathVariable Long id) {
        youthCenterService.deleteYouthCenter(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
