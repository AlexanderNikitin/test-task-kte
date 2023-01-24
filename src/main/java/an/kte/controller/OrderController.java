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
public class OrderController {
    @Autowired
    private OrderService OrderService;

    @PostMapping("/order/client/{clientId}/price/{price}")
    public String Order(@PathVariable Long clientId, @PathVariable Long price, @RequestBody List<ProductIdCount> productCountList) throws Exception {
        Order order = OrderService.constructOrder(clientId, productCountList);
        if (price != order.getPrice()) {
            throw new Exception();
        }
        OrderService.save(order);
        return order.getCheckId();
    }
}
