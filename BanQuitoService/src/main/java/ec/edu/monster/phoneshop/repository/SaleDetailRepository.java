package ec.edu.monster.phoneshop.repository;

import ec.edu.monster.phoneshop.entity.Sale;
import ec.edu.monster.phoneshop.entity.SaleDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SaleDetailRepository extends JpaRepository<SaleDetail, UUID> {

    @Query("SELECT s " +
            "FROM SaleDetail sd " +
            "JOIN Sale s ON(s.id = sd.id) " +
            "WHERE sd.product.id = :productId")
    List<Sale> getAllSalesByProductId(UUID productId);

    void deleteAllByProductId(UUID productId);
}
