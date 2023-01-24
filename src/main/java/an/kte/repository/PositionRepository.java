package an.kte.repository;

import an.kte.model.Position;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepository extends ListCrudRepository<Position, Long> {
}
