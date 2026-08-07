package com.katok.molodcenter.youthcenter;

import com.katok.molodcenter.event.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class YouthCenterService {
    @Autowired
    private YouthCenterRepository youthCenterRepository;

    @Transactional(readOnly = true)
    public YouthCenter getYouthCenterById(Long id) {
        return youthCenterRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Youth center with id " + id + " undefined"));
    }

    public List<YouthCenter> getYouthCentersByLocation(GeoLocation geoLocation, Double radius) {
        return youthCenterRepository.findNearby(geoLocation.getLatitude(), geoLocation.getLongitude(), radius);
    }

    public YouthCenter addYouthCenter(YouthCenter youthCenter) {
        return youthCenterRepository.save(youthCenter);
    }

    public void deleteYouthCenter(Long id) {
        youthCenterRepository.deleteById(id);
    }

    public YouthCenter updateYouthCenter(Long id, YouthCenter youthCenterDetails) {
        YouthCenter youthCenter = getYouthCenterById(id);

        if (youthCenterDetails.getName() != null) {
            youthCenter.setName(youthCenterDetails.getName());
        }

        if (youthCenterDetails.getGeoLocation() != null) {
            youthCenter.setGeoLocation(youthCenterDetails.getGeoLocation());
        }

        return youthCenterRepository.save(youthCenter);
    }

    @Transactional(readOnly = true)
    public List<Event> getEventsByYouthCenterId(Long id) {
        YouthCenter youthCenter = getYouthCenterById(id);

        youthCenter.getEvents().size();

        return youthCenter.getEvents();
    }
}
