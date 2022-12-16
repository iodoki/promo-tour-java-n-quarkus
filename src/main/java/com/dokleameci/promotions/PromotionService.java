package com.dokleameci.promotions;

import com.dokleameci.exceptions.NotFoundException;
import com.dokleameci.profiles.ProfileRepository;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ApplicationScoped
public class PromotionService {
    @Inject
    PromotionRepository promotionRepository;
    @Inject
    ProfileRepository profileRepository;

    public Uni<List<Promotion>> findPromotionsByProfileIdAndStatus(Long profileId, Status status) {
        return Uni.createFrom()
                .item(promotionRepository.listPromotionsByProfileIdAndStatus(profileId, status))
                .onFailure()
                .recoverWithItem(Collections.emptyList());
    }

    public void profileRedeemDailyGifts(Long profileId, BonusType bonusType,Integer  numberOfPromotions) {
       var profile =  Optional.ofNullable( profileRepository.findByIdOptional(profileId))
               .orElseThrow(() -> new NotFoundException("Customer profile doesn't exist"));

        List<Promotion> promotions = Stream
                .of(new Promotion("description ...", bonusType, 0, Status.REDEEMED, profile.get()))
                .limit(numberOfPromotions)
                .collect(Collectors.toList());

        promotionRepository.persist(promotions);
    }
    public void save(Promotion promotion) {
        promotionRepository.persist(promotion);
    }

    public Uni<Promotion> findPromotionById(Long id) {
        return Uni.createFrom()
                .item(promotionRepository.findById(id))
                .onItem()
                .ifNull()
                .failWith(new NotFoundException("Promotion with: " + id + " not found!"));
    }
}
