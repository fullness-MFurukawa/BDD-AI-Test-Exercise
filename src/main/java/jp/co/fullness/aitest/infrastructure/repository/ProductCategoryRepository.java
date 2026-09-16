package jp.co.fullness.aitest.infrastructure.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import jp.co.fullness.aitest.infrastructure.entity.ProductCategory;

/**
 * 商品カテゴリをアクセスするリポジトリ
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer>  {
    /**
     * カテゴリUUIDで1件取得する
     *
     * @param categoryUuid カテゴリUUID
     * @return 商品カテゴリ（存在しない場合はempty）
     */
    Optional<ProductCategory> findByCategoryUuid(UUID categoryUuid);
}