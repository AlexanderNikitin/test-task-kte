package an.kte.repository;

import an.kte.model.ProductStatistic;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductStatisticRepository extends ListCrudRepository<ProductStatistic, Long> {
    ProductStatistic findByProductId(Long productId);
}
