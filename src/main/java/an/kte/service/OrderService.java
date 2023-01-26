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

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Scope("singleton")
public class OrderService implements CommonService<OrderDocument> {
    private static final String START_CHECK_ID = "00100";
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private PositionRepository positionRepository;
    @Autowired
    private DiscountService discountService;

    public OrderDocument constructOrder(Long clientId, List<ProductIdCount> productCountList) {
        Optional<OrderDocument> lastOrder = orderRepository.findTopByOrderByIdDesc();
        String checkId = START_CHECK_ID;
        if (lastOrder.isPresent()) {
            OrderDocument orderDocument = lastOrder.get();
            Date date = orderDocument.getDate();
            if (isToday(date)) {
                checkId = nextCheck(orderDocument.getCheckId());
            }
        }
        return OrderDocument.builder()
                .clientId(clientId)
                .price(calculateOrderPrice(clientId, productCountList))
                .checkId(checkId)
                .build();
    }

    public OrderDocument saveOrder(Long clientId, List<ProductIdCount> productCountList) {
        OrderDocument orderDocument = constructOrder(clientId, productCountList);
        final OrderDocument savedOrderDocument = save(orderDocument);
        productCountList.stream()
                .map(idCount -> {
                    long price = discountService.getPositionPrice(
                            clientId,
                            idCount.getProductId(),
                            idCount.getCount());
                    long sourcePrice = getProductPrice(idCount.getProductId());
                    return Position.builder()
                            .orderId(savedOrderDocument.getId())
                            .count(idCount.getCount())
                            .sourcePrice(sourcePrice)
                            .finalPrice(price)
                            .finalDiscount(sourcePrice - price)
                            .productId(idCount.getProductId())
                            .build();
                })
                .forEach(position -> positionRepository.save(position));
        return savedOrderDocument;
    }

    private long calculateOrderPrice(Long clientId, List<ProductIdCount> productCountList) {
        return productCountList.stream()
                .map(idCount -> discountService.getPositionPrice(
                        clientId,
                        idCount.getProductId(),
                        idCount.getCount()))
                .reduce(Long::sum)
                .orElse(0L);
    }

    private long getProductPrice(Long productId) {
        return productRepository.findById(productId)
                .map(Product::getPrice)
                .orElse(0L);
    }

    private boolean isToday(Date date) {
        Date now = new Date();
        now.setHours(0);
        now.setMinutes(0);
        now.setSeconds(0);
        long dayStart = now.getTime();
        return date.getTime() > dayStart;
    }

    private String nextCheck(String lastCheck) {
        int asInt = Integer.parseInt(lastCheck);
        asInt++;
        return String.format("%05d", asInt);
    }

    @Override
    public ListCrudRepository<OrderDocument, Long> getRepository() {
        return orderRepository;
    }
}
