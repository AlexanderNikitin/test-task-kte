package an.kte.repository;

import an.kte.model.Discount;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountRepository extends ListCrudRepository<Discount, Long> {
}
