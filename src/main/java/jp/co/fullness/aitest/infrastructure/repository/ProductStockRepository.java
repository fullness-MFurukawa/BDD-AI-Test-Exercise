package jp.co.fullness.aitest.infrastructure.repository;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import jp.co.fullness.aitest.infrastructure.entity.ProductStock;

/**
 * 商品在庫をアクセスするリポジトリ
 */
public interface ProductStockRepository extends JpaRepository<ProductStock, Integer>{
    /**
     * 在庫UUIDで1件取得する
     *
     * @param stiockUuid 在庫UUID
     * @return 商品在庫（存在しない場合はempty）
     */
    Optional<ProductStock> findByStockUuid(UUID stockUuid);

    /**
     * 商品Idで1件取得する
     *
     * @param productId 商品Id
     * @return 商品在庫（存在しない場合はempty）
     */
    Optional<ProductStock> findByProductId(Integer productId);
}