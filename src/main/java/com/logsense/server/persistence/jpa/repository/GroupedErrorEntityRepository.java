package com.logsense.server.persistence.jpa.repository;

import com.logsense.server.persistence.jpa.entity.postgre.GroupedErrorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupedErrorEntityRepository extends JpaRepository<GroupedErrorEntity, Long> {

    Optional<GroupedErrorEntity> findByErrorHash(String errorHash);
}
