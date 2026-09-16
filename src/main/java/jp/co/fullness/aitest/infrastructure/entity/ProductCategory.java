package jp.co.fullness.aitest.infrastructure.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 商品カテゴリを表すJPAエンティティクラス
 */
@Entity
@Table(name = "product_category")
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class ProductCategory {
    /**
     * Id(主キー)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * カテゴリUUID
     */
    @Column(name = "category_uuid", nullable = false)
    private UUID categoryUuid;

    /**
     * カテゴリ名
     */
    @Column(length = 20)
    private String name;

    /**
     * コンストラクタ（業務用）
     *
     * @param categoryUuid カテゴリUUID
     * @param name         カテゴリ名
     */
    public ProductCategory(UUID categoryUuid, String name) {
        this.categoryUuid = categoryUuid;
        this.name = name;
    }
}