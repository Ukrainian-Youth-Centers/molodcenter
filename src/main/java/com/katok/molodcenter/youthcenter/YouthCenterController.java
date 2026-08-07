package com.katok.molodcenter.youthcenter;

import com.katok.molodcenter.event.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/youth-centers")
public class YouthCenterController {
    @Autowired
    private YouthCenterService youthCenterService;

    @GetMapping("/{id}")
    public YouthCenterDto getYouthCenterById(@PathVariable Long id) {
        YouthCenter youthCenter = youthCenterService.getYouthCenterById(id);

        return YouthCenterDto.toYouthCenterDto(youthCenter);
    }

    @GetMapping
    public List<YouthCenterDto> getYouthCentersByLocation(@RequestBody GeoLocationCreateDto geoLocationCreateDto) {
        List<YouthCenter> youthCenters = youthCenterService.getYouthCentersByLocation(geoLocationCreateDto.getGeoLocation(), geoLocationCreateDto.getRadius());

        return youthCenters.stream().map(YouthCenterDto::toYouthCenterDto).toList();
    }

    @GetMapping("/{id}/events")
    public List<Event> getEventsByYouthCenter(@PathVariable Long id) {
        return youthCenterService.getEventsByYouthCenterId(id);
    }

    @PostMapping
    public ResponseEntity<YouthCenterDto> createYouthCenter(@RequestBody YouthCenterCreateDto youthCenterCreateDto) {
        YouthCenter youthCenter = YouthCenter.builder()
                .geoLocation(youthCenterCreateDto.getGeoLocation())
                .name(youthCenterCreateDto.getName())
                .build();

        youthCenter = youthCenterService.addYouthCenter(youthCenter);

        return ResponseEntity.status(HttpStatus.CREATED).body(YouthCenterDto.toYouthCenterDto(youthCenter));
    }

    @PatchMapping("/{id}")
    public YouthCenterDto updateYouthCenter(@PathVariable Long id, @RequestBody YouthCenterCreateDto youthCenterCreateDto) {
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
