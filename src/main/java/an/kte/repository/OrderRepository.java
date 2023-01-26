package an.kte.repository;

import an.kte.model.OrderDocument;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends ListCrudRepository<OrderDocument, Long> {
    Optional<OrderDocument> findTopByOrderByIdDesc();
}
