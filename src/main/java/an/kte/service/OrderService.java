package an.kte.service;

import an.kte.model.OrderDocument;
import an.kte.model.Position;
import an.kte.model.Product;
import an.kte.model.ProductIdCount;
import an.kte.repository.OrderRepository;
import an.kte.repository.PositionRepository;
import an.kte.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Scope("singleton")
public class OrderService implements CommonService<OrderDocument> {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private PositionRepository positionRepository;

    public OrderDocument constructOrder(Long clientId, List<ProductIdCount> productCountList) {
        //TODO implement
        return OrderDocument.builder()
                .clientId(clientId)
                .price(calculateOrderPrice(clientId, productCountList))
                .checkId("00100")
                .build();
    }

    public OrderDocument saveOrder(Long clientId, List<ProductIdCount> productCountList) {
        OrderDocument orderDocument = constructOrder(clientId, productCountList);
        final OrderDocument savedOrderDocument = save(orderDocument);
        productCountList.stream()
                .map(idCount -> {
                    long price = calculatePositionPrice(idCount.getProductId(), idCount.getCount());
                    return Position.builder()
                            .orderId(savedOrderDocument.getId())
                            .count(idCount.getCount())
                            .finalPrice(price)
                            .finalDiscount(getProductPrice(idCount.getProductId()) - price)
                            .build();
                })
                .forEach(position -> positionRepository.save(position));
        return savedOrderDocument;
    }

    private long calculateOrderPrice(Long clientId, List<ProductIdCount> productCountList) {
        return productCountList.stream()
                .map(idCount -> calculatePositionPrice(idCount.getProductId(), idCount.getCount()))
                .reduce(Long::sum).orElse(0L);
    }

    private long calculatePositionPrice(Long productId, Long count) {
        Optional<Product> product = productRepository.findById(productId);
        return product.map(value -> value.getPrice() * count).orElse(0L);
    }

    private long getProductPrice(Long productId) {
        return productRepository.findById(productId)
                .map(Product::getPrice)
                .orElse(0L);
    }

    @Override
    public ListCrudRepository<OrderDocument, Long> getRepository() {
        return orderRepository;
    }
}
