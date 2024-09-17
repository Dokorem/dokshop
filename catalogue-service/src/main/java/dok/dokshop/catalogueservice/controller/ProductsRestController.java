package dok.dokshop.catalogueservice.controller;

import dok.dokshop.catalogueservice.controller.payloads.NewProductPayload;
import dok.dokshop.catalogueservice.entity.Product;
import dok.dokshop.catalogueservice.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/catalogue-api/products")
@RequiredArgsConstructor
public class ProductsRestController {

    private final ProductService productService;

    @GetMapping
    public Iterable<Product> getProductList(@RequestParam(name = "filter", required = false) String filter) {
        return this.productService.findAllProducts(filter);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@Valid @RequestBody NewProductPayload payload,
                                           BindingResult bindingResult,
                                           UriComponentsBuilder uriComponentsBuilder) throws BindException {
        if(bindingResult.hasErrors()) {
            if(bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            Product product = productService.createNewProduct(payload.name(), payload.description(), payload.price());
            return ResponseEntity.created(uriComponentsBuilder
                        .replacePath("/catalogue-api/products/{productId}")
                        .build(Map.of("productId", product.getId())))
                    .body(product);
        }
    }

}
