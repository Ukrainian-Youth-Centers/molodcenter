package com.katok.molodcenter.youthcenter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class YouthCenterCreateDto {
    public static YouthCenterCreateDto toYouthCenterCreateDto(YouthCenter youthCenter) {
        return new YouthCenterCreateDto(
                youthCenter.getGeoLocation(),
                youthCenter.getName()
        );
    }

    @NotNull
    private GeoLocation geoLocation;
    @NotBlank
    private String name;
}