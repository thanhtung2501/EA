package edu.miu.eafinalproject.services;

import edu.miu.eafinalproject.data.ReviewDTO;
import edu.miu.eafinalproject.domain.Review;
import edu.miu.eafinalproject.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService{
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    ReviewAdapter reviewAdaptor;
    @Override
    public ReviewDTO getReview(Long reviewId) {
        Review review= reviewRepository.findById(reviewId).orElseThrow();
        return reviewAdaptor.convertReviewToReviewDTO(review);
    }

    @Override
    public ReviewDTO updateReview(Review review) {
        Review saved = reviewRepository.save(review);
        return reviewAdaptor.convertReviewToReviewDTO(saved);
    }

    @Override
    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);

    }

    @Override
    public ReviewDTO addReview(Review review) {
        Review saved = reviewRepository.save(review);
        return reviewAdaptor.convertReviewToReviewDTO(saved);
    }

    @Override
    public List<ReviewDTO> getAllReviews() {
        List<Review> reviews = reviewRepository.findAll();
        return reviews.stream().map(review -> reviewAdaptor.convertReviewToReviewDTO(review)).collect(Collectors.toList());
    }
}
