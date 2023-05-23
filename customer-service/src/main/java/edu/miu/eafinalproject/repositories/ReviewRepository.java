package edu.miu.eafinalproject.repositories;

import edu.miu.eafinalproject.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

}
