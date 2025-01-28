package ec.edu.monster.phoneshop.repository;

import ec.edu.monster.phoneshop.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SaleRepository extends JpaRepository<Sale, UUID> {
    @Query(value = "SELECT last_value FROM sales_number_seq", nativeQuery = true)
    Long getLastSaleNumber();
}
