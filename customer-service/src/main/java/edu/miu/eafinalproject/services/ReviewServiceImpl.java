package edu.miu.eafinalproject.services;

import edu.miu.eafinalproject.converter.ReviewAdapter;
import edu.miu.eafinalproject.data.ReviewDTO;
import edu.miu.eafinalproject.domain.Customer;
import edu.miu.eafinalproject.domain.Review;
import edu.miu.eafinalproject.repositories.ReviewRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService{
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    ReviewAdapter reviewAdaptor;
    @Autowired
    ModelMapper mapper;
    @Override
    public ReviewDTO getReview(Long reviewId) {
        Optional<Review> optionalCustomer = reviewRepository.findById(reviewId);
        if(optionalCustomer.isPresent()){
           return mapper.map(optionalCustomer.get(), ReviewDTO.class);
        }


        return null;
    }

    @Override
    public ReviewDTO updateReview(Review review) {
        Review saved = reviewRepository.save(review);
        return mapper.map(saved, ReviewDTO.class);
    }

    @Override
    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);

    }

    @Override
    public ReviewDTO addReview(Review review) {
        Review saved = reviewRepository.save(review);
        return mapper.map(saved, ReviewDTO.class);
    }

    @Override
    public List<ReviewDTO> getAllReviews() {
        List<ReviewDTO> optionalCustomer = reviewRepository.findAll().stream().map(review -> mapper.map(review, ReviewDTO.class)).collect(Collectors.toList());

            return optionalCustomer;

    }
}
