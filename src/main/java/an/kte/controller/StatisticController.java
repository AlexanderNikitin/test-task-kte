package an.kte.controller;

import an.kte.model.ClientStatistic;
import an.kte.model.ProductStatistic;
import an.kte.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatisticController {
    @Autowired
    private StatisticService statisticService;

    @GetMapping("/statistic/client/{clientId}")
    public ClientStatistic getClientStatistic(@PathVariable Long clientId) {
        return statisticService.findByClientId(clientId);
    }

    @GetMapping("/statistic/product/{productId}")
    public ProductStatistic getProductStatistic(@PathVariable Long productId) {
        return statisticService.findByProductId(productId);
    }
}
