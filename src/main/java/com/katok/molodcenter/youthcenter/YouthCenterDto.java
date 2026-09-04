package com.katok.molodcenter.youthcenter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class YouthCenterDto {
    public static YouthCenterDto toYouthCenterDto(YouthCenter youthCenter) {
        return new YouthCenterDto(
                youthCenter.getId(),
                youthCenter.getGeoLocation(),
                youthCenter.getName(),
                youthCenter.getExternalId()
        );
    }

    private Long id;
    private GeoLocation geoLocation;
    private String name;
    private String externalId;
}
