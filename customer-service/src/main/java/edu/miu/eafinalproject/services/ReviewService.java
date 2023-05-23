package edu.miu.eafinalproject.services;

import edu.miu.eafinalproject.data.ReviewDTO;
import edu.miu.eafinalproject.domain.Review;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface ReviewService {
    ReviewDTO getReview(Long reviewId);
    ReviewDTO updateReview(Review review);
    void deleteReview(Long reviewId);
    ReviewDTO addReview(Review review);

    List<ReviewDTO> getAllReviews();
}
