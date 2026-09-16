package jp.co.fullness.aitest.web;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * セール登録ユースケース用ViewModel
 */
@Getter
@Setter
public class SaleRegisterViewModel {
    @NotNull(message = "対象商品を選択してください")
    private Integer productId;

    @NotNull(message = "セール価格を入力してください")
    @Min(value = 1, message = "セール価格は0より大きい値を入力してください")
    private Integer salePrice;

    @NotNull(message = "開始日を入力してください")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;

    @NotNull(message = "終了日を入力してください")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;
}
