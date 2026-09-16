package jp.co.fullness.aitest.web;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * 商品登録ユースケース用ViewModel
 */
@Getter
@Setter
public class ProductRegisterViewModel {
    @NotBlank(message = "商品名は必須です。")
    @Size(max = 30, message = "商品名は30文字以内で入力してください。")
    private String name;

    @NotNull(message = "単価は必須です。")
    @Min(value = 0, message = "単価は0以上で入力してください。")
    private Integer price;

    @NotNull(message = "カテゴリは必須です。")
    private Integer categoryId;
    // 表示用
    private String categoryName;

    @NotNull(message = "初期在庫は必須です。")
    @Min(value = 0, message = "初期在庫は0以上で入力してください。")
    private Integer initialStock;
}
