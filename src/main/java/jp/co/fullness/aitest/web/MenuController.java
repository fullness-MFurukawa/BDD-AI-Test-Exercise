package jp.co.fullness.aitest.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MenuController {

    /** セール機能の表示フラグ(演習-06の答え合わせ時のみ有効化する) */
    @Value("${app.sale-enabled:false}")
    private boolean saleEnabled;

    @GetMapping("/")
    public String showMenu(Model model) {
        model.addAttribute("saleEnabled", saleEnabled);
        return "menu";
    }
}
