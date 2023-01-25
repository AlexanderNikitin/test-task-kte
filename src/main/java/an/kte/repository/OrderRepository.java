package an.kte.repository;

import an.kte.model.OrderDocument;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends ListCrudRepository<OrderDocument, Long> {
}
