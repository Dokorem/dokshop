package dok.dokshop.managerapp.client;

import dok.dokshop.managerapp.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductsRestClient {

    List<Product> findAllProducts(String filter);

    Optional<Product> findProductById(int productId);

    Product createProduct(String name, String description, double price);

    void updateProduct(int productId, String name, String description, double price);

    void deleteProduct(int productId);

}
