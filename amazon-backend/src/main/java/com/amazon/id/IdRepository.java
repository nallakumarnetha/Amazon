package com.amazon.id;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;

public interface IdRepository extends JpaRepository<ID, Integer> {

}
