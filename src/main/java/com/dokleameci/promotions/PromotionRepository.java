package com.dokleameci.promotions;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import javax.enterprise.context.ApplicationScoped;
import java.util.List;
@ApplicationScoped
public class PromotionRepository implements PanacheRepository<Promotion> {
    List<Promotion> listPromotionsByProfileIdAndStatus(Long profileId, Status status) {
        return list("profile_id = ?1 and status = ?2", profileId, status);
    }
}
