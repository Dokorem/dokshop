package dok.dokshop.catalogueservice.controller.payloads;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record EditProductPayload(
        @NotNull(message = "{errors.product_updating.name_is_null}")
        @Size(min = 3, max = 50, message="{errors.product_updating.name_size_is_invalid}")
        String name,

        @Size(max = 1000, message="{errors.product_updating.description_size_is_invalid}")
        String description,

        @NotNull(message = "{errors.product_updating.price_is_null}")
        Double price
    ) {
}
