package an.kte.repository;

import an.kte.model.ClientStatistic;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientStatisticRepository extends ListCrudRepository<ClientStatistic, Long> {
    ClientStatistic findByClientId(Long clientId);
}
