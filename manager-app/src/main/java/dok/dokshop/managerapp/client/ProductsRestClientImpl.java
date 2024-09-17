package dok.dokshop.managerapp.client;

import dok.dokshop.managerapp.client.exceptions.BadRequestException;
import dok.dokshop.managerapp.controller.payloads.EditProductPayload;
import dok.dokshop.managerapp.controller.payloads.NewProductPayload;
import dok.dokshop.managerapp.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
public class ProductsRestClientImpl implements ProductsRestClient {

    private static final ParameterizedTypeReference<List<Product>> PRODUCTS_TYPE_REFERENCE =
            new ParameterizedTypeReference<>() {};

    private final RestClient restClient;

    @Override
    public List<Product> findAllProducts(String filter) {
        return restClient
                .get()
                .uri("/catalogue-api/products?filter={filter}", filter)
                .retrieve()
                .body(PRODUCTS_TYPE_REFERENCE);
    }

    @Override
    public Optional<Product> findProductById(int productId) {
        try {
            return Optional.ofNullable(restClient
                    .get()
                    .uri("/catalogue-api/products/{productId}", productId)
                    .retrieve()
                    .body(Product.class));
        } catch (HttpClientErrorException.NotFound exception) {
            return Optional.empty();
        }
    }

    @Override
    public Product createProduct(String name, String description, double price) {
        try {
            return restClient
                    .post()
                    .uri("/catalogue-api/products/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new NewProductPayload(name, description, price))
                    .retrieve()
                    .body(Product.class);
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));
        }
    }

    @Override
    public void updateProduct(int productId, String name, String description, double price) {
        try {
            restClient
                .patch()
                .uri("/catalogue-api/products/{productId}/edit", productId)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new EditProductPayload(name, description, price))
                .retrieve()
                .toBodilessEntity();
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));
        }
    }

    @Override
    public void deleteProduct(int productId) {
        try {
            restClient
                    .delete()
                    .uri("/catalogue-api/products/{productId}/delete", productId)
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.NotFound exception) {
            throw new NoSuchElementException();
        }
    }
}
