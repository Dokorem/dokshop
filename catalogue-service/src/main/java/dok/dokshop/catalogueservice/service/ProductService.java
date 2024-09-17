package dok.dokshop.catalogueservice.service;

import dok.dokshop.catalogueservice.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Iterable<Product> findAllProducts(String filter);

    Product createNewProduct(String name, String description, Double price);

    Optional<Product> findProductById(int productId);

    void editProduct(Integer productId, String name, String description, Double price);

    void deleteProduct(int productId);
}
