package an.kte.controller;

import an.kte.model.ExtendedProduct;
import an.kte.model.Product;
import an.kte.service.ProductService;
import an.kte.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ReviewService reviewService;

    @GetMapping("/products")
    public List<Product> products() {
        return productService.findAll();
    }

    @GetMapping("/products/{id}/client/{clientId}")
    public ExtendedProduct getProductWithReview(@PathVariable Long id, @PathVariable Long clientId) {
        Product product = productService.getById(id).orElseThrow();
        ExtendedProduct extendedProduct = new ExtendedProduct();
        extendedProduct.setId(product.getId());
        extendedProduct.setName(product.getName());
        extendedProduct.setDescription(product.getDescription());
        extendedProduct.setPrice(product.getPrice());
        extendedProduct.setReviewValueCount(reviewService.getReviewCounts(product.getId()));
        extendedProduct.setAvgReview(reviewService.avgReview(product.getId()));
        extendedProduct.setCurrentReview(reviewService.valueByClientProduct(clientId, product.getId()));
        return extendedProduct;
    }
}
