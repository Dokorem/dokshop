package dok.dokshop.managerapp.repository;

import dok.dokshop.managerapp.entity.DokShopUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface DokShopUserRepository extends CrudRepository<DokShopUser, Integer> {

    Optional<DokShopUser> findByUsername(String username);

}
