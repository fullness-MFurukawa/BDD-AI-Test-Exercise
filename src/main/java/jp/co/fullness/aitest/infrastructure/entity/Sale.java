package jp.co.fullness.aitest.infrastructure.entity;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * セールを表すJPAエンティティクラス
 */
@Entity
@Table(name = "sale")
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Sale {
    /**
     * Id(主キー)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * セールId(UUID)
     */
    @Column(name = "sale_uuid", nullable = false)
    private UUID saleUuid;
    /**
     * セール価格
     */
    @Column(name = "sale_price", nullable = false)
    private Integer salePrice;
    /**
     * 開始日
     */
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;
    /**
     * 終了日
     */
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;
    /**
     * 対象商品 結合データの取得
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    /**
     * コンストラクタ(業務用コンストラクタ)
     */
    public Sale(UUID saleUuid, Integer salePrice, LocalDate startDate, LocalDate endDate, Product product) {
        this.saleUuid = saleUuid;
        this.salePrice = salePrice;
        this.startDate = startDate;
        this.endDate = endDate;
        this.product = product;
    }
}