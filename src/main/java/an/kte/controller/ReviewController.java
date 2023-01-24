package an.kte.controller;

import an.kte.model.Review;
import an.kte.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
