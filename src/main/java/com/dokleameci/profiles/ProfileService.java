package com.dokleameci.profiles;

import com.dokleameci.exceptions.NotFoundException;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

@ApplicationScoped
public class ProfileService {
    @Inject
    ProfileRepository repository;
    public Uni<Profile> findProfileById(Long id) {
        return Uni.createFrom()
                .item(repository.findById(id))
                .onItem()
                .ifNull()
                .failWith(new NotFoundException("Profile with: " + id + " not found!"));
    }
    public Uni<List<Profile>> allProfiles() {
        return Uni.createFrom()
                .item(repository.listAll());
    }
    public void save(Profile profile) {
        repository.persist(profile);
    }

}
