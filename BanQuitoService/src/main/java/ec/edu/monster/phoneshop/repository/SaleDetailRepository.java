package ec.edu.monster.phoneshop.repository;

import ec.edu.monster.phoneshop.entity.SaleDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SaleDetailRepository extends JpaRepository<SaleDetail, UUID> {}
