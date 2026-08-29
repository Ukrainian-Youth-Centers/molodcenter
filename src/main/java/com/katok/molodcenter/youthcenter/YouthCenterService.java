package com.katok.molodcenter.youthcenter;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class YouthCenterService {
    private final YouthCenterRepository youthCenterRepository;

    @Transactional(readOnly = true)
    public YouthCenter getYouthCenterById(Long id) {
        return youthCenterRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Youth center with id " + id + " undefined"));
    }

    public Page<YouthCenter> getYouthCentersByLocation(GeoLocation geoLocation, Float radius, Pageable pageable) {
        return youthCenterRepository.findNearby(geoLocation.getLatitude(), geoLocation.getLongitude(), radius, pageable);
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
}
