package com.dokleameci.profiles;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import javax.enterprise.context.ApplicationScoped;
@ApplicationScoped
public class ProfileRepository implements PanacheRepository<Profile> {
}