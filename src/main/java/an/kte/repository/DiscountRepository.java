package an.kte.repository;

import an.kte.model.Discount;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiscountRepository extends ListCrudRepository<Discount, Long> {
    Optional<Discount> findTopByOrderByIdDesc();
}
