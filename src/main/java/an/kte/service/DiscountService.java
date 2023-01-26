package an.kte.service;

import an.kte.model.Client;
import an.kte.model.Discount;
import an.kte.model.Product;
import an.kte.repository.ClientRepository;
import an.kte.repository.DiscountRepository;
import an.kte.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@Scope("singleton")
public class DiscountService implements CommonService<Discount> {
    private final double MAX_DISCOUNT_PERCENT = .18;
    private final long SECOND_DISCOUNT_THRESHOLD = 5;
    @Autowired
    private DiscountRepository discountRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ClientRepository clientRepository;

    public long getPositionPrice(Long clientId, Long productId, Long count) {
        Optional<Product> product = productRepository.findById(productId);
        Optional<Client> client = clientRepository.findById(clientId);
        Optional<Discount> actualDiscount = getActualDiscount();
        if (product.isPresent()) {
            long price = count * product.get().getPrice();
            double percent = 0;
            if (actualDiscount.isPresent() && Objects.equals(productId, actualDiscount.get().getProductId())) {
                percent += actualDiscount.get().getPercent();
            }
            if (client.isPresent()) {
                if (count >= SECOND_DISCOUNT_THRESHOLD && client.get().getDiscount2() != null && client.get().getDiscount2() != 0) {
                    percent += client.get().getDiscount2();
                } else if (client.get().getDiscount1() != null) {
                    percent += client.get().getDiscount1();
                }
            }
            if (percent > MAX_DISCOUNT_PERCENT) {
                percent = MAX_DISCOUNT_PERCENT;
            }
            return (long) (price * (1 - percent));
        } else {
            return 0;
        }
    }

    public Optional<Discount> getActualDiscount() {
        return discountRepository.findTopByOrderByIdDesc();
    }

    @Override
    public ListCrudRepository getRepository() {
        return discountRepository;
    }
}
