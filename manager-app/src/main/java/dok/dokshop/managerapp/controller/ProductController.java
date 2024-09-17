package dok.dokshop.managerapp.controller;

import dok.dokshop.managerapp.client.ProductsRestClient;
import dok.dokshop.managerapp.client.exceptions.BadRequestException;
import dok.dokshop.managerapp.controller.payloads.EditProductPayload;
import dok.dokshop.managerapp.entity.Product;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("catalogue/products/{productId:\\d+}")
@RequiredArgsConstructor
public class ProductController {

    private final ProductsRestClient productsRestClient;

    private final MessageSource messageSource;

    @ModelAttribute(name = "product")
    public Product getProduct(@PathVariable("productId") int productId) {
        return this.productsRestClient.findProductById(productId)
                .orElseThrow(() -> new NoSuchElementException("error.productNotFound"));
    }

    @GetMapping
    public String getProduct() {
        return "/catalogue/products/productDetails";
    }


    @GetMapping("/edit")
    public String getProductEditPage() {
        return "/catalogue/products/productEdit";
    }

    @PatchMapping("/edit")
    public String updateProduct(@ModelAttribute(name = "product", binding = false) Product product,
                                EditProductPayload payload, Model model) {
        try {
            Integer productId = product.id();
            productsRestClient.updateProduct(productId, payload.name(), payload.description(), payload.price());
            return "redirect:/catalogue/products/" + productId;
        } catch (BadRequestException e) {
            model.addAttribute("payload", payload);
            model.addAttribute("error", e.getErrors());
            return "/catalogue/products/productEdit";
        }
    }

    @DeleteMapping("/delete")
    public String deleteProduct(@PathVariable("productId") int productId) {
        productsRestClient.deleteProduct(productId);
        return "redirect:/catalogue/products/list";
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String handleNoSuchElementException(NoSuchElementException e, Model model,
                                               HttpServletResponse response, Locale locale) {
        response.setStatus(HttpStatus.NOT_FOUND.value());
        model.addAttribute("error",
                this.messageSource.getMessage(e.getMessage(), new Object[0],
                        e.getMessage(), locale));
        return "errors/404";
    }

}
