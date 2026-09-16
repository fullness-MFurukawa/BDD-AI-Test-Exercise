package jp.co.fullness.aitest.web;

import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import jp.co.fullness.aitest.infrastructure.entity.Product;
import jp.co.fullness.aitest.service.SaleRegisterService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/sales")
public class SaleController {

    private final SaleRegisterService saleRegisterService;

    @ModelAttribute("saleRegisterViewModel")
    public SaleRegisterViewModel setUp() {
        return new SaleRegisterViewModel();
    }

    /**
     * 入力画面の表示
     */
    @GetMapping("/new")
    public String showInput(Model model) {
        model.addAttribute("products", saleRegisterService.findAllProducts());
        return "sales/new";
    }

    /**
     * [登録]クリック → 入力値検証 → OKなら登録して完了メッセージ、NGなら入力画面に戻す
     */
    @PostMapping("/register")
    public String register(
            @Valid @ModelAttribute("saleRegisterViewModel") SaleRegisterViewModel form,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        LocalDate today = LocalDate.now();

        // 相関チェック(入力されている項目のみ対象)
        Product product = null;
        if (form.getProductId() != null) {
            product = saleRegisterService.findProductById(form.getProductId());
            if (product == null) {
                bindingResult.rejectValue("productId", "notFound", "対象商品を選択してください");
            }
        }
        // セール価格は対象商品の単価より低いこと
        if (product != null && form.getSalePrice() != null
                && !bindingResult.hasFieldErrors("salePrice")
                && form.getSalePrice() >= product.getPrice()) {
            bindingResult.rejectValue("salePrice", "notLowerThanPrice",
                    "セール価格は単価より低い値を入力してください");
        }
        // 開始日は当日以降であること
        if (form.getStartDate() != null && form.getStartDate().isBefore(today)) {
            bindingResult.rejectValue("startDate", "pastDate",
                    "開始日に過去の日付は指定できません");
        }
        // 開始日は終了日以前であること(同日は可)
        if (form.getStartDate() != null && form.getEndDate() != null
                && form.getStartDate().isAfter(form.getEndDate())) {
            bindingResult.rejectValue("startDate", "startAfterEnd",
                    "開始日は終了日以前の日付を入力してください");
        }
        // 期間が重複するセールが存在しないこと(一部の重複を含む)
        if (!bindingResult.hasErrors() && product != null
                && saleRegisterService.existsOverlap(
                        form.getProductId(), form.getStartDate(), form.getEndDate())) {
            bindingResult.rejectValue("productId", "overlap",
                    "この商品には期間が重複するセールが登録されています");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("products", saleRegisterService.findAllProducts());
            return "sales/new";
        }

        saleRegisterService.register(
                form.getProductId(), form.getSalePrice(), form.getStartDate(), form.getEndDate());

        // PRGパターン:完了メッセージを添えて入力画面へ
        redirectAttributes.addFlashAttribute("completeMessage", "セールを登録しました");
        return "redirect:/sales/new";
    }

    /**
     * セール一覧画面の表示
     */
    @GetMapping("/list")
    public String showList(Model model) {
        model.addAttribute("sales", saleRegisterService.findAllSales());
        return "sales/list";
    }
}