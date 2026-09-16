package jp.co.fullness.aitest.service;

import java.util.UUID;

public record ProductRegisterCommand(
        UUID productUuid,
        String name,
        Integer price,
        Integer categoryId,
        Integer initialStock) {
}