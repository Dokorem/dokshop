package dok.dokshop.catalogueservice.controller.payloads;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NewProductPayload(

        @NotNull(message = "{errors.product_creating.name_is_null}")
        @Size(min = 3, max = 50, message="{errors.product_creating.name_size_is_invalid}")
        String name,

        @Size(max = 1000, message="{errors.product_creating.description_size_is_invalid}")
        String description,

        @NotNull(message = "{errors.product_creating.price_is_null}")
        Double price
    ) {
}
