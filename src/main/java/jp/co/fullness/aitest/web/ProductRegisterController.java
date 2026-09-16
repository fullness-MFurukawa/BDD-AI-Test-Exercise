package jp.co.fullness.aitest.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import jp.co.fullness.aitest.infrastructure.entity.ProductCategory;
import jp.co.fullness.aitest.service.ProductRegisterCommand;
import jp.co.fullness.aitest.service.ProductRegisterService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/products")
@SessionAttributes({"categories", "productRegisterViewModel"})
public class ProductRegisterController {

    private final ProductRegisterService productRegisterService;

    @ModelAttribute("productRegisterViewModel")
    public ProductRegisterViewModel setUp(){
        return new ProductRegisterViewModel();
    }

    /**
     * 入力画面の表示
     * @param model
     * @return
     */
    @GetMapping("/new")
    public String showInput(Model model) {
        List<ProductCategory> categories = productRegisterService.findAllCategories();
        model.addAttribute("categories", categories); 
        return "products/new";
    }

    /**
     * [完了]クリック → 入力値検証 → OKなら確認画面、NGなら入力画面に戻す
     */
    @PostMapping("/confirm")
    public String confirm(
            @Valid @ModelAttribute("productRegisterViewModel") ProductRegisterViewModel form,
            BindingResult bindingResult,
            Model model) {
        // バリデーション結果のチェック
        if (bindingResult.hasErrors()) {
            // categoriesは@SessionAttributesにあるが、切れている/初回POST直叩き対策で補充しておくと安全
            if (!model.containsAttribute("categories")) {
                model.addAttribute("categories", productRegisterService.findAllCategories());
            }
            return "products/new";
        }

        // 重複チェック
        if (productRegisterService.existsProductName(form.getName())) {
            bindingResult.rejectValue(
                "name","duplicate",
                "同じ商品名が既に登録されています。別の商品名を入力してください。");
            if (!model.containsAttribute("categories")) {
                model.addAttribute("categories", productRegisterService.findAllCategories());
            }
            return "products/new";
        }
        
        // カテゴリ名を取得して画面表示用にセット
        String categoryName = productRegisterService.findCategoryById(form.getCategoryId()).getName();
        form.setCategoryName(categoryName);
        return "products/confirm";
    }

    @PostMapping("/register")
    public String register(
        @ModelAttribute("productRegisterViewModel") ProductRegisterViewModel form,
        SessionStatus sessionStatus,
        RedirectAttributes redirectAttributes) {
        // 商品登録（Service 呼び出し）
        Integer productId = productRegisterService.register(
                new ProductRegisterCommand(
                    null, // UUIDはService側で生成
                    form.getName(),
                    form.getPrice(),
                    form.getCategoryId(),
                    form.getInitialStock()
                )
        );

        // SessionAttributes をクリア（入力情報の破棄）
        sessionStatus.setComplete();
        // 完了画面で表示するためにリダイレクト属性を設定
        redirectAttributes.addFlashAttribute("productId", productId);

        // PRGパターン：Redirect
        return "redirect:/products/complete";
    }

    @GetMapping("/complete")
    public String showComplete(@ModelAttribute("productId") Integer productId) {
        if (productId == null) {
            return "redirect:/products/new";
        }
        return "products/complete";
    }
}
