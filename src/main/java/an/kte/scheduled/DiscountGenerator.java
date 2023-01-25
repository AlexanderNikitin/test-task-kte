package an.kte.scheduled;

import an.kte.model.Discount;
import an.kte.model.Product;
import an.kte.service.DiscountService;
import an.kte.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.TimerTask;

@Component
public class DiscountGenerator extends TimerTask {
    @Autowired
    private DiscountService discountService;
    @Autowired
    private ProductService productService;
    private final Random random = new Random();
    private final double MIN_DISCOUNT = 0.05;
    private final double MAX_DISCOUNT = 0.1;

    @Override
    public void run() {
        Product topByOrderByIdDesc = productService.findTopByOrderByIdDesc();
        long lastId = topByOrderByIdDesc.getId();

        long productId = random.nextLong(1, lastId);
        double percent = random.nextDouble(MIN_DISCOUNT, MAX_DISCOUNT);

        Discount discount = Discount.builder()
                .productId(productId)
                .percent(percent)
                .build();
        discountService.save(discount);
    }
}
