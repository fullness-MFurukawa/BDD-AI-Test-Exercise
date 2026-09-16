package jp.co.fullness.aitest.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.fullness.aitest.service.ProductKeywordSearchService;
import jp.co.fullness.aitest.service.ProductSearchResult;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductSearchController {

    private final ProductKeywordSearchService service;

    @GetMapping("/search")
    public String showSearch(
            @RequestParam(name = "keyword", required = false) String keyword,
            Model model
    ) {
        String normalized = keyword == null ? "" : keyword.trim();
        model.addAttribute("keyword", normalized);

        // 初期表示は空一覧（または全件表示にしたいなら条件を変える）
        List<ProductSearchResult> results =
                normalized.isBlank() ? List.of() : service.search(normalized);

        model.addAttribute("results", results);
        model.addAttribute("resultCount", results.size());

        return "products/search";
    }
}
