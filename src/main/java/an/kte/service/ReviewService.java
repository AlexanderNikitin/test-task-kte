package an.kte.service;

import an.kte.model.Review;
import an.kte.model.ReviewValueCount;
import an.kte.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope("singleton")
public class ReviewService implements CommonService<Review> {
    @Autowired
    private ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
        Review review1 = Review
                .builder()
                .clientId(1L)
                .productId(1L)
                .value(5L)
                .build();
        Review review2 = Review
                .builder()
                .clientId(2L)
                .productId(1L)
                .value(1L)
                .build();
        reviewRepository.save(review1);
        reviewRepository.save(review2);
    }

    @Override
    public ListCrudRepository<Review, Long> getRepository() {
        return reviewRepository;
    }

    public List<ReviewValueCount> getReviewCounts(Long productId) {
        return reviewRepository.getReviewCounts(productId);
    }

    public Double avgReview(Long productId) {
        return reviewRepository.getAvg(productId);
    }

    public Long valueByClientProduct(Long clientId, Long productId) {
        return reviewRepository.getByClientProduct(clientId, productId).orElseThrow().getValue();
    }
}
