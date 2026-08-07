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

    @GetMapping("/{id}/events")
    public List<Event> getEventsByYouthCenter(@PathVariable Long id) {
        return youthCenterService.getEventsByYouthCenterId(id);
    }

    @PostMapping
    public ResponseEntity<YouthCenterDto> createYouthCenter(@RequestBody YouthCenterDto youthCenterDto) {
        YouthCenter youthCenter = YouthCenter.builder()
                .geoLocation(youthCenterDto.getGeoLocation())
                .name(youthCenterDto.getName())
                .build();

        youthCenter = youthCenterService.addYouthCenter(youthCenter);

        return ResponseEntity.status(HttpStatus.CREATED).body(YouthCenterDto.toYouthCenterDto(youthCenter));
    }

    @PatchMapping("/{id}")
    public YouthCenterDto updateYouthCenter(@PathVariable Long id, @RequestBody YouthCenterDto youthCenterDto) {
        YouthCenter youthCenterDetails = YouthCenter.builder()
                .name(youthCenterDto.getName())
                .geoLocation(youthCenterDto.getGeoLocation())
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
