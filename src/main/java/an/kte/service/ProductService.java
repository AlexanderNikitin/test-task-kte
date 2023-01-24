package an.kte.service;

import an.kte.model.Product;
import an.kte.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Service;

@Service
@Scope("singleton")
public class ProductService implements CommonService<Product> {
    @Autowired
    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
        Product client1 = Product
                .builder()
                .name("Book")
                .description("Book is a thing for the wise man.")
                .price(100000L)
                .build();
        Product client2 = Product
                .builder()
                .name("Spoon")
                .description("Spoon - is a very useful tool.")
                .price(2000L)
                .build();
        productRepository.save(client1);
        productRepository.save(client2);
    }

    @Override
    public ListCrudRepository<Product, Long> getRepository() {
        return productRepository;
    }
}
