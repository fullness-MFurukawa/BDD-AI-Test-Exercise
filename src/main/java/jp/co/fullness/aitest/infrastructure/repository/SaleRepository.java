package jp.co.fullness.aitest.infrastructure.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jp.co.fullness.aitest.infrastructure.entity.Sale;
import jp.co.fullness.aitest.service.SaleListItem;

/**
 * セールをアクセスするリポジトリ
 */
public interface SaleRepository extends JpaRepository<Sale, Integer> {

    /**
     * 同一商品に期間が重複するセールが存在するかを確認する
     * 重複 = 期間に共通する日が1日以上あること(一部の重複を含む)
     * @param productId 商品Id
     * @param startDate 開始日
     * @param endDate 終了日
     * @return true:存在する false:存在しない
     */
    @Query("""
        select count(s) > 0
        from Sale s
        where s.product.id = :productId
          and s.startDate <= :endDate
          and s.endDate >= :startDate
    """)
    boolean existsOverlap(@Param("productId") Integer productId,
                          @Param("startDate") LocalDate startDate,
                          @Param("endDate") LocalDate endDate);

    /**
     * セール一覧を取得する
     * @return セール一覧
     */
    @Query("""
        select new jp.co.fullness.aitest.service.SaleListItem(
            p.name,
            p.price,
            s.salePrice,
            s.startDate,
            s.endDate
        )
        from Sale s
        join s.product p
        order by s.startDate, p.id
    """)
    List<SaleListItem> findAllForList();
}
