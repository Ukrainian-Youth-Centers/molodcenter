package com.katok.molodcenter.youthcenter;

import com.katok.molodcenter.category.Category;
import com.katok.molodcenter.event.Event;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@Entity
@Table(name = "youth_centers")
@NoArgsConstructor
@AllArgsConstructor
public class YouthCenter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private GeoLocation geoLocation;

    @Column(nullable = false)
    private String name;

    @Builder.Default
    @OneToMany(mappedBy = "youthCenter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Event> events = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "youthCenter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Category> categories = new ArrayList<>();
}
