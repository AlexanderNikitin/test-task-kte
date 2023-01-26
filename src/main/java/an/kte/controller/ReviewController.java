package an.kte.controller;

import an.kte.model.Review;
import an.kte.model.UserReview;
import an.kte.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @GetMapping("/reviews")
    public List<Review> reviews() {
        return reviewService.findAll();
    }

    @GetMapping("/reviews/{id}")
    public Review review(@PathVariable long id) {
        return reviewService.getById(id).orElseThrow();
    }

    @PutMapping("/reviews")
    public ResponseEntity<Review> updateReview(@RequestBody UserReview userReview) {
        if (userReview.getValue() != null) {
            if (userReview.getValue() < 1 || userReview.getValue() > 5) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        Optional<Review> review = reviewService.updateReview(userReview);
        return review.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.OK));
    }
}
