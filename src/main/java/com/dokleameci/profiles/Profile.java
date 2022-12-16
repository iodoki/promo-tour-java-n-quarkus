package com.dokleameci.profiles;

import com.dokleameci.promotions.Promotion;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "profiles")
public class Profile {
    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, columnDefinition = "BIGINT")
    Long id;
    private String name;
    private ProfileStatus status = ProfileStatus.X1;
    @OneToMany(
            mappedBy = "profile",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Promotion> promotions = new ArrayList<>();
}
