package dok.dokshop.catalogueservice.repository;

import dok.dokshop.catalogueservice.entity.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product, Integer> {

    @Query("SELECT p FROM Product p WHERE p.name ILIKE :filter")
    Iterable<Product> findAllByNameLikeIgnoreCase(String filter);

}
