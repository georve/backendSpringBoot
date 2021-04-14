package com.georve.Machine.repository;

import com.georve.Machine.model.ERole;
import com.georve.Machine.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(ERole name);
}