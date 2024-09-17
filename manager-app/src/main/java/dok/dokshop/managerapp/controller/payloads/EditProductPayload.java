package dok.dokshop.managerapp.controller.payloads;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record EditProductPayload(String name, String description, Double price) {
}
