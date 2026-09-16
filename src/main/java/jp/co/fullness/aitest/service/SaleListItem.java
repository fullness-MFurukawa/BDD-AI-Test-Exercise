package jp.co.fullness.aitest.service;

import java.time.LocalDate;

/**
 * セール一覧用DTO
 */
public record SaleListItem(
        String productName,
        Integer price,
        Integer salePrice,
        LocalDate startDate,
        LocalDate endDate
) {}