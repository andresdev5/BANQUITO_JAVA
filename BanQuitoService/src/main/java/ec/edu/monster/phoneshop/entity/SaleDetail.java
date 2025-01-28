package ec.edu.monster.phoneshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sale_details")
@EntityListeners(AuditingEntityListener.class)
public class SaleDetail {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "sale_id", nullable = false)
    @JsonIgnore
    @ToString.Exclude
    private Sale sale;

    @ManyToOne(fetch = FetchType.EAGER)
    private Product product;

    private Integer quantity;

    private BigDecimal unitPrice;

    private LocalDateTime createdAt;
}
