package jp.co.fullness.aitest.infrastructure.entity;

import java.util.UUID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 商品在庫を表すJPAエンティティクラス
 */
@Entity
@Table(name = "product_stock")
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class ProductStock {
    
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "stock_uuid", nullable = false, unique = true)
    private UUID stockUuid;

    private Integer stock;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public ProductStock(UUID stockUuid, Integer stock, Product product) {
        this.stockUuid = stockUuid;
        this.stock = stock;
        this.product = product;
    }
}
