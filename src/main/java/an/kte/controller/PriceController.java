package an.kte.controller;

import an.kte.model.Order;
import an.kte.model.ProductIdCount;
import an.kte.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PriceController {
    @Autowired
    private OrderService OrderService;

    @PostMapping("/price/client/{clientId}")
    public Long price(@PathVariable Long clientId, @RequestBody List<ProductIdCount> productCountList) {
        Order order = OrderService.constructOrder(clientId, productCountList);
        return order.getPrice();
    }
}
