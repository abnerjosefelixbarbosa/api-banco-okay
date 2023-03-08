package com.org.apibancookay.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.org.apibancookay.models.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
	boolean existsByCustomerId(Long id);
}
