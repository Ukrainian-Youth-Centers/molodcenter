package com.katok.molodcenter.youthcenter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeoLocationCreateDto {
    private GeoLocation geoLocation;
    private Double radius;
}
