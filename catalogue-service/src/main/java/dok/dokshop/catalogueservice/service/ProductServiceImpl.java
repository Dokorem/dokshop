package dok.dokshop.catalogueservice.service;

import dok.dokshop.catalogueservice.entity.Product;
import dok.dokshop.catalogueservice.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Iterable<Product> findAllProducts(String filter) {
        if(filter != null && !filter.isBlank()) {
            return productRepository.findAllByNameLikeIgnoreCase("%" + filter + "%");
        } else {
            return productRepository.findAll();
        }
    }

    @Override
    @Transactional
    public Product createNewProduct(String name, String description, Double price) {
        return this.productRepository.save(new Product(null, name, description, price));
    }

    @Override
    public Optional<Product> findProductById(int productId) {
        return this.productRepository.findById(productId);
    }

    @Override
    @Transactional
    public void editProduct(Integer productId, String name, String description, Double price) {
        this.productRepository.findById(productId)
                .ifPresentOrElse(product -> {
                    product.setName(name);
                    product.setDescription(description);
                    product.setPrice(price);
                }, () -> {
                    throw new NoSuchElementException("Product not found");
                });
    }

    @Override
    @Transactional
    public void deleteProduct(int productId) {
        this.productRepository.deleteById(productId);
    }
}
