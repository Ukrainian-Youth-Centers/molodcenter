package com.katok.molodcenter.youthcenter;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeoLocation {
    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;
}
