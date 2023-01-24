package an.kte.service;

import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.Optional;

public interface CommonService<T> {

    ListCrudRepository<T, Long> getRepository();

    default Optional<T> getById(Long id) {
        return getRepository().findById(id);
    }

    default List<T> findAll() {
        return getRepository().findAll();
    }

    default List<T> findAllById(Iterable<Long> ids) {
        return getRepository().findAllById(ids);
    }

    default T save(T item) {
        return getRepository().save(item);
    }
}
