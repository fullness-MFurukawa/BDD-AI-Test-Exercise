package jp.co.fullness.aitest.infrastructure.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jp.co.fullness.aitest.infrastructure.entity.Product;
import jp.co.fullness.aitest.service.ProductSearchResult;

/**
 * 商品をアクセスするリポジトリ
 */
public interface ProductRepository extends JpaRepository<Product, Integer> {

    /**
     * 商品UUIDで1件取得する
     *
     * @param productUuid 商品UUID
     * @return 商品（存在しない場合はempty）
     */
    Optional<Product> findByProductUuid(UUID productUuid);

    /**
     * カテゴリIdでn件取得する
     *
     * @param categoryId カテゴリId
     * @return 商品のリスト
     */
    List<Product> findByCategoryId(Integer categoryId);

    /**
     * 商品が存在するかを確認する
     * @param name 商品名
     * @return true:存在する false:存在しない
     */
    boolean existsByName(String name);


    /**
     * 商品キーワード検索
     * @param keyword キーワード
     * @return 検索結果
     */
    @Query("""
        select new jp.co.fullness.aitest.service.ProductSearchResult(
            p.id,
            p.name,
            p.price,
            c.name,
            s.stock
        )
        from Product p
        join p.category c
        join ProductStock s on s.product = p
        where (:keyword is null or :keyword = '' or lower(p.name) like lower(concat('%', :keyword, '%')))
        order by p.id
    """)
    List<ProductSearchResult> searchByNameKeywordWithCategoryAndStock(@Param("keyword") String keyword);
}