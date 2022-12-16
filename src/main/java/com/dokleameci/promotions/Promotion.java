package com.dokleameci.promotions;

import com.dokleameci.profiles.Profile;
import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "promotions")
public class Promotion {
    @Id
    @GeneratedValue
    private Long id;
    private String description;
    private BonusType type = BonusType.NONE;
    private Integer totalPoints;
    private Status status = Status.NON_REDEEMED;
    @JoinColumn(
            name = "profile_id",
            referencedColumnName = "id",
            nullable = false,
            updatable = false,
            insertable = false
    )
    @ManyToOne(fetch = FetchType.LAZY)
    private Profile profile;

    public Promotion(String description, BonusType bonusType, Integer totalPoints, Status status, Profile profile) {
        this.description = description;
        this.totalPoints = totalPoints ;
        this.type = bonusType;
        this.status = status;
        this.profile = profile;

    }
}
