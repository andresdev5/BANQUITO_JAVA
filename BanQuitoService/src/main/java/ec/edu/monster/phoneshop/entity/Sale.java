package ec.edu.monster.phoneshop.entity;

import ec.edu.monster.banquito.entity.User;
import ec.edu.monster.phoneshop.enums.PurchaseMethod;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sales")
@EntityListeners(AuditingEntityListener.class)
public class Sale {
    @Id
    @GeneratedValue
    private UUID id;

    @SequenceGenerator(name = "sale_number_seq", sequenceName = "sale_number_seq", allocationSize = 1)
    @Column(nullable = false, columnDefinition = "SERIAL", unique = true, insertable = false, updatable = false)
    private BigInteger number;

    @Column(unique = true, updatable = false, insertable = false)
    private String reference;

    @OneToMany(mappedBy = "sale", cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<SaleDetail> details;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private BigDecimal subtotal;

    @Column(nullable = false)
    private BigDecimal total;

    @Column(nullable = false)
    private BigDecimal taxPercentage;

    @Column(nullable = false)
    private BigDecimal taxAmount;

    @Column(nullable = false)
    private PurchaseMethod purchaseMethod;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
