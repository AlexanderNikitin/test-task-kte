package an.kte.service;

import an.kte.model.Discount;
import an.kte.repository.DiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Service;

@Service
public class DiscountService implements CommonService {
    @Autowired
    private DiscountRepository discountRepository;

    public Discount findTopByOrderByIdDesc() {
        return discountRepository.findTopByOrderByIdDesc();
    }

    @Override
    public ListCrudRepository getRepository() {
        return discountRepository;
    }
}
