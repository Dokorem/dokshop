package dok.dokshop.catalogueservice.controller;

import dok.dokshop.catalogueservice.controller.payloads.EditProductPayload;
import dok.dokshop.catalogueservice.entity.Product;
import dok.dokshop.catalogueservice.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/catalogue-api/products/{productId:\\d+}")
@RequiredArgsConstructor
public class ProductRestController {

    private final ProductService productService;

    private final MessageSource messageSource;

    @ModelAttribute("product")
    private Product getProduct(@PathVariable("productId") int productId) {
        return productService.findProductById(productId)
                .orElseThrow(() -> new NoSuchElementException("errors.productNotFound"));
    }

    @GetMapping
    public Product findProduct(@ModelAttribute("product") Product product) {
        return product;
    }

    @PatchMapping("/edit")
    public ResponseEntity<?> updateProduct(@PathVariable("productId") int productId,
                                        @Valid @RequestBody EditProductPayload payload,
                                        BindingResult bindingResult) throws BindException {
        if(bindingResult.hasErrors()) {
            if(bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            productService.editProduct(productId, payload.name(), payload.description(), payload.price());
            return ResponseEntity.noContent()
                    .build();
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteProduct(@PathVariable("productId") int productId,
                                           BindingResult bindingResult) throws BindException {
        if(bindingResult.hasErrors()) {
            if(bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            productService.deleteProduct(productId);
            return ResponseEntity.noContent()
                    .build();
        }
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ProblemDetail> handleNoSuchElementException(NoSuchElementException e, Locale locale) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
                        messageSource.getMessage(e.getMessage(), new Object[0],
                                e.getMessage(), locale)));
    }

}
