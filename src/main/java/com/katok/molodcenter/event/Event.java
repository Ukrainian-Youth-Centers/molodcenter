package com.katok.molodcenter.event;

import com.katok.molodcenter.youthcenter.YouthCenter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "events")
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "youth_center_id", nullable = false)
    private YouthCenter youthCenter;

    @Column(nullable = false)
    private String name;

    private String description;
}
