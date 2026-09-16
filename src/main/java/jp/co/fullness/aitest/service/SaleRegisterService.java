package jp.co.fullness.aitest.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import jp.co.fullness.aitest.infrastructure.entity.Product;
import jp.co.fullness.aitest.infrastructure.entity.Sale;
import jp.co.fullness.aitest.infrastructure.repository.ProductRepository;
import jp.co.fullness.aitest.infrastructure.repository.SaleRepository;
import lombok.RequiredArgsConstructor;

/**
 * セール登録サービスクラス
 */
@Service
@RequiredArgsConstructor
public class SaleRegisterService {
    // 商品リポジトリ
    private final ProductRepository productRepository;
    // セールリポジトリ
    private final SaleRepository saleRepository;

    /**
     * 商品を全件取得する(プルダウン表示用)
     */
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    /**
     * 商品Idで取得する(存在しない場合はnull)
     */
    public Product findProductById(Integer productId) {
        return productRepository.findById(productId).orElse(null);
    }

    /**
     * 同一商品に期間が重複するセールが存在するかを確認する
     */
    public boolean existsOverlap(Integer productId, LocalDate startDate, LocalDate endDate) {
        return saleRepository.existsOverlap(productId, startDate, endDate);
    }

    /**
     * セールを登録する
     */
    @Transactional
    public Integer register(Integer productId, Integer salePrice, LocalDate startDate, LocalDate endDate) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("商品が存在しません: id=" + productId));
        Sale saved = saleRepository.save(
                new Sale(UUID.randomUUID(), salePrice, startDate, endDate, product));
        return saved.getId();
    }

    /**
     * セール一覧を取得する
     */
    public List<SaleListItem> findAllSales() {
        return saleRepository.findAllForList();
    }
}
