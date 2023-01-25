package an.kte.controller;

import an.kte.model.OrderDocument;
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
    private OrderService orderService;

    @PostMapping("/price/client/{clientId}")
    public Long price(
            @PathVariable Long clientId,
            @RequestBody List<ProductIdCount> productCountList) {
        OrderDocument orderDocument = orderService.constructOrder(clientId, productCountList);
        return orderDocument.getPrice();
    }
}
