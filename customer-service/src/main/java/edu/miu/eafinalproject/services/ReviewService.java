package edu.miu.eafinalproject.services;

import edu.miu.eafinalproject.data.ReviewDTO;
import edu.miu.eafinalproject.domain.Review;

import java.util.List;

public interface ReviewService {
    ReviewDTO getReview(Long reviewId);
    ReviewDTO updateReview(Review review);
    void deleteReview(Long reviewId);
    ReviewDTO addReview(Review review);

    List<ReviewDTO> getAllReviews();
}
