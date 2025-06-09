package com.logsense.server.persistence.jpa.repository;

import com.logsense.server.persistence.jpa.entity.postgre.GroupedErrorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupedErrorEntityRepository extends JpaRepository<GroupedErrorEntity, Long> {
}
