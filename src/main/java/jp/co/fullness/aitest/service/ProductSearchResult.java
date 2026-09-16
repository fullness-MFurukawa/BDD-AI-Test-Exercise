package jp.co.fullness.aitest.service;
/**
 * 商品キーワード検索用DTO
 */
public record ProductSearchResult(
        Integer productId,
        String name,
        Integer price,
        String categoryName,
        Integer stock
) {}