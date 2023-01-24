package an.kte.repository;

import an.kte.model.Review;
import an.kte.model.ReviewValueCount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends ListCrudRepository<Review, Long> {
    @Query(value = "SELECT count(r.value) AS count, r.value AS value FROM review AS r WHERE r.product_id = ?1 GROUP BY r.value", nativeQuery = true)
    List<ReviewValueCount> getReviewCounts(Long productId);

    @Query(value = "SELECT avg(r.value) as avg FROM review r where r.product_id = ?1", nativeQuery = true)
    Double getAvg(Long productId);

    @Query(value = "SELECT * FROM review WHERE client_id = ?1 AND product_id = ?2", nativeQuery = true)
    Optional<Review> getByClientProduct(Long clientId, Long productId);
}