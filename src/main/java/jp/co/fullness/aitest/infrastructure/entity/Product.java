package jp.co.fullness.aitest.infrastructure.entity;

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
 * 商品を表すJPAエンティティクラス
 */
@Entity
@Table(name = "product")
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Product {
    /**
     * Id(主キー)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 商品Id(UUID)
     */
    @Column(name = "product_uuid", nullable = false)
    private UUID productUuid;
    /**
     * 商品名
     */
    @Column(length = 30)
    private String name;
    /**
     * 単価
     */
    private Integer price;
    /**
     * 商品カテゴリId
     */
    //@Column(name = "category_id")
    //private Integer categoryId;
    
    /**
     * 商品カテゴリ 結合データの取得
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private ProductCategory category;


    /**
     * コンストラクタ(業務用コンストラクタ)
     * @param productUuid
     * @param name
     * @param price
     * @param categoryId
     */
    public Product(UUID productUuid, String name, Integer price, ProductCategory category) {
        this.productUuid = productUuid;
        this.name = name;
        this.price = price;
        this.category = category;
    }

}