package dok.dokshop.managerapp.controller;

import dok.dokshop.managerapp.client.ProductsRestClient;
import dok.dokshop.managerapp.client.exceptions.BadRequestException;
import dok.dokshop.managerapp.controller.payloads.NewProductPayload;
import dok.dokshop.managerapp.entity.Product;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("catalogue/products")
@RequiredArgsConstructor
public class ProductsController {

    private final ProductsRestClient productsRestClient;

    @GetMapping("list")
    public String getProductsList(@RequestParam(name = "filter", required = false) String filter,  Model model) {
        model.addAttribute("filter", filter);
        model.addAttribute("products", this.productsRestClient.findAllProducts(filter));

        return "catalogue/products/list";
    }

    @GetMapping("/createProduct")
    public String getNewProductPage() {
        return "catalogue/products/new_product";
    }

    @PostMapping("/createProduct")
    public String createProduct(NewProductPayload payload, Model model) {
        try {
            Product product = this.productsRestClient.createProduct(payload.name(), payload.description(), payload.price());
            return "redirect:/catalogue/products/%d".formatted(product.id());
        } catch (BadRequestException e) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", e.getErrors());
            return "catalogue/products/new_product";
        }
    }

}
