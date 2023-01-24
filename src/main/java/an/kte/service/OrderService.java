package an.kte.service;

import an.kte.model.ProductIdCount;
import an.kte.model.Order;
import an.kte.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope("singleton")
public class OrderService implements CommonService<Order> {
    @Autowired
    private OrderRepository OrderRepository;

    public Order constructOrder(Long clientId, List<ProductIdCount> productCountList) {
        //TODO implement
        return Order.builder()
                .price(1000L)
                .build();
    }

    @Override
    public ListCrudRepository<Order, Long> getRepository() {
        return OrderRepository;
    }
}
