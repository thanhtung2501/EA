package edu.miu.eafinalproject.repositories;

import edu.miu.eafinalproject.domain.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
}
