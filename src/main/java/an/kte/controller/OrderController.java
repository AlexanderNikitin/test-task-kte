package an.kte.controller;

import an.kte.model.OrderDocument;
import an.kte.model.ProductIdCount;
import an.kte.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/order/client/{clientId}/price/{price}")
    public ResponseEntity<String> order(
            @PathVariable Long clientId, @PathVariable Long price,
            @RequestBody List<ProductIdCount> productCountList) {
        OrderDocument orderDocumentTemp = orderService.constructOrder(clientId, productCountList);
        if (!Objects.equals(price, orderDocumentTemp.getPrice())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        OrderDocument orderDocument = orderService.saveOrder(clientId, productCountList);
        return new ResponseEntity<>(orderDocument.getCheckId(), HttpStatus.OK);
    }
}
