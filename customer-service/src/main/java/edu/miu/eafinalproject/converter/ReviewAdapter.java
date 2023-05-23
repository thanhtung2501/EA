package edu.miu.eafinalproject.converter;

import edu.miu.eafinalproject.data.CreditCardDTO;
import edu.miu.eafinalproject.data.ReviewDTO;
import edu.miu.eafinalproject.domain.CreditCard;
import edu.miu.eafinalproject.domain.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewAdapter {
    public Review convertReviewDTOToReview(ReviewDTO reviewDTO){
        return Review.builder()
                .title(reviewDTO.getTitle())
                .description(reviewDTO.getDescription())
                .stars(reviewDTO.getStars())
                .date(reviewDTO.getDate())
                .customer(reviewDTO.getCustomer())
                .product(reviewDTO.getProduct())
                .build();
    }

    public ReviewDTO convertReviewToReviewDTO(Review review){
        return ReviewDTO.builder()
                .title(review.getTitle())
                .description(review.getDescription())
                .stars(review.getStars())
                .date(review.getDate())
                .customer(review.getCustomer())
                .product(review.getProduct())
                .build();
    }
}
