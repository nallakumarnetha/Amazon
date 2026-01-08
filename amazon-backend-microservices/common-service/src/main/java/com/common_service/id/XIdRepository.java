package com.common_service.id;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;

public interface XIdRepository extends JpaRepository<XID, Integer> {

}
