package jp.co.fullness.aitest.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import jp.co.fullness.aitest.infrastructure.entity.Product;
import jp.co.fullness.aitest.infrastructure.entity.ProductCategory;
import jp.co.fullness.aitest.infrastructure.entity.ProductStock;
import jp.co.fullness.aitest.infrastructure.repository.ProductCategoryRepository;
import jp.co.fullness.aitest.infrastructure.repository.ProductRepository;
import jp.co.fullness.aitest.infrastructure.repository.ProductStockRepository;
import lombok.RequiredArgsConstructor;

/**
 * 商品登録サービスクラス
 */
@Service
@RequiredArgsConstructor
public class ProductRegisterService {
    // 商品リポジトリ
    private final ProductRepository productRepository;
    // 商品在庫リポジトリ
    private final ProductStockRepository productStockRepository;
    // 商品カテゴリリポジトリ（追加）
    private final ProductCategoryRepository productCategoryRepository;

    /**
     * 商品カテゴリを全件取得する
     */
    public List<ProductCategory> findAllCategories() {
        return productCategoryRepository.findAll();
    }

    /**
     * 商品カテゴリIDで取得する（存在しない場合は例外）
     */
    public ProductCategory findCategoryById(Integer categoryId) {
        return productCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found. id=" + categoryId));
    }

     /**
     * 商品を登録した後、初期在庫を登録する。
     * どちらかが失敗したら全体をロールバックする。
     */
    @Transactional
    public Integer register(ProductRegisterCommand command) {
        UUID productUuid = command.productUuid() != null ? command.productUuid() : UUID.randomUUID();

         // categoryId(Integer) でカテゴリ取得
        ProductCategory category = productCategoryRepository.findById(command.categoryId())
            .orElseThrow(() -> new IllegalArgumentException("カテゴリが存在しません: id=" + command.categoryId()));

        // 商品登録（category を渡す）
        Product savedProduct = productRepository.save(
            new Product(productUuid, command.name(), command.price(), category)
        );

        // 在庫登録（product を渡す）
        productStockRepository.save(
            new ProductStock(UUID.randomUUID(), command.initialStock(), savedProduct)
        );

        return savedProduct.getId();
    }

    /**
     * 既に同じ商品が存在するかを確認する
     * @param name 商品名
     * @return true:存在する false:存在しない
     */
    public boolean existsProductName(String name) {
        return productRepository.existsByName(name);
    }
}