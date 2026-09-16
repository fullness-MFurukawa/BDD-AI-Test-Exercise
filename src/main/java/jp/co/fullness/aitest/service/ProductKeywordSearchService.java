package jp.co.fullness.aitest.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.fullness.aitest.infrastructure.repository.ProductRepository;
import lombok.RequiredArgsConstructor;

/**
 * 商品キーワード検索サービスクラス
 */
@Service
@RequiredArgsConstructor
public class ProductKeywordSearchService {

    // 商品リポジトリ
    private final ProductRepository productRepository;

    /**
     * 商品キーワード検索
     * @param keyword キーワード
     * @return
     */
    @Transactional(readOnly=true)
    public List<ProductSearchResult> search(String keyword) {
        return productRepository.searchByNameKeywordWithCategoryAndStock(keyword);
    }
}
