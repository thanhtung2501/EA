package edu.miu.eafinalproject.controllers;

import edu.miu.eafinalproject.data.ReviewDTO;
import edu.miu.eafinalproject.domain.Review;
import edu.miu.eafinalproject.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @GetMapping
    public ResponseEntity<List<ReviewDTO>> getAllReviews() {
        return ResponseEntity.ok(reviewService.getAllReviews());
    }
    @GetMapping("/{id}")
    public ResponseEntity<ReviewDTO> getReview(@PathVariable Long id) {
        return ResponseEntity.ok(reviewService.getReview(id));
    }
    @PostMapping
    public ResponseEntity<ReviewDTO> addReview(@RequestBody Review review) {
        return ResponseEntity.ok(reviewService.addReview(review));
    }
    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ReviewDTO> updateReview(@RequestBody Review review, @PathVariable Long id) {
        review.setId(id);
        return ResponseEntity.ok(reviewService.updateReview(review));
    }
}
