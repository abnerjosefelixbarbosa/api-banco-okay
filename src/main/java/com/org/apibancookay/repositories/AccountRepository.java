package com.org.apibancookay.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.org.apibancookay.models.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
	@Query(value = "select * from Account account where account.customer_id = ?1", nativeQuery = true)
	Optional<Account> findByAccountId(Long accountId);	
	Optional<Account> findByAgencyAndAccount(String agency, String account);
}
