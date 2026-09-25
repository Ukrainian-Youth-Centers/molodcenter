package com.katok.molodcenter.category;

import com.katok.molodcenter.youthcenter.YouthCenter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "categories")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false, length = 20, unique = true)
    private String externalId;

    @ManyToOne
    @JoinColumn(name = "youth_center_id")
    private YouthCenter youthCenter;
}
